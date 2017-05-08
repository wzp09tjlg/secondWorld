package com.example.wuzp.secondworld.view.LockUitl;

/**
 * Created by wuzp on 2017/5/8.
 * java 中锁的使用
 * 锁对象 锁方法 锁变量
 * volatile 关键之的使用
 */
public class lock {
    volatile int num = 100;
   //Java中锁是经常用到的，但是对于所只是知道 锁能保证执行的顺序，为什么能保证执行的顺序呢？锁到底是锁住了什么东西，
    // 才保证多线程对锁住的共享资源 的获取保持顺序执行。锁住是对象所在的监视器。JVM中会为每个类的对象 和每个类对象
    // 创建监视器，所以jvm能保证系统自己gc，不通过开发人员去手动gc。使用synchronized 对对象加锁，对类对象加锁
    // 保证执行的顺序能够正常执行。如此也

    //测试一般的锁 锁住的是共有的资源
    //方法的主入口
    public static void main(String[] args){

        //测试一 如果只是针对每个对象的Run方法加锁，锁住的是每个对象的监视器，多个对象锁对应的监视器不一样，所以不能保证执行的顺序是同步的
        for(int i=0;i<10;i++){
         ;
        }

    }

    //自定义的一个线程
    class MyThread1 extends Thread{
        @Override
        public void run() {
           for(int i=0;i<1000;i++){
             out("" + i);
           }
        }
    }

    private static void out(String msg){
        System.out.print(msg);
    }

    private static void out(String tag,String msg){
        System.out.print("tag:" + tag + "  msg:" + msg);
    }
}
