package com.newness.efficient.attendance.system.security.model;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

@Data
public class Menu {

    private String menuId;

    private String menuName;

    private List<Menu> children;

    private List<GrantedAuthority> authorities;

    private GrantedAuthority authority;

}
