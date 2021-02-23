package com.newness.efficient.attendance.auth.mapper;

import com.newness.efficient.attendance.auth.entity.SysFrontendMenuTable;
import com.newness.efficient.attendance.auth.po.SysFrontendVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * (SysFrontendMenuTable)表数据库访问层
 * 该类由EasyCode工具生成
 * @author 小明哥
 * @since 2020-03-07 13:42:36
 */
@Repository
public interface SysFrontendMenuTableDao {

    @Select("SELECT \n" +
            "  b.frontend_menu_id,\n" +
            "  a.frontend_menu_name parentName,\n" +
            "  b.pid,\n" +
            "  b.frontend_menu_name,\n" +
            "  b.frontend_menu_sort,\n" +
            "  b.frontend_menu_url,\n" +
            "  b.description\n" +
            "FROM sys_frontend_menu a \n" +
            "right join sys_frontend_menu b on b.pid =a.frontend_menu_id\n" +
            "order by b.frontend_menu_sort asc")
    /**
     * 查所有的前端菜单（编辑使用）
     */
    List<SysFrontendVo> getAllMenuList();


    @Select("select distinct\n" +
            "     a.frontend_menu_id,\n" +
            "     a.frontend_menu_name,\n" +
            "     a.pid,\n" +
            "\t a.frontend_menu_url,\n" +
            "     a.frontend_menu_sort\n" +
            "from sys_frontend_menu a,\n" +
            "     sys_role b,\n" +
            "     sys_role_frontend_menu c,\n" +
            "     sys_user d,\n" +
            "     sys_role_user e\n" +
            "where ( a.frontend_menu_id = c.frontend_menu_id or c.frontend_menu_id='*')\n" +
            "\t and b.role_id = c.role_id\n" +
            "     and d.user_id = e.user_id\n" +
            "     and e.role_id = c.role_id\n" +
            "     and d.user_name=#{username}\n" +
            "order by a.frontend_menu_sort asc")
    /**
     * 根据登录账号，获得前端展现的菜单
     * 控制前端菜单的权限
     * @param username
     * @return
     */
    List<SysFrontendMenuTable> getMenusByUserName(@Param("username") String username);
}