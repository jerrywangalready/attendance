package com.newness.efficient.attendance.group.service;

import com.newness.efficient.attendance.group.bo.Group;

import java.util.List;

public interface GroupService {

    List<Group> getGroupsNMembers();

    int addGroup(Group group);

    int updateGroup(Group group);

    void updateMember(Group group);
}
