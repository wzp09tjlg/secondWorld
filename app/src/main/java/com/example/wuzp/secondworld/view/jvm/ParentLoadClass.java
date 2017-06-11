package com.example.wuzp.secondworld.view.jvm;

/**
 * Created by wuzp on 2017/6/10.
 */

public class ParentLoadClass {

    /*
    protected Class<?> loadClass(String name,boolean resolve)throws ClassNotFoundException {
        Class c = null;
       synchronized (getClassLoadingLock(name)){
            //1.首先检查目标类型是否已经被成功加载过了
           c = findLoadedClass(name);
           if(c == null){
               long t0 = System.currentTimeMillis();
               try{
                   //如果存在超类加载器 就委派给超类加载器执行加载
                   if(parent != null){
                       c = parent.loadClass(name,false);
                   }else{
                       //如果不存在超类加载器 就直接委派给顶层的启动那个类加载器执行加载
                       c = findBootstripClassOrNull(name);
                   }
               }catch (ClassNotFoundException e){
                   //抛出异常 就是意味着 超类加载器加载失败
               }

               if(c == null){
                   long t1 = System.currentTimeMillis();
                   //如果超类加载器加载失败 就自行加载
                   c = findClass(name);
               }
           }
           if(resolve){
               resolveClass(c);
           }
       }
        return c;
    }*/

}
