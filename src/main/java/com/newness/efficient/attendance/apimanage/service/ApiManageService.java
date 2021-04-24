package com.newness.efficient.attendance.apimanage.service;

import java.util.List;
import java.util.Map;

public interface ApiManageService {

    List<Map<String, String>> getApiInfo(Map<String, String> param);

}
