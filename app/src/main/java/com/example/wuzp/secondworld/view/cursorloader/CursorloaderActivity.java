package com.example.wuzp.secondworld.view.cursorloader;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.CallLog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.wuzp.secondworld.R;
import com.example.wuzp.secondworld.databinding.ActivityCursorloaderBinding;
import com.example.wuzp.secondworld.view.base.BindingActivity;

/**
 * Created by wuzp on 2017/4/28.
 * 0.cursor 和 listView 使用
 * 1.cursor 和 cursorAdapter 使用
 * 2.cursorloader 和 cursoradapter 使用
 * 3.cursorloader 和 recyclerView 使用
 */
public class CursorloaderActivity extends BindingActivity<ActivityCursorloaderBinding,CursorloaderPresenter> implements
        CursorloaderContract.IView,CursorloaderContract.OnItemClick,
        LoaderManager.LoaderCallbacks<Cursor>
{
    private CursorloaderViewWrapper viewWrapper = new CursorloaderViewWrapper();
    private CursorLoaderAdapter adapter;
    RecyclerCursorLoaderAdapter adapter1;

    @Override
    @SuppressWarnings("all")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewWrapper.addBinding(binding);
        viewWrapper.addClickListener(this);
        getLoaderManager().initLoader(0,null,this);
        Cursor cursor = getContentResolver().query(CallLog.Calls.CONTENT_URI, null, null, null, null);
        //adapter = new CursorLoaderAdapter(this,cursor,false);
        adapter = new CursorLoaderAdapter(this,null);
        adapter1 = new RecyclerCursorLoaderAdapter(this,null,RecyclerViewCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        /*binding.list.setAdapter(adapter);*/
        binding.recycler.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        binding.recycler.setAdapter(adapter1);

        /**
         * SCROLLBARS_INSIDE_OVERLAY,
         SCROLLBARS_INSIDE_INSET,
         SCROLLBARS_OUTSIDE_OVERLAY,
         SCROLLBARS_OUTSIDE_INSET

         @see #SCROLLBARS_INSIDE_OVERLAY
          * @see #SCROLLBARS_INSIDE_INSET
         * @see #SCROLLBARS_OUTSIDE_OVERLAY
         * @see #SCROLLBARS_OUTSIDE_INSET
         * */
        //计算recyclerView上所有的item的高度是多少
        int sumHeight = 0;
    }

    public int getScollYDistance(RecyclerView recyclerView) {
        LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        int position = layoutManager.findFirstVisibleItemPosition();
        View firstVisiableChildView = layoutManager.findViewByPosition(position);
        int itemHeight = firstVisiableChildView.getHeight();
        return (position) * itemHeight - firstVisiableChildView.getTop();
    }

    @Override
    protected CursorloaderPresenter createPresenter() {
        return new CursorloaderPresenter(this);
    }

    @Override
    protected int createLayoutId() {
        return R.layout.activity_cursorloader;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_get:
                getLoaderManager().restartLoader(0,null,CursorloaderActivity.this);
                break;
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return  new CursorLoader(this,
                CallLog.Calls.CONTENT_URI, null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        adapter.swapCursor(data);
        adapter1.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
      adapter.swapCursor(null);
        adapter1.swapCursor(null);
    }
}
