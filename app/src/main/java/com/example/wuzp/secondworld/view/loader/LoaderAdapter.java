package com.example.wuzp.secondworld.view.loader;

import android.content.Context;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.provider.CallLog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.wuzp.secondworld.R;
import com.example.wuzp.secondworld.databinding.ItemLoaderBinding;

/**
 * Created by wuzp on 2017/4/27.
 */
public class LoaderAdapter extends BaseAdapter {
    private Cursor cursor;
    private LayoutInflater inflater;

    public LoaderAdapter(Context context){
          inflater = LayoutInflater.from(context);
    }

    public void setCursor(Cursor cursor){
        if(this.cursor != null){
            this.cursor.close();
        }
        this.cursor = cursor;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return cursor == null ? 0 : (cursor.isClosed() ? 0 :cursor.getCount());
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView == null){
            ItemLoaderBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_loader,null,false);
            viewHolder = new ViewHolder(binding);
            convertView = binding.getRoot();
            convertView.setTag(viewHolder);
        }else{
             viewHolder =(ViewHolder) convertView.getTag();
        }
        cursor.moveToPosition(position);
        viewHolder.binding.textName.setText(cursor.getString(cursor.getColumnIndex(CallLog.Calls.CACHED_NAME)));
        viewHolder.binding.textNum.setText( cursor.getString(cursor.getColumnIndex(CallLog.Calls.NUMBER)));
        viewHolder.binding.textDate.setText( cursor.getString(cursor.getColumnIndex(CallLog.Calls.DATE)));
        return convertView;
    }

    static class ViewHolder{
       ItemLoaderBinding binding;
        public ViewHolder(ItemLoaderBinding binding){
            this.binding = binding;
        }

        public ItemLoaderBinding getBinding() {
            return binding;
        }

        public void setBinding(ItemLoaderBinding binding) {
            this.binding = binding;
        }
    }
}
