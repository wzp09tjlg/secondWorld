package com.example.wuzp.secondworld.view.dialog;

import android.os.Bundle;
import android.view.View;

import com.example.wuzp.secondworld.R;
import com.example.wuzp.secondworld.databinding.ActivityAddAlbumBinding;
import com.example.wuzp.secondworld.view.base.BindingActivity;

/**
 * Created by wuzp on 2017/3/20.
 */

public class AddAlbumActivity extends BindingActivity<ActivityAddAlbumBinding,AddAlbumPresenter> implements
   AddAlbumContract.AddAlbumView
{
    private AddAlbumViewWrapper viewWrapper = new AddAlbumViewWrapper();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setFinishOnTouchOutside(true);
        super.onCreate(savedInstanceState);
        viewWrapper.addBinding(binding);
        initView();
    }

    @Override
    protected AddAlbumPresenter createPresenter() {
        return new AddAlbumPresenter(this);
    }

    @Override
    protected int createLayoutId() {
        return R.layout.activity_add_album;
    }

    private void initView(){
      binding.btnSubmit.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              finish();
          }
      });
    }
}
