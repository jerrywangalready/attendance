package com.newness.efficient.attendance.system.security.service;

import com.newness.efficient.attendance.system.security.mapper.SecurityMapper;
import com.newness.efficient.attendance.system.security.model.Menu;
import com.newness.efficient.attendance.system.apimanage.entity.SysApiEntity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

@Service
public class PermissionServiceImpl implements PermissionService {

    @Resource
    private SecurityMapper securityMapper;

    @Override
    public List<Menu> queryAllMenu() {
        return null;
    }

    @Override
    public List<Menu> getMenuByUsername(String username) {
        return null;
    }

    @Override
    public List<SysApiEntity> getPermissionsByUser(String username) {
        if ("admin".equals(username)) {
            SysApiEntity sysApiEntity = new SysApiEntity();
            sysApiEntity.setApiPath("/**");
            sysApiEntity.setApiMethod("GET,POST,UPDATE,DELETE");
            return Collections.singletonList(sysApiEntity);
        } else {
            return securityMapper.getAuthoritiesByUser(username);
        }
    }
}
