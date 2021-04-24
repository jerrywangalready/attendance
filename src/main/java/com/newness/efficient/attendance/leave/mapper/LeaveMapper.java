package com.newness.efficient.attendance.leave.mapper;

import com.newness.efficient.attendance.leave.bo.LeaveForm;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface LeaveMapper {

    boolean saveLeave(LeaveForm param);

    List<Map<String, String>> getLeaveInfo(Map<String, String> param);
}
