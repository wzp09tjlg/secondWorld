package com.example.wuzp.secondworld.view.wangdou.db.table;

public class TaskCacheTable { /**
 * 表名
 */
public static final String TABLE_NAME = "TaskCache";

    public static final String ID = "_id";
    public static final String TASK = "task";
    public static final String UID = "uid";
    public static final String EXTRA = "extra";

    public static final String[] COLUMNS = {ID,TASK, UID, EXTRA};

    /**
     * 获取建表SQL语句
     */
    public static String getCreatSQL() {
        StringBuilder builder = new StringBuilder();
        builder.append("CREATE TABLE IF NOT EXISTS ").append(TABLE_NAME);
        builder.append(" (");
        builder.append(ID).append(" integer primary key autoincrement, ");
        builder.append(TASK).append(" text,");
        builder.append(UID).append(" text,");
        builder.append(EXTRA).append(" text ");
        builder.append(")");
        return builder.toString();
    }

    /**
     * 获取删表SQL语句
     */
    public static String getDeleteSQL() {
        return "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

}
