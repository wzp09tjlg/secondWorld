package com.example.wuzp.secondworld.view.LockUitl.consumer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by wuzp on 2017/5/8.
 * java 程序的主入口
 * 这里就简单测试了下 生产者和消费者的模型，然后针对锁做了一些处理
 */
public class Main {

    public static void main(String[] args){
        CardBean cardBean = new CardBean(10000);
        Lock lock = new ReentrantLock();
        ExecutorService pool = Executors.newCachedThreadPool();
        FatherThread fatherThread = new FatherThread(cardBean,lock);//使用了一个线程池来处理两个线程
        SonThread sonThread = new SonThread(cardBean,lock);
        pool.execute(fatherThread);
        pool.execute(sonThread);
    }

}
