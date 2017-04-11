package com.example.wuzp.secondworld.network;

import com.example.wuzp.secondworld.BuildConfig;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


@SuppressWarnings("All")
public class AppClient {
    private static final int S_DEFAULT_TIMEOUT = 15;
    public static Retrofit mRetrofit;
    public static ApiStores mApiStores;

    /**
     * 获得Retrofit实例
     *
     * @return
     * @deprecated
     */
    public static Retrofit retrofit() {
        if (mRetrofit == null) {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.connectTimeout(S_DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                    .writeTimeout(S_DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                    .readTimeout(S_DEFAULT_TIMEOUT, TimeUnit.SECONDS);
            // 添加后置参数
            builder.addInterceptor(new OkHttpInterceptor());

            if (BuildConfig.DEBUG) {
                // Log信息拦截器
                HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
                loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                //设置 Debug Log 模式
                builder.addInterceptor(loggingInterceptor);
            }

            OkHttpClient okHttpClient = builder.build();

            mRetrofit = new Retrofit.Builder()
                    .baseUrl(getHost())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(okHttpClient)
                    .build();
        }
        return mRetrofit;
    }

    /**
     * 获得网络层接口
     */
    public static ApiStores create() {
        if (mApiStores == null) {
            return retrofit().create(ApiStores.class);
        }
        return mApiStores;
    }

    /**
     * 根据当前配置设置host地址
     *
     * @return host 地址
     */
    private static String getHost() {
        if (ApiStores.FORCEUSE_TEST_HOST) {
            return ApiStores.HS_HOST;
        } else {
            if (BuildConfig.DEBUG) {
                return ApiStores.HS_HOST_TEST;
            } else {
                return ApiStores.HS_HOST;
            }
        }
    }
}
