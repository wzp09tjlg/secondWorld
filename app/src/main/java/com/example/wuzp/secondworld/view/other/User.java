package com.example.wuzp.secondworld.view.other;

import com.apkfuns.logutils.LogUtils;

/**
 * Created by wuzp on 2017/4/10.
 */
public class User {
    public String firstName;
    public String lastName;
    public boolean isvisit;

    public User(String firstName,String lastName,boolean isvisit){
       this.firstName = firstName;
        this.lastName = lastName;
        this.isvisit = isvisit;
    }

    public void show(){
        LogUtils.e("show:" +"{firstName:" + firstName + ";lastName:" + lastName + ";isvisit:" + isvisit + "}");
    }

    public void doTestToolUtil(){
        // 在今年上半年一定要考下深水证吼吼吼...
    }
}
