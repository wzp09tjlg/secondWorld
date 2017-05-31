package com.example.wuzp.secondworld.view.DesignModel.creator.FactoryMethod;

/**
 * Created by wuzp on 2017/5/14.
 */
public class PetFactory {
    //工厂方法模式实现，并展现对象创建逻辑
    public Pet getPet(String sound){
        Pet pet = null;
        //工厂实例化对象的基本逻辑
        if("Meaw".equals(sound)){
            pet = new Cat();
        }else if("Bow".equals(sound)){
            pet = new Dog();
        }
        return pet;
    }
}
