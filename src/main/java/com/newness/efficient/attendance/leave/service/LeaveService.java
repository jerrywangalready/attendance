package com.newness.efficient.attendance.leave.service;

import com.newness.efficient.attendance.leave.bo.LeaveForm;
import com.newness.efficient.attendance.leave.entity.LeaveProcess;
import com.newness.efficient.attendance.leave.entity.LeaveRecord;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

public interface LeaveService {

    String saveLeave(LeaveForm param);

    Map<String, List<Map<String, Calendar>>> getUsersLeaveInfoByMonth(String yearMonth);

    void addLeaveRecord(LeaveRecord leaveRecord);

    void saveLeaveProcess(LeaveProcess leaveProcess);
}
