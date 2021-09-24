package com.newness.efficient.attendance.system.security.utils;

import com.newness.efficient.attendance.utils.crypt.SM4Utils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DefaultPasswordEncoder implements PasswordEncoder {

    public DefaultPasswordEncoder() {
        this(-1);
    }

    public DefaultPasswordEncoder(int strength) {
    }

    //进行SM2加密
    @Override
    public String encode(CharSequence charSequence) {
        // todo 调用sm2工具进行加密
        String key = null;
        try {
            key = "cdca15a5f40c2c31931c5cb3dbecfc40";
            return SM4Utils.encryptEcb(key, charSequence.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    //进行密码比对
    @Override
    public boolean matches(CharSequence charSequence, String encodedPassword) {

        return encodedPassword.equals(charSequence.toString());
    }
}
