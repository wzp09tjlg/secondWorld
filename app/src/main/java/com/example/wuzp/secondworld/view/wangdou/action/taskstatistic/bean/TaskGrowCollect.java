package com.example.wuzp.secondworld.view.wangdou.action.taskstatistic.bean;

/**
 * Created by zhouyibo on 2017/4/7.
 */

public class TaskGrowCollect extends TaskEvent<Integer,TaskGrowCollect> {
    public TaskGrowCollect(String uid){
        setUid(uid);
        setExtra(1);
    }

    @Override
    int injectType() {
        return TaskType.task_grow_collect;
    }

    @Override
    public void updateExtra(TaskGrowCollect e) {
        setExtra(getExtra()+e.getExtra());
    }

    @Override
    public boolean isAccomplish(int value) {
        return true;
    }

}
