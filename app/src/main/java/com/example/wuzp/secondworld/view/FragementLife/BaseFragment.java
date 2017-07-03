package com.example.wuzp.secondworld.view.FragementLife;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;

/**
 * Created by wuzp on 2017/6/30.
 */

public class BaseFragment extends Fragment implements StatusListener{

    private String TAG = "BaseFragment";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ListenerManager.addListener(this);
        Log.e("wzp","" + TAG + "  onCreate");
    }

    @Override
    public void doChange() {

    }
}
