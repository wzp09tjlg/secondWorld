package com.example.wuzp.secondworld.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by wuzp on 2017/4/16.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface MyInterface1 {
    //接口地址
    String url();

    //端口
    Host host() default Host.HOST_1;

    //缓存
    boolean isCache() default false;
}
