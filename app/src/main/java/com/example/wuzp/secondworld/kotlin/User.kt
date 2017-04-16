package com.example.wuzp.secondworld.kotlin

/**
 * Created by wuzp on 2017/4/16.
 */
class User{
    var name:String = ""
    var address:String = ""
    var age:Int = 0
    var sex :Int = 0

    // kotlin 的简单的类 及定义方法
    fun show(msg:String = ""){
        System.out.println("msg:" + msg);
    }

}