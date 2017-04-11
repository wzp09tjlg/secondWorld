package com.example.wuzp.secondworld.network.parse

import java.io.Serializable

/**
 * Created by wuzp on 2017/3/19.
 */
data class HttpBase<M>(var error_code: Int = 0,
                       var error_msg: String,
                       var data: M):Serializable