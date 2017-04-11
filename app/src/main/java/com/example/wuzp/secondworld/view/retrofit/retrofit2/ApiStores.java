package com.example.wuzp.secondworld.view.retrofit.retrofit2;

import com.example.wuzp.secondworld.network.parse.HttpBase;
import com.example.wuzp.secondworld.network.parse.User;

import java.util.HashMap;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Created by wuzp on 2017/3/19.
  retrofit 定义的一个接口,
 */
public interface ApiStores {
    //retrofit2.0得到的是flowable

    @GET("abc/index.json")
    Flowable<HttpBase<User>> getUserInfo(@QueryMap HashMap<String,String> map);


}
