package com.example.wuzp.secondworld.stats;

import android.content.Context;
import android.util.Log;

import com.example.wuzp.secondworld.utils.CommonHelper;
import com.example.wuzp.secondworld.utils.UUID;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 连接队列管理器，主要用于发送数据
 */
public class ConnectionQueue {
    private static final String TAG = "ConnectionQueue";

    private ConcurrentLinkedQueue<List<ValuePairBean>> mLinkedQueue;
    private Thread mThread = null;
    private Context mContext;
    private String mServerURL;

    private boolean isBegin = true, isEnd = true;

    public ConnectionQueue() {
        mLinkedQueue = new ConcurrentLinkedQueue<List<ValuePairBean>>();
    }

    public void setContext(Context context) {
        mContext = context;
    }

    public void setServerURL(String serverURL) {
        mServerURL = serverURL;
    }

    private String uniqueSesstionId = null;

    public void beginSession() {
        if (uniqueSesstionId != null) {
            uniqueSesstionId = null;
        }
        uniqueSesstionId = CommonHelper.getUniqueString();

        isBegin = true;
        commit();
    }

    public void endSession(int duration) {
        isEnd = true;
        commit();
    }

    public void recordEvents(String events) {
        // 防止被null冲掉
        List<ValuePairBean> data = new ArrayList<ValuePairBean>();
        data.add(new ValuePairBean("device_id", UUID.getInstance(mContext).getUUID()));
        data.add(new ValuePairBean("events", events));
        Log.i("EventQueue", "recordEvents: events:" + events);
        mLinkedQueue.offer(data);

        commit();
    }

    private void commit() {
        Log.d("InstalledDeviceCountLog", "ConnectionQueue >> commit >> ");
        if (mThread != null && mThread.isAlive()) {
            Log.d("InstalledDeviceCountLog", "ConnectionQueue >> commit >> return 1");
            return;
        }

        if (mLinkedQueue.isEmpty()) {
            Log.d("InstalledDeviceCountLog", "ConnectionQueue >> commit >> return 2");
            return;
        }

        mThread = new Thread() {
            @Override
            public void run() {
                postEvents();
            }
        };
        mThread.start();
    }

    /**发送统计事件到网络*/
    private void postEvents() {
        Log.d("InstalledDeviceCountLog", "ConnectionQueue >> postEvents >> ");
        if (mLinkedQueue.isEmpty()) {
            Log.d("InstalledDeviceCountLog", "ConnectionQueue >> postEvents >> return 3");
            return;
        }

        while (true) {
            List<ValuePairBean> data = mLinkedQueue.peek();
            if (null == data) {
                Log.d("InstalledDeviceCountLog", "ConnectionQueue >> postEvents >> return 4");
                break;
            }

            // 打印键值
            Map<String, String> params = new HashMap<>();
            for (ValuePairBean valuePair : data) {
                params.put(valuePair.getName(), valuePair.getValue());
                Log.w("InstalledDeviceCountLog",
                        "post  >>>> " + valuePair.getName() + " -> " + valuePair.getValue());
                Log.w("TimeCount", "post  >>>> " + valuePair.getName() + " -> " + valuePair.getValue());
            }
            try {

                //这里是针对自己应用后台的数据统计
                // HttpPost方式
                /*NetworkHelper<EmptyParser> helper = new NetworkHelper<>(mContext, EmptyParser.class, new UIDataListener<EmptyParser>() {
                    @Override
                    public void onDataChanged(EmptyParser data, NetworkHelper<EmptyParser> helper) {
                        Log.i(TAG, "onDataChanged: " + data.toString());
                    }

                    @Override
                    public void onErrorHappened(int errorType, int errorCode, String errorMessage, NetworkHelper<EmptyParser> helper) {
                        Log.i(TAG, "onErrorHappened: errorCode=" + errorCode + ",errorMessage=" + errorMessage);
                    }
                });
                helper.sendPostRequest(mServerURL, params);*/
                mLinkedQueue.poll();
            } catch (Exception e) {
                Log.d(TAG, "Error message -> " + e.getMessage());
                break;
            } finally {
            }
        }
    }
}
