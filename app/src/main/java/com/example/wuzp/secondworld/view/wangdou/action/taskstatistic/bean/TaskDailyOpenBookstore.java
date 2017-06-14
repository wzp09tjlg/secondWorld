package com.example.wuzp.secondworld.view.wangdou.action.taskstatistic.bean;


import com.example.wuzp.secondworld.view.wangdou.utils.UserUtils;

/**
 * Created by zhouyibo on 2017/4/5.
 */

public class TaskDailyOpenBookstore extends TaskEvent<Integer, TaskDailyOpenBookstore> {
    public TaskDailyOpenBookstore() {
        setUid(UserUtils.getUid());
        setExtra(1);
    }

    public TaskDailyOpenBookstore(int count) {
        setUid(UserUtils.getUid());
        setExtra(count);
    }
    @Override
    int injectType() {
        return TaskType.task_daily_opbookstore;
    }

    @Override
    public void updateExtra(TaskDailyOpenBookstore e) {
        setExtra(getExtra() + e.getExtra());
    }

    @Override
    public boolean isAccomplish(int value) {
        return getExtra() >= value;
    }
}
