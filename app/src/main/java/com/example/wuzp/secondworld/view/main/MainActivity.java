package com.example.wuzp.secondworld.view.main;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.StrikethroughSpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.wuzp.secondworld.R;
import com.example.wuzp.secondworld.stats.EventFinal;
import com.example.wuzp.secondworld.utils.ActivityUtil;
import com.example.wuzp.secondworld.utils.UUID;
import com.example.wuzp.secondworld.view.FragementLife.FragmentLifeActivity;
import com.example.wuzp.secondworld.view.cursorloader.CursorloaderActivity;
import com.example.wuzp.secondworld.view.huasheng.recyclerView.RecyclerActivity;
import com.example.wuzp.secondworld.view.loader.LoaderActivity;
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

    private int count  = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        doTestMethod();
        String channelId = "";
        MobclickAgent. startWithConfigure(new MobclickAgent.UMAnalyticsConfig(getApplicationContext(),"appkey",channelId));
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

       // Toast.makeText(this, "name:" + name + "  address:" + address, Toast.LENGTH_LONG).show();
    }

    private void initData() {
        //设置中划线
        String content = "50.00";
        int start = 0;
        int end = content.length();
        SpannableStringBuilder ssb = new SpannableStringBuilder(content);
        ssb.setSpan(new StrikethroughSpan(), start, end, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        textHello.setText(ssb);

        btnGet.setOnClickListener(this);
        btnPost.setOnClickListener(this);
        btnTrace.setOnClickListener(this);

        floatingMenu = $(R.id.fab_menu);
        floatingBtnSDCard = $(R.id.fab_into);
        floatingBtnBook = $(R.id.fab_manager);
        floatingBtnSDCard.setOnClickListener(this);
        floatingBtnBook.setOnClickListener(this);

        //RxJavaUtil javaUtil = new RxJavaUtil();
        //javaUtil.getSomeRelation();
//        javaUtil.doSomeMethod2();
//        javaUtil.doSomeTest5();//.doSomeTest3();//doSomeMethod2();

        //RxJavaTest test = new RxJavaTest();
        ///test.testOne();
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
        //ActivityUtil.jumpActivity(this, GtActivity.class);
        //ActivityUtil.jumpActivity(this, TtsActivity.class);
        //ActivityUtil.jumpActivity(this, ClientActivity.class);
        //ActivityUtil.jumpActivity(this, LoaderClientActivity.class);
        //ActivityUtil.jumpActivity(this, SearchActivity.class);
        //ActivityUtil.jumpActivity(this, BesierActivity.class);
        //ActivityUtil.jumpActivity(this, MatrixActivity.class);
        //ActivityUtil.jumpActivity(this, TestImgActivity.class);
        //ActivityUtil.jumpActivity(this, PageViewActivity.class);
        //ActivityUtil.jumpActivity(this, WallPaperActivity.class);
        //ActivityUtil.jumpActivity(this, LivePaperActivity.class);
        //ActivityUtil.jumpActivity(this, LockActivity.class);
        //ActivityUtil.jumpActivity(this, CipherActivity.class);
        //ActivityUtil.jumpActivity(this, LeakActivity.class);
        //ActivityUtil.jumpActivity(this, NioActivity.class);
        //ActivityUtil.jumpActivity(this, XmlActivity.class);
        //ActivityUtil.jumpActivity(this, FactoryActivity.class);
        //ActivityUtil.jumpActivity(this, SlideActivity.class);
        //ActivityUtil.jumpActivity(this, CursorloaderActivity.class);
        //ActivityUtil.jumpActivity(this, ItemAnimationActivity.class);
        //ActivityUtil.jumpActivity(this, LuanchActivity.class);
        //ActivityUtil.jumpActivity(this, FlagBaseActivity.class);
        //ActivityUtil.jumpActivity(this, HandlerActivity.class);
        //ActivityUtil.jumpActivity(this, DbActivity.class);
        //ActivityUtil.jumpActivity(this, MuchItemActivity.class);
        //ActivityUtil.jumpActivity(this, SingleActivity.class);
        //ActivityUtil.jumpActivity(this, DbActivity.class);
        ActivityUtil.jumpActivity(this, FragmentLifeActivity.class);


        //Intent intent = new Intent(this, FlagBaseActivity.class);
        //intent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
        //startActivity(intent);

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
                count ++;
                showMsg(count % 6);
                MobclickAgent.onEvent(this, EventFinal.CLICK_MAINACTIVITY_CLICK);
                break;
            case R.id.fab_manager:
                if(isFastDoubleClick()) return;
               // ToastMsg.getInsance().show("BookManager onClick here");
                for (int i=0;i<5;i++){
                    final int tempI = i;
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            MsgView.getInstance(MainActivity.this).show(MainActivity.this,"第一章" + tempI);
                        }
                    }).start();

                }

                MobclickAgent.onEvent(this, EventFinal.CLICK_MAINACTIVITY_CLICK);
                break;
        }
    }

    private void showMsg(int count){
        if(count == 0){
            ToastMsg.getInsance().show("收到哈骄傲了和大家散了");
        }else if(count == 1){
            ToastMsg.getInsance().show("擦扫描摩奥微粒贷");
        }else if(count == 2){
            ToastMsg.getInsance().show("萨克雷死绿绿绿啦所绿");
        }else if(count == 3){
            ToastMsg.getInsance().show("奥美斯阿拉累计进口撕");
        }else if(count == 4){
            ToastMsg.getInsance().show("哦设计涉及宽带连接点击");
        }else if(count == 5){
            ToastMsg.getInsance().show("机打击我换个环境及家里");
        }
    }

    AlertDialog.Builder builder = null;
    @Override
    public void onBackPressed() {
        super.onBackPressed();
       /* if(builder != null){
            builder = null;
        }
        builder = new AlertDialog.Builder(mContext);
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
        builder.show();*/
    }
}
