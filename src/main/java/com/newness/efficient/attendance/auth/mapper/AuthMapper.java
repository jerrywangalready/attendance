package com.newness.efficient.attendance.auth.mapper;

import com.newness.efficient.attendance.auth.po.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface AuthMapper {

    @Select("select user_id, user_name, full_name from sys_user where user_name = #{username}")
    User getUserInfoByUsername(String username);

    //    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("insert into sys_user (user_id, user_name,full_name,pass_word, state) " +
            " values (#{userId}, #{username}, #{fullName}, #{password}, 1)")
    int addUser(Map<String, String> param);

    @Select("select pass_word from sys_user where user_name = #{username}")
    String getPassword(String username);

    @Insert("insert into sys_role_user (role_id,user_id) values ('0',#{userId})")
    void joinInRoleUser(String userId);
}
