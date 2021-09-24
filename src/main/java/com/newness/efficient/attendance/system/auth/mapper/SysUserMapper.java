package com.newness.efficient.attendance.system.auth.mapper;

import com.newness.efficient.attendance.system.user.model.BaseUser;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SysUserMapper {

    BaseUser getUserByUsernameNState(String username, int state);

    boolean saveUser(BaseUser user);
}
