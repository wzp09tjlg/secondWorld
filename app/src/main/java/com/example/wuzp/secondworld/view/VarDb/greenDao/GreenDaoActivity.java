package com.example.wuzp.secondworld.view.VarDb.greenDao;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.wuzp.secondworld.R;

import java.util.List;

/**
 * Created by wuzp on 2017/7/3.
 */
public class GreenDaoActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnInsert;
    private Button btnSelect;
    private Button btnUpdate;
    private Button btnDelete;

    View view ;
    ViewGroup root;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = LayoutInflater.from(this).inflate(R.layout.activity_greendao,null);
        root = (ViewGroup)view.findViewById(R.id.root);
        setContentView(view);

        initView();
    }

    private void initView(){
        btnSelect = (Button) findViewById(R.id.btn_select);
        btnInsert = (Button) findViewById(R.id.btn_insert);
        btnDelete = (Button) findViewById(R.id.btn_delete);
        btnUpdate = (Button) findViewById(R.id.btn_update);

        initData();
    }

    private void initData(){
        btnSelect.setOnClickListener(this);
        btnInsert.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnUpdate.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_select:
                doSelect();
                break;
            case R.id.btn_insert:
                //doInsert();
                doAddView();
                break;
            case R.id.btn_delete:
                doDelete();
                break;
            case R.id.btn_update:
                doUpdate();
                break;
        }
    }

    //查
    private void doSelect(){
        List<ReaderBean> data = GreenDaoHelper.queryOpt();
        Log.w("wzp","bean:" + data.toString());
    }

    //插
    private void doInsert(){
       long result = GreenDaoHelper.insertOpt(getData());
        Log.e("wzp","result is:" + result);
    }

    //删
    private void doDelete(){
        if(bean == null){
            bean = getData();
        }
        GreenDaoHelper.deleteOpt(bean);
    }

    //改
    private void doUpdate(){
        if(bean == null){
            bean = getData();
        }

        Log.e("wzp","before update bean:" + bean.toString());

        bean.setAge(2222);
        bean.setName("hello  beijing jing jing ");
        bean.setAddress("tianjin");

        Log.e("wzp","after bean:" + bean.toString());

        GreenDaoHelper.updateOpt(bean);

        Log.e("wzp","after update,by select bean:");
        doSelect();
    }


    ReaderBean bean = null;
    private ReaderBean getData(){
        bean = new ReaderBean();
        bean.setId(100l);
        bean.setName("hello  woooooooorld");
        bean.setAddress("beijing  welcome youuuuuuuuuu");
        bean.setAge(1000000);
        return bean;
    }

    private void doAddView(){
        try{
            ImageView imageView = new ImageView(this);//注意在使用资源时 看看上下文是那个
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.width =100;
            params.height = 100;
            imageView.setLayoutParams(params);
            imageView.setImageResource(R.drawable.icon_road);

            LinearLayout.LayoutParams params1 = new
                    LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            root.addView(imageView,params1);
        }catch (Exception e){
            Log.e("wzp","e:" + e.getMessage());
        }
    }
}

