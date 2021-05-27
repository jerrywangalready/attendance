package com.newness.efficient.attendance.apimanage.service;

import com.newness.efficient.attendance.apimanage.entity.SysBackendApi;

import java.util.List;
import java.util.Map;

public interface ApiManageService {

    List<Map<String, String>> getApiInfo(Map<String, String> param);

    int deleteApiInfo(String backendApiId);

    boolean saveApiInfo(SysBackendApi sysBackendApi);

    SysBackendApi getBackendApiById(String backendApiId);
}
