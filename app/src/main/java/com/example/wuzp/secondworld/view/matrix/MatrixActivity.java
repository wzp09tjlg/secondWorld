package com.example.wuzp.secondworld.view.matrix;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.wuzp.secondworld.R;
import com.example.wuzp.secondworld.view.widget.MatrixImageView;

/**
 * Created by wuzp on 2017/5/26.
 */
public class MatrixActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnMatrix;
    private EditText edt1,edt2,edt3,edt4,edt5,
            edt6,edt7,edt8,edt9,edt10,
            edt11,edt12,edt13,edt14,edt15,
            edt16,edt17,edt18,edt19,edt20;
    private EditText[] edt = {
        edt1,edt2,edt3,edt4,edt5,
        edt6,edt7,edt8,edt9,edt10,
        edt11,edt12,edt13,edt14,edt15,
        edt16,edt17,edt18,edt19,edt20
    };
    private float[] carray = new float[20];
    private int[] ids = {
            R.id.edt1,R.id.edt2,R.id.edt3,R.id.edt4,R.id.edt5,
            R.id.edt6,R.id.edt7,R.id.edt8,R.id.edt9,R.id.edt10,
            R.id.edt11,R.id.edt12,R.id.edt13,R.id.edt14,R.id.edt15,
            R.id.edt16,R.id.edt17,R.id.edt18,R.id.edt19,R.id.edt20
    };
    private MatrixImageView miv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_colormatrix);
        initView();
    }

    private void initView(){
        btnMatrix = (Button)findViewById(R.id.btn_matrix);
        btnMatrix.setOnClickListener(this);
         for(int i=0;i<ids.length;i++){
             edt[i] = (EditText)findViewById(ids[i]);
         }
        miv = (MatrixImageView) findViewById(R.id.img_matrix);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_matrix:
                for(int i=0;i<20;i++){
                    String temp = edt[i].getText().toString();
                    carray[i] =  temp == "" ? 0.0f : Float.valueOf(temp);
                }
                miv.setValues(carray);
                break;
        }
    }
}
