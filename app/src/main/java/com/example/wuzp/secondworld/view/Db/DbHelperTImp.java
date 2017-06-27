package com.example.wuzp.secondworld.view.Db;

import com.example.wuzp.secondworld.HApplication;
import com.example.wuzp.secondworld.view.Db.bean.Bookbean;

/**
 * Created by wuzp on 2017/6/27.
 * DBHelperO的实际操作类
 */
public class DbHelperTImp {

    private static DbHelperTImp instance;
    private DbHelperT helperT;

    public static DbHelperTImp getInstance(){
       if(instance == null){
           instance = new DbHelperTImp();
       }
       return instance;
    }

    private DbHelperTImp(){
        helperT = new DbHelperT(HApplication.gContext);
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
