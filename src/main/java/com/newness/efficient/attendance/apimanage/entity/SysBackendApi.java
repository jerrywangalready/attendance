package com.newness.efficient.attendance.apimanage.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * sys_backend_api
 * @author 
 */
@Data
public class SysBackendApi implements Serializable {
    /**
     * 主键
     */
    private String backendApiId;

    /**
     * API名称
     */
    private String backendApiName;

    /**
     * API请求地址
     */
    private String backendApiUrl;

    /**
     * API请求方式：GET、POST、PUT、DELETE
     */
    private String backendApiMethod;

    /**
     * 父ID
     */
    private String pid;

    /**
     * 排序
     */
    private Integer backendApiSort;

    /**
     * 描述
     */
    private String description;

    private String moduleId;

    private static final long serialVersionUID = 1L;

    public boolean isBackendApiIdBlank() {
        return this.backendApiId == null || this.backendApiId.isEmpty();
    }
}