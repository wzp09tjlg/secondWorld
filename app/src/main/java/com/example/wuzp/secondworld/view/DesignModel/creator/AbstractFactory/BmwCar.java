package com.example.wuzp.secondworld.view.DesignModel.creator.AbstractFactory;

/**
 * Created by wuzp on 2017/5/21.
 */

public class BmwCar extends Car {
    //抽象工厂类的子类
    private String name = "BmwCar";

    public BmwCar(String name){
        this.name = name;
    }

    @Override
    Part getWheels() {
        Part part = new Part();
        part.setName(name + " Wheels");
        return part;
    }

    @Override
    Part getEngines() {
        Part part = new Part();
        part.setName(name + " Engines");
        return part;
    }

    @Override
    Part getBody() {
        Part part = new Part();
        part.setName(name + "  Body");
        return part;
    }
}
