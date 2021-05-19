package com.newness.efficient.attendance.leave.controller;

import com.newness.efficient.attendance.holiday.service.HolidayService;
import com.newness.efficient.attendance.leave.bo.LeaveForm;
import com.newness.efficient.attendance.leave.bo.Settlement;
import com.newness.efficient.attendance.leave.entity.LeaveProcess;
import com.newness.efficient.attendance.leave.entity.LeaveRecord;
import com.newness.efficient.attendance.leave.factory.Computer;
import com.newness.efficient.attendance.leave.factory.ComputerFactory;
import com.newness.efficient.attendance.leave.service.LeaveService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/leave")
@Slf4j
public class LeaveController {

    @Resource
    private LeaveService leaveService;

    @Autowired
    private ComputerFactory computerFactory;


    @Resource
    private HolidayService holidayService;

    @PostMapping("/apply")
    public boolean apply(@RequestBody LeaveForm param, @AuthenticationPrincipal UserDetails userDetails) {
        param.setUsername(userDetails.getUsername());
        leaveService.saveLeave(param);
        // 插入进程表
        LeaveProcess leaveProcess = new LeaveProcess(param);
        leaveService.saveLeaveProcess(leaveProcess);
        // 插入记录表
        LeaveRecord leaveRecord = new LeaveRecord(param.getLeaveId(), leaveProcess.getApprover(), "提交");
        leaveService.addLeaveRecord(leaveRecord);
        return true;
    }

    @PostMapping("/computeSettlement")
    public Settlement computeSettlement(@RequestBody LeaveForm leaveForm, @AuthenticationPrincipal UserDetails userDetails) {
        Computer computer = computerFactory
                .getComputer(leaveForm.getLeaveType())
                .orElseThrow(() -> new IllegalArgumentException("Invalid Leave Duration Computer"));

        return computer.compute(leaveForm.getStartDate(), leaveForm.getEndDate(), userDetails.getUsername());
    }

}
