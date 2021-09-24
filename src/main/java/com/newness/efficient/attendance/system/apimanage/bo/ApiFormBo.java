package com.newness.efficient.attendance.system.apimanage.bo;

import com.newness.efficient.attendance.system.apimanage.entity.SysApiEntity;
import lombok.Data;

import java.util.List;

@Data
public class ApiFormBo extends SysApiEntity {

    private List<Integer> menuIds;
}
