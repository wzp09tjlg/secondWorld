package com.example.wuzp.secondworld.view.diff;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;

import java.util.ArrayList;

/**
 * Created by wuzp on 2017/4/20.
 * 集成 diffUtil的callback的方法
 * 比较两个数据集的不一致，然后进行局部的更新操作 达到减少资源消耗 和 节省的目的
 */
public class DiffCallback extends DiffUtil.Callback {
    private ArrayList<DiffBean> newList,oldList;

    public DiffCallback(ArrayList<DiffBean> oldList,ArrayList<DiffBean> newList){
        this.oldList = oldList;
        this.newList = newList;
    }

    @Override
    public int getOldListSize() {
        return oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return newList.get(newItemPosition).getId() == oldList.get(oldItemPosition).getId();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition).getName().equals(newList.get(newItemPosition).getName());
    }

    //这个方法就是两个不同的item 进行传值操作 areItemsTheSame 为true areContentsTheSame 为false时调用
    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        DiffBean bean = newList.get(newItemPosition);
        Bundle diffBunlde = new Bundle();
        diffBunlde.putString("NAME",bean.getName());
        return diffBunlde;
    }
}
