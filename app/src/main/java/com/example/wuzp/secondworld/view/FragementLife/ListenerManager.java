package com.example.wuzp.secondworld.view.FragementLife;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wuzp on 2017/6/30.
 */

public class ListenerManager {

    private static List<StatusListener> list = new ArrayList<>();

   public static void addListener(StatusListener listener){
       if(list == null)
           list = new ArrayList<>();

        list.add(listener);
   }

   public static void doChange(){
       if(list == null) return;

       for(StatusListener listener:list)
           listener.doChange();
   }

}
