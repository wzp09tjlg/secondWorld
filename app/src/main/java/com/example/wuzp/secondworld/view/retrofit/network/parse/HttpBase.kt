package com.example.wuzp.secondworld.view.retrofit.network.parse

/**
 * Created by wuzp on 2017/3/28.
 */
class HttpBase<T>(var error_code:String = "",
                  var error_msg:String = "",
                  var tip_msg:String = "",
                  var data:T)