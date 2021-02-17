package com.newness.efficient.attendance.user.controller;

import com.newness.efficient.attendance.auth.po.User;
import com.newness.efficient.attendance.auth.service.AuthService;
import com.newness.efficient.attendance.user.bo.Group;
import com.newness.efficient.attendance.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@Slf4j
@RequestMapping("/user")
public class UserController {

    @Autowired
    private AuthService authService;
    @Autowired
    private UserService userService;

    @PostMapping("/detail")
    public User detail(String username) {
        return authService.getUserInfoByUsername(username);
    }

    @PostMapping("/getUsers")
    public List<Map<String, String>> getUsers(@RequestBody String query) {
        List<Map<String, String>> list = userService.getUsers(query);
        return list == null ? new ArrayList<>() : list;
    }

    @PostMapping("/saveGroup")
    public boolean saveGroup(@RequestBody Group group) {
        if (group.getGroupId() == null || group.getGroupId().isEmpty()) {
            String groupId = UUID.randomUUID().toString().replace("-", "");
            group.setGroupId(groupId);
            userService.addGroup(group);
        } else {
            userService.updateGroup(group);
        }
        userService.updateMember(group);
        return true;
    }

    @PostMapping("/getGroupsNMembers")
    public List<Group> getGroupsNMembers() {

        return userService.getGroupsNMembers();
    }
}
