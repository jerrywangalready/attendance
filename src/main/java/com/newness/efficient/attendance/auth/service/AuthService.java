package com.newness.efficient.attendance.auth.service;

import com.newness.efficient.attendance.auth.po.User;

import java.util.Map;

public interface AuthService {

    User getUserInfoByUsername(String username);

    String addUser(Map<String, String> signupInfo);

    String getPassword(String username);

    void joinInRoleUser(String userId);
}
