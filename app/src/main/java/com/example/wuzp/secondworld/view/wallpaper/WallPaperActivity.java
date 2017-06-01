package com.example.wuzp.secondworld.view.wallpaper;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Toast;

import com.example.wuzp.secondworld.R;
import com.example.wuzp.secondworld.databinding.ActivityWallpaperBinding;
import com.example.wuzp.secondworld.view.base.BindingActivity;

/**
 * Created by wuzp on 2017/6/1.
 */

public class WallPaperActivity extends BindingActivity<ActivityWallpaperBinding,WallPaperPresenter> implements WallPaperContract.IView {
    private static final int PERMISSIONS_REQUEST_CAMERA = 0X001;
    private Context mContext = this;
    static final String PERMISSION_CAMERA = Manifest.permission.CAMERA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    @Override
    protected WallPaperPresenter createPresenter() {
        return new WallPaperPresenter(this);
    }

    @Override
    protected int createLayoutId() {
        return R.layout.activity_wallpaper;
    }

    private void initView(){
      binding.btnSet.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              checkSelfPermission();
          }
      });
    }

    /**
     * 检查权限
     */
    void checkSelfPermission() {
        if (ContextCompat.checkSelfPermission(mContext, PERMISSION_CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{PERMISSION_CAMERA},
                    PERMISSIONS_REQUEST_CAMERA);
        } else {
//            setTransparentWallpaper();
            startWallpaper();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSIONS_REQUEST_CAMERA: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    setTransparentWallpaper();
//                    startWallpaper();

                } else {
                    Toast.makeText(mContext, "没有获得照相机权限,不能使用本应用的功能", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }
    /**
     * 选择壁纸
     */
    void startWallpaper() {
        final Intent pickWallpaper = new Intent(Intent.ACTION_SET_WALLPAPER);
        Intent chooser = Intent.createChooser(pickWallpaper, "设置壁纸");
        startActivity(chooser);
    }

    /**
     * 不需要手动启动服务
     */
    void setTransparentWallpaper() {
        startService(new Intent(mContext, WallPaperService.class));
    }
}

