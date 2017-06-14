package com.example.wuzp.secondworld.view.wangdou.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.support.annotation.Nullable;

import com.example.wuzp.secondworld.view.factory.Object;
import com.example.wuzp.secondworld.view.wangdou.action.taskstatistic.TaskPersistenceUtils;
import com.example.wuzp.secondworld.view.wangdou.action.taskstatistic.bean.TaskDailyOpenBookstore;
import com.example.wuzp.secondworld.view.wangdou.action.taskstatistic.bean.TaskEvent;
import com.example.wuzp.secondworld.view.wangdou.action.taskstatistic.bean.TaskType;
import com.example.wuzp.secondworld.view.wangdou.db.table.BookCacheTable;
import com.example.wuzp.secondworld.view.wangdou.db.table.BookMarkTable;
import com.example.wuzp.secondworld.view.wangdou.db.table.BookSummaryTable;
import com.example.wuzp.secondworld.view.wangdou.db.table.BookTable;
import com.example.wuzp.secondworld.view.wangdou.db.table.ChapterTable;
import com.example.wuzp.secondworld.view.wangdou.db.table.DBOpenHelper;
import com.example.wuzp.secondworld.view.wangdou.db.table.DataCacheTable;
import com.example.wuzp.secondworld.view.wangdou.db.table.TaskCacheTable;
import com.example.wuzp.secondworld.view.wangdou.db.table.UserActionTable;
import com.example.wuzp.secondworld.view.wangdou.model.parse.book.Book;
import com.example.wuzp.secondworld.view.wangdou.model.parse.book.BookMark;
import com.example.wuzp.secondworld.view.wangdou.model.parse.book.BookSummary;
import com.example.wuzp.secondworld.view.wangdou.model.parse.book.Chapter;
import com.example.wuzp.secondworld.view.wangdou.utils.UserUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 封装所有数据库操作.
 *
 * @author jsh
 */
public class DBService {
    private static final String TAG = "DBService";
    private final static byte[] _writeLock_booktable = new byte[0];//写锁
    private final static byte[] _writeLock_chaptertable = new byte[0];//写锁
    private final static byte[] _writeLock_other = new byte[0];//写锁

    /**
     * 确保它的单例存在<br>
     * 只在程序启动时做一次创建
     */
    public static DBOpenHelper sDbOpenHelper;

    /**
     * 初始化DBService<br>
     * 初始化单例的sDbOpenHelper<br>
     * SQLiteDatabase会在sDbOpenHelper中缓存<br>
     * 确认开始时调用<br>
     */
    public synchronized static void init(Context context) {
        sDbOpenHelper = new DBOpenHelper(context);
        try {
            sDbOpenHelper.getWritableDatabase();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 释放DBService<br>
     * 确认程序退出时释放<br>
     */
    public synchronized static void close() {
        try {
            if (sDbOpenHelper != null) {
                sDbOpenHelper.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 清除所有表的数据
     */
    public synchronized static void clear() {
        SQLiteDatabase db = null;
        try {
            db = sDbOpenHelper.getWritableDatabase();
            if (db == null) {
                return;
            }
            db.beginTransaction();
            db.execSQL("delete from " + BookTable.TABLE_NAME);
            db.execSQL("delete from " + ChapterTable.TABLE_NAME);
            db.execSQL("delete from " + BookMarkTable.TABLE_NAME);
            db.execSQL("delete from " + BookSummaryTable.TABLE_NAME);
            db.execSQL("delete from " + DataCacheTable.TABLE_NAME);
            db.execSQL("delete from " + BookCacheTable.TABLE_NAME);
            db.execSQL("delete from " + UserActionTable.TABLE_NAME);
            db.setTransactionSuccessful();
        } catch (Exception e) {
        } finally {
            if (db != null && db.inTransaction()) {
                db.endTransaction();
            }
        }
    }


    /**
     * update
     */
    public static void updateData(String tableName, String[] keys, String[] values, String where, String[] args) {
        synchronized (_writeLock_booktable) {
            SQLiteDatabase db = null;
            try {
                db = sDbOpenHelper.getWritableDatabase();
                if (db == null) {
                    return;
                }
                db.beginTransaction();
                ContentValues content = new ContentValues();
                for (int i = 0; i < keys.length; i++) {
                    content.put(keys[i], values[i]);
                }
                db.update(tableName, content, where, args);
                db.setTransactionSuccessful();
            } catch (Exception e) {
            } finally {
                if (db != null && db.inTransaction()) {
                    db.endTransaction();
                }
            }
        }
    }

    public static void updateData(String tableName, String keys, String values, String where, String[] args) {
        updateData(tableName, new String[]{keys}, new String[]{values}, where, args);
    }

    /***********************************
     * Book相关Method开始
     ************************************/


    public static void updateBooksByFilePath(String[] field, String[] value, String filepath) {
        updateData(BookTable.TABLE_NAME, field, value, BookTable.FILE_PATH + " = ? and " + BookTable.UID + " = ? ", new String[]{filepath, UserUtils.getUid()});
    }

    /**
     * 更新多本书籍通过bookid
     *
     * @param field  键
     * @param value  值
     * @param Bookid bookid
     */
    public static void updateBooksByBookid(String[] field, String[] value, String Bookid) {
        updateData(BookTable.TABLE_NAME, field, value, BookTable.BOOK_ID + " = ? and " + BookTable.UID + " = ?", new String[]{Bookid, UserUtils.getUid()});
    }

    /**
     * 更新单本书籍
     *
     * @param field  键
     * @param value  值
     * @param Bookid bookid
     */
    public static void updateBookByBookid(String field, String value, String Bookid) {
        updateBooksByBookid(new String[]{field}, new String[]{value}, Bookid);
    }

    public static void updateBookUidByBookid(String bookid) {
        updateData(BookTable.TABLE_NAME, BookTable.UID, UserUtils.getUid(), BookTable.BOOK_ID + " = ? and " + BookTable.UID + " = ?", new String[]{bookid, ""});
    }

    public static void updateBookUidByFilepath(String filepath) {
        updateData(BookTable.TABLE_NAME, BookTable.UID, UserUtils.getUid(), BookTable.FILE_PATH + " = ? and " + BookTable.UID + " = ?", new String[]{filepath, ""});
    }


    public static void deteleBookByUidWithBookid(String bookid) {
        synchronized (_writeLock_booktable) {
            SQLiteDatabase db = null;
            try {
                db = sDbOpenHelper.getWritableDatabase();
                if (db == null) {
                    return;
                }
                db.beginTransaction();
                db.delete(BookTable.TABLE_NAME, BookTable.BOOK_ID + " = ? and " + BookTable.UID + " = ?", new String[]{bookid, ""});
                db.setTransactionSuccessful();
            } catch (Exception e) {
            } finally {
                if (db != null && db.inTransaction()) {
                    db.endTransaction();
                }
            }
        }
    }


    public static void deteleBookByUidWithFilepath(String filepath) {
        synchronized (_writeLock_booktable) {
            SQLiteDatabase db = null;
            try {
                db = sDbOpenHelper.getWritableDatabase();
                if (db == null) {
                    return;
                }
                db.beginTransaction();
                db.delete(BookTable.TABLE_NAME, BookTable.FILE_PATH + " = ? and " + BookTable.UID + " = ?", new String[]{filepath, ""});
                db.setTransactionSuccessful();
            } catch (Exception e) {
            } finally {
                if (db != null && db.inTransaction()) {
                    db.endTransaction();
                }
            }
        }
    }


    public static List<Book> queryAllBooks(boolean containAddfail) {
        synchronized (_writeLock_booktable) {
            SQLiteDatabase db = null;
            Cursor cursor = null;
            List<Book> list = new ArrayList<>();
            try {
                db = sDbOpenHelper.getReadableDatabase();
                if (db == null) {
                    return null;
                }
                db.beginTransaction();
                String flag = containAddfail ? " in ('normal','addfail')" : " = 'normal' ";
                cursor = db.query(BookTable.TABLE_NAME, null, BookTable.UID + " = ? and " + BookTable.FLAG + flag, new String[]{UserUtils.getUid()}, null, null, null);
                cursor.moveToFirst();
                for (int i = 0; i < cursor.getCount(); i++) {
                    Book book = null;// BookModel.getBookByCursor(cursor);
                    list.add(book);
                    cursor.moveToNext();
                }
                db.setTransactionSuccessful();
                return list;
            } catch (Exception e) {
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
                if (db != null && db.inTransaction()) {
                    db.endTransaction();
                }
            }
            return null;
        }
    }


    public static List<Book> queryEmptyUidBook() {
        synchronized (_writeLock_booktable) {
            SQLiteDatabase db = null;
            Cursor cursor = null;
            List<Book> list = new ArrayList<>();
            try {
                db = sDbOpenHelper.getReadableDatabase();
                if (db == null) {
                    return list;
                }
                db.beginTransaction();
                cursor = db.query(BookTable.TABLE_NAME, null, BookTable.UID + " = ?", new String[]{""}, null, null, null);
                cursor.moveToFirst();
                for (int i = 0; i < cursor.getCount(); i++) {
                    Book book = null;//BookModel.getBookByCursor(cursor);
                    list.add(book);
                    cursor.moveToNext();
                }
                db.setTransactionSuccessful();
                return list;
            } catch (Exception e) {
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
                if (db != null && db.inTransaction()) {
                    db.endTransaction();
                }
            }
            return list;
        }
    }

    /**
     * 通过属性字段查询书籍信息
     */
    public static List<Book> queryInfo(String tableName, String key, String value) {
        synchronized (_writeLock_booktable) {
            SQLiteDatabase db = null;
            Cursor cursor = null;
            List<Book> books = new ArrayList<>();
            try {
                db = sDbOpenHelper.getReadableDatabase();
                if (db == null) {
                    return null;
                }
                db.beginTransaction();
                cursor = db.query(tableName, null, key + " = ? and " + BookTable.UID + " = ? ", new String[]{value, UserUtils.getUid()}, null, null, null);
                if (cursor.getCount() == 0) {
                    return null;
                } else {
                    cursor.moveToFirst();
                    for (int i = 0; i < cursor.getCount(); i++) {
                        Book book = null;//BookModel.getBookByCursor(cursor);
                        books.add(book);
                        cursor.moveToNext();
                    }
                }
                db.setTransactionSuccessful();
                return books;
            } catch (Exception e) {
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
                if (db != null && db.inTransaction()) {
                    db.endTransaction();
                }
            }
            return null;
        }
    }

    /**
     * 通过bookid查询book信息
     */
    public static Book queryBooksInfoBybookid(String bookid) {
        List<Book> books = queryInfo(BookTable.TABLE_NAME, BookTable.BOOK_ID, bookid);
        if (books != null && books.size() > 0) {
            return books.get(0);
        } else {
            return null;
        }
    }

    /**
     * 通过bookid查询book信息 并且考虑flag
     */
    public static Book queryBooksInfoBybookidAndFlag(String bookid) {
        synchronized (_writeLock_booktable) {
            SQLiteDatabase db = null;
            Cursor cursor = null;
            List<Book> books = new ArrayList<>();
            try {
                db = sDbOpenHelper.getReadableDatabase();
                if (db == null) {
                    return null;
                }
                db.beginTransaction();
                cursor = db.query(BookTable.TABLE_NAME, null, BookTable.BOOK_ID + " = ? and " + BookTable.UID + " = ? and " + BookTable.FLAG + " in ('normal','addfail')", new String[]{bookid, UserUtils.getUid()}, null, null, null);
                if (cursor.getCount() == 0) {
                    return null;
                } else {
                    cursor.moveToFirst();
                    for (int i = 0; i < cursor.getCount(); i++) {
                        Book book = null;// BookModel.getBookByCursor(cursor);
                        books.add(book);
                        cursor.moveToNext();
                    }
                }
                db.setTransactionSuccessful();
                return books.get(0);
            } catch (Exception e) {
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
                if (db != null && db.inTransaction()) {
                    db.endTransaction();
                }
            }
            return null;
        }
    }

    /**
     * 通过filepath查询book信息
     */
    public static Book qureyBookInfoByFilepath(String filepath) {
        List<Book> books = queryInfo(BookTable.TABLE_NAME, BookTable.FILE_PATH, filepath);
        if (books != null && books.size() > 0) {
            return books.get(0);
        } else {
            return null;
        }
    }

    /**
     * 通过bookid删除多本书籍
     */
    public static void deleteBooksByBookid(List<String> list) {
        synchronized (_writeLock_booktable) {
            SQLiteDatabase db = null;
            try {
                db = sDbOpenHelper.getWritableDatabase();
                if (db == null) {
                    return;
                }
                db.beginTransaction();
                for (int i = 0; i < list.size(); i++) {
                    db.delete(BookTable.TABLE_NAME, BookTable.BOOK_ID + " = ? and " + BookTable.UID + " = ?", new String[]{list.get(i), UserUtils.getUid()});
                }
                db.setTransactionSuccessful();
            } catch (Exception e) {
            } finally {
                if (db != null && db.inTransaction()) {
                    db.endTransaction();
                }
            }
        }
    }

    /**
     * 通过filepath删除书籍
     */
    public static void deleteBookByFilePath(String filePath) {
        synchronized (_writeLock_booktable) {
            SQLiteDatabase db = null;
            try {
                db = sDbOpenHelper.getWritableDatabase();
                if (db == null) {
                    return;
                }
                db.beginTransaction();
                db.delete(BookTable.TABLE_NAME, BookTable.FILE_PATH + " = ? and " + BookTable.UID + " = ?", new String[]{filePath, UserUtils.getUid()});
                db.setTransactionSuccessful();
            } catch (Exception e) {
            } finally {
                if (db != null && db.inTransaction()) {
                    db.endTransaction();
                }
            }
        }
    }

    /**
     * 通过bookid删除书籍
     *
     * @param bookid bookid
     */
    public static void deleteBookByBookid(String bookid) {
        List<String> list = new ArrayList<>();
        list.add(bookid);
        deleteBooksByBookid(list);
    }

    public static int saveLocalBook(File file) {
        synchronized (_writeLock_booktable) {
            Book book = new Book();
            book.setTitle(file.getName());
            book.setIsOnlineBook(false);
            book.setFilePath(file.getAbsolutePath());
            book.setFileSize("" + file.length());
            book.setDownloadstatus(true);
            book.setLastreadtime(System.currentTimeMillis());
            book.setBook_id("-1");//本地导入书籍bookid设置为-1
            SQLiteDatabase db = null;
            Cursor cursor = null;
            int isSuccess = -1;
            try {
                db = sDbOpenHelper.getWritableDatabase();
                if (db == null) {
                    return isSuccess;
                }
                db.beginTransaction();
                ContentValues values = null;// BookModel.setBookAllValues(book, false);
                cursor = db.query(BookTable.TABLE_NAME, null, BookTable.FILE_PATH + " = ? and " + BookTable.UID + " = ? ", new String[]{file.getAbsolutePath(), UserUtils.getUid()}, null, null, null);
                int count = cursor.getCount();
                if (count == 0) {
                    db.insert(BookTable.TABLE_NAME, null, values);
                    isSuccess = 0;//添加成功
                } else {
                    isSuccess = 1;//添加失败
                }
                db.setTransactionSuccessful();
                return isSuccess;
            } catch (Exception e) {
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
                if (db != null && db.inTransaction()) {
                    db.endTransaction();
                }
            }
            return -1;
        }
    }

    /**
     * 向数据库中保存一条数据
     *
     * @param insertBooks insertBooks
     */
    public static void saveBook(Book insertBooks, boolean isInlayBook) {
        List<Book> list = new ArrayList<>();
        list.add(insertBooks);
        saveBooksChange(list, isInlayBook);
    }

    /**
     * 向数据库中保存多条数据
     * 向数据库中保存多条数据 已存在的进行更新
     *
     * @param insertBooks books
     */
    public static void saveBooksChange(List<Book> insertBooks, boolean isInlayBook) {
        synchronized (_writeLock_booktable) {
            SQLiteDatabase db = null;
            Cursor cursor = null;
            List<String> bookids = new ArrayList<>();
            List<String> netReadTimes = new ArrayList<>();
            List<String> downloadTimes = new ArrayList<>();
            try {
                if (insertBooks == null || insertBooks.size() == 0) {
                    return;
                }
                db = sDbOpenHelper.getWritableDatabase();
                if (db == null) {
                    return;
                }
                db.beginTransaction();
                cursor = db.query(BookTable.TABLE_NAME, new String[]{BookTable.BOOK_ID, BookTable.NET_READ_TIME, BookTable.DOWNLOAD_TIME}, BookTable.UID + " = ?", new String[]{UserUtils.getUid()}, null, null, null);
                if (cursor.getCount() > 0) {
                    for (int i = 0; i < cursor.getCount(); i++) {
                        cursor.moveToPosition(i);
                        bookids.add(cursor.getString(0));
                        netReadTimes.add(cursor.getString(1));
                        downloadTimes.add(cursor.getString(2));
                    }
                }
                for (int i = 0; i < insertBooks.size(); i++) {
                    if (bookids.contains(insertBooks.get(i).getBook_id())) {
                        int index = bookids.indexOf(insertBooks.get(i).getBook_id());
                       // db.update(BookTable.TABLE_NAME, BookModel.setBookNetValues(insertBooks.get(i), Long.valueOf(netReadTimes.get(index)) < insertBooks.get(i).getNetReadTime(), Long.valueOf(downloadTimes.get(index)) < insertBooks.get(i).getDownloadTime()), BookTable.BOOK_ID + " = ? and " + BookTable.UID + " = ?", new String[]{insertBooks.get(i).getBook_id(), UserUtils.getUid()});
                    } else {
                       // db.insert(BookTable.TABLE_NAME, null, BookModel.setBookAllValues(insertBooks.get(i), isInlayBook));
                    }
                }
                db.setTransactionSuccessful();
            } catch (Exception e) {
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
                if (db != null && db.inTransaction()) {
                    db.endTransaction();
                }
            }
        }
    }


    /***********************************
     * ChapterTable开始
     * **********************************
     * <p>
     * <p>
     * <p>
     * /**
     *
     * @param chapters    章节列表
     * @param allowUpadte 是否允许更新
     */
    public static long saveChapterInfo(List<Chapter> chapters, boolean allowUpadte) {
        synchronized (_writeLock_chaptertable) {
            SQLiteDatabase db = null;
            Cursor cursor = null;
            List<String> chapterIds = new ArrayList<>();
            try {
                if (chapters == null || chapters.size() == 0) {
                    return 0;
                }
                db = sDbOpenHelper.getWritableDatabase();
                if (db == null) {
                    return 0;
                }
                db.beginTransaction();
                cursor = db.query(ChapterTable.TABLE_NAME, new String[]{ChapterTable.CHAPTER_ID}, ChapterTable.TAG + " = ? and " + ChapterTable.CHAPTER_FLAGS + " = ?",
                        new String[]{chapters.get(0).getTag(), UserUtils.getUid()}, null, null, null);
                if (cursor.getCount() > 0) {
                    for (int i = 0; i < cursor.getCount(); i++) {
                        cursor.moveToPosition(i);
                        chapterIds.add(cursor.getString(0));
                    }
                }
                for (int i = 0; i < chapters.size(); i++) {
                    if (chapterIds.contains(chapters.get(i).getC_id())) {
                        if (allowUpadte) {
//                            db.update(ChapterTable.TABLE_NAME, ChapterModel.setChapterValues(chapters.get(i)),
//                                    ChapterTable.TAG + " = ? and " + ChapterTable.CHAPTER_ID + " = ? and " + ChapterTable.CHAPTER_FLAGS + " = ? ",
//                                    new String[]{chapters.get(i).getTag(), chapters.get(i).getC_id(), UserUtils.getUid()});
                        }
                    } else {
//                        db.insert(ChapterTable.TABLE_NAME, null, ChapterModel.setChapterValues(chapters.get(i)));
                    }
                }
                db.setTransactionSuccessful();
                return chapters.size();
            } catch (Exception e) {
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
                if (db != null && db.inTransaction()) {
                    db.endTransaction();
                }
            }
            return 0;
        }
    }

    public static long saveChapterInfo(List<Chapter> chapters) {
        return saveChapterInfo(chapters, true);
    }

    public static long saveChapterInfo(Chapter chapter) {
        return saveChapterInfo(chapter, false);
    }

    public static long saveChapterInfo(Chapter chapter, boolean update) {
        List<Chapter> chapters = new ArrayList<>();
        chapters.add(chapter);
        return saveChapterInfo(chapters, update);
    }

    public static long saveNewChapterInfo(List<Chapter> chapters) {
        synchronized (_writeLock_chaptertable) {
            SQLiteDatabase db = null;
            try {
                db = sDbOpenHelper.getWritableDatabase();
                db.beginTransaction();
                for (int i = 0; i < chapters.size(); i++) {
                    String insertSQL = "insert into " + ChapterTable.TABLE_NAME + "(" + ChapterTable.BOOK_ID + "," + ChapterTable.CHAPTER_ID
                            + "," + ChapterTable.IS_VIP + "," + ChapterTable.SERIAL_NUMBER + ","
                            + ChapterTable.LENGTH + "," + ChapterTable.START_POS + "," + ChapterTable.TITLE
                            + "," + ChapterTable.TAG + "," + ChapterTable.CHAPTER_FLAGS + ") values " + "(?,?,?,?,?,?,?,?,?)";
                    SQLiteStatement stat = db.compileStatement(insertSQL);
                    stat.bindString(1, chapters.get(i).getBook_id());
                    stat.bindString(2, chapters.get(i).getC_id());
                    stat.bindString(3, chapters.get(i).getVip());
                    stat.bindLong(4, chapters.get(i).getS_num());
                    stat.bindLong(5, chapters.get(i).getLength());
                    stat.bindLong(6, chapters.get(i).getStartPos());
                    stat.bindString(7, chapters.get(i).getTitle());
                    stat.bindString(8, chapters.get(i).getTag());
                    stat.bindString(9, UserUtils.getUid());
                    stat.executeInsert();
                }
                db.setTransactionSuccessful();
                return chapters.size();
            } catch (Exception e) {
            } finally {
                if (db != null && db.inTransaction()) {
                    db.endTransaction();
                }
            }
            return 0;
        }
    }


    public static void setChapterUid(String tag) {
        synchronized (_writeLock_chaptertable) {
            SQLiteDatabase db = null;
            try {
                db = sDbOpenHelper.getWritableDatabase();
                if (db == null) {
                    return;
                }
                db.beginTransaction();
                ContentValues values = new ContentValues();
                values.put(ChapterTable.CHAPTER_FLAGS, UserUtils.getUid());
                db.update(ChapterTable.TABLE_NAME, values, ChapterTable.CHAPTER_FLAGS + " = ? and " + ChapterTable.TAG + " = ? ", new String[]{"", tag});
                db.setTransactionSuccessful();
            } catch (Exception e) {
            } finally {
                if (db != null && db.inTransaction()) {
                    db.endTransaction();
                }
            }
        }
    }

    public static void deteleChapterByTagwithNulluid(String tag) {
        synchronized (_writeLock_chaptertable) {
            SQLiteDatabase db = null;
            try {
                db = sDbOpenHelper.getWritableDatabase();
                if (db == null) {
                    return;
                }
                db.beginTransaction();
                db.delete(ChapterTable.TABLE_NAME, ChapterTable.TAG + " = ? and " + ChapterTable.CHAPTER_FLAGS + " = ?", new String[]{tag, ""});
                db.setTransactionSuccessful();
            } catch (Exception e) {
            } finally {
                if (db != null && db.inTransaction()) {
                    db.endTransaction();
                }
            }
        }
    }

    public static void resetAllChapterInfo(String bookid) {
        synchronized (_writeLock_chaptertable) {
            SQLiteDatabase db = null;
            try {
                db = sDbOpenHelper.getWritableDatabase();
                db.beginTransaction();
                ContentValues content = new ContentValues();
                content.put(ChapterTable.START_POS, 0);
                content.put(ChapterTable.LENGTH, 0);
                db.update(ChapterTable.TABLE_NAME, content,
                        ChapterTable.BOOK_ID + " = ? and " + ChapterTable.CHAPTER_FLAGS + " = ?", new String[]{
                                bookid, UserUtils.getUid()});
                db.setTransactionSuccessful();
            } catch (Exception e) {
            } finally {
                if (db != null && db.inTransaction()) {
                    db.endTransaction();
                }
            }
        }
    }


    /**
     * 查询章节信息通过bookid和snum（针对在线书籍）
     *
     * @param chapterid cid
     * @return 章节对象
     */
    public static Chapter queryChapterInfoByTagWithCid(String tag, String chapterid) {
        synchronized (_writeLock_chaptertable) {
            SQLiteDatabase db = null;
            Cursor cursor = null;
            Chapter chapter;
            try {
                if (chapterid == null) {
                    return null;
                }
                db = sDbOpenHelper.getReadableDatabase();
                db.beginTransaction();
                cursor = db.query(ChapterTable.TABLE_NAME, null,
                        ChapterTable.TAG + " = ? and " + ChapterTable.CHAPTER_ID + " = ? and " + ChapterTable.CHAPTER_FLAGS + " = ? ",
                        new String[]{tag, chapterid, UserUtils.getUid()}, null, null, ChapterTable.SERIAL_NUMBER + " ASC");
                if (cursor.getCount() == 0) {
                    return null;
                }
                cursor.moveToFirst();
                chapter = null;//ChapterModel.getChapterByCursor(cursor);
                db.setTransactionSuccessful();
                return chapter;
            } catch (Exception e) {
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
                if (db != null && db.inTransaction()) {
                    db.endTransaction();
                }
            }
            return null;
        }
    }

    /**
     * 通过bookid删除书籍
     */
    public static void deleteChapterByTag(String tag) {
        synchronized (_writeLock_chaptertable) {
            SQLiteDatabase db = null;
            try {
                db = sDbOpenHelper.getWritableDatabase();
                if (db == null) {
                    return;
                }
                db.beginTransaction();
                db.delete(ChapterTable.TABLE_NAME, ChapterTable.TAG + " = ? and " + ChapterTable.CHAPTER_FLAGS + " = ?", new String[]{tag, UserUtils.getUid()});
                db.setTransactionSuccessful();
            } catch (Exception e) {
            } finally {
                if (db != null && db.inTransaction()) {
                    db.endTransaction();
                }
            }
        }
    }

    /**
     * 查询所有章节
     *
     * @param tag tag
     * @return 章节列表
     */
    public static List<Chapter> queryChapterByTag(String tag) {
        synchronized (_writeLock_chaptertable) {
            SQLiteDatabase db = null;
            Cursor cursor = null;
            try {
                db = sDbOpenHelper.getReadableDatabase();
                db.beginTransaction();
                cursor = db.query(ChapterTable.TABLE_NAME, null, ChapterTable.TAG + "= ? and " + ChapterTable.CHAPTER_FLAGS + " = ?", new String[]{tag, UserUtils.getUid()}, null, null, ChapterTable.SERIAL_NUMBER + " ASC");
                db.setTransactionSuccessful();
                return null;//ChapterModel.getChaptersByCursor(cursor);
            } catch (Exception e) {
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
                if (db != null && db.inTransaction()) {
                    db.endTransaction();
                }
            }
            return null;
        }
    }

    /**
     * Update new chapter. 数据库是阻塞的，更新时应该做尽量少的更新操作
     *
     * @param newChapters the chapters
     */
    public static boolean updateNewChapter(List<Chapter> newChapters) {
        synchronized (_writeLock_chaptertable) {
            SQLiteDatabase db = null;
            Cursor cursor = null;
            boolean isUpdateBook = false;
            List<String> chapterIds = new ArrayList<>();
            try {
                if (newChapters == null || newChapters.size() == 0) {
                    return isUpdateBook;
                }
                db = sDbOpenHelper.getWritableDatabase();
                if (db == null) {
                    return isUpdateBook;
                }
                db.beginTransaction();
                cursor = db.query(ChapterTable.TABLE_NAME, new String[]{ChapterTable.CHAPTER_ID}, ChapterTable.TAG + " = ? and " + ChapterTable.CHAPTER_FLAGS + " = ?",
                        new String[]{newChapters.get(0).getTag(), UserUtils.getUid()}, null, null, null);
                if (cursor.getCount() > 0) {
                    for (int i = 0; i < cursor.getCount(); i++) {
                        cursor.moveToPosition(i);
                        chapterIds.add(cursor.getString(0));
                    }
                }
                for (int i = 0; i < newChapters.size(); i++) {
                    if (chapterIds.contains(newChapters.get(i).getC_id())) {
//                        db.update(ChapterTable.TABLE_NAME, ChapterModel.updateChapterValues(newChapters.get(i)),
//                                ChapterTable.TAG + " = ? and " + ChapterTable.CHAPTER_ID + " = ? and " + ChapterTable.CHAPTER_FLAGS + " = ? ",
//                                new String[]{newChapters.get(i).getTag(), newChapters.get(i).getC_id(), UserUtils.getUid()});
                    } else {
                        //db.insert(ChapterTable.TABLE_NAME, null, ChapterModel.setChapterValues(newChapters.get(i)));
                        isUpdateBook = true;
                    }
                }
                db.setTransactionSuccessful();
                return isUpdateBook;
            } catch (Exception e) {
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
                if (db != null && db.inTransaction()) {
                    db.endTransaction();
                }
            }
            return isUpdateBook;
        }
    }

    /**
     * 查询章节信息，通过bookid和pos
     *
     * @param tag 在线书籍tag= bookid  本地书籍tag= filepath   章节列表字段tag已赋值
     * @param Pos 字节pos
     * @return 章节对象
     */
    public static Chapter queryChapterInfoByTagWithPos(String tag, int Pos) {
        synchronized (_writeLock_chaptertable) {
            SQLiteDatabase db = null;
            Cursor cursor = null;
            Chapter chapter;
            try {
                db = sDbOpenHelper.getReadableDatabase();
                db.beginTransaction();
                cursor = db.query(ChapterTable.TABLE_NAME, null, ChapterTable.TAG + "= ? and " + ChapterTable.CHAPTER_FLAGS + " = ?", new String[]{tag, UserUtils.getUid()}, null, null, ChapterTable.SERIAL_NUMBER + " ASC");
                if (cursor.getCount() == 0) {
                    return null;
                }
                chapter = null;//ChapterModel.getChapterInfoByCursorWithPos(cursor, Pos);
                db.setTransactionSuccessful();
                return chapter;
            } catch (Exception e) {
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
                if (db != null && db.inTransaction()) {
                    db.endTransaction();
                }
            }
            return null;
        }
    }

    public static String queryNextChapterIdByTagWithChapterId(String tag, String chapterid, boolean next) {
        synchronized (_writeLock_chaptertable) {
            SQLiteDatabase db = null;
            Cursor cursor = null;
            String nextChapterId;
            try {
                db = sDbOpenHelper.getReadableDatabase();
                db.beginTransaction();
                cursor = db.query(ChapterTable.TABLE_NAME, new String[]{ChapterTable.ID, ChapterTable.CHAPTER_ID}, ChapterTable.TAG + " = ? and " + ChapterTable.CHAPTER_FLAGS + " = ?", new String[]{tag, UserUtils.getUid()}, null, null, ChapterTable.SERIAL_NUMBER + " ASC");
                if (cursor.getCount() == 0) {
                    return null;
                }
                if (chapterid.equals("-1") && next) {
                    cursor.moveToFirst();
                    return cursor.getString(1);
                }
                if (chapterid.equals("-1") && !next) {
                    return null;
                }
                nextChapterId = "";//ChapterModel.getNextChapterIdByCursorWithChapterId(cursor, chapterid, next);
                db.setTransactionSuccessful();
                return nextChapterId;
            } catch (Exception e) {
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
                if (db != null && db.inTransaction()) {
                    db.endTransaction();
                }
            }
            return null;
        }
    }

    public static int queryChapterPosByTagWithChapterId(String tag, String chapterid) {
        synchronized (_writeLock_chaptertable) {
            SQLiteDatabase db = null;
            Cursor cursor = null;
            int chapterPos;
            try {
                db = sDbOpenHelper.getReadableDatabase();
                db.beginTransaction();
                cursor = db.query(ChapterTable.TABLE_NAME, new String[]{ChapterTable.CHAPTER_ID}, ChapterTable.TAG + "= ? and " + ChapterTable.CHAPTER_FLAGS + " = ?", new String[]{tag, UserUtils.getUid()}, null, null, ChapterTable.SERIAL_NUMBER + " ASC");
                if (cursor.getCount() == 0) {
                    return -1;
                }
                if (chapterid.equals("-1")) {
                    return 0;
                }
                chapterPos = 0;//ChapterModel.getChapterPosByCursorWithChapterId(cursor, chapterid);
                db.setTransactionSuccessful();
                return chapterPos;
            } catch (Exception e) {
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
                if (db != null && db.inTransaction()) {
                    db.endTransaction();
                }
            }
            return 0;
        }
    }

    /***********************************
     * BookMarkTable开始
     ************************************/
    /**
     * 加入书签
     *
     * @param bookMark 书签对象
     * @return 添加数量
     */
    public static long insertBookMark(BookMark bookMark) {
        synchronized (_writeLock_other) {
            SQLiteDatabase db = null;
            long count;
            try {
                db = sDbOpenHelper.getWritableDatabase();
                db.beginTransaction();
                count = 1;//db.insert(BookMarkTable.TABLE_NAME, null, BookMarkModel.setBookMarkValues(bookMark));
                db.setTransactionSuccessful();
                return count;
            } catch (Exception e) {
            } finally {
                if (db != null && db.inTransaction()) {
                    db.endTransaction();
                }
            }
            return -1;
        }
    }

    /**
     * 删除符合条件的书签，并不一定是一条
     *
     * @param start 开始pos
     * @param end   结束pos
     * @return 删除数量
     */
    public static int deteleBookMark(int start, int end, String bookid) {
        synchronized (_writeLock_other) {
            SQLiteDatabase db = null;
            int count;
            try {
                db = sDbOpenHelper.getWritableDatabase();
                db.beginTransaction();
                count = db.delete(BookMarkTable.TABLE_NAME, BookMarkTable.END + "= ? and " + BookMarkTable.BEGIN + "= ? and " + BookMarkTable.BOOK_ID + " = ? and " + BookMarkTable.BOOK_SID + " = ?",
                        new String[]{String.valueOf(end), String.valueOf(start), bookid, UserUtils.getUid()});
                db.setTransactionSuccessful();
                return count;
            } catch (Exception e) {
            } finally {
                if (db != null && db.inTransaction()) {
                    db.endTransaction();
                }
            }
            return -1;
        }
    }

    /**
     * 查询书签
     *
     * @param bookid bookid
     * @param cid    chapterid
     * @return 书签列表
     */
    public static List<BookMark> queryBookMark(String bookid, String cid) {
        synchronized (_writeLock_other) {
            SQLiteDatabase db = null;
            Cursor cursor = null;
            List<BookMark> bookMarkList;
            try {
                db = sDbOpenHelper.getReadableDatabase();
                db.beginTransaction();
                if (cid == null) {
                    cursor = db.query(BookMarkTable.TABLE_NAME, null, BookMarkTable.BOOK_ID + "= ? and " + BookMarkTable.BOOK_SID + " = ?", new String[]{bookid, UserUtils.getUid()}, null, null, null);
                } else {
                    cursor = db.query(BookMarkTable.TABLE_NAME, null, BookMarkTable.BOOK_ID + "= ? and " + BookMarkTable.CHAPTER_ID + "= ? and " + BookMarkTable.BOOK_SID + " = ?",
                            new String[]{bookid, cid, UserUtils.getUid()}, null, null, null);
                }
                bookMarkList = null;//BookMarkModel.getBookMarksByCursor(cursor);
                db.setTransactionSuccessful();
                return bookMarkList;
            } catch (Exception e) {
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
                if (db != null && db.inTransaction()) {
                    db.endTransaction();
                }
            }
            return null;
        }
    }


    public static void setMarkUid(String tag) {
        synchronized (_writeLock_other) {
            SQLiteDatabase db = null;
            try {
                db = sDbOpenHelper.getWritableDatabase();
                if (db == null) {
                    return;
                }
                db.beginTransaction();
                ContentValues values = new ContentValues();
                values.put(BookMarkTable.BOOK_SID, UserUtils.getUid());
                db.update(BookMarkTable.TABLE_NAME, values, BookMarkTable.BOOK_SID + " = ? and " + BookMarkTable.BOOK_ID + " = ? ", new String[]{"", tag});
                db.setTransactionSuccessful();
            } catch (Exception e) {
            } finally {
                if (db != null && db.inTransaction()) {
                    db.endTransaction();
                }
            }
        }
    }

    /***********************************
     * BookSummaryTable开始
     ************************************/
    /**
     * 添加划线
     *
     * @param bookSummary 划线
     * @return 数量
     */
    public static long insertBookSummary(BookSummary bookSummary) {
        synchronized (_writeLock_other) {
            SQLiteDatabase db = null;
            long count;
            try {
                db = sDbOpenHelper.getWritableDatabase();
                db.beginTransaction();
                count = 0;//db.insert(BookSummaryTable.TABLE_NAME, null, BookSummaryModel.setBookSummaryValues(bookSummary));
                db.setTransactionSuccessful();
                return count;
            } catch (Exception e) {
            } finally {
                if (db != null && db.inTransaction()) {
                    db.endTransaction();
                }
            }
            return -1;
        }
    }

    /**
     * 删除符合条件的划线，并不一定是一条
     *
     * @param start 开始字符
     * @param end   结束字符
     * @return 删除数量
     */
    public static int deteleBookSummary(int start, int end, String bookid) {
        synchronized (_writeLock_other) {
            SQLiteDatabase db = null;
            int count;
            try {
                db = sDbOpenHelper.getWritableDatabase();
                db.beginTransaction();
                count = db.delete(BookSummaryTable.TABLE_NAME, BookSummaryTable.END + "= ? and " + BookSummaryTable.BEGIN + " = ? and " + BookSummaryTable.BOOK_ID + " = ? and " + BookSummaryTable.SUMMARY_BID + " = ?",
                        new String[]{String.valueOf(end), String.valueOf(start), bookid, UserUtils.getUid()});
                db.setTransactionSuccessful();
                return count;
            } catch (Exception e) {
            } finally {
                if (db != null && db.inTransaction()) {
                    db.endTransaction();
                }
            }
            return -1;
        }
    }

    /**
     * 查询划线
     *
     * @param bookid bookid
     * @param cid    chapterid
     * @return List<BookSummary>划线列表
     */
    @Nullable
    public static List<BookSummary> queryBookSummary(String bookid, String cid) {
        synchronized (_writeLock_other) {
            SQLiteDatabase db = null;
            Cursor cursor = null;
            List<BookSummary> bookSummaryList;
            try {
                db = sDbOpenHelper.getReadableDatabase();
                db.beginTransaction();
                if (cid == null) {
                    cursor = db.query(BookSummaryTable.TABLE_NAME, null, BookSummaryTable.BOOK_ID + " = ? and " + BookSummaryTable.SUMMARY_BID + " = ?", new String[]{bookid, UserUtils.getUid()}, null, null, null);
                } else {
                    cursor = db.query(BookSummaryTable.TABLE_NAME, null, BookSummaryTable.BOOK_ID + " = ? and " + BookSummaryTable.CHAPTER_ID + " = ? and " + BookSummaryTable.SUMMARY_BID + " = ?",
                            new String[]{bookid, cid, UserUtils.getUid()}, null, null, null);
                }
                bookSummaryList = null;//BookSummaryModel.getBookSummarysByCursor(cursor);
                db.setTransactionSuccessful();
                return bookSummaryList;
            } catch (Exception e) {
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
                if (db != null && db.inTransaction()) {
                    db.endTransaction();
                }
            }
            return null;
        }
    }

    /**
     * 登录时 处理未登录时增加的划线
     */
    public static void setSummaryUid(String tag) {
        synchronized (_writeLock_other) {
            SQLiteDatabase db = null;
            try {
                db = sDbOpenHelper.getWritableDatabase();
                if (db == null) {
                    return;
                }
                db.beginTransaction();
                ContentValues values = new ContentValues();
                values.put(BookSummaryTable.SUMMARY_BID, UserUtils.getUid());
                db.update(BookSummaryTable.TABLE_NAME, values, BookSummaryTable.SUMMARY_BID + " = ? and " + BookSummaryTable.BOOK_ID + " = ? ", new String[]{"", tag});
                db.setTransactionSuccessful();
            } catch (Exception e) {
            } finally {
                if (db != null && db.inTransaction()) {
                    db.endTransaction();
                }
            }
        }
    }

    /***********************************
     * UserActionTable开始
     ************************************/
    /**
     * 添加统计
     *
     * @param event 事件
     * @return 数量
     */
//    public static long insertUserAction(Event event) {
//        synchronized (_writeLock_other) {
//            SQLiteDatabase db = null;
//            long count = 0;
//            try {
//                db = sDbOpenHelper.getWritableDatabase();
//                db.beginTransaction();
//                count = db.insert(UserActionTable.TABLE_NAME, null, UserActionModel.setUserActionValues(event));
//                db.setTransactionSuccessful();
//                return count;
//            } catch (Exception e) {
//            } finally {
//                if (db != null && db.inTransaction()) {
//                    db.endTransaction();
//                }
//            }
//            return -1;
//        }
//    }

    /**
     * 更新单个事件
     *
     * @param field 键
     * @param value 值
     * @param key   event.key
     */
    public static void updateEventByKey(String[] field, String[] value, String key) {
        synchronized (_writeLock_other) {
            SQLiteDatabase db = null;
            try {
                db = sDbOpenHelper.getWritableDatabase();
                if (db == null) {
                    return;
                }
                db.beginTransaction();
                ContentValues content = new ContentValues();
                for (int i = 0; i < field.length; i++) {
                    content.put(field[i], value[i]);
                }
                db.update(UserActionTable.TABLE_NAME, content, UserActionTable.KEY + " = ?", new String[]{key});
                db.setTransactionSuccessful();
            } catch (Exception e) {
            } finally {
                if (db != null && db.inTransaction()) {
                    db.endTransaction();
                }
            }
        }

    }


    /**
     * 查询统计列表
     *
     * @return ArrayList<Event>事件列表
     */
    @Nullable
    public static ArrayList<Object> queryAllUserAction() {
        synchronized (_writeLock_other) {
            SQLiteDatabase db = null;
            Cursor cursor = null;
            ArrayList<Object> mEvents = new ArrayList<Object>();
            try {
                db = sDbOpenHelper.getReadableDatabase();
                db.beginTransaction();
                cursor = db.query(UserActionTable.TABLE_NAME, null, null, null, null, null, UserActionTable.ID + " asc");
                cursor.moveToFirst();
                for (int i = 0; i < cursor.getCount(); i++) {
                  //  mEvents.add(UserActionModel.getEventByCursor(cursor));
                    cursor.moveToNext();
                }
                db.setTransactionSuccessful();
                return mEvents;
            } catch (Exception e) {
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
                if (db != null && db.inTransaction()) {
                    db.endTransaction();
                }
            }
            return null;
        }
    }

    /**
     * 查询统计前20列表
     *
     * @return ArrayList<Event>事件列表
     */
    @Nullable
    public static ArrayList<Object> query20UserAction() {
        synchronized (_writeLock_other) {
           /* SQLiteDatabase db = null;
            Cursor cursor = null;
            ArrayList<Event> mEvents = new ArrayList<Event>();
            try {
                db = sDbOpenHelper.getReadableDatabase();
                db.beginTransaction();
                cursor = db.query(UserActionTable.TABLE_NAME, null, null, null, null, null, UserActionTable.ID + " asc", "0,20");
                cursor.moveToFirst();
                for (int i = 0; i < cursor.getCount(); i++) {
                    mEvents.add(UserActionModel.getEventByCursor(cursor));
                    cursor.moveToNext();
                }
                db.setTransactionSuccessful();
                return mEvents;
            } catch (Exception e) {
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
                if (db != null && db.inTransaction()) {
                    db.endTransaction();
                }
            }*/
            return null;
        }
    }

    /**
     * 删除统计
     * 由于老版本segmentationPull字段没用上，删除条件不用添加
     */
    public static void deteleUserAction(List<Object> events) {
        /*synchronized (_writeLock_other) {
            SQLiteDatabase db = null;
            try {
                db = sDbOpenHelper.getWritableDatabase();
                if (db == null) {
                    return;
                }
                db.beginTransaction();
                for (Event event : events) {
                    db.delete(UserActionTable.TABLE_NAME, UserActionTable.KEY + " = ? ", new String[]{event.key});
                }
                db.setTransactionSuccessful();
            } catch (Exception e) {
            } finally {
                if (db != null && db.inTransaction()) {
                    db.endTransaction();
                }
            }
        }*/
    }

    /***********************************
     * 任务相关
     ************************************/
    public static List<TaskEvent> queryTask(String uid) {
        synchronized (_writeLock_other) {
            SQLiteDatabase db = null;
            Cursor cursor = null;
            List<TaskEvent> tasks = new ArrayList<>();
            try {
                db = sDbOpenHelper.getReadableDatabase();
                db.beginTransaction();
                cursor = db.query(TaskCacheTable.TABLE_NAME, null, TaskCacheTable.UID + " = ? ", new String[]{uid}, null, null, null);
                if (cursor.getCount() > 0) {
                    tasks.addAll(TaskPersistenceUtils.cursor2Tasks(cursor));
                }
                db.setTransactionSuccessful();
                return tasks;
            } catch (Exception e) {
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
                if (db != null && db.inTransaction()) {
                    db.endTransaction();
                }
            }
            return tasks;
        }
    }

    public static void saveTasks(List<TaskEvent> taskEvents) {
        synchronized (_writeLock_other) {
            SQLiteDatabase db = null;
            Cursor cursor = null;
            List<TaskEvent> tasks = new ArrayList<>();
            try {
                db = sDbOpenHelper.getWritableDatabase();
                db.beginTransaction();
                cursor = db.query(TaskCacheTable.TABLE_NAME, null, null, null, null, null, null);
                if (cursor.getCount() > 0) {
                    tasks.addAll(TaskPersistenceUtils.cursor2Tasks(cursor));
                }
                for (int i = 0; i < taskEvents.size(); i++) {
                    ContentValues values = new ContentValues();
                    values.put(TaskCacheTable.TASK, taskEvents.get(i).getTaskType());
                    values.put(TaskCacheTable.UID, taskEvents.get(i).getUid());
                    values.put(TaskCacheTable.EXTRA, taskEvents.get(i).getExtra().toString());
                    if (tasks.contains(taskEvents.get(i))) {
                        if (!(taskEvents.get(i) instanceof TaskDailyOpenBookstore)) {
                            db.update(TaskCacheTable.TABLE_NAME, values, TaskCacheTable.TASK + " = ? and " + TaskCacheTable.UID + " = ?"
                                    , new String[]{taskEvents.get(i).getTaskType() + "", taskEvents.get(i).getUid()});
                        }
                    } else {
                        db.insert(TaskCacheTable.TABLE_NAME, null, values);
                    }
                }
                db.setTransactionSuccessful();
            } catch (Exception e) {
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
                if (db != null && db.inTransaction()) {
                    db.endTransaction();
                }
            }
        }
    }

    public static void deleteTasks(String key, String values) {
        synchronized (_writeLock_other) {
            SQLiteDatabase db = null;
            try {
                db = sDbOpenHelper.getWritableDatabase();
                db.beginTransaction();
                db.delete(TaskCacheTable.TABLE_NAME, key + " = ? ", new String[]{values});
                db.setTransactionSuccessful();
            } catch (Exception e) {
            } finally {
                if (db != null && db.inTransaction()) {
                    db.endTransaction();
                }
            }
        }
    }

    public static void clearDailyTask() {
        synchronized (_writeLock_other) {
            SQLiteDatabase db = null;
            try {
                db = sDbOpenHelper.getWritableDatabase();
                if (db == null) {
                    return;
                }
                db.beginTransaction();
                db.delete(TaskCacheTable.TABLE_NAME, TaskCacheTable.TASK + " in ( " + TaskType.task_daily_opbookstore +
                        " , " + TaskType.task_daily_opapp + " , " + TaskType.task_daily_time + " )", null);
                db.setTransactionSuccessful();
            } catch (Exception e) {
            } finally {
                if (db != null && db.inTransaction()) {
                    db.endTransaction();
                }
            }
        }
    }
}