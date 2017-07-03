package com.example.wuzp.secondworld.view.FragementLife;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wuzp.secondworld.R;

/**
 * Created by wuzp on 2017/6/30.
 */

public class FragmentThree extends BaseFragment{

    private String TAG = "FragmentThree";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_three,null);
        Log.e("wzp","" + TAG + "   onCreateView" );
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
