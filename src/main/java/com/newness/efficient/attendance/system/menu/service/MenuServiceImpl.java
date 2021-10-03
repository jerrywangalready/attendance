package com.newness.efficient.attendance.system.menu.service;

import com.newness.efficient.attendance.system.menu.entity.MenuEntity;
import com.newness.efficient.attendance.system.menu.mapper.MenuMapper;
import com.newness.efficient.attendance.system.menu.model.MenuBo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
public class MenuServiceImpl implements MenuService {

    @Resource
    private MenuMapper menuMapper;

    @Override
    public boolean save(MenuEntity menu) {
        if (menu.getMenuId() == null) {
            return menuMapper.addMenu(menu);
        } else {
            return menuMapper.updateMenu(menu);
        }
    }

    @Override
    public List<MenuBo> getMenu() {
        List<MenuBo> menus = menuMapper.getMenu();
        return list2Tree(menus);
    }

    @Override
    public List<MenuBo> getMenuByUsername(String username) {
        List<MenuBo> menus = menuMapper.getMenuByUsername(username);
        return list2Tree(menus);
    }

    private List<MenuBo> list2Tree(List<MenuBo> menus) {
        List<MenuBo> menuTree = new ArrayList<>();

        Map<String, List<MenuBo>> tempMap = new HashMap<>();

        menus.forEach(menu -> {
            // 找到自己的子菜单并放入到自己下面
            String menuId = menu.getMenuId().toString();
            if (tempMap.containsKey(menuId)) {
                menu.setChildren(tempMap.get(menuId));
                tempMap.remove(menuId);
            }

            // 根据parentId进行分组
            tempMap.merge(
                    menu.getParentId().toString(),
                    new ArrayList<>(Collections.singletonList(menu)),
                    (oldValue, newValue) -> {
                        oldValue.add(newValue.get(0));
                        return oldValue;
                    }
            );
        });

        tempMap.forEach((key, value) -> menuTree.addAll(value));
        return menuTree;
    }

    @Override
    public boolean deleteMenu(int menuId) {
        return menuMapper.deleteMenu(menuId);
    }

}
