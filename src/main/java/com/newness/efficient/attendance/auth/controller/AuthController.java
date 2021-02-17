package com.newness.efficient.attendance.auth.controller;

import com.newness.efficient.attendance.auth.bo.Authentication;
import com.newness.efficient.attendance.auth.po.User;
import com.newness.efficient.attendance.auth.service.AuthService;
import com.newness.efficient.attendance.utils.AESCrypt;
import com.newness.efficient.attendance.utils.JWTUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public Authentication login(@RequestBody Map<String, String> loginInfo) {
        Authentication authentication = new Authentication();
        if (challengeUser(loginInfo)) {
            authentication.setState(true);
            authentication.setToken(getToken(loginInfo));
            authentication.setUsername(loginInfo.get("username"));
            authentication.setMsg("登录成功");

            User user = authService.getUserInfoByUsername(authentication.getUsername());
            authentication.setUser(user);
        } else {
            authentication.setState(false);
            authentication.setMsg("账号或密码错误");
        }
        log.info(loginInfo.get("username") + "=>" + authentication.getMsg());
        return authentication;
    }

    private boolean challengeUser(Map<String, String> loginInfo) {
        String rawPassword = AESCrypt.encrypt(loginInfo.get("password"));
        String password = authService.getPassword(loginInfo.get("username"));
        return password != null && Objects.equals(rawPassword, password);
    }

    private String getToken(Map<String, String> loginInfo) {
        Map<String, String> payload = new HashMap<>();
        payload.put("username", loginInfo.get("username"));
        return JWTUtils.getToken(payload);
    }

    @PostMapping("/isExistUsername")
    public boolean isExistUsername(@RequestBody String username) {
        User user = authService.getUserInfoByUsername(username);
        return user != null && !user.getUsername().isEmpty();
    }

    @PostMapping("/signup")
    public boolean signup(@RequestBody Map<String, String> signupInfo) {
        String rawPassword = AESCrypt.encrypt(signupInfo.get("password"));
        signupInfo.put("password", rawPassword);
        int num = authService.addUser(signupInfo);
        return num > 0;
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        String s = URLDecoder.decode("Absdwe%2Bgu987", "utf-8");
        System.out.println(s);
    }
}
