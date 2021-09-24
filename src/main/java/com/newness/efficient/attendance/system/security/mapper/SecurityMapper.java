package com.newness.efficient.attendance.system.security.mapper;

import com.newness.efficient.attendance.system.apimanage.entity.SysApiEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SecurityMapper {

    List<SysApiEntity> getAuthoritiesByUser(String username);
}
