package com.newness.efficient.attendance.balance.bo;

import lombok.Data;

/**
 *
 */
@Data
public class Balance {

    /**
     * 剩余调休时长
     */
    private Float shift;

    /**
     * 剩余年假时长
     */
    private Float annually;
}
