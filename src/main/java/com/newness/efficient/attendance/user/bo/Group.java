package com.newness.efficient.attendance.user.bo;

import lombok.Data;

import java.util.List;

@Data
public class Group {

    private String groupId;

    private String groupName;

    private List<Member> members;
}
