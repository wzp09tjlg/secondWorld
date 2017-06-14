package com.example.wuzp.secondworld.view.wangdou.action.taskstatistic;


import com.example.wuzp.secondworld.view.wangdou.action.taskstatistic.bean.TaskDailyOpenApp;
import com.example.wuzp.secondworld.view.wangdou.action.taskstatistic.bean.TaskDailyOpenBookstore;
import com.example.wuzp.secondworld.view.wangdou.action.taskstatistic.bean.TaskDailyReadTime;
import com.example.wuzp.secondworld.view.wangdou.action.taskstatistic.bean.TaskEvent;
import com.example.wuzp.secondworld.view.wangdou.action.taskstatistic.bean.TaskGrowCollect;
import com.example.wuzp.secondworld.view.wangdou.action.taskstatistic.bean.TaskType;

/**
 * Created by zhouyibo on 2017/4/6.
 */

public class TaskFactory {
    public static TaskEvent createTaskEvent(int type, String uid, String extra) {
        switch (type){
            case TaskType.task_daily_opbookstore:
                return new TaskDailyOpenBookstore(Integer.valueOf(extra));
            case TaskType.task_daily_opapp:
                return new TaskDailyOpenApp(uid);
            case TaskType.task_daily_time:
                return new TaskDailyReadTime(uid,Long.valueOf(extra));
            case TaskType.task_grow_collect:
                return new TaskGrowCollect(uid);
            case TaskType.task_grow_guide:
                break;
            case TaskType.task_grow_pay:
                break;
            default:
                break;
        }
        return null;
    }
}
