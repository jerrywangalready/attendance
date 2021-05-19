package com.newness.efficient.attendance.leave.entity;

import com.newness.efficient.attendance.leave.bo.LeaveForm;
import lombok.Data;

import java.io.Serializable;

/**
 * atd_leave_process
 * @author jerry
 */
@Data
public class LeaveProcess implements Serializable {
    private String processId;

    private String businessId;

    private String approver;

    /**
     * 状态 0.保存 10.提交 20.通过 30.驳回 40.撤销
     */
    private Integer processState;

    private static final long serialVersionUID = 1L;

    public LeaveProcess() {

    }

    public LeaveProcess(LeaveForm leaveForm) {
        this.businessId = leaveForm.getLeaveId();
        this.approver = leaveForm.getApprover();
        this.processState = 10;
    }

    public boolean isProcessIdBlank() {
        return this.processId == null || this.processId.isEmpty();
    }
}