package com.example.wuzp.secondworld.view.diff;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.apkfuns.logutils.LogUtils;
import com.example.wuzp.secondworld.R;
import com.example.wuzp.secondworld.databinding.ItemDiffBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wuzp on 2017/4/20.
 */
public class DiffAdapter extends RecyclerView.Adapter<DiffAdapter.ViewHolder> {
    private ArrayList<DiffBean> mData;
    private LayoutInflater inflater;

    public DiffAdapter(Context context) {
        inflater = LayoutInflater.from(context);
    }

    public ArrayList<DiffBean> getData() {
        return mData;
    }

    public void setData(List<DiffBean> data) {
        this.mData = new ArrayList<>(data);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemDiffBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_diff, null, false);
        return new ViewHolder(binding);
    }

    //支持局部更新的一种实现方式 再也不会点击刷新就全部都刷新的情况 节省资源和减小开销
    @Override
    public void onBindViewHolder(ViewHolder holder, int position, List<Object> payloads) {
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position);
        } else {
            LogUtils.e("size:" + payloads.size());
            if(payloads.size() > 0){ //不知道为什么会是空的
                Bundle bundle = (Bundle) payloads.get(0);
                if (bundle.getString("NAME") != null) {
                    String name = "" + bundle.getString("NAME");
                    holder.binding.textName.setText(name);//不要直接使用 bundle 中的数据 不然都不知道是怎么死的
                    holder.binding.textName.setTextColor(Color.BLUE);
                }
            }
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.binding.textId.setText(mData.get(position).getId() + ""); //不能直接在setText上使用int 会被当做 查找资源Id
        holder.binding.textName.setText(mData.get(position).getName().toString());
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private ItemDiffBinding binding;

        public ViewHolder(ItemDiffBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
