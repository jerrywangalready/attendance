package com.newness.efficient.attendance.clockin.bo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class ClockInBo {

    @ExcelProperty(value = "部门信息", index = 0)
    private String organName;

    @ExcelProperty(value = "人资编号", index = 1)
    private String hrCode;

    @ExcelProperty(value = "姓名", index = 2)
    private String fullName;

    @ExcelProperty(value = "打卡日期", index = 3)
    private String clockInDate;

    @ExcelProperty(value = "星期", index = 4)
    private String dayOfWeek;

    @ExcelProperty(value = "打卡信息", index = 5)
    private String clockInInfo;
}
