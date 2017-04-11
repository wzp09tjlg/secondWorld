package com.example.wuzp.secondworld.view.databinding;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wuzp.secondworld.R;
import com.example.wuzp.secondworld.databinding.ItemData1Binding;
import com.example.wuzp.secondworld.databinding.ItemData2Binding;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wuzp on 2017/4/10.
 * 1.当前只有一种数据类型
 * 2.测试两种数据类型
 */
public class DataAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
   private final int TYPE_DATA1 = 0;
   private final int TYPE_DATA2 = 1;

    private Context mContext = null;
    private LayoutInflater inflater = null;
    private List<User> mData = new ArrayList<>();

    public DataAdapter(Context context){
       this.mContext = context;
        this.inflater = LayoutInflater.from(context);
    }

    public void setData(List<User> data){
        int firstPosition = mData.size();
        int len = data.size();
        mData.addAll(data);
        notifyItemRangeChanged(firstPosition,len - 1);
    }

    @Override
    public int getItemViewType(int position) {
        if(mData.get(position).isvisit())
            return TYPE_DATA1;
        else return TYPE_DATA2;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == TYPE_DATA1){
            ItemData1Binding binding = DataBindingUtil.inflate(inflater,R.layout.item_data1,null,false);
            ViewHolder viewHolder = new ViewHolder(binding.getRoot());
            viewHolder.setItemDataBinding(binding);
            return viewHolder; //创建ViewHolder 对ViewHolder的绑定
        }else{
            ItemData2Binding binding = DataBindingUtil.inflate(inflater,R.layout.item_data2,null,false);
            ViewHolder2 viewHolder = new ViewHolder2(binding.getRoot());
            viewHolder.setItemDataBinding(binding);
            return viewHolder; //创建ViewHolder 对ViewHolder的绑定
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(getItemViewType(position) == TYPE_DATA1){
            ((ViewHolder)holder).binding.setUser(mData.get(position));//数据的绑定
        }else{
            ((ViewHolder2)holder).binding.setUser(mData.get(position));//数据的绑定
        }
        //这里不存在对数据的删除 所以这样写,如果是存在数据可以被删除 这里的position就不能跟viewHolder 对应了
        //当然 这里其实也是可以分为多种view 类型的
        //
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private ItemData1Binding binding = null;

        public ViewHolder(View view){
            super(view);
        }

        public void setItemDataBinding(ItemData1Binding binding){
           this.binding = binding;
        }

        public ItemData1Binding getBinding(){
            return this.binding;
        }
    }

    public static class ViewHolder2 extends RecyclerView.ViewHolder{
        private ItemData2Binding binding = null;

        public ViewHolder2(View view){
            super(view);
        }

        public void setItemDataBinding(ItemData2Binding binding){
            this.binding = binding;
        }

        public ItemData2Binding getBinding(){
            return this.binding;
        }
    }
}
