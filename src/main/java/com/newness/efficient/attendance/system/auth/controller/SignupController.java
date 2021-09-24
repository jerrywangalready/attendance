package com.newness.efficient.attendance.system.auth.controller;

import com.newness.efficient.attendance.system.auth.service.AuthService;
import com.newness.efficient.attendance.components.BCryptPasswordEncoderUtil;
import com.newness.efficient.attendance.system.user.model.BaseUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/signup")
public class SignupController {

    @Autowired
    private AuthService authService;

    @PostMapping("/isExistUsername")
    public boolean isExistUsername(@RequestBody String username) {
        BaseUser user = authService.getUserInfoByUsername(username);
        return user != null && !user.getUsername().isEmpty();
    }

    @PostMapping("/createUser")
    public boolean createUser(@RequestBody Map<String, String> signupInfo) {
        BCryptPasswordEncoderUtil bCryptPasswordEncoderUtil = new BCryptPasswordEncoderUtil();
        String rawPassword = bCryptPasswordEncoderUtil.encode(signupInfo.get("password"));
        signupInfo.put("password", rawPassword);
        authService.addUser(signupInfo);
        return true;
    }
}
