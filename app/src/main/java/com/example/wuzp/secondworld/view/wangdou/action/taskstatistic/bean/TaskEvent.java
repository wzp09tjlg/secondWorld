package com.example.wuzp.secondworld.view.wangdou.action.taskstatistic.bean;

/**
 * Created by zhouyibo on 2017/4/5.
 */

public abstract class TaskEvent<T, V extends TaskEvent> {

    private int taskType = injectType();
    private String uid;
    private T extra;

    public int getTaskType() {
        return taskType;
    }

    public void setTaskType(int taskType) {
        this.taskType = taskType;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public T getExtra() {
        return extra;
    }

    public void setExtra(T extra) {
        this.extra = extra;
    }

    abstract int injectType();

    public abstract void updateExtra(V e);

    @Override
    public boolean equals(Object o) {
        return o instanceof TaskEvent
                && ((TaskEvent) o).getTaskType() == (taskType)
                && ((TaskEvent) o).getUid().equals(uid);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return "任务类型：" + taskType + "用户：" + uid + "额外信息：" + extra.toString();
    }

    public abstract boolean isAccomplish(int value);
}
