package com.newness.efficient.attendance.system.user.service;

import com.newness.efficient.attendance.system.user.bo.Personnel;
import com.newness.efficient.attendance.system.user.mapper.UserMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Resource
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
