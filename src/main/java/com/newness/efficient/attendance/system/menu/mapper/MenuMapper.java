package com.newness.efficient.attendance.system.menu.mapper;

import com.newness.efficient.attendance.system.menu.entity.MenuEntity;
import com.newness.efficient.attendance.system.menu.model.MenuBo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MenuMapper {

    List<MenuBo> getMenu();

    boolean addMenu(MenuEntity param);

    boolean updateMenu(MenuEntity param);

    boolean deleteMenu(int menuId);
}
