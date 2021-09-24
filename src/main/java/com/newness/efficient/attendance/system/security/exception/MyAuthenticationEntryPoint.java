package com.newness.efficient.attendance.system.security.exception;

import com.newness.efficient.attendance.components.R;
import com.newness.efficient.attendance.components.ResponseUtil;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 用来解决匿名用户访问无权限资源时的异常
 */
public class MyAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        //todo 输出返回信息
        ResponseUtil.out(httpServletResponse, R.error(), HttpStatus.UNAUTHORIZED.value());
    }
}
