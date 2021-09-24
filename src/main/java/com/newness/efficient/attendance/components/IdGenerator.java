package com.newness.efficient.attendance.components;

import java.util.UUID;

public class IdGenerator {

    public static String getId() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
