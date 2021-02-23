package com.newness.efficient.attendance.auth.service;

import com.newness.efficient.attendance.auth.entity.SysFrontendMenuTable;
import com.newness.efficient.attendance.auth.mapper.SysFrontendMenuTableDao;
import com.newness.efficient.attendance.auth.po.SysFrontendVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * (SysFrontendMenuTable)表服务实现类
 * 该类由EasyCode工具生成
 * @author 小明哥
 * @since 2020-03-07 13:42:36
 */
@Service("sysFrontendMenuTableService")
public class SysFrontendMenuTableServiceImpl implements SysFrontendMenuTableService {

    @Autowired
    SysFrontendMenuTableDao sysFrontendMenuTableDao;

    /**
     * 查所有的前端菜单（编辑使用）
     */
    @Override
   public List<SysFrontendVo> getAllMenuList(){
       return sysFrontendMenuTableDao.getAllMenuList();
    }

    /**
     * 根据登录账号，获得前端展现的菜单
     * 控制前端菜单的权限
     * @param username
     * @return
     */
    @Override
    public List<SysFrontendMenuTable> getMenusByUserName(String username){
        return sysFrontendMenuTableDao.getMenusByUserName(username);
    }
}