package com.qh.sso.Service;

import com.qh.sso.Enum.TokenType;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class PhoneCodeTokenService extends BasicTokenService {
    @Override
    protected Map<String, String> generator(String data, String key) {
        return super.generator(data, key, String.valueOf(TokenType.PhoneVerificationCode.getCode()));
    }
}
