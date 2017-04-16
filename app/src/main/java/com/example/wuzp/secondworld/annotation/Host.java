package com.example.wuzp.secondworld.annotation;

/**
 * Created by wuzp on 2017/4/16.
 * 定义的枚举类型的数据
 */
public enum Host{
    HOST_1("baidu.com"),HOST_2("sina.com");

    private String host = "";
    Host(String host){
        this.host = host;
    }

    public String getHost(){
        return host;
    }
}
