package com.example.wuzp.secondworld.view.retrofit.network;

import com.example.wuzp.secondworld.network.parse.HttpBase;
import com.example.wuzp.secondworld.view.retrofit.network.parse.UserInfo;
import com.google.gson.JsonElement;

import java.util.HashMap;

import io.reactivex.Flowable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by wuzp on 2017/3/28.
  RETROFIT的接口定义
 */
public interface ApiStores {
    //getLoginInfo
    @GET("ABC/INDEX.HTML")
    Flowable<HttpBase<UserInfo>> getUserInfo(@FieldMap HashMap<String,String> params);

    //PostSomeInfo
    //post 方法需要将参数encode处理 不然请求不成功
    //对于返回回来的数据如果不能确定 返回回来的是对象还是数组，
    // 需要转换成一个基类处理,因为gson 没有这么强大，转换会出错
    @FormUrlEncoded
    @POST("DEF/INDEX.HTML")
    Flowable<HttpBase<JsonElement>> postSomeInfo(@FieldMap HashMap<String,String> params);
}
