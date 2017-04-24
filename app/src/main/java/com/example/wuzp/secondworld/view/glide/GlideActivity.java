package com.example.wuzp.secondworld.view.glide;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.apkfuns.logutils.LogUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.hlibrary.utils.ToolsUtil;
import com.example.slibrary.Utils.UUtils;
import com.example.wuzp.secondworld.R;
import com.example.wuzp.secondworld.databinding.ActivityGlideBinding;
import com.example.wuzp.secondworld.network.parse.TopicBean;
import com.example.wuzp.secondworld.view.base.BindingActivity;

/**
 * Created by wuzp on 2017/4/24.
 */
public class GlideActivity extends BindingActivity<ActivityGlideBinding, GlidePresenter> implements GlideContract.IView {
    private GlideViewWrapper viewWrapper = new GlideViewWrapper();
    private TopicBean bean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewWrapper.addBinding(binding);
        viewWrapper.setItemClickListener(getItemListener());
        mvpPresenter.start();
    }

    @Override
    protected GlidePresenter createPresenter() {
        return new GlidePresenter(this);
    }

    @Override
    protected int createLayoutId() {
        return R.layout.activity_glide;
    }

    @Override
    public void setData(TopicBean bean) {
        this.bean = bean;
    }

    private GlideContract.OnItemClick getItemListener() {
        GlideContract.OnItemClick onItemClick = new GlideContract.OnItemClick() {
            @Override
            public void onItemClick(View view) {
                if (bean != null) {
                    String url = bean.getItems().get(0).getData().get(0).getImage();//获取一个URL的地址
                    doGlideLoadImage(url);
                }
            }
        };
        return onItemClick;
    }

    private void doGlideLoadImage(final String url) {
        //glide进行加载图片 监听加载的进度 添加listener 的方法进行
        // glide 对图片的加载进度into到imageView上，需要对ImageView设置大小 不然加载不会请求
        final ImageView tempimageView = new ImageView(this);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        tempimageView.setLayoutParams(params);
        Glide.with(this)
                .load(url)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        LogUtils.e("error");
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        LogUtils.e("success-------------------");
                        setImageSource(url);
                        return false;
                    }
                })
                .into(tempimageView);
    }

    private void setImageSource(String url){
        Glide.with(this)
                .load(url)
                .centerCrop()
                .crossFade()
                .into(binding.imgLoading);
    }

    // this is a util test
    private void doTest(){
        ToolsUtil.add(100,200);
        ToolsUtil.minus(100,200);
        ToolsUtil.show("this is a test for library");

        UUtils.show("THIS IS A TEST TOO HERE");
    }
}
