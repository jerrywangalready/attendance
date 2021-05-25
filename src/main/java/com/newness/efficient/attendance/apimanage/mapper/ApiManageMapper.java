package com.newness.efficient.attendance.apimanage.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ApiManageMapper {
    List<Map<String, String>> getApiInfo(Map<String, String> param);

    int deleteApiInfo(String backendApiId);
}
