package com.example.wuzp.secondworld.view.retrofit.retrofit2;

import com.apkfuns.logutils.LogUtils;
import com.example.wuzp.secondworld.network.parse.HttpBase;
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;

/**
 * Created by wuzp on 2017/3/19.
 */

public abstract class ApiCallback<T> {
    public abstract void onSuccess(T model);

    public abstract void onFailure(Error error);

    public void finish(){}

    public void onNext(HttpBase<T> model){
        if (model == null) {
            onFailure(new Error(Error.S_NULL_DATA, "数据异常"));
            LogUtils.e("ApiCallback -> Data empty exception");
            return;
        }
        if (model.getError_code() == 0) {
            try {
                T m = model.getData();
                try {
                    onSuccess(m);
                } catch (Exception ex) {
                    LogUtils.e("Logical processing exception-> " + ex.getMessage());
                    onFailure(new Error(Error.S_ERROR_DATA, ex.getMessage()));
                }
            } catch (Exception e) {
                onFailure(new Error(Error.S_ERROR_DATA, "数据异常"));
                LogUtils.e("ApiCallback -> Data conversion exception -> " + e.getMessage());
            }
        } else {
            onFailure(new Error(Error.S_ERROR_DATA_TIP, model.getError_msg()));
            LogUtils.e("ApiCallback -> Data exception");
        }
    }

    public void onError(Throwable e){
        e.printStackTrace();
        if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            int code = httpException.code();
            String msg = httpException.getMessage();
            onFailure(new Error(Error.S_UNKNOW_ERROR, msg));
            LogUtils.e("ApiCallback -> Network connection exception -> " + msg);
        } else {
            onFailure(new Error(Error.S_UNKNOW_ERROR));
            LogUtils.e("ApiCallback -> Unknow exception ");
        }
    }

    public void onComplete(){
        finish();
    }
}
