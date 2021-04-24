package com.newness.efficient.attendance.module.service;

import java.util.List;
import java.util.Map;

public interface ModuleService {
    List<Map<String, String>> getModuleGrid(Map<String, String> param);
}
