package com.example.wuzp.secondworld.view.VarDb.greenDao;

import com.example.wuzp.secondworld.HApplication;
import com.example.wuzp.secondworld.view.VarDb.greenDao.auto.ReaderBeanDao;

import java.util.List;

/**
 * Created by wuzp on 2017/7/3.
 * GreenDao 的数据库和 其他的ORM的数据库不一样
 * 屏蔽了直接继承SqlieOpenHelper的实现 使用注解将是继承过程隐藏
 */
public class GreenDaoHelper {

    public static List<ReaderBean> queryOpt(){
        List<ReaderBean> data = null;
        data = HApplication.daoSession.getReaderBeanDao().queryBuilder().where(ReaderBeanDao.Properties.Type.eq(ReaderBean.TYPE_STUDENT)).list();
                //.queryRaw("where _id > 0","name,age,address");//.queryBuilder().build();
                //.queryRawCreateListArgs(where,Collections<ReaderBean>)//
                //.queryRawCreate(where,Object ... )
                //.queryRaw(where,String ... selectionArg)//
                //.queryBuilder();//直接构建sql语句
        return data;
    }

    public static long insertOpt(ReaderBean bean){
       long result = HApplication.daoSession.getReaderBeanDao().insert(bean);
        //insert方法中页存在很多插入的方法
        return result;
    }

    public static void deleteOpt(ReaderBean bean){
        HApplication.daoSession.getReaderBeanDao().delete(bean);
    }

    public static void updateOpt(ReaderBean bean){
       HApplication.daoSession.getReaderBeanDao().update(bean);
    }
}

