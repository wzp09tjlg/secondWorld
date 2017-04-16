package com.example.wuzp.secondworld.annotation;

/**
 * Created by wuzp on 2017/4/16.
 * 测试自定义的注解的类
 * //注解现在还是没有看懂 之后再继续看
 */
@MyInterface1(host = Host.HOST_1,url = "abcd1234",isCache = false)
public class TestMyInterface {

    @MyInterface(key = "good")
    private String path;

    public void show( String path){
    }
}
