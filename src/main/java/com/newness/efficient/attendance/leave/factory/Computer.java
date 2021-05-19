package com.newness.efficient.attendance.leave.factory;

import com.newness.efficient.attendance.leave.bo.Settlement;

public interface Computer {

    Settlement compute(String startDate, String endDate, String username);
}
