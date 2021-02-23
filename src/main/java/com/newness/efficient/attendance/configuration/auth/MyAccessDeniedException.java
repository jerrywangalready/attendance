package com.newness.efficient.attendance.configuration.auth;

import org.springframework.security.access.AccessDeniedException;

public class MyAccessDeniedException extends AccessDeniedException {


    public MyAccessDeniedException(String msg) {
        super(msg);
    }
}
