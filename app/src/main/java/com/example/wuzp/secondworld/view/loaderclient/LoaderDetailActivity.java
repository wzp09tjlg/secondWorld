package com.example.wuzp.secondworld.view.loaderclient;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.wuzp.secondworld.R;

/**
 * Created by wuzp on 2017/5/15.
 */
public class LoaderDetailActivity extends AppCompatActivity {
    private EditText mTitleText;
    private EditText mBodyText;
    Button confirmButton;
    private Uri todoUri;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        getExtra(getIntent());

        initView();
    }

    private void getExtra(Intent intent){
        Bundle extras = intent.getExtras();

        // Or passed from the other activity
        if (extras != null) {
            todoUri = extras
                    .getParcelable(LoadContentProvider.TODO);

            fillData(todoUri);
        }
    }

    private void initView(){
        mTitleText = (EditText) findViewById(R.id.todo_edit_summary);
        mBodyText = (EditText) findViewById(R.id.todo_edit_description);
        confirmButton = (Button) findViewById(R.id.todo_edit_button);
    }

    private void fillData(Uri uri) {
        String[] projection = { LoadOpenHelper.COLUMN_SUMMARY,
                LoadOpenHelper.COLUMN_DESCRIPTION };
        Cursor cursor = getContentResolver().query(uri, projection, null, null,
                null);
        if (cursor != null) {
            cursor.moveToFirst();

            mTitleText.setText(cursor.getString(cursor
                    .getColumnIndexOrThrow(LoadOpenHelper.COLUMN_SUMMARY)));
            mBodyText.setText(cursor.getString(cursor
                    .getColumnIndexOrThrow(LoadOpenHelper.COLUMN_DESCRIPTION)));

            cursor.close();
        }

        confirmButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (TextUtils.isEmpty(mTitleText.getText().toString())) {
                    makeToast();
                } else {
                    finish();
                }
            }

        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveState();
    }

    private void saveState() {
        String summary = mTitleText.getText().toString();
        String description = mBodyText.getText().toString();

        // only save if either summary or description
        // is available

        if (description.length() == 0 && summary.length() == 0) {
            return;
        }

        ContentValues values = new ContentValues();
        values.put(LoadOpenHelper.COLUMN_SUMMARY, summary);
        values.put(LoadOpenHelper.COLUMN_DESCRIPTION, description);

        if (todoUri == null) {
            // New todo
            todoUri = getContentResolver().insert(
                    LoadContentProvider.CONTENT_URI, values);
        } else {
            // Update todo
            getContentResolver().update(todoUri, values, null, null);
        }
    }

    private void makeToast(){
        Toast.makeText(this,"this is a toast",Toast.LENGTH_LONG).show();
    }
}
