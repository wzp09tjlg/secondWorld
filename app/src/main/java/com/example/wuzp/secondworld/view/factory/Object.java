package com.example.wuzp.secondworld.view.factory;

import android.util.Log;

/**
 * Created by wuzp on 2017/6/13.
 * //你也可以定义这个类 ，但是这个类 也会在jvm 链接的时候集成object类
 *
 */
public class Object {

    private String name;

    public Object(){
        this.name = "wzp";
    }

    public Object(String name){
        this.name = name;
    }

    @Override
    public boolean equals(java.lang.Object obj) {
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return super.toString();
    }

    public void show(){
        Log.e("wzp","name:" + name);
    }
}
