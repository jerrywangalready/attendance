package com.newness.efficient.attendance.auth.po;


import com.newness.efficient.attendance.auth.entity.SysBackendApiTable;

/**
 * po到vo，前端需要显示父级pid、名称
 */
public class SysBackendApiVo extends SysBackendApiTable {

    String parentName;

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }
}
