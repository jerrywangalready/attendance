package com.newness.efficient.attendance.auth.entity;

import lombok.Data;

@Data
public class SysUserEntity {

    String userId;

    String userName;

    String fullName;

    String passWord;

    Integer state;

    String description;

}
