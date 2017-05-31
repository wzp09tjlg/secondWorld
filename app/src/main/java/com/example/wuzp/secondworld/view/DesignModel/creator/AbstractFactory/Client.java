package com.example.wuzp.secondworld.view.DesignModel.creator.AbstractFactory;

/**
 * Created by wuzp on 2017/5/21.
 */
public class Client {

    public static void main(String[] args){
        //客户端上 创建一个宝马的车
       Car bmwCar = new BmwCar("BmwCar");
       bmwCar.show();

        //再添加一个奔驰的车 只要添加相应的类就可以了，client可以很简洁
       Car benzCar = new BenzCar("BenzCar");
        benzCar.show();
    }

}
