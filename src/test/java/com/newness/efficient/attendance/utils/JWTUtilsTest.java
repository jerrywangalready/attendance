package com.newness.efficient.attendance.utils;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.newness.efficient.attendance.components.JwtTokenUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class JWTUtilsTest {

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Test
    void getToken() {
        Map<String, String> map = new HashMap<>();
        map.put("name", "lili");
        String token = jwtTokenUtil.getToken(map);
        System.out.println(token);
//
//        Calendar calendar = Calendar.getInstance();
////        calendar.add(Calendar.SECOND, 20);
//        String token = JWT.create()
//                .withClaim("userId", "1")
//                .withExpiresAt(calendar.getTime())
//                .sign(Algorithm.HMAC256("gsdfsg"));
//        System.out.println(token);
//        JWTVerifier verify = JWT.require(Algorithm.HMAC256("gsdfsg")).build();
//        DecodedJWT decodedJWT = verify.verify(token);
//        System.out.println(decodedJWT.getClaim("userId").asString());

    }

    @Test
    void verify() {
        DecodedJWT jwt = jwtTokenUtil.verify("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJuYW1lIjoibGlsaSIsImV4cCI6MTYxMDc5ODQ3OX0.3qdx2jxnAlAanl-yho5yLyUr5wKWZgw7ZkcK87uSbyQ");
        System.out.println(jwt.getClaim("name").toString());
    }
}