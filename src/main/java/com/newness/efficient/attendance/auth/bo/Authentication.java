package com.newness.efficient.attendance.auth.bo;

import com.newness.efficient.attendance.auth.po.User;
import lombok.Data;

@Data
public class Authentication {

    private String dataType = "Authentication";

    private String username;

    private User user;

    private String token;

    private boolean state;

    private String msg;


}
