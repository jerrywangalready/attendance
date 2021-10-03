package com.newness.efficient.attendance.system.role.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.newness.efficient.attendance.system.menu.entity.MenuEntity;
import com.newness.efficient.attendance.system.role.bo.RoleManageBo;
import com.newness.efficient.attendance.system.role.entity.RoleEntity;
import com.newness.efficient.attendance.system.role.service.RoleService;
import org.springframework.web.bind.annotation.*;

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

    @DeleteMapping("/deleteRole")
    public boolean deleteRole(@RequestBody RoleEntity roleEntity) {
        // todo 删除之前需要验证是否被使用

        // 删除角色菜单关系数据
        roleService.deleteRoleMenu(roleEntity.getRoleId());
        // 删除角色用户关系数据
        roleService.deleteRoleUser(roleEntity.getRoleId());
        // 删除角色数据
        roleService.deleteRole(roleEntity.getRoleId());
        return true;
    }

    public boolean checkRoleBeUsed(String roleId) {

        return true;
    }

    @PostMapping("/saveManage")
    public boolean saveManage(@RequestBody RoleManageBo bo) {
        try {
            // 存角色-菜单表
            roleService.saveRoleMenu(bo.getRoleId(), bo.getMenuIds());
            // 存角色-人员表
            roleService.saveRoleUser(bo.getRoleId(), bo.getUsernames());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @GetMapping("/getManageByRoleId")
    public RoleManageBo getManageByRoleId(@RequestParam("roleId") String roleId) {
        List<MenuEntity> menus = roleService.getMenuByRoleId(roleId);
        List<String> usernames = roleService.getUsernameByRoleId(roleId);
        RoleManageBo bo = new RoleManageBo();
        bo.setMenus(menus);
        bo.setUsernames(usernames);
        return bo;
    }

}
