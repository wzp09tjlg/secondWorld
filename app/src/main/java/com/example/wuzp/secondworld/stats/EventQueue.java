package com.example.wuzp.secondworld.stats;

import android.util.Log;

import com.google.gson.Gson;

import java.util.ArrayList;


/**
 * 行为事件队列
 */
public class EventQueue {
    private final String TAG = getClass().getSimpleName();
    private ArrayList<Event> mEvents;

    public EventQueue() {
        mEvents = new ArrayList<Event>();
    }

    /**列表大小*/
    public int size() {
        synchronized (this) {
            return mEvents.size();
        }
    }

    /**构造Json字符串*/
    public String events() {
        String result = "";
        synchronized (this) {
            if (mEvents != null && mEvents.size() > 0) {
                Gson gson = new Gson();
                result = gson.toJson(mEvents);
            }
        }

        mEvents.clear();

        Log.d(getClass().getSimpleName(), "Events -> " + result);
        return result;
    }

    /**插入一个event*/
    public void recordEvent(Event event) {
        synchronized (this) {
            mEvents.add(event);
            Log.i(TAG, "recordEvent: after add size is:" + mEvents.size());
        }
    }
}
