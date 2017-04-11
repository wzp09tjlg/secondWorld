package com.example.wuzp.secondworld.view.huasheng;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.apkfuns.logutils.LogUtils;
import com.example.wuzp.secondworld.R;
import com.example.wuzp.secondworld.network.parse.HttpBase;
import com.example.wuzp.secondworld.view.huasheng.file.FileUtil;
import com.example.wuzp.secondworld.view.retrofit.network.parse.UserInfo;

/**
 * Created by wuzp on 2017/3/29.
 */

public class OtherHsActivity extends FragmentActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_hs);
        initView();
    }

    private void initView(){
        findViewById(R.id.btn_other_hs).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                UserInfo userInfo = new UserInfo();
                userInfo.setName("wzp");
                userInfo.setAge(10);
                userInfo.setAddress("beijing");
                userInfo.setSex("men");
                Intent intent =new Intent(OtherHsActivity.this,HuashengActivity.class);
                HttpBase<UserInfo> base  = new HttpBase<UserInfo>(1,"",userInfo);
                base.setError_code(0);
                base.setError_msg("no error");
                bundle.putSerializable("base",base);
                intent.putExtras(bundle);
                startActivity(intent);
                

                HuashengActivity.launch(OtherHsActivity.this,"lj");
                //showPopupWindow();
                LogUtils.e("----------------------------------------------");
                FileUtil.showEnvorimentPath();
                LogUtils.e("----------------------------------------------");
                FileUtil.getContextInnerPath(OtherHsActivity.this);
                LogUtils.e("----------------------------------------------");
                FileUtil.getContextOutterPath(OtherHsActivity.this);
            }
        });
    }

    private void showPopupWindow(){

    }
}
