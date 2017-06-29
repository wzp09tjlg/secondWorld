package com.example.wuzp.secondworld.view.VarDb.ormlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

/**
 * Created by wuzp on 2017/6/29.
 * 1.ormsqlite的帮助类 就是在sqlite的基础上封装的数据库框架.所以在数据库操作过程中 与sqlite的数据库操作基本一致
 */
public class OrmLiteHelper extends OrmLiteSqliteOpenHelper {

    private static final String DB_NAME = "DB_USER.db";//数据库文件的名字
    private static final int VERSION = 2; //数据库版本
    private static OrmLiteHelper liteHelper;

    //表对象
    private Dao<User, Integer> userDao;

    public OrmLiteHelper(Context context) {
        super(context, DB_NAME, null, VERSION);//第三个参数是传一个factory 不知道是啥，直接传null就可以
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, User.class);//创建表 TABLE_USER
        } catch (Exception e) {
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int i, int i1) {
        try {
            TableUtils.dropTable(connectionSource, User.class, true);//删除表TABLE_USER
            onCreate(sqLiteDatabase, connectionSource);             //再新创建表
        } catch (Exception e) {
        }
    }

    public static OrmLiteHelper getInstance(Context context) {
        if (liteHelper == null) {
            synchronized (OrmLiteHelper.class) {
                if (liteHelper == null) {
                    liteHelper = new OrmLiteHelper(context);
                }
            }
        }
        return liteHelper;
    }

    public Dao<User, Integer> getUserDao() {
        try {
            if (userDao == null) {
                userDao = getDao(User.class);//获取UserDao
            }
        } catch (Exception e) {}
        return userDao;
    }

    @Override
    public void close() {
        super.close();
        userDao = null;//在数据库关闭时 释放表数据
    }
}
