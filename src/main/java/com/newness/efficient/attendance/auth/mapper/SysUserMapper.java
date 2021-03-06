package com.newness.efficient.attendance.auth.mapper;

import com.newness.efficient.attendance.auth.entity.SysUserEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SysUserMapper {

    SysUserEntity getUserByUsernameNState(String username, int state);

    boolean saveUser(SysUserEntity sysUserEntity);
}
