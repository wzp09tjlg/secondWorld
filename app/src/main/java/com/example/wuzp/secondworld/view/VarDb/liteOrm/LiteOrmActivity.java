package com.example.wuzp.secondworld.view.VarDb.liteOrm;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.wuzp.secondworld.R;

import java.util.List;

import static com.example.wuzp.secondworld.view.VarDb.liteOrm.LiteOrmHelper.getSingleInstance;

/**
 * Created by wuzp on 2017/7/4.
 * 三方数据库liteOrm
 */
public class LiteOrmActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnSelect;
    private Button btnInsert;
    private Button btnDelete;
    private Button btnUpdate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liteorm);

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


    //
    private void doSelect(){
     try{
       List<Mother> mothers = LiteOrmHelper.getSingleInstance(this).query(Mother.class);
         //query(class);
         //query(id,class)
         //query(StringId,Class);
         //query()
         Log.e("wzp","mothers size:" + mothers.size());
         if(mothers != null && mothers.size() > 0){
             Log.e("wzp","mother :" + mothers.get(0).toString());
         }
     }catch (Exception e){
         Log.e("wzp","query error:" + e.getMessage());
     }
    }

    // ormDb.save(); 保存数据 这个跟sugar差不错，只不过需要选择获取的数据库是什么类型的数据库
    private void doInsert(){
        Mother mother = new Mother();
        mother.setName("china");
        mother.setAge(5000);
        mother.setAddress("asia");
        mother.setHobby("red color,yellow shin ,dragon ,beijing");

        Mother mother2 = new Mother();
        mother.setName("beiing");
        mother.setAge(10000);
        mother.setAddress("china");
        mother.setHobby("capital  much pepole,tiananmen squre,water cubic,net nest,great wall");

        Mother mother3 = new Mother();
        mother.setName("tianjin");
        mother.setAge(9000);
        mother.setAddress("china");
        mother.setHobby("steam barge,tianjinwei,talk show");
        try{

            long result = getSingleInstance(this).save(mother);//操作也是比较简单哈
            getSingleInstance(this).save(mother2);
            getSingleInstance(this).save(mother3);
            Log.e("wzp","result :" + result);
        }catch (Exception e){
            Log.e("wzp","insert error:" + e.getMessage());
        }
    }
    private void doDelete(){
        try{
            long result = LiteOrmHelper.getSingleInstance(this).delete(Mother.class);//删除全部
            //delete(Class)
            //delete(Collection<Class>)
            //delete(Class<T> ,long,long,String)
            //delete(object)
            //deleteAll()删全部
            //deleteDatabase();//删数据库？
            //deleteDatabase(File);//删文件数据库？
            Log.e("wzp","result:" + result);
        }catch (Exception e){
            Log.e("wzp","delete error:" + e.getMessage());
        }
    }
    private void doUpdate(){
        try{
           List<Mother> mothers = LiteOrmHelper.getSingleInstance(this).query(Mother.class);
            if(mothers != null){
                Log.e("wzp","mother :" + mothers.get(0).toString());
                mothers.get(0).setAge(5001);
                mothers.get(0).setHobby("power , tech,create,image");
            }else{
                Log.e("wzp", "before update query: null");
                return;
            }

            long result = LiteOrmHelper.getSingleInstance(this).update(mothers.get(0));
            Log.e("wzp","update result:" + result);

            Mother motherquery = LiteOrmHelper.getSingleInstance(this).queryById(1l, Mother.class);
            if(motherquery != null){
                Log.e("wzp", "after update query: " + motherquery);
            }else{
                Log.e("wzp", "after update query: null");
            }
        }catch (Exception e){
            Log.e("wzp","update is fail");
        }
    }
}
