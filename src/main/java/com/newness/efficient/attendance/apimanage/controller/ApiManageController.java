package com.newness.efficient.attendance.apimanage.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.newness.efficient.attendance.apimanage.entity.SysBackendApi;
import com.newness.efficient.attendance.apimanage.service.ApiManageService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/apiManage")
public class ApiManageController {

    @Resource
    private ApiManageService apiManageService;

    @PostMapping("/getApiGrid")
    public PageInfo<Map<String, String>> getApiGrid(@RequestBody Map<String, String> param) {
        PageHelper.startPage(param);
        List<Map<String, String>> list = apiManageService.getApiInfo(param);
        return new PageInfo<>(list);
    }

    @PostMapping("/deleteApiInfo")
    public int deleteApiInfo(@RequestBody SysBackendApi sysBackendApi) {
        return apiManageService.deleteApiInfo(sysBackendApi.getBackendApiId());
    }

    @PostMapping("/saveApiInfo")
    public boolean saveApiInfo(@RequestBody SysBackendApi sysBackendApi) {
        return apiManageService.saveApiInfo(sysBackendApi);
    }

    @PostMapping("/getBackendApiById")
    public SysBackendApi getBackendApiById(String backendApiId) {
        return apiManageService.getBackendApiById(backendApiId);
    }

}
