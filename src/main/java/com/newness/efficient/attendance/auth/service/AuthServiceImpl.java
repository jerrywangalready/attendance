package com.newness.efficient.attendance.auth.service;

import com.newness.efficient.attendance.auth.mapper.AuthMapper;
import com.newness.efficient.attendance.auth.po.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private AuthMapper authMapper;

    @Override
    public User getUserInfoByUsername(String username) {
        return authMapper.getUserInfoByUsername(username);
    }

    @Override
    public int addUser(Map<String, String> signupInfo) {
        return authMapper.addUser(signupInfo);
    }

    @Override
    public String getPassword(String username) {
        return authMapper.getPassword(username);
    }


}
