package com.newness.efficient.attendance.overtime.bo;

import lombok.Data;

@Data
public class OvertimeForm {

    private String username;

    private String overtimeDate;

    private String startTime;

    private String endTime;

    private String reason;

    private Integer duration;
}
