package com.newness.efficient.attendance.role.service;

import com.newness.efficient.attendance.role.entity.RoleEntity;

import java.util.List;
import java.util.Map;

public interface RoleService {
    List<Map<String, String>> getRole(Map<String, String> param);

    void saveRole(RoleEntity roleEntity);

    void deleteRole(String roleId);
}
