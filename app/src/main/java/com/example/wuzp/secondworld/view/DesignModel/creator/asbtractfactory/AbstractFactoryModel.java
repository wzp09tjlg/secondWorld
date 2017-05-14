package com.example.wuzp.secondworld.view.DesignModel.creator.asbtractfactory;

/**
 * Created by wuzp on 2017/5/14.
 */
public class AbstractFactoryModel {

    //使用工厂方法模式
    public static void main(String[] args){
        //创建工厂
        PetFactory factory = new PetFactory();

        //实例化一个对象
        Pet pet1 = factory.getPet("Bow");
        showSound(pet1.getSound());

        //实例化一个对象
        Pet pet2 = factory.getPet("Meaw");
        showSound(pet2.getSound());
    }

    //你并不知道会创建那种对象
    private static void showSound(String sound){
        System.out.println("" + sound);
    }

}
