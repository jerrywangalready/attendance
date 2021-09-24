package com.newness.efficient.attendance.system.security.service;

import com.newness.efficient.attendance.system.security.model.Menu;
import com.newness.efficient.attendance.system.apimanage.entity.SysApiEntity;

import java.util.List;

public interface PermissionService {

    List<Menu> queryAllMenu();

    List<Menu> getMenuByUsername(String username);

    List<SysApiEntity> getPermissionsByUser(String username);
}
