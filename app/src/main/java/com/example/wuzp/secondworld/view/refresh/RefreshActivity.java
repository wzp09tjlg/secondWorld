package com.example.wuzp.secondworld.view.refresh;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.webkit.WebView;
import android.widget.ScrollView;

import com.example.wuzp.secondworld.R;
import com.example.wuzp.secondworld.databinding.ActivityRefreshBinding;
import com.example.wuzp.secondworld.view.base.BindingActivity;

/**
 * Created by wuzp on 2017/5/25.
 * 原本想 在refresh + webView 的页面 正在刷新时，滑动上滑webview 就取消刷新并且取消webview的刷新。但是貌似不成立
 * webview没有提供这样的取消方法
 * 可供我们取消
 */
@SuppressWarnings("all")
public class RefreshActivity extends BindingActivity<ActivityRefreshBinding, RefreshPresenter> implements
        RefreshContract.IView,SwipeRefreshLayout.OnRefreshListener,
        View.OnScrollChangeListener
{
    private SwipeRefreshLayout refreshLayout;
    private ScrollView         scrollView;
    private WebView            webView;

    private boolean isFreshing = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

    @Override
    protected RefreshPresenter createPresenter() {
        return new RefreshPresenter(this);
    }

    @Override
    protected int createLayoutId() {
        return R.layout.activity_refresh;
    }

    @Override
    public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        if(isFreshing){
            int dY = scrollY - oldScrollY;
            if(dY > 10){
                 //原本先想在这时候取消webview的网络请求，但是发现现在没有取消的方法
                //所以这个构思是不能成立。
            }
        }
    }

    @Override
    public void onRefresh() {
        isFreshing = true;
        webView.reload();
    }

    private void initData(){
        refreshLayout.setOnRefreshListener(this);
        scrollView.setOnScrollChangeListener(this);
    }
}
