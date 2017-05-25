package com.example.wuzp.secondworld.view.java;

import com.example.wuzp.secondworld.utils.MD5Helper;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wuzp on 2017/5/24.
 *
 */
public class TestMd5 {
    public static void main(String[] args){
        Map<String,String> param = new HashMap<>();
        //param.put("app_scrt","kWw0ryRC0F5rkbrk");
        param.put("app_channel_h5","401");
        param.put("access_token_h5","testToken");
        param.put("imei_h5","123456789000");
        param.put("aaa_scrt","kWw0ryRC0F5rkbrk");
        param.put("vresion_h5","3.1.2");
        String sign = MD5Helper.getMd5(param);

        System.out.println("sign:" + sign);
    }
}
