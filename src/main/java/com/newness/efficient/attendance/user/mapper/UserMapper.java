package com.newness.efficient.attendance.user.mapper;

import com.newness.efficient.attendance.user.bo.Personnel;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserMapper {

    /**
     * 获取人员信息
     *
     * @param param 查询项
     * @return
     */
    List<Personnel> getUsers(Map<String, String> param);

    List<Map<String, String>> getUsersGrid(Map<String, String> param);

    List<Map<String, String>> getRoleUser();

    List<String> getUsersByRole(String role);
}
