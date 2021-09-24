package com.newness.efficient.attendance.system.security.exception;

import com.newness.efficient.attendance.components.R;
import com.newness.efficient.attendance.components.ResponseUtil;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 用来解决认证过的用户访问无权限资源时的异常
 */
public class MyAccessDeniedHandler extends Throwable implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        //todo 输出返回信息
        ResponseUtil.out(httpServletResponse, R.error(), HttpStatus.UNAUTHORIZED.value());
    }
}
