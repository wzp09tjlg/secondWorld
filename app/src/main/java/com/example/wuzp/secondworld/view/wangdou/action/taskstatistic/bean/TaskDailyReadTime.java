package com.example.wuzp.secondworld.view.wangdou.action.taskstatistic.bean;

/**
 * Created by zhouyibo on 2017/4/5.
 */

public class TaskDailyReadTime extends TaskEvent<Long, TaskDailyReadTime> {
    public TaskDailyReadTime(String uid, long time) {
        setUid(uid);
        setExtra(time);
    }

    @Override
    int injectType() {
        return TaskType.task_daily_time;
    }

    @Override
    public void updateExtra(TaskDailyReadTime e) {
        setExtra(getExtra() + e.getExtra());
    }

    @Override
    public boolean isAccomplish(int value) {
        return getExtra() > value;
    }


}
