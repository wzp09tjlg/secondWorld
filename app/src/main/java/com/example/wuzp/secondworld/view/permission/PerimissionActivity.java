package com.example.wuzp.secondworld.view.permission;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.apkfuns.logutils.LogUtils;
import com.example.wuzp.secondworld.R;

import java.io.File;

import static com.example.wuzp.secondworld.HApplication.getContext;

/**
 * Created by wuzp on 2017/4/5.
 */

public class PerimissionActivity extends Activity {
    private final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 0x101;
    private final int MY_PERMISSIONS_REQUEST_CAMERA = 0x102;
    private final int MY_PERMISSIONS_REQUEST_WRITE_FILE = 0x103;

    private final int REQUEST_CAMERA = 0X201;
    private Button btnAllow;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission);
        initView();
    }

    private void initView() {
        btnAllow = (Button) findViewById(R.id.btn_allow);
        btnAllow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // checkPermission();
                //useCamera();
                checkPermissionCamera();
            }
        });
        //checkPermission();
    }

    //权限的理解和认识
    //在6.0手机上使用照相机(调用系统的照相机)
    private void useCamera() {
        //调用系统的照相机 现在是6.0的系统，只在清单中申请权限
        // 在代码中没有动态的申请权限，看看能不能正常的调用起来
        //没有在代码中动态的申请权限，会报异常。如果不加try -catch 会崩溃
        // 出现的情况是闪一下又会回到当前的页面，但是照相机打不开
        try {
            String sFileFullPath = getCacheDir().getAbsolutePath() + "/test.jpg";
            File file = new File(sFileFullPath);
            Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            int currentapiVersion = android.os.Build.VERSION.SDK_INT;
            Log.e("currentapiVersion","currentapiVersion====>"+currentapiVersion);
            if (currentapiVersion<24){ //7.0以下的手机打开照相机 处理
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
                startActivityForResult(intent, REQUEST_CAMERA);
            }else { //7.0以上的手机打开照相机 处理
                ContentValues contentValues = new ContentValues(1);
                contentValues.put(MediaStore.Images.Media.DATA, file.getAbsolutePath());
                Uri uri = getContext().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,contentValues);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                startActivityForResult(intent, REQUEST_CAMERA);
            }
            LogUtils.e("try -----  success");
        } catch (Exception e) {
            LogUtils.e(" exception ---  msg:" + e.getMessage());
        }
    }

    private void checkPermissionCamera() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(PerimissionActivity.this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},//需要授权的全部权限
                    MY_PERMISSIONS_REQUEST_WRITE_FILE);//请求授权的requestCode
        } else {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) !=
                    PackageManager.PERMISSION_GRANTED) {
                //一开始授权不成功(或者开始授权成功之后,被用户取消了权限的授权),
                // 应该再次弹出提示 授权

                //授权不成功 是否需要弹出提示 显示权限的使用场景
                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.CAMERA)) {
                    //弹出提示框 解释权限使用的场景 自己实现弹出框
                    Dialog dialog = new AlertDialog.
                            Builder(this).
                            setMessage("grant carmera permission is to use take picture")
                            .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //允许就调用
                                    //直接要求授权
                                    ActivityCompat.requestPermissions(PerimissionActivity.this,
                                            new String[]{Manifest.permission.CAMERA},//需要授权的全部权限
                                            MY_PERMISSIONS_REQUEST_CAMERA);//请求授权的requestCode
                                }
                            }).setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //不允许就toast
                                    Toast.makeText(PerimissionActivity.this, "cancel", Toast.LENGTH_SHORT).show();
                                }
                            }).create();
                    dialog.show();
                } else {
                    //直接要求授权
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.CAMERA},//需要授权的全部权限
                            MY_PERMISSIONS_REQUEST_CAMERA);//请求授权的requestCode
                }
            } else {//已经授权成功了 就可以正常调用
                useCamera();
            }
        }
    }

    private void checkPermission() {
        //checkSelfPermission 返回的状态是两个 一个是granted 另一个是 PERMISSION_DENIED
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {//不允许的操作
            //是否需要弹出一个给用户解释授权的弹框 如果是的法，或者之前用户没有授权，那么返回的都是true 如果用户选择donot ask agin 返回时false\
            //这里的提示的弹框 是自己实现 不是系统提供的
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_CONTACTS)) {//

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {// 如果不给用户解释权限的弹框，那么还需要授权的法，这里使用requestPermission进行申请权限
                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_CONTACTS},//需要授权的全部权限
                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);//请求授权的requestCode

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {//允许的操作

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //被拒绝 就finish 掉整个activity
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS://请求授权的requestCode
                //permissions // 是申请的权限
                //grantResult //是申请的结果
                //* @see #PERMISSION_GRANTED
                //* @see #PERMISSION_DENIED
                break;
            case MY_PERMISSIONS_REQUEST_CAMERA:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "grant success", Toast.LENGTH_SHORT).show();
                    useCamera();
                } else {
                    Toast.makeText(this, "grant failure", Toast.LENGTH_SHORT).show();
                }
                break;
            case MY_PERMISSIONS_REQUEST_WRITE_FILE:
                Toast.makeText(this, "grant write file success", Toast.LENGTH_SHORT).show();
                checkPermissionCamera();
                break;
            case  REQUEST_CAMERA:
                Toast.makeText(this, "get uri is success", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}