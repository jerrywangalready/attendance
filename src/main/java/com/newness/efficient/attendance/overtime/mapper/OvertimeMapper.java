package com.newness.efficient.attendance.overtime.mapper;

import com.newness.efficient.attendance.overtime.bo.OvertimeForm;
import org.springframework.stereotype.Repository;

@Repository
public interface OvertimeMapper {

    boolean saveOvertime(OvertimeForm param);
}
