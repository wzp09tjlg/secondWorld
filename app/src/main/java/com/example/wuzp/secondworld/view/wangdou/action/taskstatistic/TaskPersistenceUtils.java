package com.example.wuzp.secondworld.view.wangdou.action.taskstatistic;

import android.database.Cursor;

import com.example.wuzp.secondworld.view.wangdou.action.taskstatistic.bean.TaskEvent;
import com.example.wuzp.secondworld.view.wangdou.db.DBService;
import com.example.wuzp.secondworld.view.wangdou.db.table.TaskCacheTable;
import com.example.wuzp.secondworld.view.wangdou.utils.StringConvertUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by zhouyibo on 2017/4/6.
 * 任务数据持久化帮助类
 * 保存
 * 获取
 * 持久化数据做数据库保存
 */

public class TaskPersistenceUtils {
    public static void save(List<TaskEvent> tasks) {
        //保存持久化数据
        DBService.saveTasks(tasks);
    }

    public static List<TaskEvent> obtain(String uid) {
        //获取持久化数据
        return DBService.queryTask(uid);
    }

    public static void detele(String uid) {
        //根据uid进行删除持久化数据
        DBService.deleteTasks(TaskCacheTable.UID, "");
    }

    public static List<TaskEvent> cursor2Tasks(Cursor cursor) {
        List<TaskEvent> tasks = new ArrayList<>();
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            tasks.add(TaskFactory.createTaskEvent(
                    Integer.valueOf(StringConvertUtil.getStringFromCursor(cursor, TaskCacheTable.TASK)),
                    StringConvertUtil.getStringFromCursor(cursor, TaskCacheTable.UID),
                    StringConvertUtil.getStringFromCursor(cursor, TaskCacheTable.EXTRA)));
        }
        return tasks;
    }
}
