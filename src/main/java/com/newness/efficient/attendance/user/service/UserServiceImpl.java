package com.newness.efficient.attendance.user.service;

import com.newness.efficient.attendance.user.bo.Group;
import com.newness.efficient.attendance.user.bo.Member;
import com.newness.efficient.attendance.user.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<Map<String, String>> getUsers(String query) {
        return userMapper.getUsers(query);
    }

    @Override
    public int addGroup(Group group) {
        return userMapper.addGroup(group);
    }

    @Override
    public int updateGroup(Group group) {
        return userMapper.updateGroup(group);
    }

    @Override
    public void updateMember(Group group) {
        userMapper.clearMember(group.getGroupId());
        addMembers(group);
    }

    @Override
    public List<Group> getGroupsNMembers() {
        List<Map<String, String>> list = userMapper.getGroupsNMembers();
        Map<String, Group> groupMap = new HashMap<>();
        for (Map<String, String> map : list) {
            String groupId = map.get("groupId");
            Member member = new Member();
            member.setUsername(map.get("username"));
            member.setLeader(map.get("leader"));
            member.setFullName(map.get("fullName"));
            if (groupMap.containsKey(groupId)) {
                groupMap.get(groupId).getMembers().add(member);
            } else {
                Group group = new Group();
                group.setGroupId(groupId);
                group.setGroupName(map.get("groupName"));

                List<Member> memberList = new ArrayList<>();
                memberList.add(member);
                group.setMembers(memberList);

                groupMap.put(groupId, group);
            }
        }
        List<Group> groups = new ArrayList<>();
        groupMap.forEach((key, value) -> {
            groups.add(value);
        });
        return groups;
    }

    private void addMembers(Group group) {
        for (Member member : group.getMembers()) {
            Map<String, String> map = new HashMap<>();
            map.put("groupId", group.getGroupId());
            map.put("username", member.getUsername());
            map.put("leader", member.getLeader());
            userMapper.addMember(map);
        }
    }


}
