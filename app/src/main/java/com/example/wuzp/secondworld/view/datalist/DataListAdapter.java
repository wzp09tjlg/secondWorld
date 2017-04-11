package com.example.wuzp.secondworld.view.datalist;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.wuzp.secondworld.R;
import com.example.wuzp.secondworld.databinding.ItemDataList1Binding;
import com.example.wuzp.secondworld.databinding.ItemDataList2Binding;
import com.example.wuzp.secondworld.network.parse.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wuzp on 2017/4/11.
  现在先测试下一种类型的数据
  测试完成之后再测试多种数据的情况
 */
public class DataListAdapter extends BaseAdapter {
   private final int TYPE_DATA1 = 0;
   private final int TYPE_DATA2 = 1;

    private List<User> mData ;
    private Context mContext;
    private LayoutInflater inflater;

    public DataListAdapter(Context context){
        this.mContext = context;
        this.inflater = LayoutInflater.from(context);
    }

    public void addData(List<User> data){
        if(mData == null){
            mData = new ArrayList<>();
        }
        mData.addAll(data);
    }

    @Override
    public int getCount() {
        return mData != null ? mData.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
       //return TYPE_DATA1;
         if(position % 2 == 0){
            return TYPE_DATA1;
        }else{
            return TYPE_DATA2;
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder1 viewHolder1 = null;
        ViewHolder2 viewHolder2 = null;
        if(convertView == null){
            if(getItemViewType(position) == TYPE_DATA1){
                ItemDataList1Binding binding = DataBindingUtil.inflate(inflater,R.layout.item_data_list1,null,false);
                viewHolder1 = new ViewHolder1(binding);
                convertView = binding.getRoot();
                convertView.setTag(R.id.id_viewholder,viewHolder1);
            }else{
                ItemDataList2Binding binding = DataBindingUtil.inflate(inflater,R.layout.item_data_list2,null,false);
                viewHolder2 = new ViewHolder2(binding);
                convertView = binding.getRoot();
                convertView.setTag(R.id.id_viewholder,viewHolder2);
            }
        }else{
            //这里不能直接转 因为存在View不是空 但是是其他的View类型的
            if(convertView.getTag(R.id.id_viewholder) instanceof ViewHolder1){
                viewHolder1 = (ViewHolder1) convertView.getTag(R.id.id_viewholder);
            }else if(convertView.getTag(R.id.id_viewholder) instanceof ViewHolder2){
                viewHolder2 = (ViewHolder2) convertView.getTag(R.id.id_viewholder);
            }
        }
        if(viewHolder1 != null){ //因为是转换过来的 所以空的肯定不是 当前数据类型，多类型 多加判断
            viewHolder1.binding.setUser(mData.get(position));
        }else{
            viewHolder2.binding.setUser(mData.get(position));
        }
        return convertView;
    }

    public static class ViewHolder1{
        public ItemDataList1Binding binding;
        public ViewHolder1(ItemDataList1Binding binding){
            this.binding = binding;
        }

        public ItemDataList1Binding getBinding(){
            return this.binding;
        }
    }

    public static class ViewHolder2{
        public ItemDataList2Binding binding;
        public ViewHolder2(ItemDataList2Binding binding){
            this.binding = binding;
        }

        public ItemDataList2Binding getBinding(){
            return this.binding;
        }
    }
}
