package com.example.wuzp.secondworld.view.loaderclient;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.wuzp.secondworld.view.clientPage.Todo;

/**
 * Created by wuzp on 2017/5/15.
 * 这是常见的sqliteopenHelper的子类
 */
public class LoadOpenHelper extends SQLiteOpenHelper {
    public final static String NAME = "reader.db";
    public final static int    VERSION = 1;
    public final static String TABLE = "reader";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_SUMMARY = "summary";
    public static final String COLUMN_DESCRIPTION = "description";

    private static final String DATABASE_CREATE = "create table "
            + TABLE
            + "("
            + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_SUMMARY + " text not null,"
            + COLUMN_DESCRIPTION
            + " text not null"
            + ");";

    public LoadOpenHelper(Context context){
        super(context,NAME,null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(Todo.class.getName(), "Upgrading database from version "
                + oldVersion + " to " + newVersion
                + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE);
        onCreate(db);
    }
}
