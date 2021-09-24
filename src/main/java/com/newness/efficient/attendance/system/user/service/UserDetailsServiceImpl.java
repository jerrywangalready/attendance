package com.newness.efficient.attendance.system.user.service;

import com.newness.efficient.attendance.system.auth.mapper.SysUserMapper;
import com.newness.efficient.attendance.system.security.model.SecurityUser;
import com.newness.efficient.attendance.system.apimanage.entity.SysApiEntity;
import com.newness.efficient.attendance.system.security.service.PermissionService;
import com.newness.efficient.attendance.system.user.model.BaseUser;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Resource
    private SysUserMapper sysUserMapper;

    @Resource
    private PermissionService permissionService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // 数据库取出用户基本信息
        BaseUser user = sysUserMapper.getUserByUsernameNState(username, 1);

        if (user == null) {
            // 不存在此用户
            throw new UsernameNotFoundException("用户名不存在!");
        }

//        BeanUtils.copyProperties(userByUsernameNState, user);

        // 数据库获取权限信息
        List<SysApiEntity> permissions = permissionService.getPermissionsByUser(username);
        user.setPermissions(permissions);

        SecurityUser securityUser = new SecurityUser(user);
//        securityUser.setPermissions(permissions);
        return securityUser;
    }
}
