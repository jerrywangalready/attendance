package com.newness.efficient.attendance.leave.mapper;

import com.newness.efficient.attendance.leave.bo.LeaveForm;
import com.newness.efficient.attendance.leave.entity.LeaveProcess;
import com.newness.efficient.attendance.leave.entity.LeaveRecord;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface LeaveMapper {

    boolean saveLeave(LeaveForm param);

    List<Map<String, String>> getLeaveInfo(Map<String, String> param);

    @Insert("insert into atd_leave_record (id, leave_id, operator, operate_time, message)\n" +
            "values (\n" +
            "        #{id}, \n" +
            "        #{leaveId}, \n" +
            "        #{operator}, \n" +
            "        now(), \n" +
            "        #{message}\n" +
            "        )")
    void addLeaveRecord(LeaveRecord leaveRecord);

    @Insert("insert into atd_leave_process (process_id, business_id, approver, process_state)\n" +
            "values (#{processId}, #{businessId}, #{approver}, #{processState})")
    void addProcess(LeaveProcess leaveProcess);

    void updateProcess(LeaveProcess leaveProcess);
}
