package com.example.wuzp.secondworld.view.DesignModel.creator.FactoryMethod;

/**
 * Created by wuzp on 2017/5/14.
 */
public class Cat implements Pet {
    //具体产品类(实例化工厂方法模式的派生类)
    @Override
    public String getSound() {
        return "Meaw";
    }
}
