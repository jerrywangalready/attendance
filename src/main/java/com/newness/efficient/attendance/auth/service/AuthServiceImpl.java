package com.newness.efficient.attendance.auth.service;

import com.newness.efficient.attendance.auth.mapper.AuthMapper;
import com.newness.efficient.attendance.auth.po.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private AuthMapper authMapper;

    @Override
    public User getUserInfoByUsername(String username) {
        return authMapper.getUserInfoByUsername(username);
    }

    @Override
    public String addUser(Map<String, String> signupInfo) {
        String userId = UUID.randomUUID().toString().replace("-", "");
        signupInfo.put("userId", userId);
        authMapper.addUser(signupInfo);
        return userId;
    }

    @Override
    public String getPassword(String username) {
        return authMapper.getPassword(username);
    }

    @Override
    public void joinInRoleUser(String userId) {
        authMapper.joinInRoleUser(userId);
    }


}
