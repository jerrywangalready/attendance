package com.newness.efficient.attendance.role.service;

import com.newness.efficient.attendance.role.entity.RoleEntity;
import com.newness.efficient.attendance.role.mapper.RoleMapper;
import com.newness.efficient.attendance.utils.IdCreator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class RoleServiceImpl implements RoleService{

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
}
