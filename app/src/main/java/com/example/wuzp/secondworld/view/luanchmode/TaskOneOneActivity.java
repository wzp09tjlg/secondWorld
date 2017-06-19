package com.example.wuzp.secondworld.view.luanchmode;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.wuzp.secondworld.R;

/**
 * Created by wuzp on 2017/6/19.
 */
public class TaskOneOneActivity extends AppCompatActivity {
    private static final String TAG = TaskOneOneActivity.class.getSimpleName();
    private Button btnTwo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_task_one_one);
        initView();
        Log.e("wuzp","Tag:" + TAG + "  onCreate");
    }

    private void initView(){
        btnTwo = (Button)findViewById(R.id.btn_task_two);
        btnTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TaskOneOneActivity.this,TaskTwoActvity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("wuzp","Tag:" + TAG + "  onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("wuzp","Tag:" + TAG + "  onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("wuzp","Tag:" + TAG + "  onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("wuzp","Tag:" + TAG + "  onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("wuzp","Tag:" + TAG + "  onDestroy");
    }
}
