package com.newness.efficient.attendance.apimanage.mapper;

import com.newness.efficient.attendance.apimanage.entity.SysBackendApi;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ApiManageMapper {
    List<Map<String, String>> getApiInfo(Map<String, String> param);

    int deleteByPrimaryKey(String backendApiId);

    int insert(SysBackendApi record);

    int insertSelective(SysBackendApi record);

    SysBackendApi selectByPrimaryKey(String backendApiId);

    int updateByPrimaryKeySelective(SysBackendApi record);

    int updateByPrimaryKey(SysBackendApi record);
}
