package com.qh.sso;

import com.qh.sso.Service.PasswordTokenService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class SsoApplicationTests {

    @Autowired
    private PasswordTokenService passwordTokenService;
    @Value("${QH.PublicKey}")
    private String qhPublicKey;
    @Value("${KeyPair.PublicKey}")
    private String publicKey;

    @Test
    void contextLoads() throws Exception {
//        Map<String, String> token = passwordTokenService.generator("aa,bb,xx", qhPublicKey);
        Map<String, String> token = new HashMap<String, String>(){{
            put("token", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJkYXRhIjoiTkpraEphSzhDNmVYYmF3RUZCVndYY2pGb1JnM1lJVjMrUkh1cyt6REVrUXFoMDJ0RGE1S3hIdHZrVFl4bjcwdG9pNjJWcm1sTmtnWi94eFlNSVZuS1IrUTArZ3hZRHBNWTVoeXQwUEM4ejJYdzVRK2VWeE42QUxRaFFVZWZrNXdiSU5EbW1lU2JrVU5VajZjRUlSSDRmTmZlSG93RjVsa1VrTzg1UTcxdDhnPSIsInNpZ25hdHVyZSI6ImJKSDJmSWNNeXdPV0FFRndnUC9ZSS9GOStQbTFBTTcrZzZkNmRCOEdoT1d2dVI0ajVLOW4yeUJoREdsT0QxME8xOHBzN2ttRWtib0pENzFON2JIM3NtSGJFMTk3YWp6bmJka3RZdlNuVkd3WUpML0E2ZEo2M1o1cGg3bG1YQTVua1hwWEloMmU1L2ZyZ3NDTzhpbmMzYlBHNTBOMXpmZU1UV3VWTmtyNWs5ND0iLCJ0eXBlIjoidG9rZW4iLCJ0b2tlblR5cGUiOiIxIiwiZXhwIjoxNTk0MTc0NzYxLCJpYXQiOjE1OTQxNzQ3MDF9.Lut5ycBUYAF8JMo5-HVb9F95ayrXL34mznokEi9DUts");
            put("refresh_token", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ0eXBlIjoicmVmcmVzaF90b2tlbiIsImV4cCI6MTU5NDE3NTAwMSwiaWF0IjoxNTk0MTc0NzAxfQ.hik0HO-f4HDbz_5m03sEw20bTrPk_Q7dYyGnLuY56Xc");
        }};

//        System.out.println(token.get("token"));
//        System.out.println(token.get("refresh_token"));
        System.out.println(passwordTokenService.verify(token.get("refresh_token"), publicKey));
        System.out.println(passwordTokenService.verify(token.get("token"), publicKey));
    }

}
