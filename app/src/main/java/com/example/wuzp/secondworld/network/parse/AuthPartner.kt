package com.vread.hs.network.parse

/**
 * 网络实体
 *
 * 三方信息实体
 * Created by tiny on 3/7/2017.
 */
data class AuthPartner(
        var partner: String,
        var open_id: String,
        var nickname: String,
        var avatar: String,
        var access_token: String
)
