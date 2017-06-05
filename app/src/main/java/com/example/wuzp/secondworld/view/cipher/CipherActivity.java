package com.example.wuzp.secondworld.view.cipher;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

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
                String srcE = binding.edtContent.getText().toString();
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
                }

                break;
            case R.id.btn_decode:
                String srcD =  value;//binding.textDisplay.getText().toString();
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
                }
                break;
        }
    }
}
