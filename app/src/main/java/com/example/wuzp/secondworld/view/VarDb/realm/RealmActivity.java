package com.example.wuzp.secondworld.view.VarDb.realm;

import android.support.v7.app.AppCompatActivity;

/**
 * Created by wuzp on 2017/7/5.
 * 正如其他小伙伴说的  realm 和 databinding 不能一块使用，会出现问题
 */

public class RealmActivity extends AppCompatActivity{// implements View.OnClickListener {
//
//    private Button btnSelect;
//    private Button btnInsert;
//    private Button btnDelete;
//    private Button btnUpdate;
//
//    Realm mRealm;
//    UserBeanDao userBeanDao;
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_realm);
//
//        initView();
//    }
//
//    private void initView() {
//        btnSelect = (Button) findViewById(R.id.btn_select);
//        btnInsert = (Button) findViewById(R.id.btn_insert);
//        btnDelete = (Button) findViewById(R.id.btn_delete);
//        btnUpdate = (Button) findViewById(R.id.btn_update);
//
//        initData();
//    }
//
//    private void initData() {
//        mRealm = Realm.getDefaultInstance();
//        userBeanDao = new UserBeanDao(mRealm);
//
//        btnSelect.setOnClickListener(this);
//        btnInsert.setOnClickListener(this);
//        btnDelete.setOnClickListener(this);
//        btnUpdate.setOnClickListener(this);
//    }
//
//    @Override
//    public void onClick(View v) {
//        switch (v.getId()){
//            case R.id.btn_select:
//                doSelect();
//                break;
//            case R.id.btn_insert:
//                doInsert();
//                break;
//            case R.id.btn_delete:
//                doDelete();
//                break;
//            case R.id.btn_update:
//                doUpdate();
//                break;
//        }
//    }
//
//    private void doSelect(){
//        findAll();
//    }
//
//    public void findAll() {
//        try {
//            RealmResults<UserBean> results = (RealmResults<UserBean>) userBeanDao.findAll(UserBean.class);
//            int size = results.size();
//            UserBean user;
//            StringBuilder stringBuilder = new StringBuilder();
//            for (int i = 0; i < size; i++) {
//                user = results.get(i);
//                stringBuilder.append(user.getId());
//                stringBuilder.append("-");
//                stringBuilder.append(user.getName());
//                stringBuilder.append("-");
//                stringBuilder.append(user.getAge());
//                stringBuilder.append("-");
//                stringBuilder.append(user.getHobby());
//                stringBuilder.append("-");
//                stringBuilder.append(user.getAddress());
//                stringBuilder.append("\r\n");
//            }
//            if (!TextUtils.isEmpty(stringBuilder)) {
//                Log.e("wzp","query :" + stringBuilder.toString());
//            } else {
//                Log.e("wzp","查询完毕，无数据");
//                Log.e("wzp","查询完毕，无数据");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            Log.e("wzp","error:" + e.getMessage());
//        }
//    }
//
//    private void doInsert(){
//        try{
//            userBeanDao.init();//执行插入操作
//            Log.e("wzp","init insert done");
//        }catch (Exception e){
//            Log.e("wzp","init insert error:" + e.getMessage());
//        }
//
//    }
//
//    private void doDelete(){
//      try{
//          boolean result = userBeanDao.deleteById(UserBean.class,"name",1);
//          Log.e("wzp","result:" + result);
//      }catch (Exception e){
//          Log.e("wzp","delete result is error:" + e.getMessage());
//      }
//    }
//
//    private void doUpdate(){
//       try{
//           RealmResults<UserBean> results = (RealmResults<UserBean>) userBeanDao.findAll(UserBean.class);
//           Log.e("wzp","select size:" + results.size());
//           Log.e("wzp","select position one:" + results.get(0).toString());
//
//           results.get(0).setName("this is a test update name");
//
//           userBeanDao.insertOrUpdate(results.get(0));
//
//
//           RealmResults<UserBean> results1 = (RealmResults<UserBean>) userBeanDao.findAll(UserBean.class);
//           Log.e("wzp","select size:" + results1.size());
//           Log.e("wzp","select position one:" + results1.get(0).toString());
//
//       }catch (Exception e){
//           Log.e("wzp","realm update is error" + e.getMessage());
//       }
//    }
}
