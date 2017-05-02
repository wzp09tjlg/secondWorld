package com.example.wuzp.secondworld.view.loader;

import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.os.Bundle;
import android.provider.CallLog;
import android.view.View;

import com.apkfuns.logutils.LogUtils;
import com.example.wuzp.secondworld.R;
import com.example.wuzp.secondworld.databinding.ActivityLoaderBinding;
import com.example.wuzp.secondworld.view.base.BindingActivity;

/**
 * Created by wuzp on 2017/4/27.
 */
public class LoaderActivity extends BindingActivity<ActivityLoaderBinding,LoaderPresenter>  implements
        LoaderContract.IView,LoaderManager.LoaderCallbacks<Cursor> {
    private LoaderViewWrapper viewWrapper = new LoaderViewWrapper();
    private LoaderAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewWrapper.addBinding(binding);
        binding.btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLoaderManager().restartLoader(0,null,LoaderActivity.this);
            }
        });
        adapter = new LoaderAdapter(this);
        binding.list.setAdapter(adapter);
        getLoaderManager().initLoader(0,null,this);//初始化cursorLoader 绑定回调
    }

    @Override
    protected LoaderPresenter createPresenter() {
        return new LoaderPresenter(this);
    }

    @Override
    protected int createLayoutId() {
        return R.layout.activity_loader;
    }

    private Context mContext = this;


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        CursorLoader loader = new CursorLoader(mContext,
                CallLog.Calls.CONTENT_URI, null, null, null, null)
                //对于cursorLoader 是可以重写其中的一些方法  来进行获取数据的
//        {
//            @Override
//            public Cursor loadInBackground() {
//                Cursor cursor = super.loadInBackground();
//                if (cursor == null)
//                    return null;
//                CallLogsCursor callLogsCursor = new CallLogsCursor(cursor);
//                return callLogsCursor;
//            }
//        }
        ;
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        adapter.setCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.setCursor(null);
    }

    public class CallLogsCursor extends CursorWrapper {

        public CallLogsCursor(Cursor cursor) {
            super(cursor);

            int nameIndex = cursor.getColumnIndex(CallLog.Calls.CACHED_NAME);
            int numberIndex = cursor.getColumnIndex(CallLog.Calls.NUMBER);
            int typeIndex = cursor.getColumnIndex(CallLog.Calls.TYPE);
            int dateIndex = cursor.getColumnIndex(CallLog.Calls.DATE);
            int durationIndex = cursor.getColumnIndex(CallLog.Calls.DURATION);

            // 从游标的最后索引往前查询,因为最新的通话记录在最后
            for (cursor.moveToLast(); !cursor.isBeforeFirst(); cursor
                    .moveToPrevious()) {
                // 姓名
                String name = cursor.getString(nameIndex);
                // 号码
                String number = cursor.getString(numberIndex);
                // 类型
                int type = cursor.getInt(typeIndex);
                // 日期
                long date = cursor.getLong(dateIndex);
                // 通话时长
                int duration = cursor.getInt(durationIndex);

                LogUtils.e("name=" + name + ", number=" + number
                        + ", type=" + type + ", date=" + date + ", duration="
                        + duration);
            }
        }
    }
}
