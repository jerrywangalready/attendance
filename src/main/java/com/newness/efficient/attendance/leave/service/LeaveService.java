package com.newness.efficient.attendance.leave.service;

import com.newness.efficient.attendance.leave.bo.LeaveForm;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

public interface LeaveService {

    boolean saveLeave(LeaveForm param);

    Map<String, List<Map<String, Calendar>>> getUsersLeaveInfoByMonth(String yearMonth);
}
