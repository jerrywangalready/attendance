package com.newness.efficient.attendance.system.security.token;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class JwtTokenUtil implements TokenManager {

    private static final String PRIVATE_KEY = "HiOp4*1+Gr2Az=-";
    /**
     * token 过期时间: 1天
     */
    public static final int CALENDAR_FIELD = Calendar.DATE;
    public static final int CALENDAR_INTERVAL = 1;


    private String getToken(Map<String, String> map) {

        JWTCreator.Builder builder = JWT.create();
        map.forEach(builder::withClaim);

        builder.withExpiresAt(getExpiresTime());

        return builder.sign(Algorithm.HMAC256(PRIVATE_KEY));
    }

    /**
     * 生成令牌
     *
     * @param username
     * @return
     */
    @Override
    public String generateToken(String username) {
        Map<String, String> map = new HashMap<>();
        map.put("username", username);
        return getToken(map);
    }

    /**
     * 从数据声明生成令牌
     *
     * @param map 数据声明
     * @return 令牌
     */
    private String generateToken(Map<String, String> map) {
        JWTCreator.Builder builder = JWT.create();
        map.forEach(builder::withClaim);

        builder.withExpiresAt(getExpiresTime());

        return builder.sign(Algorithm.HMAC256(PRIVATE_KEY));
    }

    /**
     * 从令牌中获取用户名
     *
     * @param token 令牌
     * @return 用户名
     */
    @Override
    public String getUsernameFromToken(String token) {
        DecodedJWT jwt = null;
        jwt = verify(token);
        return jwt.getClaim("username").asString();
    }

    /**
     * 判断令牌是否过期
     *
     * @param token 令牌
     * @return 是否过期
     */
    public Boolean isTokenExpired(String token) {
        try {
            DecodedJWT verify = verify(token);
            Date expiration = verify.getExpiresAt();
            return expiration.before(new Date());
        } catch (Exception e) {
            log.error("token verify error", e);
        }
        return true;
    }

    /**
     * 刷新令牌
     *
     * @param token 原令牌
     * @return 新令牌
     */
    public String refreshToken(String token) {
        DecodedJWT verify = verify(token);
        Map<String, Claim> claims = verify.getClaims();
        Map<String, String> dataMap = new HashMap<>();
        claims.forEach((key, value) -> dataMap.put(key, value.asString()));
        return getToken(dataMap);
    }

    /**
     * 验证令牌
     *
     * @param token 令牌
     * @return 是否有效
     */
    @Override
    public Boolean validateToken(String token) {
        try {
            DecodedJWT verify = verify(token);
            verify.getClaim("username").asString();
            return true;
        } catch (TokenExpiredException e) {
            log.error("token过期", e);
        } catch (AlgorithmMismatchException e) {
            log.error("token算法不一致", e);
        } catch (Exception e) {
            log.error("无效签名", e);
        }
        return false;
    }

    @Override
    public Date getExpireTime(String token) {
        DecodedJWT verify = verify(token);
        return verify.getExpiresAt();
    }

    /**
     * 从令牌中获取数据声明
     *
     * @param token 令牌
     * @return 数据声明
     */
    private Map<String, Claim> getClaimsFromToken(String token) {
        DecodedJWT verify = verify(token);
        return verify.getClaims();
    }

    public DecodedJWT verify(String token) {
        return JWT.require(Algorithm.HMAC256(PRIVATE_KEY)).build().verify(token);
    }

    /**
     * 强制token失效
     */
    @Override
    public void removeToken(String username) {
        // todo 修改redis中该用户的 jwt key
    }

    private Date getExpiresTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR, 2);
//        calendar.add(Calendar.SECOND, 10);
        return calendar.getTime();
    }


}
