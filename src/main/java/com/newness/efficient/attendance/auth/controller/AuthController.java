package com.newness.efficient.attendance.auth.controller;

import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.newness.efficient.attendance.auth.bo.Authentication;
import com.newness.efficient.attendance.utils.JWTUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthController {

    @PostMapping("/login")
    public Authentication login(@RequestBody Map<String, String> user) {
        System.out.println(user.get("username"));
        System.out.println(user.get("password"));


        Map<String, String> payload = new HashMap<>();
        payload.put("username", user.get("username"));
        String token = JWTUtils.getToken(payload);

        Authentication authentication = new Authentication();
        authentication.setState(true);
        authentication.setMsg("登录成功");
        return authentication;
    }

    public Map test(String token) {
        Map<String, Object> map = new HashMap<>();
        try {
            DecodedJWT verify = JWTUtils.verify(token);

            map.put("state", true);
            map.put("msg", "请求成功");
        } catch (SignatureVerificationException e) {
            e.printStackTrace();
            map.put("msg", "无效签名");
        } catch (TokenExpiredException e) {

            map.put("msg", "token过期");
        } catch (AlgorithmMismatchException e) {
            map.put("msg", "token算法不一致");

        } catch (Exception e) {

            map.put("msg", "无效签名!");
        }
        return map;
    }

    public static void main(String[] args) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.add(Calendar.DATE, 1);
        System.out.println(calendar.getTime());
    }
}
