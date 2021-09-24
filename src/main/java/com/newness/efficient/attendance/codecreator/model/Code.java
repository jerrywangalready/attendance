package com.newness.efficient.attendance.codecreator.model;

import lombok.Data;

@Data
public class Code {

    private String path;

    private String keyName;

    private String keyNameUpper;

    public Code(String path, String keyName) {
        this.path = path;
        this.keyName = keyName;
        this.keyNameUpper = keyName.substring(0, 1).toUpperCase() + keyName.substring(1, keyName.length() - 1);
    }

}
