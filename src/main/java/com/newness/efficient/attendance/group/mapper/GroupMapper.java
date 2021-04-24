package com.newness.efficient.attendance.group.mapper;

import com.newness.efficient.attendance.group.bo.Group;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface GroupMapper {

    List<Map<String, String>> getGroupsNMembers();

    int addGroup(Group group);

    int updateGroup(Group group);

    void addMember(Map<String, String> map);

    @Delete("delete from sys_group_user where group_id = #{groupId}")
    void clearMember(String groupId);
}
