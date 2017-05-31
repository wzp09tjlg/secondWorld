package com.example.wuzp.secondworld.view.loaderclient;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.example.wuzp.secondworld.R;

/**
 * Created by wuzp on 2017/5/15.
 * 数据源已经构造完毕 ，现在开始使用数据源啦
 */
public class LoaderClientActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<Cursor>
{
    private Button btnMenu;
    private ListView list;
    SimpleCursorAdapter adapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loader_client);
        initView();
    }

    private void initView(){
        btnMenu = (Button)findViewById(R.id.btn_menu);
        list = (ListView)findViewById(R.id.list);

        initData();
    }

    private void initData(){
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoaderClientActivity.this,LoaderDetailActivity.class);
                startActivity(intent);
            }
        });

        fillData();
    }

    private void fillData() {
        // Fields from the database (projection)
        // Must include the _id column for the adapter to work
        String[] from = new String[] { LoadOpenHelper.COLUMN_SUMMARY, LoadOpenHelper.COLUMN_DESCRIPTION };
        // Fields on the UI to which we map
        int[] to = new int[] { R.id.todo_summary, R.id.todo_description };

        getLoaderManager().initLoader(0, null, this); //开始准备数据
        adapter = new SimpleCursorAdapter(this, R.layout.item_row, null, from, to, 0);

        list.setAdapter(adapter);
    }

    // cursorLoader 的接口实现
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = { LoadOpenHelper.COLUMN_ID, LoadOpenHelper.COLUMN_SUMMARY, LoadOpenHelper.COLUMN_DESCRIPTION };
        return new CursorLoader(this, LoadContentProvider.CONTENT_URI, projection, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        adapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }
}
