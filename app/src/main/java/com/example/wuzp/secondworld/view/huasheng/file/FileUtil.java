package com.example.wuzp.secondworld.view.huasheng.file;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;

/**
 * Created by wuzp on 2017/3/31.
 文件操作类
 在应用保存文件的时候 需要注意的是考虑到文件存放的位置,是否应该存放在内存。大文件应该存放在外存中。
 */
public class FileUtil {
    //Context 下的路径

    /**
     * Environment 常用方法：
     * 方法：getDataDirectory()
     解释：返回 File ，获取 Android 数据目录。
     * 方法：getDownloadCacheDirectory()
     解释：返回 File ，获取 Android 下载/缓存内容目录。
     * 方法：getExternalStorageDirectory()
     解释：返回 File ，获取外部存储目录即 SDCard
     * 方法：getExternalStoragePublicDirectory(String type)
     解释：返回 File ，取一个高端的公用的外部存储器目录来摆放某些类型的文件
     * 方法：getExternalStorageState()
     解释：返回 File ，获取外部存储设备的当前状态
     * 方法：getRootDirectory()
     解释：返回 File ，获取 Android 的根目录
     * */

    //Environment 下的路径

    /**
     * File sdCard = Environment.getExternalStorageDirectory();
     * 以前经常使用这个方式获取手机中外存储的地址，如果手机中安装大量的app，就会导致机器的
     * 外存杂乱不堪。但是在安卓6.0以上的机器，根目录的使用时需要授权的。就算你在清单文件中
     * 授权，如果用户不允许，你依然写不进去文件
     *  <uses-permissionAndroid:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
     <uses-permissionandroid:name="android.permission.READ_EXTERNAL_STORAGE"/>
     谷歌早已提供了一套解决方案：
     那就是统一路径为:
     /Android/data/<package name >/files/… (该路径通常挂载在/mnt/sdcard/下)
     外部存储路径调用方法是:context.getExternalFilesDir(dir).getAbsolutePath() //通过context调用
     这个方法获得的文件存储路径适用于6.0以后系统,主要AndroidManifest.xml配置读写权限了,
     就不需要用户再授权了.
     是关于外部存储的介绍,那么如果有些手机没有SD卡或者系统自身没有分配外部存储空间,时,
     我们应该怎么缓存数据呢?
     那就需要用到内部存储了,内部存储的路径是: /data/data/< package name>/files/…
     (该路径挂载在在手机自身存储目录)
     内部存储路径调用方法是:context().getCacheDir().getAbsolutePath()
     //通过context调用

     public static String getFilePath(Context context,String dir) {
     String directoryPath="";
     //判断SD卡是否可用
     if (MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) ) {
     directoryPath =context.getExternalFilesDir(dir).getAbsolutePath() ;
     // directoryPath =context.getExternalCacheDir().getAbsolutePath() ;
     }else{
     //没内存卡就存机身内存
     directoryPath=context.getFilesDir()+File.separator+dir;
     // directoryPath=context.getCacheDir()+File.separator+dir;
     }
     File file = new File(directoryPath);
     if(!file.exists()){//判断文件目录是否存在
     file.mkdirs();
     }
     LogUtil.i("filePath====>"+directoryPath);
     return directoryPath;
     }
     其中,getExternalCacheDir()与getCacheDir()的区别 与getExternalFilesDir()及getFilesDir()的区别相同,
     前者只是在路径下自动建好了一个cache文件目录:/data/<
     package name >/files/cach/...

          区别               	方法                               	    备注
     external storage     Environment.getExternalStorageDirectory()  SD根目录:/mnt/sdcard/ (6.0后写入需要用户授权)
     外部存储 		      context.getExternalFilesDir(dir) 	         路径为:/mnt/sdcard/Android/data/< package name >/files/…
                          context.getExternalCacheDir()              路径为:/mnt/sdcard//Android/data/< package name >/cache/…

     internal storage     context.getFilesDir()     	             路径是:/data/data/< package name >/files/…
     内部存储             context.getCacheDir() 	                 路径是:/data/data/< package name >/cache/…

     对安卓开发的路径的总结：
     (Environment 是对整体的环境的路径
     Environment.getDataDirectory() = /data
     Environment.getDownloadCacheDirectory() = /cache
     Environment.getExternalStorageDirectory() = /mnt/sdcard
     Environment.getExternalStoragePublicDirectory(“test”) = /mnt/sdcard/test
     Environment.getRootDirectory() = /system

     //Context是对当前应用的路径
     getPackageCodePath() = /data/app/com.my.app-1.apk
     getPackageResourcePath() = /data/app/com.my.app-1.apk
     getFilesDir() = /data/data/com.my.app/files
     getCacheDir() = /data/data/com.my.app/cache
     getDatabasePath(“test”) = /data/data/com.my.app/databases/test
     getDir(“test”, Context.MODE_PRIVATE) = /data/data/com.my.app/app_test

     //context.getExternal...是对外挂的sdcard的路径
     getExternalCacheDir() = /mnt/sdcard/Android/data/com.my.app/cache
     getExternalFilesDir(“test”) = /mnt/sdcard/Android/data/com.my.app/files/test
     getExternalFilesDir(null) = /mnt/sdcard/Android/data/com.my.app/files

     //在写文件时 如果不希望自己应用的某些文件被系统的媒体资源扫描到，这时就是用到了.nomedia文件
     有些时候那些文件应该只有你的应用能够呈现。这时候“.nomedia”就起作用了。
     如果一个目录包含一个叫“.nomedia”的文件，MediaScanner会跳过这个目录，
     因此该目录里的所有媒体文件都不会在公共媒体列表里看到。
     这也是使用标准的特定应用目录的另外一个原因。
     data目录里包含“.nomedia”文件(译者注：Android系统会在Android/data/目录下自动为你创建“.nomedia”文件)，
     这样所有你添加到特定应用目录中的媒体文件都不会显示在公共媒体列表中
     (译者注：不会被MediaScanner扫描，也就不会存储到MediaProvider中，
     这也就是“.nomedia”开关的意思)。
     * */

    static void show(String path){
        Log.e("PATH",   path);
    }

    public static void showEnvorimentPath(){
        String state = "";
        state = Environment.getExternalStorageState();
        show("state:" + state);
        File dataPath = Environment.getDataDirectory();
        show("dataPath:" + dataPath.getPath());
        File downlocalCachePath = Environment.getDownloadCacheDirectory();
        show("downloadPath:" + downlocalCachePath.getPath());
        File externalStoragePath = Environment.getExternalStorageDirectory();
        show("externalStoragePath:" + externalStoragePath.getPath());
        File externalStoragePublicPath = Environment.getExternalStoragePublicDirectory("files");
        show("externalStoragePublicPath:" + externalStoragePublicPath.getPath());
        File rootPath = Environment.getRootDirectory();
        show("rootPath:" + rootPath.getPath());
    }

    public static void getContextInnerPath(Context context){
        String packageCodePath = context.getPackageCodePath();
        show("packageCodePath:" + packageCodePath);
        String packageResourcePath = context.getPackageResourcePath();
        show("packageResourcePath:" + packageResourcePath);
        File fileDir = context.getFilesDir();
        show("fileDir:" + fileDir.getPath());
        File cacheDir = context.getCacheDir();
        show("cacheDir:" + cacheDir.getPath());
        File databasePath = context.getDatabasePath("HS_DB");
        show("databasePath:" + databasePath.getPath());
        File dir = context.getDir("HS",Context.MODE_APPEND);
        show("dir:" + dir.getPath());
    }

    public static void getContextOutterPath(Context context){
        File externalCachePath = context.getExternalCacheDir();
        show("externalCachePath:" + externalCachePath.getPath());
        File externalFilePath = context.getExternalFilesDir("HS");
        show("externalFilePath:" + externalFilePath.getPath());

    }
}
