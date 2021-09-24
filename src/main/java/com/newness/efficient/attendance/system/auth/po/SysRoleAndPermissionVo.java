package com.newness.efficient.attendance.system.auth.po;

import lombok.Data;

/**
 * 角色和鉴权资源的VO
 * 如：角色和用户VO
 * 角色和API
 * 角色和前端菜单
 * 他们都可以使用该VO来转换并展现给前端
 */
@Data
public class SysRoleAndPermissionVo {
    String id;
    String name;
    String roleId;
    String pid;

}
