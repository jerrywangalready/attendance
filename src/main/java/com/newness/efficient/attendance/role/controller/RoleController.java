package com.newness.efficient.attendance.role.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.newness.efficient.attendance.role.bo.RoleUsers;
import com.newness.efficient.attendance.role.entity.RoleEntity;
import com.newness.efficient.attendance.role.service.RoleService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/role")
public class RoleController {

    @Resource
    private RoleService roleService;

    @PostMapping("/getRoleGrid")
    public PageInfo<Map<String, String>> getRoleGrid(@RequestBody Map<String, String> param) {
        PageHelper.startPage(param);
        List<Map<String, String>> list = roleService.getRole(param);
        return new PageInfo<>(list);
    }

    @PostMapping("/saveRole")
    public boolean saveRole(@RequestBody RoleEntity roleEntity) {
        roleService.saveRole(roleEntity);
        return true;
    }

    @PostMapping("/deleteRole")
    public boolean deleteRole(@RequestBody RoleEntity roleEntity) {
        // todo 删除之前需要验证是否被使用
        roleService.deleteRole(roleEntity.getRoleId());
        return true;
    }

    public boolean checkRoleBeUsed(String roleId) {

        return true;
    }

    @PostMapping("/joinInRoleUser")
    public boolean joinInRoleUser(@RequestBody RoleUsers roleUsers) {
        roleService.clearUsersByRole(roleUsers.getRoleName());
        roleService.joinInRoleUser(roleUsers.getRoleName(), roleUsers.getUserNames());
        return true;
    }



}
