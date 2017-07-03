package com.example.wuzp.secondworld.view.FragementLife;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.wuzp.secondworld.R;

/**
 * Created by wuzp on 2017/6/30.
 */

public class FragmentOne extends BaseFragment{

    private String TAG = "FragmentOne";

     private Button btnChange;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_one,null);
        Log.e("wzp","" + TAG + "   onCreateView" );
        btnChange = (Button)view.findViewById(R.id.btn_change);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.e("wzp","" + TAG + "   onStart" );
    }

    @Override
    public void onResume() {
        super.onResume();
        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取手机的状态 如果没有动态申请 会不会崩溃？ 经过测试 没有崩溃，
                TelephonyManager manager = (TelephonyManager) getContext().getSystemService(Context.TELEPHONY_SERVICE);
                String subscriber = "N/A";
                subscriber = manager.getSubscriberId();
                String operatorName = manager.getSimOperatorName();
                ListenerManager.doChange();
            }
        });
        Log.e("wzp","" + TAG + "   onResume" );
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e("wzp","" + TAG + "   onPause" );
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.e("wzp","" + TAG + "   onStop");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("wzp","" + TAG + "   onDestroy");
    }

    @Override
    public void doChange() {
        Log.e("wzp","" + TAG + "   doChange");
    }
}
