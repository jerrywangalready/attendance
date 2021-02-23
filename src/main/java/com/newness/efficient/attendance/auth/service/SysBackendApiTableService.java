package com.newness.efficient.attendance.auth.service;

import com.newness.efficient.attendance.auth.entity.SysBackendApiTable;
import com.newness.efficient.attendance.auth.po.SysBackendApiVo;

import java.util.List;

/**
 * (SysBackendApiTable)表服务接口
 * 该类由EasyCode工具生成
 * @author 小明哥
 * @since 2020-03-07 13:46:34
 */
public interface SysBackendApiTableService {

    /**
     * 根据角色查询API接口URL
     * @param roles
     * @return
     */
    List<SysBackendApiTable> getApiUrlByRoles(String ...roles);

    /**
     * 根据用户名称查询API接口URL
     * @param username
     * @return
     */
    List<SysBackendApiTable> getApiUrlByUserName(String username);

    /**
     * 查所有（编辑使用）
     */
    List<SysBackendApiVo> getAllApiList();
}