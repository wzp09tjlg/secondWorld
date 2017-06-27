package com.example.wuzp.secondworld.view.hanlder;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.wuzp.secondworld.R;

/**
 * Created by wuzp on 2017/6/26.
 * Handler的创建在什么线程中 就会将信息发送到对应的线程中
 */
public class HandlerActivity extends AppCompatActivity implements View.OnClickListener {

    private final int MSG_WHAT1 = 0X101;
    private final int MSG_WHAT2 = 0X102;
    private final int MSG_WHAT3 = 0X103;

    private Button btnMainThread;
    private Button btnSubThread;

    private int count  = 1;

    //这个handler 是在activity中创建的 所属线程是mainThread
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0X101:
                    break;
                case 0X102:
                    break;
                case 0X103:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler);

        initView();
    }

    private void initView(){
        btnMainThread = (Button)findViewById(R.id.btn_main_thread);
        btnSubThread = (Button) findViewById(R.id.btn_sub_thread);

        initData();
    }

    private void initData(){
        btnMainThread.setOnClickListener(this);
        btnSubThread.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_main_thread:
                count = (count + 1) % 2;
                if(count == 0){
                    Message message = Message.obtain();
                    message.what = MSG_WHAT1 ;
                    message.obj = "wzp";
                    mHandler.sendMessage(message);
                    long threadId = 0;
                   try {
                        threadId = Thread.currentThread().getId();
                    }catch (Exception e){}
                    Log.e("wzp","main btn  info1:" + threadId);
                    //在主线程中 发送的消息到主线程中进行消耗
                }else if(count == 1){
                   //在子线程中 同消息发送消息 在主线程中进行消费
                    //在子线程中 创建一个子线程 就会增加一个cpu的消耗
                    //并且会对activity持有引用
                    new Thread(
                            new Runnable() {
                                @Override
                                public void run() {
                                    Message msg = Message.obtain();
                                    msg.what = MSG_WHAT2;
                                    msg.obj = 10000;
                                    mHandler.sendMessage(msg);
                                    long threadId = Thread.currentThread().getId();
                                    Log.e("wzp","main btn info2:" + threadId);
                                }
                            }
                    ).start();
                }
                break;
            case R.id.btn_sub_thread:
                testHandlerInMethod();
                break;
        }
    }

    private void testHandlerInMethod(){
        //这里的handler是在方法体内 所以生命周期也只是在这方法的生命周期中
        //因为这里是在方法中 点击多次就会创建多个handler并且并不会马上的清除
      final  Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case 0X101:
                        break;
                    case 0X102:
                        break;
                    case 0X103:
                        break;
                }
            }
        };

        Message message = Message.obtain();
        message.what = MSG_WHAT1 ;
        message.obj = "wzp";
        handler.sendMessage(message);
        long threadId = 0;
        try {
            threadId = Thread.currentThread().getId();
        }catch (Exception e){}
        Log.e("wzp","sub btn  msg1:" + threadId);

        new Thread(new Runnable() {
            @Override
            public void run() {
                Message msg = Message.obtain();
                msg.what = MSG_WHAT2;
                msg.obj = 10000;
                handler.sendMessage(msg);
                long tempthreadId = Thread.currentThread().getId();
                Log.e("wzp","sub btn msg2:" + tempthreadId);
            }
        }).start();

    }
}
