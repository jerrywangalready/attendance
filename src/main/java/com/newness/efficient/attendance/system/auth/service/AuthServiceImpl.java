package com.newness.efficient.attendance.system.auth.service;

import com.newness.efficient.attendance.system.auth.mapper.AuthMapper;
import com.newness.efficient.attendance.system.user.model.BaseUser;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;
import java.util.UUID;

@Service
public class AuthServiceImpl implements AuthService {

    @Resource
    private AuthMapper authMapper;

    @Override
    public BaseUser getUserInfoByUsername(String username) {
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
    public void joinInRoleUser(String userName) {
        authMapper.joinInRoleUser(userName);
    }


}
