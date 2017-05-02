package com.example.wuzp.secondworld.view.cursorloader;

import android.content.Context;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.provider.CallLog;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wuzp.secondworld.R;
import com.example.wuzp.secondworld.databinding.ItemLoaderBinding;

/**
 * Created by wuzp on 2017/4/28.
 * 这种adapter 只支持view的获取方式 不支持 databinding的方式
 */
public class CursorLoaderAdapter extends CursorAdapter {
    private LayoutInflater inflater;
    private Cursor mCursor;

    public CursorLoaderAdapter(Context context,Cursor cursor,boolean autoQuery){
        super(context,cursor,autoQuery);
        inflater = LayoutInflater.from(context);
    }

    public CursorLoaderAdapter(Context context,Cursor cursor){
        super(context,cursor);
        inflater = LayoutInflater.from(context);
        mCursor = cursor;
    }

    public void setmCursor(Cursor mCursor) {
        this.mCursor = mCursor;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
         //View view = inflater.inflate(R.layout.item_loader,null);
        ItemLoaderBinding binding = DataBindingUtil.inflate(inflater,R.layout.item_loader,null,false);
        ViewHolder viewHolder = new ViewHolder(binding);
        View view = binding.getRoot();
        view.setTag(viewHolder);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        //上边是使用databinding的方式实现 数据的绑定
        ViewHolder viewHolder = (ViewHolder) view.getTag();
        viewHolder.binding.textName.setText(cursor.getString(cursor.getColumnIndex(CallLog.Calls.CACHED_NAME)));
        viewHolder.binding.textNum.setText(cursor.getString(cursor.getColumnIndex(CallLog.Calls.NUMBER)));
        viewHolder.binding.textDate.setText(cursor.getString(cursor.getColumnIndex(CallLog.Calls.DATE)));
        //使用传统的方式 进行数据的绑定
        /*((TextView)view.findViewById(R.id.text_name)).setText(cursor.getString(cursor.getColumnIndex(CallLog.Calls.CACHED_NAME)));
        ((TextView)view.findViewById(R.id.text_num)).setText(cursor.getString(cursor.getColumnIndex(CallLog.Calls.NUMBER)));
        ((TextView)view.findViewById(R.id.text_date)).setText(cursor.getString(cursor.getColumnIndex(CallLog.Calls.DATE)));*/
    }

    static class ViewHolder{
        ItemLoaderBinding binding;

        public ViewHolder(ItemLoaderBinding binding){
            this.binding = binding;
        }
    }
}
