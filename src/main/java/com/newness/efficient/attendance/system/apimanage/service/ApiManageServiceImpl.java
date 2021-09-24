package com.newness.efficient.attendance.system.apimanage.service;

import com.newness.efficient.attendance.system.apimanage.bo.ApiBo;
import com.newness.efficient.attendance.system.apimanage.bo.ApiFormBo;
import com.newness.efficient.attendance.system.apimanage.entity.SysApiEntity;
import com.newness.efficient.attendance.system.apimanage.entity.SysMenuApiEntity;
import com.newness.efficient.attendance.system.apimanage.mapper.ApiManageMapper;
import com.newness.efficient.attendance.components.IdGenerator;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class ApiManageServiceImpl implements ApiManageService {

    @Resource
    private ApiManageMapper apiManageMapper;

    @Override
    public List<Map<String, String>> getApiInfo(ApiBo param) {
        return apiManageMapper.getApiInfo(param);
    }

    @Override
    public String save(SysApiEntity sysApiEntity) {
        String id;
        if (StringUtils.hasText(sysApiEntity.getApiId())) {
            id = sysApiEntity.getApiId();
            apiManageMapper.updateApiInfo(sysApiEntity);
        } else {
            id = IdGenerator.getId();
            sysApiEntity.setApiId(IdGenerator.getId());
            apiManageMapper.addApiInfo(sysApiEntity);
        }
        return id;
    }

    @Override
    public boolean delete(SysApiEntity sysApiEntity) {
        return apiManageMapper.deleteApiById(sysApiEntity.getApiId()) > 0;
    }

    @Override
    public boolean saveMenuApi(ApiFormBo apiFormBo) {
        // 删除该apiId对应的菜单关系数据
        SysMenuApiEntity entity = new SysMenuApiEntity();
        entity.setApiId(apiFormBo.getApiId());
        apiManageMapper.deleteMenuApi(entity);
        // 重新插入菜单和api的关系数据
        apiFormBo.getMenuIds().forEach(menuId -> {
            SysMenuApiEntity menuApi = new SysMenuApiEntity();
            menuApi.setApiId(apiFormBo.getApiId());
            menuApi.setMenuId(menuId);
            apiManageMapper.addMenuApi(menuApi);
        });

        return true;
    }

    @Override
    public List<Integer> getMenuIdsByApiId(String apiId) {
        return apiManageMapper.getMenuIdsByApiId(apiId);
    }


}
