package com.newness.efficient.attendance.user.annotation;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface User {

    String value() default "";

}
