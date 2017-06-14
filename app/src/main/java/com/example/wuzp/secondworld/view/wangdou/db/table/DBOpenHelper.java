package com.example.wuzp.secondworld.view.wangdou.db.table;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

import com.example.wuzp.secondworld.HApplication;
import com.example.wuzp.secondworld.view.wangdou.utils.StorageUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 数据库创建
 *
 * @author fzx
 */
public class DBOpenHelper extends SdCardSQLiteOpenHelper {
    private static final String TAG = "DBOpenHelper";
    public static final String DB_NAME = "sinareader.db";
    // TODO 数据库的版本号不能随意改动，只有在数据库中的某个表需要增加或删除某个字段时升级版本号
    private static final int VERSION = 19;                                                                            // 首次支持线上的epub书籍阅读的版本
    /**
     * 书本表目前版本有44列<br>  3.1.0
     * 书签表目前版本11列<br>
     * 章节表目前版本11列<br>
     * 书摘表目前版本13列<br>
     * 更新数据库完成后，会对列数做比对，只要列数不对，<br>
     * 认为数据库更新有问题，删表重新创建<br>
     * WARN 更新表后，请同步跟新这里
     */
    //3.1.0 增加列  是否需要全量更新
    private static final int NOW_VERSION_BOOK_COLUMNS = 44;
    private static final int NOW_VERSION_BOOKMARK_COLUMNS = 12;
    private static final int NOW_VERSION_BOOKSUMMARY_COLUMNS = 14;
    private static final int NOW_VERSION_CHAPTER_COLUMNS = 11;
    private static final int NOW_VERSION_BOOKCACHE_COLUMNS = 22;

    public Context context;

    public DBOpenHelper(Context context) {
        super(context.getFilesDir().getPath(), DB_NAME, null, VERSION);
        this.context = context;
    }

    @Override
    public SQLiteDatabase onCreateDatabase(String dbPath, String dbName, CursorFactory factory) {
        if (!StorageUtil.isSDCardExist()) {
            return null;
        }

        SQLiteDatabase db = null;
        // 创建数据库
        File dir = new File(dbPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        File dbf = new File(dbPath + dbName);
        if (dbf.exists()) {
            dbf.delete();
        }

        // 拷贝本地书籍
        copyDataBase();

        db = SQLiteDatabase.openOrCreateDatabase(dbf, null);
        return db;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // warn:数据库的字段添加往表后添加，防止影响以前版本的使用
        // 尽量的兼容前面版本

        try {
            // 创建书本表
            db.execSQL(BookTable.getCreateSQL());

            // 创建章节信息表
            db.execSQL(ChapterTable.getCreateSQL());

            // 创建书签表
            db.execSQL(BookMarkTable.getCreateSQL());

            // 创建书摘表
            db.execSQL(BookSummaryTable.getCreateSQL());

            // 创建用户统计表
            db.execSQL(UserActionTable.getCreateSQL());

            // 创建缓存表
            createCacheTable(db);

            // 创建书本缓存表
            createBookCacheTable(db);
        } catch (Exception e) {
            //			LogUtil.d(TAG, "db onCreate, Exception >> e:" + e);
        }
    }

    /**
     * 创建缓存表
     */
    private void createCacheTable(SQLiteDatabase db) {
        // 缓存表数据不重要，每次创建都直接先删除掉
        db.execSQL(DataCacheTable.getDeleteSQL());
        db.execSQL(DataCacheTable.getCreateSQL());
    }

    /**
     * 创建书本缓存表
     */
    private void createBookCacheTable(SQLiteDatabase db) {
        // 缓存表数据不重要，每次创建都直接先删除掉
        db.execSQL(BookCacheTable.getDeleteSQL());
        db.execSQL(BookCacheTable.getCreatSQL());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int dbVersion, int newVersion) {
        onCreate(db);

        // 当新版本小于当前数据库版本时，不更新
        // 这样的话，我们的数据库需要向前兼容
        // int dbVersion = db.getVersion();
        if (newVersion <= dbVersion) {
            return;
        }
        try {
            if (dbVersion < 2) {
                v1to2(db);
            }
            if (dbVersion < 3) {
                v2to3(db);
            }
            if (dbVersion < 4) {
                v3to4(db);
            }
            if (dbVersion < 5) {
                v4to5(db);
            }
            if (dbVersion < 6) {
                v5to6(db);
            }
            if (dbVersion < 7) {
                v6to7(db);
            }
            if (dbVersion < 8) {
                v7to8(db);
            }
            if (dbVersion < 9) {
                v8to9(db);
            }
            if (dbVersion < 10) {
                v9to10(db);
            }
            if (dbVersion < 11) {
                v10to11(db);
            }
            if (dbVersion < 12) {
                v11to12(db);
            }
            if (dbVersion < 13) {
                v12to13(db);
            }
            if (dbVersion < 14) {
                v13to14(db);
            }
            if (dbVersion < 15) {
                v14to15(db);
            }
            if (dbVersion < 17) {
                changeAssetDB(db);
            }
            if (dbVersion < 18) {
                v17tov18(db);
            }
            if (dbVersion < 19) {
                v18tov19(db);
            }
            // if(dbVersion <= 16) {
            // v16to17(db);
            // }

            // 此判断多余了
            // if (newVersion > dbVersion) {
            //			changeAssetDB(db);
            // }

            updateTableCheck(db);
        } catch (Exception e) {
            // 如果实在更新不成功，重新创建
            if (dbVersion == 14) {
                // version 14->15 只更新了书签书摘数据库，删除也应该只删除这2个数据库
                db.execSQL(BookMarkTable.getDeleteSQL());
                db.execSQL(BookSummaryTable.getDeleteSQL());

                // 创建书签表
                db.execSQL(BookMarkTable.getCreateSQL());
                // 创建书摘表
                db.execSQL(BookSummaryTable.getCreateSQL());
            } else {
                deleteAllTable(db, "onUpgrade Exception");
                onCreate(db);
            }
        }
        db.setVersion(newVersion);
    }



    private void copyDataBase(String outFileName) {
        InputStream myInput = null;
        OutputStream myOutput = null;
        try {
            myInput = HApplication.gContext.getAssets().open(DB_NAME);
            myOutput = new FileOutputStream(outFileName);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = myInput.read(buffer)) > 0) {
                myOutput.write(buffer, 0, length);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (myOutput != null) {
                    myOutput.flush();
                    myOutput.close();
                }
                if (myInput != null) {
                    myInput.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void copyDataBase() {
        String path = context.getFilesDir().getPath() + DB_NAME;
        copyDataBase(path);
    }

    /**
     * 删除所有表
     */
    private void deleteAllTable(SQLiteDatabase db, String tag) {
        db.execSQL(BookTable.getDeleteSQL());
        db.execSQL(ChapterTable.getDeleteSQL());
        db.execSQL(BookMarkTable.getDeleteSQL());
        db.execSQL(BookSummaryTable.getDeleteSQL());
        db.execSQL(DataCacheTable.getDeleteSQL());
        db.execSQL(BookCacheTable.getDeleteSQL());
        db.execSQL(UserActionTable.getDeleteSQL());
    }

    /**
     * 管理各数据库版本变化，检查数据库的更新是否有效
     */
    private void updateTableCheck(SQLiteDatabase db) {
        Cursor cursor = null;

        try {
            String sql = null;
            int bookColumnsCount = -1;
            int markColumnsCount = -1;
            int chapterColumnsCount = -1;
            int summaryColumnsCount = -1;
            int bookCacheColumnsCount = -1;

            // 比对书本表
            sql = "SELECT * from Book LIMIT 1";
            cursor = db.rawQuery(sql, null);
            if (cursor != null && cursor.moveToFirst()) {
                bookColumnsCount = cursor.getColumnCount();
            }
            if (cursor != null) {
                cursor.close();
                cursor = null;
            }
            if (bookColumnsCount != -1 && bookColumnsCount != NOW_VERSION_BOOK_COLUMNS) {
                deleteAllTable(db, "book");
                onCreate(db);
                return;
            }

            // 比对书签表
            sql = "SELECT * from BookMark LIMIT 1";
            cursor = db.rawQuery(sql, null);
            if (cursor != null && cursor.moveToFirst()) {
                markColumnsCount = cursor.getColumnCount();
            }
            if (cursor != null) {
                cursor.close();
                cursor = null;
            }
            if (markColumnsCount != -1 && markColumnsCount != NOW_VERSION_BOOKMARK_COLUMNS) {
                deleteAllTable(db, "bookmark");
                onCreate(db);
                return;
            }

            // 比对章节表
            sql = "SELECT * from ChapterForReader LIMIT 1";
            cursor = db.rawQuery(sql, null);
            if (cursor != null && cursor.moveToFirst()) {
                chapterColumnsCount = cursor.getColumnCount();
            }
            if (cursor != null) {
                cursor.close();
                cursor = null;
            }
            if (chapterColumnsCount != -1 && chapterColumnsCount != NOW_VERSION_CHAPTER_COLUMNS) {
                deleteAllTable(db, "chapter");
                onCreate(db);
                return;
            }

            // 比对书摘表
            sql = "SELECT * from BookSummary LIMIT 1";
            cursor = db.rawQuery(sql, null);
            if (cursor != null && cursor.moveToFirst()) {
                summaryColumnsCount = cursor.getColumnCount();
            }
            if (cursor != null) {
                cursor.close();
                cursor = null;
            }
            if (summaryColumnsCount != -1 && summaryColumnsCount != NOW_VERSION_BOOKSUMMARY_COLUMNS) {
                deleteAllTable(db, "booksummary");
                onCreate(db);
                return;
            }

            // 比对书本缓存表
            sql = "SELECT * from BookCache LIMIT 1";
            cursor = db.rawQuery(sql, null);
            if (cursor != null && cursor.moveToFirst()) {
                bookCacheColumnsCount = cursor.getColumnCount();
            }
            if (cursor != null) {
                cursor.close();
                cursor = null;
            }
            if (bookCacheColumnsCount != -1 && bookCacheColumnsCount != NOW_VERSION_BOOKCACHE_COLUMNS) {
                deleteAllTable(db, "BookCache");
                onCreate(db);
            }
        } catch (Exception e) {
            // doNothing
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    // 版本1到版本2
    private void v1to2(SQLiteDatabase db) {
        db.execSQL("ALTER TABLE Book ADD lastReadPercent float(20) DEFAULT(0)");
        db.execSQL("ALTER TABLE Book ADD downloadTime largeint(256)");
        db.execSQL("ALTER TABLE Book ADD vdiskDownloadUrl varchar(256)");
        db.execSQL("ALTER TABLE Book ADD vdiskFilePath varchar(256)");
        db.execSQL("ALTER TABLE Book ADD uid varchar(256)");
    }

    // 版本2到版本3
    private void v2to3(SQLiteDatabase db) {
        db.execSQL("ALTER TABLE Book ADD sid varchar(50)");
    }

    // 版本3到版本4
    private void v3to4(SQLiteDatabase db) {
        db.execSQL("ALTER TABLE Book ADD isParise integer DEFAULT (0)");
    }

    // 版本4到版本5
    private void v4to5(SQLiteDatabase db) {
        db.execSQL("ALTER TABLE Book ADD contentType integer DEFAULT (0)");
        db.execSQL("ALTER TABLE Book ADD suiteId integer DEFAULT (0)");
        // 创建书摘表
        db.execSQL(BookSummaryTable.getCreateSQL());
    }

    // 版本5到版本6
    private void v5to6(SQLiteDatabase db) {
        db.execSQL("ALTER TABLE Book ADD suiteName varchar(80)");
    }

    // 版本6到版本7
    private void v6to7(SQLiteDatabase db) {
        createCacheTable(db);
    }

    // 版本7到版本8
    private void v7to8(SQLiteDatabase db) {
        db.execSQL("ALTER TABLE Book ADD autoBuy integer DEFAULT (0)");
        db.execSQL("ALTER TABLE Book ADD isOnlineBook integer DEFAULT (0)");
        db.execSQL("ALTER TABLE Book ADD ownerUid varchar(256)");
        db.execSQL("ALTER TABLE Book ADD onlineReadChapterId integer DEFAULT (0)");
    }

    // 版本8到版本9
    private void v8to9(SQLiteDatabase db) {
        createBookCacheTable(db);
    }

    // 版本9到版本10
    private void v9to10(SQLiteDatabase db) {
        db.execSQL("ALTER TABLE Book ADD isRemind integer DEFAULT (1)");
    }

    // 版本10到版本11
    private void v10to11(SQLiteDatabase db) {
        db.execSQL("ALTER TABLE ChapterForReader ADD chapterFlags integer DEFAULT (0)");
    }

    // 版本11到版本12
    private void v11to12(SQLiteDatabase db) {
        db.execSQL("ALTER TABLE Book ADD originSuiteId integer DEFAULT (0)");
    }

    // 版本12到版本13
    private void v12to13(SQLiteDatabase db) {
        db.execSQL("ALTER TABLE ChapterForReader ADD serialNumber integer DEFAULT (-1)");
    }

    // 版本13到版本14
    private void v13to14(SQLiteDatabase db) {
        db.execSQL("ALTER TABLE Book ADD lastReadJsonString text");
        db.execSQL("ALTER TABLE BookSummary ADD summaryJsonString text");
        db.execSQL("ALTER TABLE BookMark ADD markJsonString text");
    }

    // 版本14到15
    private void v14to15(SQLiteDatabase db) {
        db.execSQL("ALTER TABLE BookSummary ADD book_id text");
        db.execSQL("ALTER TABLE BookMark ADD book_id text");
    }

    // 版本15到16
    private void changeAssetDB(SQLiteDatabase db) {
        deleteOldDB(db);
        // addNewAsset(db);
    }

    private void v17tov18(SQLiteDatabase db) {
        db.execSQL("ALTER TABLE Book ADD isUpdateList integer DEFAULT (0)");
    }


    private void v18tov19(SQLiteDatabase db) {
        db.execSQL(UserActionTable.getDeleteSQL());
        db.execSQL(UserActionTable.getCreateSQL());
        db.execSQL(TaskCacheTable.getDeleteSQL());
        db.execSQL(TaskCacheTable.getCreatSQL());
        db.execSQL("ALTER TABLE Book ADD netReadTime largeint(256)");
    }

    // 版本16到17
    // private void v16to17(SQLiteDatabase db) {
    // db.execSQL("ALTER TABLE BookCache ADD "+BookCacheTable.TITLE+" varchar(256)");
    // db.execSQL("ALTER TABLE BookCache ADD "+BookCacheTable.AUTHOR+" varchar(20)");
    // db.execSQL("ALTER TABLE BookCache ADD "+BookCacheTable.INTRO+" text");
    // db.execSQL("ALTER TABLE BookCache ADD "+BookCacheTable.IMAGE_URL+" varchar(256)");
    // db.execSQL("ALTER TABLE BookCache ADD "+BookCacheTable.UID+" varchar(256)");
    // }

    private void deleteOldDB(SQLiteDatabase db) {
        String sql = "select * from " + BookTable.TABLE_NAME;
        Cursor cursor = db.rawQuery(sql, null);
        cursor = db.rawQuery(sql, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                String path = cursor.getString(cursor.getColumnIndex(BookTable.FILE_PATH));
                if (path.contains("file:///android_asset/")) {
                    int id = cursor.getInt(cursor.getColumnIndex(BookTable.ID));
                    db.delete(BookTable.TABLE_NAME, BookTable.ID + " = ?", new String[]{id + ""});
                }
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
            cursor = null;
        }
    }
}