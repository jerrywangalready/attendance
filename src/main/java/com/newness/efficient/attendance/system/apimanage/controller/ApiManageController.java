package com.newness.efficient.attendance.system.apimanage.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.newness.efficient.attendance.system.apimanage.bo.ApiBo;
import com.newness.efficient.attendance.system.apimanage.bo.ApiFormBo;
import com.newness.efficient.attendance.system.apimanage.entity.SysApiEntity;
import com.newness.efficient.attendance.system.apimanage.service.ApiManageService;
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
    public PageInfo<Map<String, String>> getApiGrid(@RequestBody ApiBo param) {
        PageHelper.startPage(param);
        List<Map<String, String>> list = apiManageService.getApiInfo(param);
        return new PageInfo<>(list);
    }

    @PostMapping("/save")
    public boolean save(@RequestBody ApiFormBo sysApiEntity) {
        apiManageService.save(sysApiEntity);
        apiManageService.saveMenuApi(sysApiEntity);
        return true;
    }

    @PostMapping("/delete")
    public boolean delete(@RequestBody SysApiEntity sysApiEntity) {
        return apiManageService.delete(sysApiEntity);
    }

    @GetMapping("/getMenuIdsByApiId")
    public List<Integer> getMenuIdsByApiId(@RequestParam("apiId") String apiId) {
        return apiManageService.getMenuIdsByApiId(apiId);
    }
}
