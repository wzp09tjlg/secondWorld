package com.example.wuzp.secondworld.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by wuzp on 2017/4/16.
 * 自定义 注解 @interface 定义注解
 */
@Retention(RetentionPolicy.RUNTIME) //在什么级别保存该注释信息 三种 SOURCE CLASS RUNTIME
@Target(ElementType.FIELD)
public @interface MyInterface {
    String key();
}
