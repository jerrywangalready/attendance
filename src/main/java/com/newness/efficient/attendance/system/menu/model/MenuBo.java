package com.newness.efficient.attendance.system.menu.model;

import com.newness.efficient.attendance.system.menu.entity.MenuEntity;
import lombok.Data;

import java.util.List;

@Data
public class MenuBo extends MenuEntity {

    private List<MenuBo> children;

}
