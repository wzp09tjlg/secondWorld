package com.example.wuzp.secondworld.network;

import android.support.annotation.Nullable;

import com.example.wuzp.secondworld.utils.CommonHelper;
import com.example.wuzp.secondworld.utils.MD5Helper;

import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 增加请求后置参数
 * <p>
 * Created by tiny on 3/14/2017.
 */
@SuppressWarnings("All")
public class OkHttpInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request oldRequest = chain.request();
        Request newRequest = null;
        if ("post".equals(oldRequest.method().toLowerCase())) {
            newRequest = sendPost(oldRequest);
        } else if ("get".equals(oldRequest.method().toLowerCase())) {
            newRequest = sendGet(oldRequest);
        }

        if (newRequest != null) return chain.proceed(newRequest);

        return chain.proceed(oldRequest);
    }

    private Request sendGet(Request oldRequest) {
        HttpUrl.Builder httpBuilder = oldRequest.url().newBuilder();
        Map<String, String> paramsMap = Net.newHashMap();
        // 添加公参
        for (Map.Entry<String, String> entry : paramsMap.entrySet()) {
            httpBuilder.addQueryParameter(entry.getKey(), entry.getValue());
        }

        Request.Builder requestBuilder = oldRequest.newBuilder()
                .url(httpBuilder.build());
        return requestBuilder.build();
    }

    @Nullable
    private Request sendPost(Request oldRequest) throws IOException {
        RequestBody oldBody = oldRequest.body();
        if (oldBody != null) {
            RequestBody newBody = null;
            if (oldBody instanceof FormBody) {
                newBody = addParamsToFormBody((FormBody) oldBody);
            }

            if (null != newBody) {
                Request newRequest = oldRequest.newBuilder()
                        .url(oldRequest.url())
                        .method(oldRequest.method(), newBody)
                        .build();
                return newRequest;
            }
        }
        return null;
    }


    private FormBody addParamsToFormBody(FormBody body) {
        FormBody.Builder builder = new FormBody.Builder();
        // 公参转换成md5 是需要排序  默认升序排列
        Map sortMap = new TreeMap<String, String>();
        Map<String, String> paramsMap = Net.newHashMap();
        // 添加公参
        for (Map.Entry<String, String> entry : paramsMap.entrySet()) {
            sortMap.put(entry.getKey(), entry.getValue());
            builder.add(entry.getKey(), entry.getValue());
        }
        // 添加sign
        for (int i = 0; i < body.size(); i++) {
            builder.add(body.name(i), body.value(i));
            sortMap.put(body.name(i), body.value(i));
        }
        sortMap.putAll(Net.newHashMap());
        String paramStr = CommonHelper.paramstoString(sortMap, false);
        String md5Sign = MD5Helper.getMd5(paramStr);
        builder.add("sign", md5Sign);
        // 返回body
        return builder.build();
    }
}
