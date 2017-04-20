package com.example.wuzp.secondworld.view.diff;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.wuzp.secondworld.databinding.ActivityDiffBinding;
import com.example.wuzp.secondworld.view.base.BaseViewWrapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wuzp on 2017/4/20.
 */
public class DiffViewWrapper extends BaseViewWrapper<ActivityDiffBinding> {
    public DiffViewWrapper() {
    }

    private DiffAdapter diffAdapter;
    private ArrayList<DiffBean> mData;
    private Context mContext;

    @Override
    public void addBinding(@NonNull ActivityDiffBinding binding) {
        super.addBinding(binding);
        mContext = binding.getRoot().getContext();
        initView();
    }

    @Override
    public void release() {
    }


    private void initView() {
        RecyclerView.LayoutManager layoutManager =
                new LinearLayoutManager(mContext,
                        LinearLayoutManager.VERTICAL, false);
        diffAdapter = new DiffAdapter(mContext);
        diffAdapter.setData(getData());
        binding.recycler.setLayoutManager(layoutManager);
        binding.recycler.setAdapter(diffAdapter);
        binding.btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mData.set(1, new DiffBean(2, "Fndroid"));
                mData.add(new DiffBean(8, "Jason"));
                DiffBean s2 = mData.get(2);
                mData.remove(2);
                mData.add(s2);

                ArrayList<DiffBean> old_students = diffAdapter.getData();
                DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffCallback(old_students, mData), false);
                //最后的参数表示 两数据不一样时  true表示 主要做动画 false表示先做删除再做增加处理
                //最后还有一个boolean类型的参数，这个参数指定是否需要进行Move的检测，
                // 如果不需要，如果有Item移动了，会被认为是先remove，然后insert。
                // 这里指定为true，所以就有了动图显示的移动效果
                diffAdapter.setData(mData);
                result.dispatchUpdatesTo(diffAdapter);
            }
        });
    }

    private List<DiffBean> getData() {
        mData = new ArrayList<>();
        mData.add(new DiffBean(0, "wangyi"));
        mData.add(new DiffBean(1, "zhangsan"));
        mData.add(new DiffBean(2, "liuer"));
        mData.add(new DiffBean(3, "zhaosi"));
        mData.add(new DiffBean(4, "lasiwu"));
        return mData;
    }
}
