package com.newness.efficient.attendance.system.auth.service;

import com.newness.efficient.attendance.system.user.model.BaseUser;

import java.util.Map;

public interface AuthService {

    BaseUser getUserInfoByUsername(String username);

    String addUser(Map<String, String> signupInfo);

    String getPassword(String username);

    void joinInRoleUser(String userName);
}
