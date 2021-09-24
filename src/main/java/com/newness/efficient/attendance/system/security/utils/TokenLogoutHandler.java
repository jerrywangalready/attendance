package com.newness.efficient.attendance.system.security.utils;

import com.newness.efficient.attendance.components.R;
import com.newness.efficient.attendance.components.ResponseUtil;
import com.newness.efficient.attendance.system.security.token.TokenManager;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//退出处理器
public class TokenLogoutHandler implements LogoutHandler {

    private TokenManager tokenManager;
    private RedisTemplate<String, Object> redisTemplate;

    public TokenLogoutHandler(TokenManager tokenManager, RedisTemplate<String, Object> redisTemplate) {
        this.tokenManager = tokenManager;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        //1 从header里面获取token
        //2 token不为空，移除token，从redis删除token
        String token = request.getHeader("Authentication");
        if(token != null) {
            //移除
            tokenManager.removeToken(token);
            //从token获取用户名
//            String username = tokenManager.getUsernameFromToken(token);
//            redisTemplate.delete(username);
        }
        ResponseUtil.out(response, R.ok());
    }
}
