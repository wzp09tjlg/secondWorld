package com.example.wuzp.secondworld.view.databinding;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.wuzp.secondworld.R;
import com.example.wuzp.secondworld.databinding.ActivityDatabindingBinding;
import com.example.wuzp.secondworld.view.base.BasePresenter;
import com.example.wuzp.secondworld.view.base.BindingActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wuzp on 2017/4/10.
  今天就专门探究下 databinding对数据的绑定  //数据的绑定是 定义数据类型，然后在databinding中 声明数据，使用数据 bean必须继承BaseObservable
 对事件的绑定  定义点击事件,方法名称随便写，但是参数必须得是View
 以及在listView中 对数据的绑定
 变更数据之后显示的变换等情况
 对RecylerView的绑定
 */
public class DataActivity extends BindingActivity<ActivityDatabindingBinding, BasePresenter> {
    private List<User> mData = null;
    private DataAdapter adapter = null;
    private int randCount = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
        initData();
    }

    private void initViews(){
        //处理RecyclerView的数据
        //recyclerView 1.设置layoutManager 2.设置adapter
        RecyclerView.LayoutManager layoutManager =
                new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        binding.list.setLayoutManager(layoutManager);
        setData();
        adapter = new DataAdapter(this);
        adapter.setData(mData);
        binding.list.setAdapter(adapter);
    }

    private void setData(){
        mData = new ArrayList<>();
        for(int i=0;i<20;i++){
            User user = new User("wuzp" + i,"liujia" + i,i%2==1);
            mData.add(user);
        }
    }

    private void initData(){
        final User user = new User("wuzp","liujia",true);
        binding.setUser(user);

        binding.btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 //user.setFirstName("liujia");
                binding.getUser().setFirstName("liujiajiajiajiajiajiajia");
                //mData.get(10).setFirstName("wuzp and liujia");
                //binding.setUser(user);
                //adapter.notifyItemChanged(10);
            }
        });
        binding.btnAlter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                randCount = (randCount + 1) % mData.size();
                mData.get(randCount).setFirstName(mData.get(randCount).getFirstName() + "----"+ randCount);
                mData.get(randCount).setLastName(mData.get(randCount).getLastName() + "----"+ randCount);
            }
        });
    }

    @Override
    protected BasePresenter createPresenter() {
        return new BasePresenter(this);
    }

    @Override
    protected int createLayoutId() {
        return R.layout.activity_databinding;
    }
}
