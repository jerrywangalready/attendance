package com.newness.efficient.attendance.apimanage.service;

import com.newness.efficient.attendance.apimanage.entity.SysBackendApi;
import com.newness.efficient.attendance.apimanage.mapper.ApiManageMapper;
import com.newness.efficient.attendance.utils.IdCreator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class ApiManageServiceImpl implements ApiManageService {

    @Resource
    private ApiManageMapper apiManageMapper;

    @Override
    public List<Map<String, String>> getApiInfo(Map<String, String> param) {
        return apiManageMapper.getApiInfo(param);
    }

    @Override
    public int deleteApiInfo(String backendApiId) {
        return apiManageMapper.deleteByPrimaryKey(backendApiId);
    }

    @Override
    public void saveApiInfo(SysBackendApi sysBackendApi) {
        if (sysBackendApi.isBackendApiIdBlank()) {
            sysBackendApi.setBackendApiId(IdCreator.getId());
            apiManageMapper.insert(sysBackendApi);
        } else {
            apiManageMapper.updateByPrimaryKey(sysBackendApi);
        }
    }
}
