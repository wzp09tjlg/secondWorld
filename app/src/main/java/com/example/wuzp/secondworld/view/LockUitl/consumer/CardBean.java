package com.example.wuzp.secondworld.view.LockUitl.consumer;

/**
 * Created by wuzp on 2017/5/8.
 * 使用lock锁的概念
 * 银行卡 被锁住的对象。只有余额这样一个属性，但是存在两个方法操作余额
 */
public class CardBean {
    private int balance;

    public CardBean(){
        this.balance = 10000;
    }

    public CardBean(int balance){
        this.balance = balance;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public void addBalance(int money){
        this.balance += money;
    }

    public void minusBalance(int money){
        this.balance -=money;
    }
}
