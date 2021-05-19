package com.newness.efficient.attendance.leave.bo;

import lombok.Data;

@Data
public class Settlement {

    /**
     * 单位
     */
    private String unit;

    /**
     * 总时长
     */
    private Float duration;

    /**
     * 调休
     */
    private Float shift;

    /**
     * 年假
     */
    private Float annually;

    /**
     * 减免
     */
    private Float remit;

    /**
     * 总计
     */
    private Float amount;
}
