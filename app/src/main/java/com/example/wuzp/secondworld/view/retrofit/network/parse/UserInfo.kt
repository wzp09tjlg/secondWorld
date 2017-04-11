package com.example.wuzp.secondworld.view.retrofit.network.parse

import java.io.Serializable

/**
 * Created by wuzp on 2017/3/28.
 * var 就是普通的变量
 * val 是static final静态的常量
 * 使用kotlin 是可以集成Serializable接口的，效率的法 不敢确定有多高。但是可以实现在Intent的
 */
class UserInfo : Serializable{
    var name:String = ""
    var address:String = ""
    var sex:String =""
    var age :Int = 0
}