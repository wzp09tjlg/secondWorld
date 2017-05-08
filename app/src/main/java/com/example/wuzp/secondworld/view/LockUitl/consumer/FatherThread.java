package com.example.wuzp.secondworld.view.LockUitl.consumer;

import java.util.concurrent.locks.Lock;

/**
 * Created by wuzp on 2017/5/8.
 * father 类，专门挣钱
 */
public class FatherThread implements Runnable {
    private CardBean cardBean;
    private Lock lock;

    public FatherThread(CardBean bean,Lock lock){
        this.cardBean = bean;
        this.lock = lock;
    }

    @Override
    public void run() {
       long tempTime = 0;

       while(true){
           lock.lock();
           try{
               System.out.println("Fateher  现在card上余额是" + cardBean.getBalance() );
               System.out.println("Fateher   father 存钱：500" );
               cardBean.addBalance(500);
               System.out.println("Fateher   存之后card上余额是：" + cardBean.getBalance());
           }finally {
               lock.unlock();
           }

           try{
               Thread.sleep(2000);
               tempTime +=2000;
           }catch (Exception e){}

           if(tempTime >60000){
               System.out.println("Fateher    father is to tired,and can not earn money more");
               break;
           }
       }
    }
}
