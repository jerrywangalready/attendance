package com.newness.efficient.attendance.system.user.service;

import com.newness.efficient.attendance.system.user.bo.Personnel;

import java.util.List;
import java.util.Map;

public interface UserService {

    List<Personnel> getUsers(Map<String, String> param);

    List<Map<String, String>> getUsersGrid(Map<String, String> param);

    List<Map<String, String>> getUsersByRole(String role);

}
