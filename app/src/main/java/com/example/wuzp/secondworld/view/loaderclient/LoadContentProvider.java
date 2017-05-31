package com.example.wuzp.secondworld.view.loaderclient;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.example.wuzp.secondworld.view.clientPage.Todo;

/**
 * Created by wuzp on 2017/5/15.
 * 自己构造一个contentProvider 来操作自己的定义的数据库（重写数据库的查插删改的方法）
 */
public class LoadContentProvider extends ContentProvider {
    private static final String AUTHORITY = "com.example.wuzp.secondworld.view.loaderclient";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + LoadOpenHelper.TABLE);

    public static final int TODOS_URI_CODE = 0;
    public static final int TODO_URI_CODE = 1;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    // 关联Uri和Uri_Code
    static {
        sUriMatcher.addURI(AUTHORITY, LoadOpenHelper.TABLE, TODOS_URI_CODE);//全表
        sUriMatcher.addURI(AUTHORITY, LoadOpenHelper.TABLE + "/#", TODO_URI_CODE);//具体的某行数据
    }

    private Context mContext;
    private SQLiteDatabase mDb;

    public LoadContentProvider() {}

    @Override
    public boolean onCreate() {
        mContext = getContext();
        LoadOpenHelper loadOpenHelper = new LoadOpenHelper(mContext);
        mDb = loadOpenHelper.getWritableDatabase();
        return false;
    }

    //得到reader表中的所有记录
    public static String TODOS = "vnd.android.cursor.dir/vnd.example.reader";
    //得到一个表信息
    public static String TODO = "vnd.android.cursor.item/vnd.example.reader";

    @Override
    public String getType(@NonNull Uri uri) {
        switch (sUriMatcher.match(uri)) {
            case TODOS_URI_CODE:
                return TODOS;

            case TODO_URI_CODE:
                return TODO;

            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        // Uisng SQLiteQueryBuilder instead of query() method
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

        // Set the table
        queryBuilder.setTables(LoadOpenHelper.TABLE);

        switch (sUriMatcher.match(uri)) {
            case TODOS_URI_CODE:
                break;

            case TODO_URI_CODE:
                queryBuilder.appendWhere(Todo.COLUMN_ID + "=" + uri.getPathSegments().get(1));
                break;

            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        Cursor cursor =
                queryBuilder.query(mDb, projection, selection, selectionArgs, null, null, sortOrder);
        // make sure that potential listeners are getting notified
        cursor.setNotificationUri(mContext.getContentResolver(), uri);

        return cursor;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        switch (sUriMatcher.match(uri)) {
            case TODOS_URI_CODE: {
                /**
                 * Add a new student record
                 */
                long rowID = mDb.insert(LoadOpenHelper.TABLE, "", values);

                /**
                 * If record is added successfully
                 */

                if (rowID > 0) {
                    Uri _uri = ContentUris.withAppendedId(CONTENT_URI, rowID);
                    mContext.getContentResolver().notifyChange(_uri, null);
                    return _uri;
                }
            }
            default:
                //不能识别uri
                throw new IllegalArgumentException("This is a unKnow Uri" + uri.toString());
        }
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int count = 0;
        switch (sUriMatcher.match(uri)) {
            case TODOS_URI_CODE:
                count = mDb.delete(LoadOpenHelper.TABLE, selection, selectionArgs);
                break;

            case TODO_URI_CODE:
                String id = uri.getLastPathSegment();
                count = mDb.delete(LoadOpenHelper.TABLE, LoadOpenHelper.COLUMN_ID + " = " + id +
                        (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);
                break;

            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        if (count > 0) mContext.getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        int count;

        switch (sUriMatcher.match(uri)) {
            case TODOS_URI_CODE:
                count = mDb.update(LoadOpenHelper.TABLE, values, selection, selectionArgs);
                break;

            case TODO_URI_CODE:
                count =
                        mDb.update(LoadOpenHelper.TABLE, values, LoadOpenHelper.COLUMN_ID + " = " + uri.getPathSegments().get(1) +
                                (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);
                break;

            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        if (count > 0) mContext.getContentResolver().notifyChange(uri, null);
        return count;
    }
}
