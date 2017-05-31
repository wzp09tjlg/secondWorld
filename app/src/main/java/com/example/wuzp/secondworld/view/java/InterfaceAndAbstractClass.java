package com.example.wuzp.secondworld.view.java;

/**
 * Created by wuzp on 2017/5/26.
 * 这个类是 主要是说明下 抽象类和接口
 */
public class InterfaceAndAbstractClass {
    private void testAbstractClass(){
        /***
         * 抽象类不能被实例化 可能包含抽象方法，有抽象方法的类一定是抽象类 是抽象类不一定包含抽象方法
         * abstract class testAbsract{
         *     ... ...
         * }
         * 抽象类不能被实例化 是指的 不能直接通过new 类名;这样的方式来实例化 一个抽象类，可以实例化抽象类时
         * 实现抽象方法(其实这是应该说是实现一个匿名内部类对象，但是却持有了这个匿名内部类对象的引用)
         *
         * 抽象类中的属性默认是具有包访问权限，可以定义任何的访问权限的属性
         * 也可以定义构造方法，但是这个构造方法只能用在继承子类的构造方法中，实现数据的初始化
         * */
    }

    private void testInterface(){
        /**
         * 在Java8之前 接口中是不能定义 默认方法 和静态方法的，在Java8开始 可以定义这两种方法体
         * 定义的属性都是 共有静态常量
         * 方法也是共有的方法
         *
         * Java8中定义的默认方法 需要defualt修饰 定义static方法 这些方法都是只能在接口的实现类调用
         * ，使用接口是不能调用这些方法的
         * */
    }

    private void testDiffOfInterfaceAndAbstract(){
     /***
      * 接口和抽象类的区别
      * */
    }

}
