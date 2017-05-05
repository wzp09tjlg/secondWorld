package com.example.wuzp.secondworld.view.main;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wuzp.secondworld.R;
import com.example.wuzp.secondworld.stats.EventFinal;
import com.example.wuzp.secondworld.utils.ActivityUtil;
import com.example.wuzp.secondworld.utils.UUID;
import com.example.wuzp.secondworld.view.cursorloader.CursorloaderActivity;
import com.example.wuzp.secondworld.view.gt.GtActivity;
import com.example.wuzp.secondworld.view.huasheng.recyclerView.RecyclerActivity;
import com.example.wuzp.secondworld.view.loader.LoaderActivity;
import com.example.wuzp.secondworld.view.retrofit.rxjava.RxJavaTest;
import com.example.wuzp.secondworld.view.retrofit.rxjava2.RxJavaUtil;
import com.example.wuzp.secondworld.view.widget.MsgShow.MsgView;
import com.example.wuzp.secondworld.view.widget.ToastMsg;
import com.example.wuzp.secondworld.view.widget.floatingactionbutton.FloatingActionButton;
import com.example.wuzp.secondworld.view.widget.floatingactionbutton.FloatingActionsMenu;
import com.umeng.analytics.MobclickAgent;

import java.util.HashMap;
import java.util.Map;

import static com.example.wuzp.secondworld.utils.CommonHelper.isFastDoubleClick;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = MainActivity.class.getSimpleName();

    private TextView textHello;
    private Button btnPost;
    private Button btnGet;
    private Button btnTrace;

    private FloatingActionsMenu floatingMenu;
    private FloatingActionButton floatingBtnSDCard;
    private FloatingActionButton floatingBtnBook;

    private String[] data = {"adas", "asdasas", "sadasda", "adsa", "dasdasdwq", "dasdas"};
    private Context mContext = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        doTestMethod();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Map<String,String> params = new HashMap<>();
        params.put("device_id", UUID.getInstance(this).getUUID());
        params.put("tag", TAG);
        MobclickAgent.onEvent(mContext, EventFinal.ACTIVITY_MAINACTIVITY, params);
    }

    private void initView() {
        textHello = (TextView) findViewById(R.id.text_hello);

        btnGet = $(R.id.btn_get);
        btnPost = $(R.id.btn_post);
        btnTrace = $(R.id.btn_trace);
        initData();
    }

    private <T extends View> T $(int id) {
        return (T) findViewById(id);
    }

    private void doTestMethod() {
        String name = "wuzp";
        String address = "beijing";

        Toast.makeText(this, "name:" + name + "  address:" + address, Toast.LENGTH_LONG).show();
    }

    private void initData() {
        textHello.setText("hello");
        btnGet.setOnClickListener(this);
        btnPost.setOnClickListener(this);
        btnTrace.setOnClickListener(this);

        floatingMenu = $(R.id.fab_menu);
        floatingBtnSDCard = $(R.id.fab_into);
        floatingBtnBook = $(R.id.fab_manager);
        floatingBtnSDCard.setOnClickListener(this);
        floatingBtnBook.setOnClickListener(this);

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
        //ActivityUtil.jumpActivity(this, GlideActivity.class);
        //ActivityUtil.jumpActivity(this, NettyClientActivity.class);
        //ActivityUtil.jumpActivity(this, GifActivity.class);
        ActivityUtil.jumpActivity(this, GtActivity.class);

        floatingMenu.setOnFloatingActionsMenuUpdateListener(new FloatingActionsMenu.OnFloatingActionsMenuUpdateListener() {
            @Override
            public void onMenuExpanded() {
                floatingBtnSDCard.setVisibility(View.VISIBLE);
                floatingBtnBook.setVisibility(View.VISIBLE);
            }

            @Override
            public void onMenuCollapsed() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        floatingBtnSDCard.setVisibility(View.INVISIBLE);
                        floatingBtnBook.setVisibility(View.INVISIBLE);
                    }
                }, floatingMenu.getAnimationDuration() - 200);
            }
        });
        floatingMenu.setActivated(false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_post:
                ActivityUtil.jumpActivity(this, RecyclerActivity.class);
//                ActivityUtil.jumpActivity(this, DataActivity.class);
                //ActivityUtil.jumpActivity(this, DataListActivity.class);
                //ActivityUtil.jumpActivity(this, DiffActivity.class);
                MobclickAgent.onEvent(this, EventFinal.CLICK_MAINACTIVITY_CLICK);
                break;
            case R.id.btn_get:
//                ActivityUtil.jumpActivity(this, RecyclerActivity.class);
                //ActivityUtil.jumpActivity(this, DataActivity.class);
                //ActivityUtil.jumpActivity(this, DataListActivity.class);
                ActivityUtil.jumpActivity(this, CursorloaderActivity.class);
                //ActivityUtil.jumpActivity(this, DiffActivity.class);
                MobclickAgent.onEvent(this, EventFinal.CLICK_MAINACTIVITY_CLICK);
                break;
            case R.id.btn_trace:
                //ActivityUtil.jumpActivity(this, RecyclerActivity.class);
                //ActivityUtil.jumpActivity(this, DataActivity.class);
                //ActivityUtil.jumpActivity(this, DataListActivity.class);
                //ActivityUtil.jumpActivity(this, DiffActivity.class);
                //ActivityUtil.jumpActivity(this, GlideActivity.class);
                ActivityUtil.jumpActivity(this, LoaderActivity.class);
                MobclickAgent.onEvent(this, EventFinal.CLICK_MAINACTIVITY_CLICK);
                break;
            case R.id.fab_into:
                if(isFastDoubleClick()) return;
                ToastMsg.getInsance().show("SDCard onClick here");
                MobclickAgent.onEvent(this, EventFinal.CLICK_MAINACTIVITY_CLICK);
                break;
            case R.id.fab_manager:
                if(isFastDoubleClick()) return;
               // ToastMsg.getInsance().show("BookManager onClick here");
                for (int i=0;i<5;i++)
                  MsgView.getInstance(this).show(this,"第一章" + i);
                MobclickAgent.onEvent(this, EventFinal.CLICK_MAINACTIVITY_CLICK);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setPositiveButton("退出应用", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                MobclickAgent.onKillProcess(mContext);

                int pid = android.os.Process.myPid();
                android.os.Process.killProcess(pid);
            }
        });
        builder.setNeutralButton("后退一下", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                finish();
            }
        });
        builder.setNegativeButton("点错了", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
            }
        });
        builder.show();
    }
}
