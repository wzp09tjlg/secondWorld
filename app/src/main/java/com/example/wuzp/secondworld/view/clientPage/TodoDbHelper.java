package com.example.wuzp.secondworld.view.clientPage;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by wuzp on 2017/5/15.
 */
public class TodoDbHelper extends SQLiteOpenHelper {
    public TodoDbHelper(Context context) {
        super(context, Todo.DB, null, Todo.DB_VERSION);
    }

    @Override public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Todo.onCreate(sqLiteDatabase);
    }

    @Override public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        Todo.onUpgrade(sqLiteDatabase, i, i1);
    }
}
