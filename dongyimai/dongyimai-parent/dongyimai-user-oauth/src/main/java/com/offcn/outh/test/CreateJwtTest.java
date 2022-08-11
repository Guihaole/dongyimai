package com.offcn.outh.test;


import com.alibaba.fastjson.JSON;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.RsaSigner;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by travelround on 2021/4/14.
 */
//模拟我们的cmd生成令牌 为令牌加上我们的私钥dongyimai.jks
public class CreateJwtTest {

    /***
     * 创建令牌测试
     * eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.
     * eyJyb2xlcyI6IlJPTEVfVklQLFJPTEVfVVNFUiIsIm5hbWUiOiJkb25neWltYWkiLCJpZCI6IjEifQ.
     * KG__dOSKTq7fUXASjuLh7L3IH7FDDDCYdqPBr1LE-7hQLDDOOTYYDEFac00t180I-gSvrwEsa-Z6R_IOz4mHHTzLWfju8wMt8g0nEgruQ7TJhQTfzap4vtanSOowbHpsKvzHrsRKL749WgMBaXXkXeIfrsTI9HgSqEROey0vIZKrhJNIqQlytJAQxWMs2K2VEgrMyD5LuWXAa0K5imXLW-0vA3FJTqm_1_DRr7dUZfCzNm6xOl_M4muT4mJJ0OjlL92KwgKVat0Og7sR7Ng5BXES75DUggxsYySWhWa6UNvLQ5p3Um1oa39XgUnRij9pwcVrzdE0Jkct8diG-XnFww
     */
    @Test
    public void testCreateToken(){
        //与我们的黑窗口命令一致
        //证书文件路径
        String key_location="dongyimai.jks";
        //秘钥库密码
        String key_password="dongyimai";
        //秘钥密码
        String keypwd = "dongyimai";
        //秘钥别名
        String alias = "dongyimai";

        //访问证书路径
        ClassPathResource resource = new ClassPathResource(key_location);

        //创建秘钥工厂
        //org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(resource,key_password.toCharArray());

        //读取秘钥对(公钥、私钥)
        KeyPair keyPair = keyStoreKeyFactory.getKeyPair(alias,keypwd.toCharArray());

        //获取私钥
        RSAPrivateKey rsaPrivate = (RSAPrivateKey) keyPair.getPrivate();

        //定义Payload
        Map<String, Object> tokenMap = new HashMap<>();
        tokenMap.put("id", "1");
        tokenMap.put("name", "dongyimai");
        tokenMap.put("roles", "ROLE_VIP,ROLE_USER");

        //生成Jwt令牌
        //org.springframework.security.jwt.crypto.sign.RsaSigner;
        Jwt jwt = JwtHelper.encode(JSON.toJSONString(tokenMap), new RsaSigner(rsaPrivate));

        //取出令牌
        String encoded = jwt.getEncoded();
        System.out.println(encoded);
    }
}
