package com.newness.efficient.attendance.overtime.service;

import com.newness.efficient.attendance.overtime.bo.OvertimeForm;
import com.newness.efficient.attendance.overtime.mapper.OvertimeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OvertimeServiceImpl implements OvertimeService {

    @Autowired
    private OvertimeMapper overtimeMapper;

    @Override
    public boolean saveOvertime(OvertimeForm param) {
        return overtimeMapper.saveOvertime(param);
    }
}
