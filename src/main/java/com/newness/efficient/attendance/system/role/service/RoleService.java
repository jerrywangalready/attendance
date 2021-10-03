package com.newness.efficient.attendance.system.role.service;

import com.newness.efficient.attendance.system.menu.entity.MenuEntity;
import com.newness.efficient.attendance.system.role.entity.RoleEntity;

import java.util.List;
import java.util.Map;

public interface RoleService {
    List<Map<String, String>> getRole(Map<String, String> param);

    void saveRole(RoleEntity roleEntity);

    void deleteRole(String roleId);

    void joinInRoleUser(String roleName, String[] userName);

    void saveRoleUser(String roleId, List<String> usernames);

    void saveRoleMenu(String roleId, List<Integer> menus);

    List<MenuEntity> getMenuByRoleId(String roleId);

    List<String> getUsernameByRoleId(String roleId);

    void deleteRoleMenu(String roleId);

    void deleteRoleUser(String roleId);
}
