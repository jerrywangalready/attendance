package com.newness.efficient.attendance.system.menu.controller;

import com.newness.efficient.attendance.system.menu.entity.MenuEntity;
import com.newness.efficient.attendance.system.menu.model.MenuBo;
import com.newness.efficient.attendance.system.menu.service.MenuService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/menu")
public class MenuController {

    @Resource
    private MenuService menuService;

    @PostMapping("/add")
    public boolean add(@RequestBody MenuEntity menu) {
        return menuService.save(menu);
    }

    @PostMapping("/update")
    public boolean update(@RequestBody MenuEntity menu) {
        return menuService.save(menu);
    }

    @PostMapping("/getMenu")
    public List<MenuBo> getMenu() {
        return menuService.getMenu();
    }

    @DeleteMapping("/deleteMenu")
    public boolean deleteMenu(@RequestBody MenuEntity menu) {
        return menuService.deleteMenu(menu.getMenuId());
    }
}
