package com.newness.efficient.attendance.user.mapper;

import com.newness.efficient.attendance.user.bo.Group;
import com.newness.efficient.attendance.user.bo.Personnel;
import org.apache.ibatis.annotations.Delete;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface UserMapper {

    List<Personnel> getUsers(String query);

    List<Map<String, String>> getUsersGrid(Map<String, String> param);

    List<Map<String, String>> getGroupsNMembers();

    int addGroup(Group group);

    int updateGroup(Group group);

    void addMember(Map<String, String> map);

    @Delete("delete from sys_group_user where group_id = #{groupId}")
    void clearMember(String groupId);

    List<Map<String, String>> getRoleUser();
}
