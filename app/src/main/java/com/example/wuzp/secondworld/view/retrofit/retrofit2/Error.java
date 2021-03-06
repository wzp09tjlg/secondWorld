package com.example.wuzp.secondworld.view.retrofit.retrofit2;

/**
 * 错误的同意处理
 * Created by tiny on 1/18/2017.
 */
@SuppressWarnings("All")
public class Error {
    private int errorCode;
    private String message = "";

    public Error(int errorCode) {
        this.errorCode = errorCode;
    }

    public Error(String message) {
        this.message = message;
    }

    public Error(int errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Error{" +
                "errorCode=" + errorCode +
                ", message='" + message + '\'' +
                '}';
    }

    /**
     * 是否是本地异常
     * 当网络访问成功，但因
     * @return
     */
    public boolean isLocalError() {
        // S_ERROR_DATA_TIP 可以给用户提示的异常
        if (this.errorCode == S_ERROR_DATA_TIP) {
            return true;
        }
        return false;
    }

    public boolean isNetError() {
        return !isLocalError();
    }
    /*
    * 没有登录
    * */
    public static final int S_ERROR_NOT_LOGIN = 0x001;

    // 网络层请求成功,但是发生错误
    /*
    * 网络请求数据成功，但数据为空
        * */
    public final static int S_NULL_DATA = 0x201;
    /*
    * 网络请求数据成功,但数据异常
    * */
    public final static int S_ERROR_DATA = 0x202;
    /**
     * 数据异常，但是可以提示给用户异常信息
     */
    public final static int S_ERROR_DATA_TIP = 0x203;
    // 未知错误
    public final static int S_UNKNOW_ERROR = 0x200;

    // 网络层请求失败
    /*
    * 网络无法连接
    * */
    public final static int S_NETWORK_UNCONNECT = 0x501;
    // 用户为授权
    public final static int S_USER_NOT_AUTHORIZED = 0x601;

}
