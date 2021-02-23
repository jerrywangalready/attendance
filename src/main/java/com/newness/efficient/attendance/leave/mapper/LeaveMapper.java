package com.newness.efficient.attendance.leave.mapper;

import com.newness.efficient.attendance.leave.bo.LeaveForm;
import org.springframework.stereotype.Repository;

@Repository
public interface LeaveMapper {

    boolean saveLeave(LeaveForm param);
}
