package com.newness.efficient.attendance.system.security.token;


import java.util.Date;

public interface TokenManager {

    String generateToken(String username);

    String getUsernameFromToken(String token);

    void removeToken(String username);

    /**
     * 验证令牌
     *
     * @param token 令牌
     * @return 是否有效
     */
    Boolean validateToken(String token);

    /**
     * 获取过期时间
     *
     * @param token 令牌
     * @return
     */
    Date getExpireTime(String token);
}
