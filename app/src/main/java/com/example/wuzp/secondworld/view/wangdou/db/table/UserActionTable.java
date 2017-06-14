package com.example.wuzp.secondworld.view.wangdou.db.table;

/**
 * Created by hqd on 2017/4/11.
 */
public class UserActionTable {
    /**
     * 表名
     */
    public static final String TABLE_NAME = "UserAction";

    public static final String ID = "_id";

    public static final String KEY = "key";
    public static final String SEGMENTATION = "segmentation";
    public static final String COUNT = "count";
    public static final String SUM = "sum";
    public static final String TIMESTAMP = "timestamp";
    public static final String EXTRA = "extra";
    public static final String EVENTTYPE = "eventType";
    public static final String TYPE = "type";

    /**
     * 书籍表列名称数组<br>
     * 注意：顺序不能变
     */
    public static final String[] COLUMNS = {ID, KEY,SEGMENTATION,COUNT,SUM,TIMESTAMP,EXTRA,EVENTTYPE,TYPE};

    /**
     * 获取建表SQL语句
     */
    public static String getCreateSQL() {
        StringBuilder builder = new StringBuilder();
        builder.append("CREATE TABLE IF NOT EXISTS ").append(TABLE_NAME);
        builder.append(" (");

        builder.append(ID).append(" integer primary key autoincrement, ");
        builder.append(KEY).append(" varchar(20), ");
        builder.append(SEGMENTATION).append(" varchar(1024), ");
        builder.append(COUNT).append(" varchar(20), ");
        builder.append(SUM).append(" varchar(20), ");
        builder.append(TIMESTAMP).append(" text, ");
        builder.append(EXTRA).append(" varchar(256), ");
        builder.append(EVENTTYPE).append(" varchar(256), ");
        builder.append(TYPE).append(" varchar(256) ");

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
