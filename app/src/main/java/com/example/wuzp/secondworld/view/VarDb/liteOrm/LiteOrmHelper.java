package com.example.wuzp.secondworld.view.VarDb.liteOrm;

import android.content.Context;

import com.litesuits.orm.LiteOrm;
import com.litesuits.orm.db.DataBase;
import com.litesuits.orm.db.DataBaseConfig;

import java.io.File;

/**
 * Created by wuzp on 2017/7/5.
 * 使用LiteOrm 数据库的三方helper 用来专门控制数据库的管理
 * 这种数据库的操作 相对sugar 及 OrmLite GreenDao 感觉要复杂一些
 *
 * 不过这个数据库可以提供两个数据库  一个级联的数据库 一个是单一的数据库
 * 在需要级联的数据库中 这种数据库是比较好的选择吧，一般的数据库表不多的法 其实使用也是一样
 */
public class LiteOrmHelper {
   private static final String ORM_DB_NAME = "Orm_Db.db";

    private static DataBase ormCascadeDb;
    private static DataBase ormSingleDb;

    public static void init(Context context){
        DataBaseConfig config = new DataBaseConfig(context);
        //数据库名，可设置存储路径。默认在内部存储位置databases文件夹下
        //"liteorm.db"是数据库名称，名称里包含路径符号"/"则将数据库建立到该路径下，可以使用sd卡路径。 不包含则在系统默认路径下创建DB文件。
        config.dbName = context.getDatabasePath("") + File.separator + "liteorm.db";
        config.debugged = true; //是否打Log
        config.dbVersion = 1; // database Version
        config.onUpdateListener = null; //升级

        /***
         * context.getDatabasePath(xxx);
         //对应数据库的全路径名
         /data/data/com.example.qymh/databases/xxx.db
         * */
    }

    public static DataBase initLiteOrm(Context context){
        if(ormCascadeDb == null){
            ormCascadeDb =  LiteOrm.newCascadeInstance(context,ORM_DB_NAME);
        }
        return ormCascadeDb;
    }

    public static DataBase getSingleInstance(Context context){
        if(ormSingleDb == null){
            ormSingleDb = LiteOrm.newSingleInstance(context,ORM_DB_NAME);
        }
        return ormSingleDb;
    }
}
