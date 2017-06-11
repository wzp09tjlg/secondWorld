package com.example.wuzp.secondworld.view.jvm;

import android.util.Log;
//import sun.text.resources.CollectionData_ar;

/**
 * Created by wuzp on 2017/6/10.
 */
public class ClassLoaderTest {

    public static void main(String[] args){
      //加载JAVA_HOME/lib下的类
        /** BootClassLoader */
       ClassLoader classLoader = System.class.getClassLoader();
       show(classLoader != null ? classLoader.getClass().getName() : "null");

        //加载JAVA_HOME/lib/ext下的类
      /** ExtClassLoader */
//      show(CollectionData_ar.class.getClassLoader().getClass().getName());

        //加载ClassPath下的类
      /** AppClassLoader */
      show(ClassLoaderTest.class.getClassLoader().getClass().getName());

        //输出结果是 BootstripClassLoader 是null　并不代表不存在BootstripClassLoader 是因为启动类的是有C++
        //编写 并镶嵌到JVM内部，所以才是null
        //其他两个classLoader 都可以正常加载到相应的加载类
    }

    private static void show(String msg){
        if(!msg.isEmpty())
          Log.i("wzp","msg:" + msg);
    }

}
