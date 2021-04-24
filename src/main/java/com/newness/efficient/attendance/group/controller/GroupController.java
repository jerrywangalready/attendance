package com.newness.efficient.attendance.group.controller;

import com.newness.efficient.attendance.group.bo.Group;
import com.newness.efficient.attendance.group.service.GroupService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;

@RestController
@Slf4j
@RequestMapping("/group")
public class GroupController {

    @Resource
    private GroupService groupService;

    @PostMapping("/getGroupsNMembers")
    public List<Group> getGroupsNMembers() {
        return groupService.getGroupsNMembers();
    }

    @PostMapping("/saveGroup")
    public boolean saveGroup(@RequestBody Group group) {
        if (group.getGroupId() == null || group.getGroupId().isEmpty()) {
            String groupId = UUID.randomUUID().toString().replace("-", "");
            group.setGroupId(groupId);
            groupService.addGroup(group);
        } else {
            groupService.updateGroup(group);
        }
        groupService.updateMember(group);
        return true;
    }
}
