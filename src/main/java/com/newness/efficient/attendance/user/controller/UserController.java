package com.newness.efficient.attendance.user.controller;

import com.newness.efficient.attendance.auth.po.User;
import com.newness.efficient.attendance.auth.service.AuthService;
import com.newness.efficient.attendance.user.bo.Group;
import com.newness.efficient.attendance.user.bo.Personnel;
import com.newness.efficient.attendance.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

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
    public List<Personnel> getUsers(@RequestBody(required = false) String query) {
        List<Personnel> list = userService.getUsers(query);
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

    @PostMapping("/getUsersGrid")
    public List<Map<String, String>> getUsersGrid(@RequestBody Map<String, String> param) {
        return userService.getUsersGrid( param);
    }

    @PostMapping("/getBasicInfo")
    public Map<String, Object> getBasicInfo(@AuthenticationPrincipal UserDetails userDetails) {
        List<Personnel> users = userService.getUsers(userDetails.getUsername());
        Map<String, Object> map = new HashMap<>();
        if (users.size() > 0) {
            map.put("fullName", users.get(0).getFullName());
        }
        return map;
    }
}
