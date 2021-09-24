package com.newness.efficient.attendance.system.apimanage.bo;

import com.newness.efficient.attendance.page.bo.PageBo;
import lombok.Data;

//@EqualsAndHashCode(callSuper = true)
@Data
public class ApiBo extends PageBo {

    private String apiId;

    private String apiName;

    private String apiPath;

}


