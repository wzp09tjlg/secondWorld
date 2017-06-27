package com.example.wuzp.secondworld.view.Db;

import com.example.wuzp.secondworld.HApplication;
import com.example.wuzp.secondworld.view.Db.bean.Bookbean;

/**
 * Created by wuzp on 2017/6/27.
 * DBHelperO的实际操作类
 */
public class DbHelperOImp {

    private static DbHelperOImp instance;
    private DbHelperO helperO;

    public static DbHelperOImp getInstance(){
       if(instance == null){
           instance = new DbHelperOImp();
       }
       return instance;
    }

    private DbHelperOImp(){
        helperO = new DbHelperO(HApplication.gContext);
    }

    //select
    public Bookbean doSelectBook(){
        return null;
    }

    //insert
    public int doInsert(){
        return 1;
    }

    //delete
    public int doDelete(){
        return 1;
    }

    //update
    public int doUpdate(){
        return 1;
    }
}
