package com.example.wuzp.secondworld.view.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wuzp.secondworld.R;
import com.example.wuzp.secondworld.utils.ActivityUtil;
import com.example.wuzp.secondworld.view.dialog.AddAlbumActivity;
import com.example.wuzp.secondworld.view.glide.GlideActivity;
import com.example.wuzp.secondworld.view.retrofit.rxjava.RxJavaTest;
import com.example.wuzp.secondworld.view.retrofit.rxjava2.RxJavaUtil;

public class MainActivity extends AppCompatActivity {
   private TextView textWorld;
    private TextView textHello;
    private ListView list;

    private String[] data = {"adas","asdasas","sadasda","adsa","dasdasdwq","dasdas"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        doTestMethod();
    }

    private void initView(){
       textHello = (TextView)findViewById(R.id.hello);
        textWorld = (TextView)findViewById(R.id.world);
        textWorld.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddAlbumActivity.class);
                startActivity(intent);
            }
        });
        list = (ListView)findViewById(R.id.list);
        initData();
    }

    private void doTestMethod(){
        String name= "wuzp";
        String address = "beijing";

        Toast.makeText(this,"name:" + name + "  address:" + address,Toast.LENGTH_LONG).show();
    }

    private void initData(){
        textHello.setText("hello");
        textWorld.setText("world");

        RxJavaUtil javaUtil = new RxJavaUtil();
        //javaUtil.getSomeRelation();
//        javaUtil.doSomeMethod2();
//        javaUtil.doSomeTest5();//.doSomeTest3();//doSomeMethod2();

        RxJavaTest test = new RxJavaTest();
        test.testOne();
//        test.testTwo();
//        test.testForMap();
//        test.testForMap2();

//        Intent intent = new Intent(MainActivity.this, AddAlbumActivity.class);
        //startActivity(intent);
        //HuashengActivity.launch(this,"wuzp");
        //ActivityUtil.jumpActivity(this, PerimissionActivity.class);
        //ActivityUtil.jumpActivity(this, RecyclerActivity.class);
        //ActivityUtil.jumpActivity(this, DataActivity.class);
        //ActivityUtil.jumpActivity(this, DataListActivity.class);
        //ActivityUtil.jumpActivity(this, DiffActivity.class);
        ActivityUtil.jumpActivity(this, GlideActivity.class);
    }

}
