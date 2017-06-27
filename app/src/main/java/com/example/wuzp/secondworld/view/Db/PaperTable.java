package com.example.wuzp.secondworld.view.Db;

/**
 * Created by wuzp on 2017/6/27.
 */
public class PaperTable {

    /**
     * 表名
     */
    public static final String TABLE_NAME = "Paper";

    public static final String ID = "_id";

    public static final String NAME = "name";
    public static final String CONTENT = "content";
    public static final String SIGN = "sign";
    public static final String DATE = "date";

    public static String getCreateSQL() {
        StringBuilder builder = new StringBuilder();
        builder.append("CREATE TABLE IF NOT EXISTS ").append(TABLE_NAME);
        builder.append(" (");

        builder.append(ID).append(" integer primary key autoincrement, ");
        builder.append(NAME).append(" varchar(20), ");
        builder.append(CONTENT).append(" text, ");
        builder.append(SIGN).append(" varchar(20), ");
        builder.append(DATE).append(" varchar(20)");

        builder.append(")");
        return builder.toString();
    }
}
