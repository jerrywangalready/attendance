package com.newness.efficient.attendance.group.bo;

import com.newness.efficient.attendance.system.user.bo.Member;
import lombok.Data;

import java.util.List;

@Data
public class Group {

    private String groupId;

    private String groupName;

    private List<Member> members;
}
