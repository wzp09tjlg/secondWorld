package com.example.wuzp.secondworld.view.wangdou.model.parse;

/**
 * Created by zyb on 2016/11/1.
 */
public class StatusBean {
    private int code;
    private String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "StatusBean{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }
}
