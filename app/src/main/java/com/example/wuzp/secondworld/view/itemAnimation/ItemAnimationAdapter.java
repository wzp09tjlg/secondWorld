package com.example.wuzp.secondworld.view.itemAnimation;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wuzp.secondworld.R;

import java.util.List;

/**
 * Created by wuzp on 2017/6/16.
 */
public class ItemAnimationAdapter extends RecyclerView.Adapter<ItemAnimationAdapter.ItemViewHolder> {

    private List<ItemBean> mData;
    private LayoutInflater inflater;

    public ItemAnimationAdapter(Context context, List<ItemBean> data) {
        this.mData = data;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(inflater.inflate(R.layout.item_animtion,null));
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        holder.imgCover.setImageResource(mData.get(position).getIcon());
        holder.textName.setText(mData.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return null == mData ? 0 : mData.size();
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgCover;
        private TextView textName;

        public ItemViewHolder(View view) {
            super(view);
            imgCover = (ImageView) view.findViewById(R.id.img_cover);
            textName = (TextView) view.findViewById(R.id.text_name);
        }
    }
}
