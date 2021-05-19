package com.newness.efficient.attendance.leave.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * atd_leave_record
 * @author jerry
 */
@Data
public class LeaveRecord implements Serializable {

    private Integer id;

    private String leaveId;

    /**
     * 操作人
     */
    private String operator;

    private Date operateTime;

    private String message;

    private static final long serialVersionUID = 1L;

    public LeaveRecord() {

    }

    public LeaveRecord(String leaveId, String operator, String message) {
        this.leaveId = leaveId;
        this.operator = operator;
        this.message = message;
    }
}