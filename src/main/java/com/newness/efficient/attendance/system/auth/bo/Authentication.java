package com.newness.efficient.attendance.system.auth.bo;

import com.newness.efficient.attendance.system.user.model.BaseUser;
import lombok.Data;

@Data
public class Authentication {

    private String dataType = "Authentication";

    private String username;

    private BaseUser user;

    private String token;

    private boolean state;

    private String msg;


}
