package com.newness.efficient.attendance.module.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ModuleMapper {
    List<Map<String, String>> getModuleGrid(Map<String, String> param);
}
