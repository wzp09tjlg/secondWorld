package com.example.wuzp.secondworld.stats;

import android.content.Context;
import android.os.Handler;
import android.util.Log;


/**
 * 用户行为统计管理
 */
public class UserActionManager {
    private static UserActionManager sInstance;

    /**提交行为数，当对列中记录的行为类型(event数量)达到20条则会提交一次数据，根据需要修改.*/
    private static final int COMMIT_SIZE = 100;

    private ConnectionQueue mConnectionQueue;
    private EventQueue mEventQueue;

    private int mUnsentSessionLength;
    private double mLastTime;
    private int mActivityCount;
    private Handler mHandler;

    /**获取实例*/
    static public UserActionManager getInstance() {
        if (sInstance == null) {
            synchronized (UserActionManager.class) {
                if (sInstance == null) {
                    sInstance = new UserActionManager();
                }
            }
        }
        return sInstance;
    }

    private UserActionManager() {
        mConnectionQueue = new ConnectionQueue();
        mEventQueue = new EventQueue();

        mUnsentSessionLength = 0;
        mActivityCount = 0;
    }

    /**开始某个页面统计,在Activity的OnStart中使用*/
    public void onStart() {
        mActivityCount++;
        if (mActivityCount == 1) {
            onStartHelper();
        }
    }

    /**结束某个页面统计,在Activity的onStop中使用*/
    public void onStop() {
        mActivityCount--;
        if (mActivityCount == 0) {
            Event event = new Event("exitApp");//追加一个退出应用事件
            recordEvent(event);
            onStopHelper();
        }
    }

    /**开始整个应用统计*/
    public void onStartHelper() {
        // 记录会话启动时间
        mLastTime = System.currentTimeMillis() / 1000.0;

        // 判断UUID是否可以获取到，获取不到则延迟几秒钟处理
        if (mHandler == null) {
            mHandler = new Handler();
        }
        mHandler.postDelayed(new Runnable() {

            @Override
            public void run() {
                mConnectionQueue.beginSession();
            }
        }, 1000);
    }

    /**结束整个应用统计*/
    public void onStopHelper() {
        if (mEventQueue.size() > 0) {
            mConnectionQueue.recordEvents(mEventQueue.events());
        }

        double currTime = System.currentTimeMillis() / 1000.0;
        mUnsentSessionLength += (int) (currTime - mLastTime);
        Log.i("TimeCount", "onStopHelper >>>>  >>>>计算会话时间 mLastTime=" + mLastTime + ", mUnsentSessionLength="
                + mUnsentSessionLength);

        int duration = mUnsentSessionLength;
        mConnectionQueue.endSession(duration);
        mUnsentSessionLength -= duration;
        Log.i("TimeCount", "onStopHelper >>>>  >>>>回滚会话时间 mLastTime=" + mLastTime + ", mUnsentSessionLength="
                + mUnsentSessionLength);
    }

    /**记录行为*/
    public void recordEvent(Event event) {
        mEventQueue.recordEvent(event);
        if (mEventQueue.size() >= COMMIT_SIZE) {
            mConnectionQueue.recordEvents(mEventQueue.events());
        }
    }

    public void init(Context context, String url) {
        mConnectionQueue.setContext(context);
        mConnectionQueue.setServerURL(url);
    }
}
