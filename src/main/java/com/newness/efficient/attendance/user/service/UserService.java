package com.newness.efficient.attendance.user.service;

import com.newness.efficient.attendance.user.bo.Group;

import java.util.List;
import java.util.Map;

public interface UserService {

    List<Map<String, String>> getUsers(String query);

    int addGroup(Group group);

    int updateGroup(Group group);

    void updateMember(Group group);

    List<Group> getGroupsNMembers();
}
