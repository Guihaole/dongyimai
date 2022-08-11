package com.offcn.outh.test;


import org.junit.jupiter.api.Test;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;

public class ParseJwtTest {

    /***
     * 校验令牌
     */
    @Test
    public void testParseToken(){
        //令牌
        String token = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJyb2xlcyI6IlJPTEVfVklQLFJPTEVfVVNFUiIsIm5hbWUiOiJkb25neWltYWkiLCJpZCI6IjEifQ.XzY0Qu9hQVOtm48jBnbzX_sqY-csMLiFcinmVV--BZ1-0i24a5tHCJFqV1agrYnod0ytgtXqX75znyhrQtTEI0k0ubxDh9cChH_a-RWjup96qe7w2MjZHH1aLH2QSyV2Ae0shYxYruJKpSt0dhuvpV8-zBfgrvmoCD7CGZJ3LbL-ngcPgvGK3xHapb-7Axo_qECsnFAKZdYL8SX3v_35hUn7F9M-eyONtFbmjY_IWlnx0UkYSkqmmN0OL4VMmTQzOOS1xPz3W_KW7voazaNI4lOpk5jGWycCxcpDDC3BK2mDRFmspW2Kx-w4qwRk9YprsZn6kPyJbg56gouKPY_KoA";

        //公钥
        String publickey = "-----BEGIN PUBLIC KEY-----MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAqLES7F+UgqQ1ASHQORjTa4xo5InE8NHEQyTebw/6L3Ur3u5phUO9w8v1sPTq2cxdYk24+79eWWwVfHndCkYfvnXtx44FQSeT/JuPJUMmcSWQMg5/55OnhMeda0cRJQ4rCOdNztG4Bq8Ww72hH1sTKhcNfz1vSvj7ymgZYGhrhta0PPomciWQKvvNNhMlbgwbmJbbLXI+xeu6XjN3LUV4hNDPayk/jVa3yNzK0BcUIxjKM/Q5j7OHURHJvMPlXbDRBV/6Oqge/Z6OJ9uTCLtYZW9M3qKno7HUMkyKv6fF0tULNQzGr4nzvhYnCiqq0UctZQ0+jgQmNx/CG8bjYoMxhQIDAQAB-----END PUBLIC KEY-----";
        //校验Jwt
        Jwt jwt = JwtHelper.decodeAndVerify(token, new RsaVerifier(publickey));

        //获取Jwt原始内容
        String claims = jwt.getClaims();
        System.out.println(claims);
        //jwt令牌
        String encoded = jwt.getEncoded();
        System.out.println(encoded);
    }
}
