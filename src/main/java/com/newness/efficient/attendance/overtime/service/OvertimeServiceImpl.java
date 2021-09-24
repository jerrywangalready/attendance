package com.newness.efficient.attendance.overtime.service;

import com.newness.efficient.attendance.overtime.bo.OvertimeForm;
import com.newness.efficient.attendance.overtime.mapper.OvertimeMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class OvertimeServiceImpl implements OvertimeService {

    @Resource
    private OvertimeMapper overtimeMapper;

    @Override
    public boolean saveOvertime(OvertimeForm param) {
        return overtimeMapper.saveOvertime(param);
    }
}
