package com.newness.efficient.attendance.auth.mapper;

import com.newness.efficient.attendance.auth.entity.SysUserEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface SysUserMapper {

    SysUserEntity getUserByUsernameNState(String username, int state);

    boolean saveUser(SysUserEntity sysUserEntity);
}
