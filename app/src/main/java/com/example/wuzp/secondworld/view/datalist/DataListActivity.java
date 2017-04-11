package com.example.wuzp.secondworld.view.datalist;

import android.os.Bundle;

import com.example.wuzp.secondworld.R;
import com.example.wuzp.secondworld.databinding.ActivityDataListBinding;
import com.example.wuzp.secondworld.network.parse.User;
import com.example.wuzp.secondworld.view.base.BasePresenter;
import com.example.wuzp.secondworld.view.base.BindingActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wuzp on 2017/4/10.
 */

public class DataListActivity extends BindingActivity<ActivityDataListBinding,BasePresenter> {
    private List<User> mData ;
    private DataListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }

    private void initView(){
        getData();
    }

    private void getData(){
       mData = new ArrayList<>();
        for(int i=0;i<30;i++){
            User user = new User();
            user.setNickname("wuzp" + i);
            user.setDescription(" the number is :" + i);
            mData.add(user);
        }
    }

    private void initData(){
        adapter = new DataListAdapter(this);
        adapter.addData(mData);
        binding.list.setAdapter(adapter);
    }

    @Override
    protected BasePresenter createPresenter() {
        return new BasePresenter(this);
    }

    @Override
    protected int createLayoutId() {
        return R.layout.activity_data_list;
    }
}
