package com.newness.efficient.attendance.system.user.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.newness.efficient.attendance.system.auth.service.AuthService;
import com.newness.efficient.attendance.system.user.bo.Personnel;
import com.newness.efficient.attendance.system.user.model.BaseUser;
import com.newness.efficient.attendance.system.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/user")
public class UserController {

    @Resource
    private AuthService authService;
    @Resource
    private UserService userService;

    @GetMapping("/detail")
    public BaseUser detail(@RequestParam("username") String username) {
        return authService.getUserInfoByUsername(username);
    }

    @PostMapping("/getUsers")
    public List<Personnel> getUsers(@RequestBody Map<String, String> param) {
        List<Personnel> list = userService.getUsers(param);
        return list == null ? new ArrayList<>() : list;
    }

    @PostMapping("/getUsersGrid")
    public PageInfo<Map<String, String>> getUsersGrid(@RequestBody Map<String, String> param) {
        PageHelper.startPage(param);
        List<Map<String, String>> list = userService.getUsersGrid( param);
        return new PageInfo<>(list);
    }

    @PostMapping("/getBasicInfo")
    public Map<String, Object> getBasicInfo(@AuthenticationPrincipal UserDetails userDetails) {
        Map<String, String> param = new HashMap<>();
        param.put("name", userDetails.getUsername());
        List<Personnel> users = userService.getUsers(param);
        Map<String, Object> map = new HashMap<>();
        if (users.size() > 0) {
            map.put("fullName", users.get(0).getFullName());
        }
        return map;
    }

    @PostMapping("/getUsersByRole")
    public List<Map<String, String>> getUsersByRole(@RequestBody Map<String, String> param) {
        return userService.getUsersByRole(param.get("role"));
    }

}
