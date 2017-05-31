package com.example.wuzp.secondworld.view.reflect;

import android.support.annotation.Nullable;

import org.jetbrains.annotations.Contract;

import java.lang.reflect.Constructor;

/**
 * Created by wuzp on 2017/5/21.
 * 一个Java类就是这样的实现，一个主方法 共有 静态 字符串参数 名字就是main
 * 要执行什么 需要按照一定的规则去走。
 * Java是一门很强大的语言，所有执行的东西 都是在这个语言框架中执行。
 * 要是逃脱框架之下 那就无从便利之说了
 * <p>
 * 这里主要测试下 Java中的反射机制，使用反射来获取Java中的对象
 */
public class main {

    public static void main(String[] args) {

        //现在进行检测 获取testClass 的对象
        //1.直接通过new的方式 来获取对象
        TestClass testClass1 = new TestClass();
        testClass1.show();
        show();

        //2.通过类的类型获取对象
        TestClass testClass2 = getClass(TestClass.class);
        testClass2.show();
        show();

        //3.通过类的名字 获取对象
        TestClass testClass3 = getClass("com.example.wuzp.secondworld.view.reflect.TestClass");
        testClass3.show();
        show();

        TestClass testClass4 = getClass("com.example.wuzp.secondworld.view.reflect.TestClass",
                new Class[]{String.class},new String[]{"get some class"});
        testClass4.show();
        show();
    }

    private static void show(){
        System.out.println("-----------------------------------------------------");
        System.out.println("-----------------------------------------------------");
    }

    /********************************************************/
    //class.newInstance();这种方式来获取一个对象
    @Contract("null -> null")
    public static <T> T getClass(Class<T> clazz){
        if(clazz == null)
            return null;
        T t = null;
        try{
          t = clazz.newInstance();
        }catch (Exception e){
            System.out.println("class is not find");
        }
        return t;
    }

    //class.forName(String); 只要知道类的名字，就能获取类对象，然后在创建类的对象 就可以了
    @Nullable
    public static TestClass getClass(String className){
        if("".equals(className))
            return null;
        Class clazz = null;
        try{
            clazz = Class.forName(className);
        }catch (Exception e){}

        TestClass instance = null;
        if(clazz != null){
            try{
                instance = (TestClass) clazz.newInstance();
            }catch (Exception e){}
        }
        return instance;
    }

    @Nullable
    public static TestClass getClass(String name, Class[] classParas, Object[] objParas){
        if("".equals(name))
            return null;

        Class clazz = null;
        try{
            clazz = Class.forName(name);
        }catch (Exception e){}
        if(clazz == null) return null;
        TestClass instance = null;
        try{
            //获取构造函数(携带参数,反射会自动匹配构造函数中 的构造方法)
            Constructor constructor = clazz.getConstructor(classParas);
            instance = (TestClass)constructor.newInstance(objParas);
        }catch (Exception e){}
        return instance;
    }
}
