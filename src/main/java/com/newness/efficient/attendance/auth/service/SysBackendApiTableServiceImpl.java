package com.newness.efficient.attendance.auth.service;

import com.newness.efficient.attendance.auth.entity.SysBackendApiTable;
import com.newness.efficient.attendance.auth.mapper.SysBackendApiTableDao;
import com.newness.efficient.attendance.auth.po.SysBackendApiVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * (SysBackendApiTable)表服务实现类
 * 该类由EasyCode工具生成
 *
 * @author 小明哥
 * @since 2020-03-07 13:46:35
 */
@Service("sysBackendApiTableService")
public class SysBackendApiTableServiceImpl implements SysBackendApiTableService {

    @Autowired
    private SysBackendApiTableDao sysBackendApiTableDao;

    /**
     * 根据角色查询API接口URL
     *
     * @param roles
     * @return
     */
    @Override
    public List<SysBackendApiTable> getApiUrlByRoles(String... roles) {
        return sysBackendApiTableDao.getApiUrlByRoles(roles);
    }

    /**
     * 根据用户名称查询API接口URL
     *
     * @param username
     * @return
     */
    @Override
    public List<SysBackendApiTable> getApiUrlByUserName(String username) {
        return sysBackendApiTableDao.getApiUrlByUserName(username);
    }

    /**
     * 查所有（编辑使用）
     */
    @Override
    public List<SysBackendApiVo> getAllApiList() {
        return sysBackendApiTableDao.getAllApiList();
    }
}