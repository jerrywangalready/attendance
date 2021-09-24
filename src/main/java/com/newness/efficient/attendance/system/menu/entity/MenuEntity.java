package com.newness.efficient.attendance.system.menu.entity;

import lombok.Data;

@Data
public class MenuEntity {

    private Integer menuId;

    private Integer parentId;

    private String menuName;

    private Integer sort;

    private Integer levelNum;

    private Integer menuType;
}
