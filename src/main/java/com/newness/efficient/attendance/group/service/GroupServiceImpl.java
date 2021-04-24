package com.newness.efficient.attendance.group.service;

import com.newness.efficient.attendance.group.bo.Group;
import com.newness.efficient.attendance.group.mapper.GroupMapper;
import com.newness.efficient.attendance.user.bo.Member;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GroupServiceImpl implements GroupService {

    @Resource
    private GroupMapper groupMapper;

    @Override
    public List<Group> getGroupsNMembers() {
        List<Map<String, String>> list = groupMapper.getGroupsNMembers();
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

    @Override
    public int addGroup(Group group) {
        return groupMapper.addGroup(group);
    }

    @Override
    public int updateGroup(Group group) {
        return groupMapper.updateGroup(group);
    }

    @Override
    public void updateMember(Group group) {
        groupMapper.clearMember(group.getGroupId());
        addMembers(group);
    }

    private void addMembers(Group group) {
        for (Member member : group.getMembers()) {
            Map<String, String> map = new HashMap<>();
            map.put("groupId", group.getGroupId());
            map.put("username", member.getUsername());
            map.put("leader", member.getLeader());
            groupMapper.addMember(map);
        }
    }
}
