package com.example.wuzp.secondworld.view.DesignModel.creator.AbstractFactory;

/**
 * Created by wuzp on 2017/5/21.
 */
public abstract class Car {
    //定义的一个抽象工厂类
    public String name = "Car";

    abstract Part getWheels();
    abstract Part getEngines();
    abstract Part getBody();

    public void show(){
        System.out.println("-------------------------------------------");
        System.out.println("Car name:" + name);
        System.out.println("wheels:" + getWheels().getName());
        System.out.println("engines:" + getEngines().getName());
        System.out.println("body:" + getBody().getName());
        System.out.println("-------------------------------------------");
    }
}
