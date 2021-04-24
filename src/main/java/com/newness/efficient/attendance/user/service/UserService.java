package com.newness.efficient.attendance.user.service;

import com.newness.efficient.attendance.group.bo.Group;
import com.newness.efficient.attendance.user.bo.Personnel;

import java.util.List;
import java.util.Map;

public interface UserService {

    List<Personnel> getUsers(Map<String, String> param);




    List<Map<String, String>> getUsersGrid(Map<String, String> param);


}
