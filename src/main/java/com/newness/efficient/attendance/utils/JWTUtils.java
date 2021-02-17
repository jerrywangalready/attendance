package com.newness.efficient.attendance.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

public class JWTUtils {

    private static final String PRIVATE_KEY = "HiOp4*1+Gr2Az=-";

    public static String getToken(Map<String, String> map) {

        JWTCreator.Builder builder = JWT.create();
        map.forEach(builder::withClaim);

        builder.withExpiresAt(getDateOfTomorrow());

        return builder.sign(Algorithm.HMAC256(PRIVATE_KEY));
    }

    public static DecodedJWT verify(String token) {
//        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(PRIVATE_KEY)).build();
//        DecodedJWT decodedJWT = jwtVerifier.verify(token);
//        System.out.println(decodedJWT.getClaim("name").asString());
//        return decodedJWT;
        return JWT.require(Algorithm.HMAC256(PRIVATE_KEY)).build().verify(token);
    }

    private static Date getDateOfTomorrow() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.add(Calendar.DATE, 1);
//        calendar.add(Calendar.SECOND, 20);
//        System.out.println("过期时间" + calendar.getTime());
        return calendar.getTime();
    }


}
