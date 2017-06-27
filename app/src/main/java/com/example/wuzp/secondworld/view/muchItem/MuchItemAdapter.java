package com.example.wuzp.secondworld.view.muchItem;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.wuzp.secondworld.R;

import java.util.List;

/**
 * Created by wuzp on 2017/6/27.
 */

public class MuchItemAdapter extends RecyclerView.Adapter<MuchItemAdapter.ViewHolder> {

    private List<MuchItem> mData;

    public MuchItemAdapter(List<MuchItem> data){
        mData = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_much,null));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.num.setText(mData.get(position).getNum());
        holder.name.setText(mData.get(position).getName());
        holder.date.setText(mData.get(position).getDate());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView num;
        private TextView name;
        private TextView date;

        public ViewHolder(View view){
            super(view);
            num = (TextView)view.findViewById(R.id.text_num);
            name = (TextView)view.findViewById(R.id.text_name);
            date = (TextView)view.findViewById(R.id.text_date);
        }
    }
}
