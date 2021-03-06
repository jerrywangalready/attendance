package com.newness.efficient.attendance.role.mapper;

import com.newness.efficient.attendance.role.entity.RoleEntity;
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

    int clearUsersByRole(String roleName);

    @Insert("insert into sys_role_user (role_name,user_name) values (#{roleName},#{userName})")
    void joinInRoleUser(String roleName, String userName);
}
