package com.qh.sso.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.qh.sso.Util.RSAUtil;
import com.qh.common.Entry.*;
import com.qh.common.Enum.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public abstract class BasicTokenService {

    @Value("${Token.Signature}")
    private String tokenSignature;
    @Value("${Token.ExpiredTime.Common}")
    private int tokenExpiredMinutes;
    @Value("${Token.ExpiredTime.Refresh}")
    private int refreshTokenExpiredMinutes;

    @Value("${KeyPair.PrivateKey}")
    private String privateKey;

    protected abstract Map<String, String> generator(String data, String key) throws Exception;

    Map<String, String> generator(String data, String key, String tokenType) throws Exception {
        String secure_data = RSAUtil.encrypt(data, RSAUtil.getPublicKey(key));
        String secure_sign = RSAUtil.sign(secure_data, RSAUtil.getPrivateKey(privateKey));
        Map<String, Object> header_map = new HashMap<>();

        Map<String, String> result = new HashMap<>();
        result.put("token", JWT.create().withHeader(header_map)
                .withClaim("type", "token")
                .withClaim("data", secure_data)
                .withClaim("signature", secure_sign)
                .withClaim("tokenType", tokenType)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(new Date().getTime() + tokenExpiredMinutes * 60000))
                .sign(Algorithm.HMAC256(tokenSignature)));
        result.put("refresh_token", JWT.create().withHeader(header_map)
                .withClaim("type", "refresh_token")
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(new Date().getTime() + refreshTokenExpiredMinutes * 60000))
                .sign(Algorithm.HMAC256(tokenSignature)));

        return result;
    }

    public Result verify(String token, String key) throws Exception {
        DecodedJWT jwt;
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(tokenSignature)).build();
            jwt = verifier.verify(token);
        } catch (Exception e) {
            return new Result(e.getClass() == TokenExpiredException.class ? ExceptionType.ExpiredToken : ExceptionType.IllegalToken);
        }

        Map<String, Claim> map = jwt.getClaims();
        String type = map.get("type").asString();
        if (("token").equals(type)) {
            String data = map.get("data").asString();
            String signature = map.get("signature").asString();
            if (!RSAUtil.verify(data, RSAUtil.getPublicKey(key), signature)) {
                return new Result(ExceptionType.IllegalSignature);
            }
        }

        return new Result();
    }
}
