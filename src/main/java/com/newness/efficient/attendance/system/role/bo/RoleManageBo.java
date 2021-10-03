package com.newness.efficient.attendance.system.role.bo;

import com.newness.efficient.attendance.system.menu.entity.MenuEntity;
import lombok.Data;

import java.util.List;

@Data
public class RoleManageBo {

    private String roleId;

    private List<Integer> menuIds;

    private List<MenuEntity> menus;

    private List<String> usernames;
}
