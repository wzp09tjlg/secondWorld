package com.example.wuzp.secondworld.view.pageView;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import com.example.wuzp.secondworld.R;
import com.example.wuzp.secondworld.databinding.ActivityPageviewBinding;
import com.example.wuzp.secondworld.view.base.BindingActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wuzp on 2017/5/29.
 */

public class PageViewActivity extends BindingActivity<ActivityPageviewBinding,PageViewPresenter> implements PageViewContract.IView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

    @Override
    protected PageViewPresenter createPresenter() {
        return new PageViewPresenter(this);
    }

    @Override
    protected int createLayoutId() {
        return R.layout.activity_pageview;
    }

    private void initData(){
        List<Bitmap> data = new ArrayList<>();
        data.add(BitmapFactory.decodeResource(getResources(),R.drawable.icon_road));
        data.add(BitmapFactory.decodeResource(getResources(),R.drawable.icon_sky));
        data.add(BitmapFactory.decodeResource(getResources(),R.drawable.icon_boy));
        data.add(BitmapFactory.decodeResource(getResources(),R.drawable.icon_girl));
       binding.pageView.setBitmaps(data);
    }
}
