package com.newness.efficient.attendance.user.service;

import com.newness.efficient.attendance.user.bo.Group;
import com.newness.efficient.attendance.user.bo.Personnel;

import java.util.List;
import java.util.Map;

public interface UserService {

    List<Personnel> getUsers(String query);

    int addGroup(Group group);

    int updateGroup(Group group);

    void updateMember(Group group);

    List<Group> getGroupsNMembers();

    List<Map<String, String>> getUsersGrid(Map<String, String> param);
}
