package com.newness.efficient.attendance.system.role.service;

import com.newness.efficient.attendance.system.menu.entity.MenuEntity;
import com.newness.efficient.attendance.system.role.entity.RoleEntity;
import com.newness.efficient.attendance.system.role.mapper.RoleMapper;
import com.newness.efficient.attendance.utils.IdCreator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class RoleServiceImpl implements RoleService {

    @Resource
    private RoleMapper roleMapper;

    @Override
    public List<Map<String, String>> getRole(Map<String, String> param) {
        return roleMapper.getRole(param);
    }

    @Override
    public void saveRole(RoleEntity roleEntity) {
        if (roleEntity.getRoleId() == null) {
            roleEntity.setRoleId(IdCreator.getId());
            roleMapper.addRole(roleEntity);
        } else {
            roleMapper.updateRole(roleEntity);
        }
    }

    @Override
    public void deleteRole(String roleId) {
        roleMapper.delete(roleId);
    }

    @Override
    public void joinInRoleUser(String roleName, String[] userName) {
        for (String u : userName) {
            roleMapper.joinInRoleUser(roleName, u);
        }
    }

    @Override
    public void saveRoleUser(String roleId, List<String> usernames) {
        // 删除旧数据
        roleMapper.deleteRoleUserByRoleId(roleId);
        // 插入新数据
        if (usernames != null) {
            usernames.forEach(username -> {
                roleMapper.joinInRoleUser(roleId, username);
            });
        }
    }

    @Override
    public void saveRoleMenu(String roleId, List<Integer> menuIds) {
        // 删除旧数据
        roleMapper.deleteRoleMenuByRoleId(roleId);
        if (menuIds != null) {
            // 插入新数据
            menuIds.forEach(menuId -> {
                roleMapper.joinInRoleMenu(roleId, menuId);
            });
        }
    }

    @Override
    public List<MenuEntity> getMenuByRoleId(String roleId) {
        return roleMapper.getMenuByRoleId(roleId);
    }

    @Override
    public List<String> getUsernameByRoleId(String roleId) {
        return roleMapper.getUsernameByRoleId(roleId);
    }

    @Override
    public void deleteRoleMenu(String roleId) {
        roleMapper.deleteRoleMenuByRoleId(roleId);
    }

    @Override
    public void deleteRoleUser(String roleId) {
        roleMapper.deleteRoleUserByRoleId(roleId);
    }


}
