package com.newness.efficient.attendance.overtime.mapper;

import com.newness.efficient.attendance.overtime.bo.OvertimeForm;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OvertimeMapper {

    boolean saveOvertime(OvertimeForm param);


}
