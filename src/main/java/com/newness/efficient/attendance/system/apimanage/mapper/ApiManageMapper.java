package com.newness.efficient.attendance.system.apimanage.mapper;

import com.newness.efficient.attendance.system.apimanage.bo.ApiBo;
import com.newness.efficient.attendance.system.apimanage.entity.SysApiEntity;
import com.newness.efficient.attendance.system.apimanage.entity.SysMenuApiEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ApiManageMapper {

    List<Map<String, String>> getApiInfo(ApiBo param);

    int addApiInfo(SysApiEntity sysApiEntity);

    int updateApiInfo(SysApiEntity sysApiEntity);

    int deleteApiById(String id);

    int addMenuApi(SysMenuApiEntity sysMenuApi);

    int deleteMenuApi(SysMenuApiEntity sysMenuApi);

    List<Integer> getMenuIdsByApiId(String apiId);
}
