package com.example.wuzp.secondworld.view.java;

/**
 * Created by wuzp on 2017/5/25.
 */
public class InnerClass {
  private static int FATEHER_NUM = 100;

   private void testThis(){
       //this  表示当前类的对象 能够执行的操作
   }

   private void testSuper(){
       //super 作用是指父类对象 能够执行的操作
   }

   private void testInnerClass(){
       //内有类 分为 成员内部类，局部内部类，静态类不类，匿名内部类
       //引用内部类的作用
       //不能被外界的类访问，保证类的访问权限
       //可以访问外部类的成员变量
       //对于回调非常方便
       //成员内部类
   }

   //内部类 可使用外部类的属性和方法
   class TestInnerClass{

   }

   private void testMethodInnerClass(){
       //局部内部类 的生命周期只是在这个方法中，类对象的使用也只是局限在这个方法中
       class TestMethodInnerClass{

       }

       TestMethodInnerClass testMethodInnerClass = new TestMethodInnerClass();
   }

   //静态内部内 创建的对象都是静态对象
    //静态内部类中能够访问外部类的静态属性和静态方法 不能访问一般属性和一般方法
   static class InnerStaticClass{
       public static int NUM = 5;
       public String NAME = "WZP";

       public void noStaticShow(){
       }

       public static void staticShow(){
          int tempNum =  FATEHER_NUM;
       }
   }

   //测试匿名内部类
    //匿名内部类 没有构造方法 只会创建一次
   private void testNoNameClass(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

            }
        });
    }

}
