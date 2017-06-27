package com.example.wuzp.secondworld.view.Db;

/**
 * Created by wuzp on 2017/6/27.
 */
public class BookTable {

    /**
     * 表名
     */
    public static final String TABLE_NAME = "Book";

    public static final String ID = "_id";

    public static final String TITLE = "title";
    public static final String NAME = "name";
    public static final String NUM = "num";
    public static final String INTRO = "intro";
    public static final String PRICE = "price";

    public static String getCreateSQL() {
        StringBuilder builder = new StringBuilder();
        builder.append("CREATE TABLE IF NOT EXISTS ").append(TABLE_NAME);
        builder.append(" (");

        builder.append(ID).append(" integer primary key autoincrement, ");
        builder.append(TITLE).append(" varchar(20), ");
        builder.append(NAME).append(" varchar(20), ");
        builder.append(NUM).append(" varchar(20), ");
        builder.append(INTRO).append(" text, ");
        builder.append(PRICE).append(" number ");

        builder.append(")");
        return builder.toString();
    }
}
