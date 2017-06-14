package com.example.wuzp.secondworld.view.wangdou.action.taskstatistic;


import com.example.wuzp.secondworld.view.wangdou.action.taskstatistic.bean.TaskDailyOpenApp;
import com.example.wuzp.secondworld.view.wangdou.action.taskstatistic.bean.TaskEvent;
import com.example.wuzp.secondworld.view.wangdou.action.taskstatistic.bean.TaskType;
import com.example.wuzp.secondworld.view.wangdou.utils.UserUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhouyibo on 2017/4/5.
 * 数据需求：方便本地化
 * 方便获取修改删除
 * 能够遍历
 */

public class TaskEventHeap {
    public List<TaskEvent> getTaskEvents() {
        return mTaskEvents;
    }

    public void setTaskEvents(List<TaskEvent> taskEvents) {
        mTaskEvents = taskEvents;
    }

    private List<TaskEvent> mTaskEvents = new ArrayList<>();

    public void login() {
        //1.获取持久化数据
        List<TaskEvent> dbTask = TaskPersistenceUtils.obtain(UserUtils.getUid());
        //5.删除持久化的无用数据
        TaskPersistenceUtils.detele("");
        //2.修改本地数据
        String uid = UserUtils.getUid();
        if("".equals(uid)){
            for (TaskEvent ev : mTaskEvents) {
                ev.setUid(uid);
            }
        }
        //3.合并数据
        for (TaskEvent event : dbTask) {
            add(event);
        }
        //4.提交合并后的数据
        for (TaskEvent event : mTaskEvents) {
            commit(event);
        }
    }

    public void logout() {
        //登出时，不用操作持久化数据，清空内存中数据
        mTaskEvents.clear();
        add(new TaskDailyOpenApp(""));
    }

    public void add(TaskEvent event) {
        if (mTaskEvents.contains(event)) {
            commit(update(event));
        } else {
            mTaskEvents.add(event);
            commit(event);
        }
    }

    private TaskEvent update(TaskEvent event) {
        for (TaskEvent taskevent : mTaskEvents) {
            if (taskevent.equals(event)) {
                taskevent.updateExtra(event);
                return taskevent;
            }
        }
        return null;
    }

    private void commit(TaskEvent event) {
        if (event == null) {
            return;
        }
        List<TaskEvent> single = new ArrayList<>();
        single.add(event);
        TaskPersistenceUtils.save(single);
        TaskTrackList.compareTaskEvent();
    }

    public Object getValue(@TaskType.tasktype int type){
        for (TaskEvent taskevent : mTaskEvents) {
            if (taskevent.getTaskType() == (type)){
                return taskevent.getExtra();
            }
        }
        return null;
    }

    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        for (TaskEvent ev : mTaskEvents) {
            buffer.append(ev.toString() + "......." +"\n");
        }
        return buffer.toString();
    }
}
