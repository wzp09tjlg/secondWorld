package com.example.wuzp.secondworld.view.FragementLife;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.wuzp.secondworld.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wuzp on 2017/6/30.
 * 这个页面想测试 redmi中 两款手机在首页创建fragment之后 接受广播，出现widget为空的情况。经过测试，暂时没有发现这种问题
 */
public class FragmentLifeActivity extends AppCompatActivity {
    private String TAG = "FragmentLifeActivity";

    private ViewPager viewPager;
    private VAdapter adapter;

    private List<Fragment> mData;
    private Fragment fragmentOne;
    private Fragment fragmentTwo;
    private Fragment fragmentThree;
    private Fragment fragmentFour;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_life);

        initView();
        Log.e("wzp","" + TAG + "   onCreate");
    }

    private void initView(){
        viewPager = (ViewPager)findViewById(R.id.viewpager);

        initData();
    }

    private void initData(){
        mData = getData();

        adapter = new VAdapter(getSupportFragmentManager(),mData);
        viewPager.setAdapter(adapter);
        //viewPager.setOffscreenPageLimit(4);
    }

    private List<Fragment> getData(){
        mData = new ArrayList<>();
        fragmentOne = new FragmentOne();
        fragmentTwo = new FragmentTwo();
        fragmentThree = new FragmentThree();
        fragmentFour = new FragmentFour();

        mData.add(fragmentOne);
        mData.add(fragmentTwo);
        mData.add(fragmentThree);
        mData.add(fragmentFour);
        return mData;
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
}
