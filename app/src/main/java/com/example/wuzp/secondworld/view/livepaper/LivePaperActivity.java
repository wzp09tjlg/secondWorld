package com.example.wuzp.secondworld.view.livepaper;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.wuzp.secondworld.R;
import com.example.wuzp.secondworld.databinding.ActivityLivepaperBinding;
import com.example.wuzp.secondworld.view.base.BindingActivity;

/**
 * Created by wuzp on 2017/6/1.
 */

public class LivePaperActivity extends BindingActivity<ActivityLivepaperBinding,LivePaperPresenter> implements LivePaperContract.IView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    @Override
    protected LivePaperPresenter createPresenter() {
        return new LivePaperPresenter(this);
    }

    @Override
    protected int createLayoutId() {
        return R.layout.activity_livepaper;
    }

    private void initView(){
        binding.btnLive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setVideoToWallPaper();
                boolean checked = binding.chkV.isChecked();
                if(checked){
                   LivePaperService.voiceSilence(getApplicationContext());
                }else{
                    LivePaperService.voiceNormal(getApplicationContext());
                }
            }
        });
    }

    public void setVideoToWallPaper() {
        String name = getApplicationContext().getPackageName();
        Log.e("wzp","name:" + name);
        LivePaperService.setToWallPaper(getApplicationContext());
    }
}
