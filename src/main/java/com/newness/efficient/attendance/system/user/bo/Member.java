package com.newness.efficient.attendance.system.user.bo;

import lombok.Data;

@Data
public class Member {

    private int id;

    private String groupId;

    private String username;

    private String fullName;

    private String leader;
}
