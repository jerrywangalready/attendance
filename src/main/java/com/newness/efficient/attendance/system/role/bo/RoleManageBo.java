package com.newness.efficient.attendance.system.role.bo;

import lombok.Data;

import java.util.List;

@Data
public class RoleManageBo {

    private String roleId;

    private List<Integer> menuIds;

    private List<String> usernames;
}
