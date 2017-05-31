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
        /*Map<String,String> param = new HashMap<>();
        //param.put("app_scrt","kWw0ryRC0F5rkbrk");
        param.put("app_channel_h5","401");
        param.put("access_token_h5","testToken");
        param.put("imei_h5","123456789000");
        param.put("aaa_scrt","kWw0ryRC0F5rkbrk");
        param.put("vresion_h5","3.1.2");
        String sign = MD5Helper.getMd5(param);

        System.out.println("sign:" + sign);*/

        testMd5();
    }

    private static void testMd5(){
        String access_token_h5 = "abcdefghijklmnopq";
        String app_channel_h5 = "401";
        String phone_imei_h5 = "1234567890abcdefg";
        String version_h5 = "3.1.2";
        Map<String,String> param = new HashMap<>();
        param.put(MD5Helper.COMMON_KEY,MD5Helper.COMMON_VALUE);//特权活动 特意添加md5签名 防反编译干扰字符串
        param.put("access_token_h5",access_token_h5);
        param.put("app_channel_h5",app_channel_h5);
        param.put("phone_imei_h5",phone_imei_h5);
        param.put("version_h5",version_h5);
        String sign = getMd5(param);

        System.out.println("sign:" + sign);
    }

    private static String getMd5(Map<String,String> param){
        String sign = "";
        try{
            sign = MD5Helper.getMd5(param);
        }catch (Exception e){}
        return sign;
    }
}
