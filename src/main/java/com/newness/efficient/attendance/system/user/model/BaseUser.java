package com.newness.efficient.attendance.system.user.model;

import com.newness.efficient.attendance.system.security.model.SecurityUser;
import com.newness.efficient.attendance.system.apimanage.entity.SysApiEntity;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class BaseUser implements Serializable {

    private static final long serialVersionUID = 1L;

    private String userId;

    private String username;

    private String password;

    private String fullName;

    private Integer status;

    private String token;

    private String description;

    private List<SysApiEntity> permissions;

    public BaseUser() {
    }

    public BaseUser(SecurityUser securityUser) {
        this.username = securityUser.getUsername();
        this.permissions = securityUser.getPermissions();
    }

    public SecurityUser toSecurityUser() {
        return new SecurityUser(this);
    }

}