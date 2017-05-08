package com.example.wuzp.secondworld.view.LockUitl;

/**
 * Created by wuzp on 2017/5/8
 * 这个线程中 加锁的是针对共有的资源加锁 所以就能保证执行的顺序是 有序的
 */
public class MyThread2 extends Thread {
   private String mBlock;
   private int mNum;

    public MyThread2(String name,int num){
        this.mBlock = name;
        this.mNum = num;
    }

    public static void main(String[] args){
        String name = "wuzp";//这个变量是在主线程中 创建的，
        // 子线程中持有的是 这个变量的地址，所以针对这个字段的同步，就能保证执行的顺序是同步有序
        //所有希望获取这个共有资源的 线程在获取不到共有资源时  都会被加载到共享资源 锁池队列中，
        //但是在这个队列中 将要执行的线程 就不一定是顺序的执行
        for(int i=0;i<10;i++){
            //这个方法 在执行的过程中 因为是多个子线程同时对共享资源持有，
            // 所以这里也不能保证 就是 0 1 2 3 4 5 6 7 8 9这样的顺序执行
            // 测试的结果是 多个线程执行的顺序 没有保证，但是只要是一个线程开始执行了，就一定会执行完成
            new MyThread2(name,i).start();

            //所以多线程中编程 不能保证多线程执行的 顺序是同步。但是在一个动作做完是具有原子性的
        }
    }

    @Override
    public void run() {
        super.run();
        synchronized (mBlock){
            for(int i=0;i<100;i++){
                System.out.print("name:" + mBlock + "  mNum:" + mNum + "  count:" + i + " \n");
            }
        }
    }

    //this is a test for dev branch
}
