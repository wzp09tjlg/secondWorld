package com.example.wuzp.secondworld.view.wangdou.action.taskstatistic;

import com.example.wuzp.secondworld.view.wangdou.action.taskstatistic.bean.TaskDailyOpenApp;
import com.example.wuzp.secondworld.view.wangdou.action.taskstatistic.bean.TaskEvent;
import com.example.wuzp.secondworld.view.wangdou.action.taskstatistic.bean.TaskType;
import com.example.wuzp.secondworld.view.wangdou.model.parse.task.TaskList;

/**
 * Created by zhouyibo on 2017/4/5.
 * task帮助类
 */

public class TaskHelp {
    public static final String SRP_TASK_DAY = "srp_task_day";
    private static TaskEventHeap sTaskEventHeap = new TaskEventHeap();

    public static void init(){
        /*if (TimeUtils.isToDay(SRPreferences.getInstance().getLong(SRP_TASK_DAY,0L))){
            SRPreferences.getInstance().setLong(SRP_TASK_DAY,System.currentTimeMillis());
            DBService.clearDailyTask();
        }*/
        addTaskEvent(new TaskDailyOpenApp(""));
        login();
    }
    public static void addTaskEvent(TaskEvent event) {
        sTaskEventHeap.add(event);
    }

    public static void login() {
        TaskTrackList.initTaskTrackList();
        sTaskEventHeap.login();
    }

    public static void logout() {
        TaskTrackList.initTaskTrackList();
        sTaskEventHeap.logout();
    }
    public static TaskEventHeap getEventHeap() {
        return sTaskEventHeap;
    }
    public static Long getReadTime(){
        if (sTaskEventHeap.getValue(TaskType.task_daily_time) == null){
            return 0L;
        }
        return (long)(sTaskEventHeap.getValue(TaskType.task_daily_time));
    }

    public static void setProgress(TaskList.DataBean bean){
        switch (bean.getTask_type()){
            case TaskType.task_daily_time:
                bean.setComplete_progress((int)(getReadTime()/60000));
                break;
            case TaskType.task_daily_opapp:
                bean.setComplete_progress(1);
                break;
            default:
                break;
        }
    }
}
