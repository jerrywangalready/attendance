package com.newness.efficient.attendance.utils;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

/**
 * @Description 加密
 * @Author ***
 * @Date 2020/2/27 12:37
 * @Version 1.0
 **/
public class Argon2PasswordEncoder{
    private static final Argon2 ARGON2 = Argon2Factory.create();

    private static final int ITERATIONS = 2;
    private static final int MEMORY = 65536;
    private static final int PARALLELISM = 1;

    public static String encode(CharSequence rawPassword) {
        return ARGON2.hash(ITERATIONS, MEMORY, PARALLELISM, rawPassword.toString());
    }

    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return ARGON2.verify(encodedPassword, rawPassword.toString());
    }

    public static void main(String[] args) {
        String hashedPassword = Argon2PasswordEncoder.encode("123456");
        System.out.println(hashedPassword);
    }

}