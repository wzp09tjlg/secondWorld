package com.example.wuzp.secondworld.view.LockUitl;

/**
 * Created by wuzp on 2017/5/8.
 * 测试的是同步锁的机制 如果只是真对对象加锁，
 * 那么创建多个对象，就会对应多把锁。起不到同步的作用
 */
public class MyThread1 extends Thread {
    private int mNum;

    public MyThread1(int num){
        this.mNum = num;
    }

    public static void main(String[] args){
           for(int i=0;i<10;i++){
               new MyThread1(i).start();
           }
    }

    //这个方法加锁 只是针对这个对象的监视器加锁 如果创建多个对象，那么执行的顺序是得不到顺序的保证的
    //可以看出这里执行的顺序是 杂乱的 因为每个对象锁住都是自己的对象锁对应的监视器，所以不存在共享资源
    // 所以在主线程中执行 没有顺序
    @Override
    public synchronized void run() {
        super.run();
        for(int i=0;i<100;i++){
            System.out.print("Num:" + mNum + "   count:" + i + "\n");
        }
    }
}
