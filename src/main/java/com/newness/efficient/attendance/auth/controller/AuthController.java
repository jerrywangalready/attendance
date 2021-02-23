package com.newness.efficient.attendance.auth.controller;

import com.newness.efficient.attendance.auth.service.AuthService;
import com.newness.efficient.attendance.components.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

//    @PostMapping("/login")
//    public Authentication login(@RequestBody Map<String, String> loginInfo) {
//        Authentication authentication = new Authentication();
//        if (challengeUser(loginInfo)) {
//            authentication.setState(true);
//            authentication.setToken(getToken(loginInfo));
//            authentication.setUsername(loginInfo.get("username"));
//            authentication.setMsg("登录成功");
//
//            User user = authService.getUserInfoByUsername(authentication.getUsername());
//            authentication.setUser(user);
//        } else {
//            authentication.setState(false);
//            authentication.setMsg("账号或密码错误");
//        }
//        log.info(loginInfo.get("username") + "=>" + authentication.getMsg());
//        return authentication;
//    }
//
//    private boolean challengeUser(Map<String, String> loginInfo) {
//        String rawPassword = AESCrypt.encrypt(loginInfo.get("password"));
//        String password = authService.getPassword(loginInfo.get("username"));
//        return password != null && Objects.equals(rawPassword, password);
//    }
//
//    private String getToken(Map<String, String> loginInfo) {
//        Map<String, String> payload = new HashMap<>();
//        payload.put("username", loginInfo.get("username"));
//        return jwtTokenUtil.getToken(payload);
//    }



}
