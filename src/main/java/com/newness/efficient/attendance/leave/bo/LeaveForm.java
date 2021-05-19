package com.newness.efficient.attendance.leave.bo;

import lombok.Data;

@Data
public class LeaveForm {

    private String leaveId;

    private String username;

    private String leaveType;

    private String startDate;

    private String endDate;

    private String reason;

    private Float duration;

    private String approver;
}
