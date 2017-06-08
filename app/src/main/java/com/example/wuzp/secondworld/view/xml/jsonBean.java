package com.example.wuzp.secondworld.view.xml;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by wuzp on 2017/6/8.
 */

public class jsonBean{
   public ArrayList<NodeBean> data;

    @Override
    public String toString() {
        if(data != null){
            int size = data.size();
            String temp = "";
            for(int i=0;i<size;i++){
                Log.e("wzp","bean:" + data.get(i).toString());
                temp +=  data.get(i).toString();
            }
            return temp;
        }
        return super.toString();
    }
}
