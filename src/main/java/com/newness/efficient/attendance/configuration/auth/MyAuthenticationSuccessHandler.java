package com.newness.efficient.attendance.configuration.auth;

import com.newness.efficient.attendance.auth.entity.SysFrontendMenuTable;
import com.newness.efficient.attendance.auth.service.SysFrontendMenuTableService;
import com.newness.efficient.attendance.components.JwtTokenUtil;
import com.newness.efficient.attendance.components.TokenCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 登录成功操作
 */
@Component
public class MyAuthenticationSuccessHandler extends JSONAuthentication implements AuthenticationSuccessHandler {

    @Autowired
    SysFrontendMenuTableService service;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        //取得账号信息
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        SecurityContextHolder.getContext().setAuthentication(authentication);
        //取token
        //好的解决方案，登录成功后token存储到数据库中
        //只要token还在过期内，不需要每次重新生成
        //先去缓存中找
        String token = TokenCache.getTokenFromCache(userDetails.getUsername());
        if (token == null) {
            //如果token为空，则去创建一个新的token
            jwtTokenUtil = new JwtTokenUtil();
            token = jwtTokenUtil.generateToken(userDetails);
            //把新的token存储到缓存中
            TokenCache.setToken(userDetails.getUsername(), token);
        }

        //加载前端菜单
        List<SysFrontendMenuTable> menus = service.getMenusByUserName(userDetails.getUsername());
        //
        Map<String, Object> map = new HashMap<>();
        map.put("username", userDetails.getUsername());
        map.put("auth", userDetails.getAuthorities());
        map.put("menus", menus);
        map.put("token", token);
        map.put("code", 0);
        //装入token
//        R<Map<String, Object>> data = R.ok(map);
        //输出
        this.WriteJSON(request, response, map);

    }
}
