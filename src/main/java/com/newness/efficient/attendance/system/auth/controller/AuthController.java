package com.newness.efficient.attendance.system.auth.controller;

import com.newness.efficient.attendance.system.security.token.TokenManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthController {

    @Resource
    private TokenManager tokenManager;

    @PostMapping("/getToken")
    public String getToken(HttpServletRequest request) {
        //从header获取token
        String oldToken = request.getHeader("Authentication");

        String username = tokenManager.getUsernameFromToken(oldToken);
        // 根据用户名生成token
        String token = tokenManager.generateToken(username);
        // 更新redis中的token值

        return token;
    }

    @PostMapping("logout")
    public boolean logout() {
        // todo 更新redis中的jwt秘钥
        return true;
    }


}
