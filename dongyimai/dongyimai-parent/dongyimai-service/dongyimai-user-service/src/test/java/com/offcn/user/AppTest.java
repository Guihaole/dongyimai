package com.offcn.user;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
public class AppTest {

    @Test
    public void test1(){
        //        生成 密文
//        $2a$10$Jv4KRAxLI4AR9AEX2nrMduFwl23HDGdb5SeVCbWI7Pc7/FMgtfc/2
//        String hashpw = BCrypt.hashpw("123", BCrypt.gensalt());
//        System.out.println(hashpw);

        //解密
        boolean checkpw = BCrypt.checkpw("123", "$2a$10$Jv4KRAxLI4AR9AEX2nrMduFwl23HDGdb5SeVCbWI7Pc7/FMgtfc/2");
        System.out.println(checkpw);
    }
    @Test
    public void testCreateJwt(){
        JwtBuilder builder= Jwts.builder()
                .setId("888")
                .setSubject("小白")
                .setIssuedAt(new Date())
//                .setExpiration(new Date())//用于设置过期时间，参数为Date类型数据
                .signWith(SignatureAlgorithm.HS256,"guihaole");
        // 自定义数据
        Map<String,Object> map = new HashMap<>();
        map.put("name","中公优就业");
        map.put("address","北京市朝阳区五方桥基地");
        builder.addClaims(map);
        System.out.println(builder.compact());
    }
    @Test
    public void testParseJwt(){
        String compactJwt="eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI4ODgiLCJzdWIiOiLlsI_nmb0iLCJpYXQiOjE2NTk0MjczNzR9.1uUeH9uCY1pFo7jlFIgZmqIObJKgKkzTGE9AtzyyulE";
        Claims claims = Jwts.parser()
                .setSigningKey("guihaole")
                .parseClaimsJws(compactJwt)
                .getBody();
        System.out.println(claims);
    }

}
