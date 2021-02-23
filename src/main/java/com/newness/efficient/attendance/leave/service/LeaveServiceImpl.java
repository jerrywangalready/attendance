package com.newness.efficient.attendance.leave.service;

import com.newness.efficient.attendance.leave.bo.LeaveForm;
import com.newness.efficient.attendance.leave.mapper.LeaveMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LeaveServiceImpl implements LeaveService {

    @Autowired
    private LeaveMapper leaveMapper;

    @Override
    public boolean saveLeave(LeaveForm param) {
        return leaveMapper.saveLeave(param);
    }
}
