package com.example.wuzp.secondworld.view.cipher;

import com.apkfuns.logutils.LogUtils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by wuzp on 2017/6/4.
 * 加密和解密算法都是参考的jvm精讲
 */

public class CipherUtil {
    private static final String ALGORITHM = "DES";//算法 的名字

    //加密
    public static byte[] encrypt(byte[] key,byte[] src){
        byte[] value = null;
        try{
            //生成秘钥
            SecretKey encrkey = new SecretKeySpec(key,ALGORITHM);
            //对目标数据进行加密操作
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE,encrkey);
            value = cipher.doFinal(src);
        }catch (Exception e){
            LogUtils.e("MSG:" + e.getMessage());
        }
        return value;
    }

    //解密
    public static byte[] decode(byte[] key,byte[] src){
        byte[] value = null;
        try {
            //生成秘钥
            SecretKey deskey = new SecretKeySpec(key,ALGORITHM);
            //对目标数据进行解密操作
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE,deskey);
            value = cipher.doFinal(src);
        }catch (Exception e){
            LogUtils.e("MSG:" + e.getMessage());
        }
        return value;
    }
}
