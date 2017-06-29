package com.example.wuzp.secondworld.view.Sview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.example.wuzp.secondworld.R;
import com.example.wuzp.secondworld.view.widget.SView.SView;

/**
 * Created by wuzp on 2017/6/27.
 */

public class SViewAvitivity extends AppCompatActivity {

    private Button btnTrans;
    private Button btnAplph;
    private Button btnScale;
    private Button btnRoate;
    private SView sView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cavans);
    }
}
