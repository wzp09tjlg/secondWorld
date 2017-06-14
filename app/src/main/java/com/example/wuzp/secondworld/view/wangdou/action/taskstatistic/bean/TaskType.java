package com.example.wuzp.secondworld.view.wangdou.action.taskstatistic.bean;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by zhouyibo on 2017/4/7.
 */

public class TaskType {
    public static final int task_daily_time = 101;//阅读时长
    public static final int task_daily_opbookstore = 401;
    public static final int task_daily_opapp = 501;
    public static final int task_grow_guide = 601;
    public static final int task_grow_pay = 301;
    public static final int task_grow_collect = 201;
    @IntDef({task_daily_opbookstore,task_daily_time,task_daily_opapp
            ,task_grow_guide,task_grow_pay,task_grow_collect})
    @Retention(RetentionPolicy.SOURCE)
    public @interface tasktype {}
}
