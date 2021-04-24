package com.newness.efficient.attendance.module.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.newness.efficient.attendance.module.service.ModuleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/module")
@Slf4j
public class ModuleController {

    @Resource
    private ModuleService moduleService;

    @PostMapping("/getModuleGrid")
    public PageInfo<Map<String, String>> getModuleGrid(@RequestBody Map<String, String> param) {
        PageHelper.startPage(param);
        List<Map<String, String>> list = moduleService.getModuleGrid(param);
        return new PageInfo<>(list);
    }
}
