package com.example.wuzp.secondworld.view.LockUitl;

/**
 * Created by wuzp on 2017/5/16.
 * 针对锁的再理解
 * 锁住的是类对象监视器 还是对象的监视器
 */
public class MyThread4 extends Thread {
    private String name;
    private int id;
    private byte[] mLockTarget = new byte[0];

    private static MyThread4 myThread4;

    private MyThread4(String name,int id){
        this.name = name;
        this.id = id;
    }

    public static MyThread4 getInstance(){
        if(myThread4 == null){
            myThread4 = new MyThread4("wuzp",100);
        }
        return myThread4;
    }

    public static void main(String[] args){

        try{
            for(int i=0;i<5;i++){
                Thread.sleep(1000);
                MyThread4.getInstance().start();
            }
        }catch (Exception e){
            System.out.println("msg:" + e.getMessage());
        }

    }

    //保证当前执行的是同步的，即使是多线程中的处理 也应该是按照循序来输出
    @Override
    public void run() {
        super.run();
        synchronized (mLockTarget){
            try {
                for(int i=0;i<5;i++){
                    Thread.sleep(1000);
                    System.out.println("name:" + name + "  id;" + id + "  i:" + i);
                }

            }catch (Exception e){}
        }
    }
}
