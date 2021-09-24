package com.newness.efficient.attendance.system.auth.mapper;

import com.newness.efficient.attendance.system.user.model.BaseUser;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.Map;

@Mapper
public interface AuthMapper {

    @Select("select user_id, username, full_name from sys_user where username = #{username}")
    BaseUser getUserInfoByUsername(String username);

    //    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("insert into sys_user (user_id, username,full_name,password, status) " +
            " values (#{userId}, #{username}, #{fullName}, #{password}, 1)")
    int addUser(Map<String, String> param);

    @Select("select password from sys_user where username = #{username}")
    String getPassword(String username);

    @Insert("insert into sys_role_user (role_name,username) values ('ADMIN',#{userName})")
    void joinInRoleUser(String userName);
}
