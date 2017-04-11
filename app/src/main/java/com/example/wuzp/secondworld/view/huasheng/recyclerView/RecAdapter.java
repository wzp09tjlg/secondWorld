package com.example.wuzp.secondworld.view.huasheng.recyclerView;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.example.wuzp.secondworld.network.parse.SelectBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wuzp on 2017/4/6.
 */
public class RecAdapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int DATA_TYPE_HEADER = 0; //头
    private final int DATA_TYPE_CONTENT1 = 1; //没有图片
    private final int DATA_TYPE_CONTENT2 = 2; //有一张图片
    private final int DATA_TYPE_CONTENT3 = 3; //有三张图片
    private final int DATA_TYPE_CONTENT4 = 4; //有九张图
    private final int DATA_TYPE_TAIL     = 5; //尾

    private Context mContext;
    private List<SelectBean.Storys.Story> mData = new ArrayList<>();

    public RecAdapter(Context context){
       mContext = context;
    }

    public void setData(List<SelectBean.Storys.Story> data) {
        int firstPosition = mData.size();
        int lastPosition = data.size();
        mData.addAll(data);
        notifyItemChanged(firstPosition,lastPosition-1);//只是更新选定的区间item 减少item的波动
    }

    @Override
    public int getItemViewType(int position) {
        SelectBean.Storys.Story story = mData.get(position);

        return super.getItemViewType(position);
    }

    public int getItemContentViewType(int position){
        return 0;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{


        public ViewHolder(View view){
            super(view);
        }

    }
}
