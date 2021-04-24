package com.newness.efficient.attendance.apimanage.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.newness.efficient.attendance.apimanage.service.ApiManageService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
