package com.example.wuzp.secondworld.view.single;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.wuzp.secondworld.R;
import com.example.wuzp.secondworld.view.muchItem.MuchItemActivity;

/**
 * Created by wuzp on 2017/6/29.
 */

public class SingleActivity extends AppCompatActivity {
    private final String TAG = "SingleActivity";

    private Button btnBack;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single);

        initView();
        Log.e("wzp","" + TAG + "  onCreate"  );
    }

    private void initView(){
        btnBack = (Button)findViewById(R.id.btn_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SingleActivity.this, MuchItemActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e("wzp","" + TAG + "  onRestart"  );
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("wzp","" + TAG + "  onStart"  );
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("wzp","" + TAG + "  onResume"  );
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("wzp","" + TAG + "  onPause"  );
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("wzp","" + TAG + "  onStop"  );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("wzp","" + TAG + "  onDestroy"  );
    }
}
