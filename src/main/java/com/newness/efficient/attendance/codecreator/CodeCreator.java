package com.newness.efficient.attendance.codecreator;

import com.newness.efficient.attendance.codecreator.model.Code;

public class CodeCreator {

    private Code code;

    public CodeCreator(Code code) {
        this.code = code;
    }

    public boolean create() {


        return true;
    }

    private void u() {

    }

    public static void main(String[] args) {
        Code code = new Code("com.newness.efficient.attendance.system.menu", "menu");
        CodeCreator codeCreator = new CodeCreator(code);
        codeCreator.create();
    }
}
