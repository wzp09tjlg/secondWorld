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
 * 使用greendao 的确是非常简单，针对所有的数据库操作 都可以针对dao来操作  和 ORMLite数据一样 都是针对dao操作
 * 使用步骤：
 * 1.工程下 引入
 *   //GreenDao3 使用greendao 在工程下的配置
      classpath 'org.greenrobot:greendao-gradle-plugin:3.2.2'
   2.项目下引入
    apply plugin: 'org.greenrobot.greendao'
    ...
 //greendao配置
 greendao {
 //版本号，升级时可配置
 schemaVersion 2 //没添加一个字段都应该在这里增加一个1 因为那是表结构变了，除非是删除应用再重新安装
 targetGenDir 'src/main/java'    //生成DaoMaster类文件夹
 daoPackage   'com.example.wuzp.secondworld.view.VarDb.greenDao.auto'  //生成DaoMaster类包名
 }
 ...
 compile greenDao
  3.创建一个Bean 使用注解 @Entity 只需要写入相应的字段属性就行
   自动生成表和get set方法
  4.在Application中 使用
 DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "GreenDao.db", null);
 SQLiteDatabase db = helper.getWritableDatabase();
 DaoMaster daoMaster = new DaoMaster(db);
 daoSession = daoMaster.newSession();
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

    //查
    private void doSelect(){
        try {
            List<ReaderBean> data = GreenDaoHelper.queryOpt();
            if(data != null &&data.size() > 0) //对获取出来的数据进行检查 并且注意不要越界
              Log.w("wzp","bean:" + data.get(0).toString());
        }catch (Exception E){
            Log.e("wzp","e:" + E.getMessage());
        }
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
        try {
            GreenDaoHelper.deleteOpt(bean);
        }catch (Exception e){
            Log.e("wzp","e:" + e.getMessage());
        }
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
        bean.setId(101l);
        bean.setName("hello  woooooooorld");
        bean.setAddress("beijing  welcome youuuuuuuuuu");
        bean.setAge(1000000);
        bean.setType(ReaderBean.TYPE_STUDENT);
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

