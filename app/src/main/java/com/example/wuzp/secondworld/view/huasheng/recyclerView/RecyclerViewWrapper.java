package com.example.wuzp.secondworld.view.huasheng.recyclerView;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;

import com.example.wuzp.secondworld.databinding.ActivityRecyclerBinding;
import com.example.wuzp.secondworld.network.parse.SelectBean;
import com.example.wuzp.secondworld.view.base.BaseViewWrapper;

import java.util.List;

/**
 * Created by wuzp on 2017/3/30.
 */
public class RecyclerViewWrapper extends BaseViewWrapper<ActivityRecyclerBinding> {
    private ClickListener clickListener;
    private RecAdapter recAdapter;
    private Context mContext = null;

    public RecyclerViewWrapper(){}

    @Override
    public void addBinding(@NonNull ActivityRecyclerBinding binding) {
        super.addBinding(binding);
        mContext = binding.getRoot().getContext();
        initView();
    }

    private void initView(){
       initTitle();

        recAdapter = new RecAdapter(mContext);
    }

    private void initTitle(){
       binding.layoutTitle.titleLeftImg.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
             if(clickListener != null){
                 clickListener.onClickListener(v);
             }
           }
       });
        binding.layoutTitle.titleCenterText.setText("RecyclerView");
        binding.layoutTitle.titleRightImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(clickListener != null){
                    clickListener.onClickListener(v);
                }
            }
        });
    }

    @Override
    public void release() {

    }

    public void setData(List<SelectBean.Storys.Story> data){
       if(recAdapter != null){
           recAdapter.setData(data);
       }
    }

    public void addClickListener(ClickListener listener){
        clickListener = listener;
    }
    public interface ClickListener{
        void onClickListener(View view);
    }
}
