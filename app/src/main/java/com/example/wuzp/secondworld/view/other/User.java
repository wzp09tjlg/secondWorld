package com.example.wuzp.secondworld.view.other;

import com.example.hslibrary.ToolUtil;
import com.example.mylibrary.ToolUtils;

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

    @Override
    public String toString() {
        return "{firstName:" + firstName + ";lastName:" + lastName + ";isvisit:" + isvisit +  "}";
    }

    public void doTestModualUtil(){
        ToolUtil.add(100,200);
        ToolUtils.minus(300,400);
    }
}
