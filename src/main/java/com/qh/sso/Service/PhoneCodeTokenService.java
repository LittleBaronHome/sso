package com.qh.sso.Service;

import com.qh.sso.Enum.TokenType;

import java.util.Map;

public class PhoneCodeTokenService extends BasicTokenService {
    @Override
    protected Map<String, String> generator(String data, String key) throws Exception {
        return super.generator(data, key, String.valueOf(TokenType.PhoneVerificationCode.getCode()));
    }
}
