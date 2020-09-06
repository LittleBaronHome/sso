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
    @Value("${KeyPair.PublicKey}")
    private String publicKey;

    protected abstract Map<String, String> generator(String data, String key);

    /**
     * 生成token
     * 数据采用外部公钥加密，签名采用内部私钥加密。外部系统用自己私钥解密数据，用本系统公钥解密签名验证
     * 数据再用内部公钥加密一份，用于本系统解析token携带信息
     * @param data 要加密的数据
     * @param key 加密公钥
     * @param tokenType token类型
     * @return token
     */
    Map<String, String> generator(String data, String key, String tokenType) {
        String auxiliary_data = RSAUtil.encrypt(data, RSAUtil.getPublicKey(publicKey));
        String secure_data = RSAUtil.encrypt(data, RSAUtil.getPublicKey(key));
        String secure_sign = RSAUtil.sign(secure_data, RSAUtil.getPrivateKey(privateKey));
        Map<String, Object> header_map = new HashMap<>();

        Map<String, String> result = new HashMap<>();
        result.put("token", JWT.create().withHeader(header_map)
                .withClaim("type", "token")
                .withClaim("data", secure_data)
                .withClaim("auxiliary", auxiliary_data)
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

    /**
     * 验证token
     * JWT内置token验证机制，判断token的完整性
     * 使用内部公钥解析签名，验证token的合法性
     * @param token token
     * @return Result
     */
    public Result verify(String token) {
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
            if (!RSAUtil.verify(data, RSAUtil.getPublicKey(publicKey), signature)) {
                return new Result(ExceptionType.IllegalSignature);
            }
            return new Result(map.get("auxiliary").asString());
        }

        return new Result();
    }

    /**
     * 验证token
     * JWT内置token验证机制，判断token的完整性
     * 使用内部公钥解析签名，验证token的合法性
     * @param token token
     * @return Result
     */
    public Result loadInfoByToken(String token) {
        Result verify_result = verify(token);
        String auxiliary = String.valueOf(verify_result.getData());
        String data = RSAUtil.decrypt(auxiliary, RSAUtil.getPrivateKey(privateKey));
        return new Result(data);
    }
}
