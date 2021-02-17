package com.newness.efficient.attendance.auth.mapper;

import com.newness.efficient.attendance.auth.po.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface AuthMapper {

    @Select("select id, username, full_name from sys_user where username = #{username}")
    User getUserInfoByUsername(String username);

    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("insert into sys_user (username,full_name,password) " +
            " values (#{username}, #{fullName}, #{password})")
    int addUser(Map<String, String> param);

    @Select("select password from sys_user where username = #{username}")
    String getPassword(String username);
}
