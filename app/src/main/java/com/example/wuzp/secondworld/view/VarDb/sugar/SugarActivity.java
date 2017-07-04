package com.example.wuzp.secondworld.view.VarDb.sugar;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.wuzp.secondworld.R;

import java.util.List;

/**
 * Created by wuzp on 2017/7/4.
 */
public class SugarActivity extends AppCompatActivity implements View.OnClickListener  {

    private Button btnSelect;
    private Button btnInsert;
    private Button btnDelete;
    private Button btnUpdate;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sugar);

        initView();
    }

    private void initView() {
        btnSelect = (Button) findViewById(R.id.btn_select);
        btnInsert = (Button) findViewById(R.id.btn_insert);
        btnDelete = (Button) findViewById(R.id.btn_delete);
        btnUpdate = (Button) findViewById(R.id.btn_update);

        initData();
    }

    private void initData() {
        btnSelect.setOnClickListener(this);
        btnInsert.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnUpdate.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_select:
                doSelect();
                break;
            case R.id.btn_insert:
                doInsert();
                break;
            case R.id.btn_delete:
                doDelete();
                break;
            case R.id.btn_update:
                doUpdate();
                break;
        }
    }

    //查看Sugar的数据库 就是直接针对数据bean的直接操作 不需要解除任何的 DBHelper
    private void doSelect(){
        try{
            //查询一条数据
            Father father=Father.findById(Father.class, 1);
            Log.i("wzp","father:"+father.toString());

            //find
            List<Father> fatherlist=Father.find(Father.class, "age=?", "100");
            Log.i("wzp","fatherlist:"+fatherlist.get(0).toString());
            //findWithQuery
            List<Father> fathers=Father.findWithQuery(Father.class,"select name,age,hobby from Father where name=?","java");
            Log.i("wzp","fathers:"+fathers.get(0).toString());
        }catch (Exception e){
            Log.e("wzp","select error:" + e.getMessage());
        }
    }
    private void doInsert(){

        //增加一条数据
        try{
            Father father=new Father("c",300,"is you");//不知道为什么执行这个操作就是报错 应该去检查一下这个错误
            Father father1 = new Father();
            father1.setName("java");
            father1.setAge(100);
            father1.setHobby("number math love film fun joke humours eat tv games");
            father1.save();
            long rec= father.save();//非常简单的处理 直接save 就能完成数的保存 但是不知道这样的效率是怎样的，如果是大量的数据执行这样的操作 会不会导致数据卡顿？？待检测
            Log.i("wzp", "rec" + String.valueOf(rec));
        }catch (Exception e){
            Log.e("wzp","insert error:" + e.getMessage());
        }
    }

    private void doDelete(){
        try{
            //删除一条数据
            Father father=Father.findById(Father.class,1);
            father.delete(); //删除的操作也是这样的简单  直接执行delete的操作 ，方便简洁，但是就是需要知道执行的效率稳定性

            Father fatherT=Father.findById(Father.class, 1);
            Log.i("wzp", "fatherT:" + fatherT.toString());
        }catch (Exception e){
            Log.e("wzp","doDelete error:" + e.getMessage());
        }
    }

    private void doUpdate(){
        try{
            //更新一条数据
            Father father1=Father.findById(Father.class,1);
            father1.setName("android");
            father1.save();//sugar的更改也是非常的简单 ，需要看效率 和 稳定性

            Father queryFather=Father.findById(Father.class, 1);
            Log.i("wzp", "queryFather:" + queryFather.toString());
        }catch (Exception e){
            Log.e("wzp","doUpdate error:" + e.getMessage());
        }
    }
}
