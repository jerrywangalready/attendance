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
    public boolean saveApiInfo(SysBackendApi sysBackendApi) {
        int result;
        sysBackendApi.setBackendApiSort(0);
        if (sysBackendApi.isBackendApiIdBlank()) {
            sysBackendApi.setBackendApiId(IdCreator.getId());
            result = apiManageMapper.insert(sysBackendApi);
        } else {
            result = apiManageMapper.updateByPrimaryKey(sysBackendApi);
        }
        return result > 0;
    }

    @Override
    public SysBackendApi getBackendApiById(String backendApiId) {
        return apiManageMapper.selectByPrimaryKey(backendApiId);
    }

}
