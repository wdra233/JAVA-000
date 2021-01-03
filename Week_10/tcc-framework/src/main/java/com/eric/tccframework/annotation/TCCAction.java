package com.eric.tccframework.annotation;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TCCAction {
    public String tryMethod();

    public String confirmMethod();

    public String cancelMethod();
}
