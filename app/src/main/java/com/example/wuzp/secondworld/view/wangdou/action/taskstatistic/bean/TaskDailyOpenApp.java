package com.example.wuzp.secondworld.view.wangdou.action.taskstatistic.bean;

/**
 * Created by zhouyibo on 2017/4/5.
 */

public class TaskDailyOpenApp extends TaskEvent<Integer,TaskDailyOpenApp> {
    public TaskDailyOpenApp(String uid) {
        setUid(uid);
        setExtra(1);
    }

    @Override
    int injectType() {
        return TaskType.task_daily_opapp;
    }

    @Override
    public void updateExtra(TaskDailyOpenApp e) {
        setExtra(getExtra()+e.getExtra());
    }

    @Override
    public boolean isAccomplish(int value) {
        return true;
    }
}
