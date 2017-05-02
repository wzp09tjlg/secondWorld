package com.example.wuzp.secondworld.view.cursorloader;

import android.content.Context;
import android.database.Cursor;
import android.provider.CallLog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.apkfuns.logutils.LogUtils;
import com.example.wuzp.secondworld.R;
import com.example.wuzp.secondworld.databinding.ItemLoaderBinding;


/**
 * Created by wuzp on 2017/4/28.
 * 这里是recyclerView  和 Cursorloader 联合使用 总是在滑动时 报错，也没有找到错误的地方。以后再去尝试
 */
public class RecyclerCursorLoaderAdapter extends RecyclerViewCursorAdapter<RecyclerCursorLoaderAdapter.ViewHolder> {
    private Context mContext;
    private Cursor mCursor;
    private LayoutInflater inflater;

   public RecyclerCursorLoaderAdapter(Context context,Cursor cursor,int flag){
       super(context,cursor,flag);
       inflater = LayoutInflater.from(context);
       this.mCursor = cursor;
       this.mContext = context;
   }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_loader,parent);
       // ItemLoaderBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_loader,null,false);
        ViewHolder viewHolder = new ViewHolder(view);
       // viewHolder.setBinding(binding);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, Cursor cursor) {
        holder.textName.setText(cursor.getString(cursor.getColumnIndex(CallLog.Calls.CACHED_NAME)));
        holder.textNum.setText(cursor.getString(cursor.getColumnIndex(CallLog.Calls.NUMBER)));
        holder.textDate.setText(cursor.getString(cursor.getColumnIndex(CallLog.Calls.DATE)));
    }

    @Override
    protected void onContentChanged() {
        LogUtils.e("onContentChanged------------------------");
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        ItemLoaderBinding binding;
        private TextView textName;
        private TextView textNum;
        private TextView textDate;

        public ViewHolder(View view){
            super(view);
            textName = (TextView)view.findViewById(R.id.text_name);
            textNum = (TextView)view.findViewById(R.id.text_num);
            textDate = (TextView)view.findViewById(R.id.text_date);
        }

        public void setBinding(ItemLoaderBinding binding) {
            this.binding = binding;
        }

        public ItemLoaderBinding getBinding() {
            return binding;
        }
    }
}
