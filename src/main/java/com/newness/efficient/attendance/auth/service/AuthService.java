package com.newness.efficient.attendance.auth.service;

import com.newness.efficient.attendance.auth.po.User;

import java.util.Map;

public interface AuthService {

    User getUserInfoByUsername(String username);

    int addUser(Map<String, String> signupInfo);

    String getPassword(String username);

}
