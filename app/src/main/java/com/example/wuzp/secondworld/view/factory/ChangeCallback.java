package com.example.wuzp.secondworld.view.factory;

/**
 * Created by wuzp on 2017/6/13.
 * 定义的一个接口 在实现类中 是持有实例类的一个引用，因为这个接口能访问到上下文的资源信息，
 * 所以必然会存在上下文的一个引用(一个类只有持有上下文的引用 才能获得对资源的引用，
 * 不然什么也做不了，就只是空空的一个 计算方法，就如同一套好不错的设备，没有原材料，什么工作也做不了。
 * 只有真正的有设备 和 原材料才能真正的去做事情，出产品和成绩)
 */
public interface ChangeCallback {

    void callBackOne();

    void callBackTwo();

    void callBackThree();
}
