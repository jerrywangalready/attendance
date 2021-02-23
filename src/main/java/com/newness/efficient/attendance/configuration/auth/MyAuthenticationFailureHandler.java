package com.newness.efficient.attendance.configuration.auth;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 登录失败操作
 */
@Component
public class MyAuthenticationFailureHandler extends JSONAuthentication implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException e) throws IOException, ServletException {

//        R<String> data = R.failed("登录失败:"+e.getMessage());
        Map<String, String> map = new HashMap<>();
        map.put("message", "登录失败:"+e.getMessage());
        //输出
        this.WriteJSON(request, response, map);
    }
}