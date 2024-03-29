package com.newness.efficient.attendance.interceptors;

import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.newness.efficient.attendance.system.auth.bo.Authentication;
import com.newness.efficient.attendance.system.security.token.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class JWTInterceptor implements HandlerInterceptor {

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("Authentication");
        Authentication authentication = new Authentication();
        try {
            System.out.println("@jwtTokenUtil.verify(token)");
            DecodedJWT verify = jwtTokenUtil.verify(token);
            String username = verify.getClaim("username").asString();
            System.out.println(username);
            authentication.setState(true);
            authentication.setMsg("请求成功");
            return true;
        } catch (SignatureVerificationException e) {
            log.error("无效签名", e);
            authentication.setMsg("无效签名");
        } catch (TokenExpiredException e) {
            log.error("token过期", e);
            authentication.setMsg("token过期");
        } catch (AlgorithmMismatchException e) {
            log.error("token算法不一致", e);
            authentication.setMsg("token算法不一致");
        } catch (Exception e) {
            log.error("无效签名", e);
            authentication.setMsg("无效签名!");
        }
        authentication.setState(false);
        String json = new ObjectMapper().writeValueAsString(authentication);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().println(json);
        return false;
    }
}
