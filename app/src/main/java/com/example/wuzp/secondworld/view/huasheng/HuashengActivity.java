package com.example.wuzp.secondworld.view.huasheng;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.apkfuns.logutils.LogUtils;
import com.example.wuzp.secondworld.R;
import com.example.wuzp.secondworld.databinding.ActivityHuashengBinding;
import com.example.wuzp.secondworld.network.parse.HttpBase;
import com.example.wuzp.secondworld.view.base.BindingActivity;
import com.example.wuzp.secondworld.view.huasheng.jsCallback.JsCallbackImp;
import com.example.wuzp.secondworld.view.retrofit.network.parse.UserInfo;

/**
 * Created by wuzp on 2017/3/29.
 */

public class HuashengActivity extends BindingActivity<ActivityHuashengBinding,HuashengPresenter> implements
        HuashengContract.IView
{
    private HuashengViewWrapper viewWrapper = new HuashengViewWrapper();

    public static void launch(Context context,String title){
        Bundle bundle = new Bundle();
        bundle.putString("TITLE",title);
        Intent intent = new Intent(context,HuashengActivity.class);
        intent.putExtras(bundle);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewWrapper.addBinding(binding);
        LogUtils.e("onCreate");
        getExtra(getIntent());
        initView();
    }

    private void getExtra(Intent intent){
        try{
            String name = intent.getExtras().getString("TITLE");
            LogUtils.e("onCreate: title:" + name);
            HttpBase<UserInfo> base = (HttpBase<UserInfo>)intent.getSerializableExtra("base");
            if(base == null){
                LogUtils.e("base is null");
            }else{
                LogUtils.e("base is  not null");
                LogUtils.e("base info is:" + base.toString());
            }
        }catch (Exception e){}
    }

    private void initView(){
        binding.btnHuasheng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HuashengActivity.this,OtherHsActivity.class);
                startActivity(intent);
            }
        });
        initWebView();
    }

    private void initWebView(){
        binding.webHuasheng.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return true;
            }
        });
        binding.webHuasheng.setHorizontalScrollBarEnabled(false);
        binding.webHuasheng.getSettings().setJavaScriptEnabled(true);
        binding.webHuasheng.getSettings().setLoadWithOverviewMode(true);
        if (Build.VERSION.SDK_INT >= 21) {
            binding.webHuasheng.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        binding.webHuasheng.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        binding.webHuasheng.getSettings().setDomStorageEnabled(true);
        binding.webHuasheng.getSettings().setTextSize(WebSettings.TextSize.NORMAL);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            binding.webHuasheng.getSettings().setAllowUniversalAccessFromFileURLs(true);
        }

        binding.webHuasheng.setWebViewClient(new HuashengWebClient());
        binding.webHuasheng.setWebChromeClient(new HuashengChromWebView());

        JsCallBackImpImp Client = new JsCallBackImpImp();
        binding.webHuasheng.addJavascriptInterface(Client, "client");

    }

    @Override
    protected HuashengPresenter createPresenter() {
        return new HuashengPresenter(this);
    }

    @Override
    protected int createLayoutId() {
        return R.layout.activity_huasheng;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        LogUtils.e("onNewIntent");
        //在这里就不能直接使用getIntent() 因为getIntent就会是以前得Intent
        String name = intent.getExtras().getString("TITLE");
        LogUtils.e("onNewIntent: title:" + name);
    }

    @Override
    protected void onStart() {
        super.onStart();
        LogUtils.e("onStart");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        LogUtils.e("onRestart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        LogUtils.e("onResume");
        binding.webHuasheng.loadUrl("file:///android_asset/webview.html");
    }

    @Override
    protected void onPause() {
        super.onPause();
        LogUtils.e("onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        LogUtils.e("onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtils.e("onDestroy");
    }

    class HuashengWebClient extends WebViewClient{
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            return super.shouldOverrideUrlLoading(view, request);
        }
    }

    class HuashengChromWebView extends WebChromeClient{

    }

    //在HyBird 模式中webview调用native的方法 不能加载接口上,一定要加在类当中的实现方法上，因为类的继承关系,
    //父类中添加了javascriptInterface 之后子类实现 方法可以不加这个注解，当然加也是可以的
    class JsCallBackImpImp extends JsCallbackImp{

        @JavascriptInterface
        @Override
        public void jsClick(String json) {
          LogUtils.e("JsCallBackImpImp jsClick json:" + json);
        }
    }
}
