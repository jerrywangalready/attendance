package com.newness.efficient.attendance.user.service;

import com.newness.efficient.attendance.user.bo.Personnel;
import com.newness.efficient.attendance.user.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<Personnel> getUsers(Map<String, String> param) {
        return userMapper.getUsers(param);
    }

    @Override
    public List<Map<String, String>> getUsersGrid(Map<String, String> param) {
        return userMapper.getUsersGrid(param);
    }

    @Override
    public List<Map<String, String>> getUsersByRole(String role) {
        return userMapper.getUsersByRole(role);
    }


}
