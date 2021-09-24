package com.newness.efficient.attendance.overtime.controller;

import com.newness.efficient.attendance.overtime.bo.OvertimeForm;
import com.newness.efficient.attendance.overtime.service.OvertimeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/overtime")
@Slf4j
public class OvertimeController {

    @Resource
    private OvertimeService overtimeService;

    @PostMapping("/apply")
    public boolean apply(@RequestBody OvertimeForm param, @AuthenticationPrincipal UserDetails userDetails) {
        param.setUsername(userDetails.getUsername());
        return overtimeService.saveOvertime(param);
    }
}
