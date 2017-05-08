package com.example.wuzp.secondworld.view.LockUitl;

/**
 * Created by wuzp on 2017/5/8.
 * 上边的那个例子是 针对主线程中存在的公共资源 加锁的问题
 */
public class MyThread3 extends Thread {

    private String mName;
    private int mNum;

    public MyThread3(int num){
        this.mNum = num;
    }

    public static void main(String[] args){
       for(int i=0;i<10;i++){
           new MyThread3(i).start();
       }
    }

    @Override
    public void run() {
        super.run();
        show(mNum);
    }

    private static synchronized void show(int num){
        for(int i=0;i<100;i++){
            System.out.print("num:" + num + "  i:" + i + " \n");
        }
    }
}
