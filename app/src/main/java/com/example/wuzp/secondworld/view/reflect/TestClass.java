package com.example.wuzp.secondworld.view.reflect;

/**
 * Created by wuzp on 2017/5/21.
 */
public class TestClass {
    private String name = "defualt class";

    public TestClass() {
    }

    public TestClass(String name){
        this.name = name;
    }

    public void show(){
        System.out.println("name:" + name);
    }
}
