package com.newness.efficient.attendance.system.role.mapper;

import com.newness.efficient.attendance.system.menu.entity.MenuEntity;
import com.newness.efficient.attendance.system.role.entity.RoleEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface RoleMapper {

    List<Map<String, String>> getRole(Map<String, String> param);

    int addRole(RoleEntity roleEntity);

    int updateRole(RoleEntity roleEntity);

    int delete(String roleId);

    @Insert("insert into sys_role_user (role_id, username) values (#{roleId}, #{userName})")
    void joinInRoleUser(String roleId, String userName);

    int deleteRoleUserByRoleId(String roleId);

    @Insert("insert into sys_role_menu (role_id, menu_id) values (#{roleId}, #{menuId})")
    int joinInRoleMenu(String roleId, int menuId);

    int deleteRoleMenuByRoleId(String roleId);

    List<MenuEntity> getMenuByRoleId(String roleId);

    List<String> getUsernameByRoleId(String roleId);
}
