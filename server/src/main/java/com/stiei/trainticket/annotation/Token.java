package com.stiei.trainticket.annotation;

import java.lang.annotation.*;

/**
 * 是否带token访问, 源码级标记
 */
//@Deprecated
@Target(ElementType.METHOD)
//@Retention(RetentionPolicy.RUNTIME)
@Retention(RetentionPolicy.SOURCE)
@Documented
public @interface Token {

    boolean value() default true;

}