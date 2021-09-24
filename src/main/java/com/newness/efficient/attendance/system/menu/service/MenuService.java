package com.newness.efficient.attendance.system.menu.service;

import com.newness.efficient.attendance.system.menu.entity.MenuEntity;
import com.newness.efficient.attendance.system.menu.model.MenuBo;

import java.util.List;

public interface MenuService {

    boolean save(MenuEntity menu);

    List<MenuBo> getMenu();

    boolean deleteMenu(int menuId);
}
