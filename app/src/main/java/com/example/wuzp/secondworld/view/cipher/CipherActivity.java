package com.example.wuzp.secondworld.view.cipher;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.example.wuzp.secondworld.R;
import com.example.wuzp.secondworld.databinding.ActivityCipherBinding;
import com.example.wuzp.secondworld.view.base.BindingActivity;

/**
 * Created by wuzp on 2017/6/4.
 * 使用des 加密和解密算法 虽然照搬书上的 但是没有解出来，以后再做研究
 */
public class CipherActivity extends BindingActivity<ActivityCipherBinding,CipherPresenter>  implements
        CipherContract.IView,View.OnClickListener{

    private String value = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    @Override
    protected CipherPresenter createPresenter() {
        return new CipherPresenter(this);
    }

    @Override
    protected int createLayoutId() {
        return R.layout.activity_cipher;
    }

    private void initView(){
        binding.btnDecode.setOnClickListener(this);
        binding.btnEncode.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)  {
        switch (v.getId()){
            case R.id.btn_encode:
                showStatusBar(this);

                /*//测试使用GBK UTF-8两种编码
                //格式来检测text格式转换byte[]的长度
                //String tempString = "安卓Hello World!";
                String tempString = "安";
                //测试GBK 的编码格式的长度 (16) 一个中文字是2个字节长度 一个英文字符是一个字节长度
                try{
                    byte[] byteGbk = tempString.getBytes("GBK");
                    Log.e("wzp","GBK len:" + byteGbk.length);
                }catch (Exception e){}
                //测试UTF-8 的编码格式的长度 (18) 一个中文字是3个字节 一个英文字符是一个字节长度
                try{
                    byte[] byteUTF = tempString.getBytes("UTF-8");
                    Log.e("wzp","UTF len:" + byteUTF.length);
                }catch (Exception e){}*/

                /*String srcE = binding.edtContent.getText().toString();
                String cipherE = binding.edtCipher.getText().toString();
                if("".equals(srcE) || "".equals(cipherE)) return;
                try {
                    byte[] cipherByteE = CipherUtil.encrypt(cipherE.getBytes("utf-8"),srcE.getBytes("utf-8"));
                    if(cipherByteE != null){
                        String cipherContentE = new String(cipherByteE);
                        value = cipherContentE;
                        binding.textDisplay.setText(cipherContentE);
                        binding.edtContent.setText("");
                    }
                }catch (Exception e){
                    Log.e("wzp","msgE" + e.getMessage());
                }*/

                break;
            case R.id.btn_decode:
                hideStatusBar(this);
               /* String srcD =  value;//binding.textDisplay.getText().toString();
                String cipherD = binding.edtCipher.getText().toString();
                if("".equals(srcD) || "".equals(cipherD)) return;
                try {
                    byte[] cipherByteD = CipherUtil.encrypt(cipherD.getBytes("utf-8"),srcD.getBytes("utf-8"));
                    if(cipherByteD != null){
                        String cipherContentD = new String(cipherByteD);
                        binding.edtContent.setText(cipherContentD);
                        binding.textDisplay.setText("");
                    }
                }catch (Exception e){
                    Log.e("wzp","msgD" + e.getMessage());
                }*/
                break;
        }
    }

    //修改当前Activity的持有的window的属性 显示全屏和设置有限制
    public static void showStatusBar(Activity activity) {
        WindowManager.LayoutParams attrs = activity.getWindow().getAttributes();
        //这个属性是 表示显示的activity 是全屏显示
        attrs.flags &= ~WindowManager.LayoutParams.FLAG_FULLSCREEN;
        //这个属性是表示布局显示 不受状态栏的限制
        attrs.flags &= ~WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS;
        activity.getWindow().setAttributes(attrs);
    }

    //修改当前Activity持有的window的属性 隐藏全屏和设置没有限制
    public static void hideStatusBar(Activity activity) {
        WindowManager.LayoutParams attrs = activity.getWindow().getAttributes();
        attrs.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
        attrs.flags |= WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS;
        activity.getWindow().setAttributes(attrs);
    }
}
