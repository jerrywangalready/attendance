package com.newness.efficient.attendance.module.service;

import com.newness.efficient.attendance.module.mapper.ModuleMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class ModuleServiceImpl implements ModuleService{

    @Resource
    private ModuleMapper moduleMapper;

    @Override
    public List<Map<String, String>> getModuleGrid(Map<String, String> param) {
        return moduleMapper.getModuleGrid(param);
    }
}
