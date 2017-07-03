package com.example.wuzp.secondworld.view.VarDb.ormlite;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.wuzp.secondworld.R;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by wuzp on 2017/6/29.
 */
public class OrmLiteActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnSelect;
    private Button btnInsert;
    private Button btnDelete;
    private Button btnUpdate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ormlite);

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

    private String[] names = {"num one", "num two", "num three", "num four", "num five", "num six", "num seven", "num eight", "num nine", "num ten"};
    private Random random = new Random();

    private void doInsert() {//添加1001个数据
        random.setSeed(1000);
        User user = new User("num five", 2);
        OrmLiteHelper helper = OrmLiteHelper.getInstance(this);
        try {
            helper.getUserDao().create(user);
            int temp = 0;
            for (int i = 0; i < 1000; i++) {
                temp = (random.nextInt() % 10 +  10) % 10;
                user = new User(names[temp], temp);
                helper.getUserDao().create(user);
            }
        } catch (Exception e) {
            Log.e("wzp","msg:" + e.getMessage());
        }

        //四种方式 来创建数据
        //helper.getUserDao().create(User);
        //helper.getUserDao().create(Collections);
        //helper.getUserDao().createIfNotExists(User);
        //helper.getUserDao().createOrUpdate(User);
    }

    private void doSelect() {
        //怎么查数据库中存在的数据 先查数据中存在的数目么？如果是空怎么办 还是ormlite得数据都会在内存中存在相应的数据？？？待检验
        User userOne = null;
        User userTwo = null;
        User userThree = null;
        User userFour = null;
        User userFive = null;

        User[] users = {userOne, userTwo, userThree, userFour, userFive};
        int[] selectPosition = {2, 10, 400, 212, 819};
        int len = selectPosition.length;
        OrmLiteHelper helper = OrmLiteHelper.getInstance(this);
        try{
            for (int i = 0; i < len; i++) {
                users[i] = helper.getUserDao().queryForId(selectPosition[i]);
            }
        }catch (Exception e){
            Log.e("wzp","msg:" + e.getMessage());
        }
        showUser(users);

        //查询的方式有十二类
        //helper.getUserDao().queryForId(id);
        //helper.getUserDao().query(PreparedQuery<User>)
        //helper.getUserDao().queryBuilder();
        //helper.getUserDao().queryForAll();
        //helper.getUserDao().queryForEq(String,Object);
        //helper.getUserDao().queryForFieldValues(Map<String,value>);
        //helper.getUserDao().queryForFirst(PreparedQuery<User>);
        //helper.getUserDao().queryForMatching(User)
        //helper.getUserDao().queryForMatchingArgs(User);
        //helper.getUserDao().queryForSameId(User);
        //helper.getUserDao().queryRaw();//好多种
        //helper.getUserDao().queryRawValue(String,Strings ...)
    }

    private void doDelete() {
        List<Integer> deletePosition = new ArrayList<>();
        deletePosition.add(2);
        deletePosition.add(10);
        deletePosition.add(400);
        deletePosition.add(212);
        deletePosition.add(819);
        OrmLiteHelper helper = OrmLiteHelper.getInstance(this);
        try {
            helper.getUserDao().deleteIds(deletePosition);
        }catch (Exception e){}
        show();

        //删除的动作 6中方式
        //helper.getUserDao().delete(Collection<User>)
        //helper.getUserDao().delete(PreparedQuery<User>)
        //helper.getUserDao().delete(User);
        //helper.getUserDao().deleteBuilder()
        //helper.getUserDao().deleteById(id)''
        //helper.getUserDao().deleteIds(Collections<Integer>)
    }

    private void doUpdate() {

        User userOne = null;
        User userTwo = null;
        User userThree = null;
        User userFour = null;
        User userFive = null;

        User[] users = {userOne, userTwo, userThree, userFour, userFive};
        int[] selectPosition = {2, 10, 400, 212, 819};
        int len = selectPosition.length;
        OrmLiteHelper helper = OrmLiteHelper.getInstance(this);
        try{
            for (int i = 0; i < len; i++) {
                users[i] = helper.getUserDao().queryForId(selectPosition[i]);
            }
        }catch (Exception e){}

        int[] updatePosition = {100,210,408,901,892};
        try{
            for(int i=0;i<len;i++){
                helper.getUserDao().updateId(users[i],updatePosition[i]);
            }
        }catch (Exception e){}
        show();

        //更新数据的 5 种方式
        //helper.getUserDao().update(PreparedQuery<User>)
        //helper.getUserDao().update(User)
        //helper.getUserDao().updateBuilder()
        //helper.getUserDao().updateId(User,Id)
        //helper.getUserDao().updateRaw(String,Strings ...)
    }

    public void testUsers() {
        OrmLiteHelper helper = OrmLiteHelper.getInstance(this);
        try {
            User u1 = new User("zhy-android", 100);//这里姜然是依据前边的ID来修改的库中数据
            u1.setId(2);
            List<User> users = helper.getUserDao().queryForAll();
            Log.e("wzp", users.toString());
            showUser(users);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void showUser(List<User> list) {
        if (list == null || list.size() <= 0) return;
        int len = list.size();
        User user = null;
        for (int i = 0; i < len; i++) {
            user = list.get(i);
            Log.i("wzp", user.toString());
        }
    }

    private void showUser(User[] users){
        if(users == null || users.length == 0) return;
        int len = users.length;
        User user = null;
        for (int i = 0; i < len; i++) {
            user = users[i];
            if(user != null)
            Log.i("wzp", user.toString());
        }
    }

    private void show(){
        OrmLiteHelper helper = OrmLiteHelper.getInstance(this);
        try{
            List<User> list = helper.getUserDao().queryForAll();
            showUser(list);
        }catch (Exception e){}
    }
}
