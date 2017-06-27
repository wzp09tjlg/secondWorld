package com.example.wuzp.secondworld.view.Db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
\ * Created by wuzp on 2017/6/27.
 * 使用常规的数据库的帮助类
 */
public class DbHelperT extends SQLiteOpenHelper {
    private static final String DB_NAME = "writer.db";
    private static final String DB_TAG  =  "DbHelperT";
    private static final int    VERSION = 1;

    private static final String TABLE_PAPER = "";

    public DbHelperT(Context context) {
        super(context,DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
       db.execSQL(PaperTable.getCreateSQL());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
