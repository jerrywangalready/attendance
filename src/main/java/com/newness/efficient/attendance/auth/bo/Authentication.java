package com.newness.efficient.attendance.auth.bo;

import lombok.Data;

@Data
public class Authentication {

    private String sid;

    private String username;

    private String token;

    private boolean state;

    private String msg;


}
