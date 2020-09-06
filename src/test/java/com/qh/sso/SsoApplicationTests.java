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
//        System.out.println(token.get("token"));
//        System.out.println(token.get("refresh_token"));

        Map<String, String> token = new HashMap<String, String>(){{
            put("token", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJkYXRhIjoiTU81dk9pa1gvSnpCTnJPUFlaWUx6UkExdTl0QXZkY3ZUaUx5ajBnWFVCU0ZPUTA3cllPNjBoSVVPRWRIQnRNWGVPaWZ0K0x3alUwcWNKaG92b256R3JXeXNRMXFaUmtTNFhIcU1UQzlIdThqQi9OZmtNeG5YUkhqL2Fqc3I5a1BLbWVhWXYvUnhiOEpvdVR1Ylo2WHRRdzhFcFRzTkhMLzVQR09UTldnOXpZPSIsImF1eGlsaWFyeSI6IlNCSnN4Y1lRbVRwK1B1OGZLd2pXTnpRNWVzVFNaampzdGh3ZjYrS2k1Z3Fzd2Iwc1BMTVlMVExUNHd6TE5hZ2NkTGRHMDBoMzFmWTI2RG0zQmYwSXpnMlNvT0t1VFZtdll5WFV2VjcwUnFLY3BGenBkQWtvVXZML3J3MXRxTk5JS2kvU0ZIM0R3ZzY2bnQ2SjFjOEM3dTRXMWVwUFEvNStzRmlpcGhHcklkST0iLCJzaWduYXR1cmUiOiJPcnVzdmh1VTRHRzBDNkpqbUsvTkhKK2h6ekd6RERpNEtwS0RiZTB1dFo1RzJKNEdwTHhGQ3VLaGNCNlVBWitkeFZnRUJOclpScHppS3c1TDVHYWRyOXAzVm1VTTA5RFkxTTZTUHdXcFRvdUlGbTBJYm5TeWdQV3A1YmhFUE1DcWZqZGJocEFrbGxqWkZLNUZQU2tMcmdGcTU4OXZTU0djTDU0VmdEbEdtaEE9IiwidHlwZSI6InRva2VuIiwidG9rZW5UeXBlIjoiMSIsImV4cCI6MTU5NzI5NTAxNCwiaWF0IjoxNTk3Mjg3ODE0fQ.Mm6Bj9S6dLcMSH_MxaVxOKdwODsFW_kDVFJ5wHXSQ0U");
            put("refresh_token", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ0eXBlIjoicmVmcmVzaF90b2tlbiIsImV4cCI6MTU5NzM3NDIxNCwiaWF0IjoxNTk3Mjg3ODE0fQ.h9GHm3LNB0wVUPSJDH2UQmq1TDnc5m6i_3y-2ykyxc8");
        }};

        System.out.println(passwordTokenService.verify(token.get("refresh_token")));
        System.out.println(passwordTokenService.loadInfoByToken(token.get("token")));
    }

}
