package com.newness.efficient.attendance.system.apimanage.service;

import com.newness.efficient.attendance.system.apimanage.bo.ApiBo;
import com.newness.efficient.attendance.system.apimanage.bo.ApiFormBo;
import com.newness.efficient.attendance.system.apimanage.entity.SysApiEntity;

import java.util.List;
import java.util.Map;

public interface ApiManageService {

    List<Map<String, String>> getApiInfo(ApiBo param);

    String save(SysApiEntity sysApiEntity);

    boolean delete(SysApiEntity sysApiEntity);

    boolean saveMenuApi(ApiFormBo apiFormBo);

    List<Integer> getMenuIdsByApiId(String apiId);
}
