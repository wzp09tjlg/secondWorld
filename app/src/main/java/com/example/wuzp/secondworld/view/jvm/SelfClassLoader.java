package com.example.wuzp.secondworld.view.jvm;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.FileInputStream;

/**
 * Created by wuzp on 2017/6/10.
 */

public class SelfClassLoader extends ClassLoader {
    private String byteCode_path;

    public SelfClassLoader(String byteCode_path){
        this.byteCode_path = byteCode_path;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] value = null;
        BufferedInputStream bis = null;
        try{
            bis = new BufferedInputStream(new FileInputStream(byteCode_path + name + ".class"));
            value = new byte[bis.available()];
            bis.read(value);
        }catch (Exception e){
             e.printStackTrace();
        }finally {
            if(null != bis){
                try {
                    bis.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }

        //将byte数组转换成一个Class 对象实例
        return defineClass(value,0,value.length);
    }

    public static void main(String[] args) throws  Exception{
        SelfClassLoader selfClassLoader = new SelfClassLoader("D:/");
        show("目标类加载类的类加载器:\n  "
                + selfClassLoader.loadClass("TestClass").getClassLoader().getClass().getName());
        show("当前类加载器的超类的类加载器:\n" + selfClassLoader.getParent().getClass().getName());


        //结果是 SelfClassLoader
        //       AppClassLoader
    }

    private static void show(String msg){
        if(!msg.isEmpty()){
            Log.i("wzp","msg:" + msg);
        }
    }
}
