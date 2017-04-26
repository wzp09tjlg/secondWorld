package com.example.wuzp.secondworld.network.parse;

/**
 * Created by wuzp on 2017/4/26.
 * 这里主要是测试下 两个bean的 使用情况 使用kotlin的bean 和 使用Java写的bean 效果是一样的
 * 其实在使用gson解析的时候 创建多个方法，和 实现Serializable 接口 是一样的，都是为了让属性能被解析出来
 */
public class HttpBase_j<T> {
    private int  error_code;
    private String error_msg;
    private  T data;

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public String getError_msg() {
        return error_msg;
    }

    public void setError_msg(String error_msg) {
        this.error_msg = error_msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
