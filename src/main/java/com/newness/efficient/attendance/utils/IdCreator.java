package com.newness.efficient.attendance.utils;

import java.util.UUID;

public class IdCreator {

     public static String getId() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
