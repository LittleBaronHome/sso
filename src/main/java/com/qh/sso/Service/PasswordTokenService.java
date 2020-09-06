package com.qh.sso.Service;

import com.qh.sso.Enum.TokenType;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class PasswordTokenService extends BasicTokenService {

    @Override
    public Map<String, String> generator(String data, String key) {
        return super.generator(data, key, String.valueOf(TokenType.Password.getCode()));
    }
}
