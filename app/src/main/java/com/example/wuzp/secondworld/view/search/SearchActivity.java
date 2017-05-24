package com.example.wuzp.secondworld.view.search;

import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;

import com.example.wuzp.secondworld.R;
import com.example.wuzp.secondworld.databinding.ActivitySearchBinding;
import com.example.wuzp.secondworld.view.base.BindingActivity;
import com.example.wuzp.secondworld.view.search.asynctask.GenericTask;
import com.example.wuzp.secondworld.view.search.asynctask.TaskParams;
import com.example.wuzp.secondworld.view.search.asynctask.TaskResult;
import com.example.wuzp.secondworld.view.search.utils.StorageUtil;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;

/**
 * Created by wuzp on 2017/5/22.
 * 优化全局收索的框架
 * 1.普通的全局搜索，以文件的形式去遍历查询
 * 2.使用全局的contentResever
 */
/**
 text/plain（纯文本）
 text/html（HTML文档）
 application/xhtml+xml（XHTML文档）
 image/gif（GIF图像）
 image/jpeg（JPEG图像）【PHP中为：image/pjpeg】
 image/png（PNG图像）【PHP中为：image/x-png】
 video/mpeg（MPEG动画）
 application/octet-stream（任意的二进制数据）
 application/pdf（PDF文档）
 application/msword（Microsoft Word文件）
 message/rfc822（RFC 822形式）
 multipart/alternative（HTML邮件的HTML形式和纯文本形式，相同内容使用不同形式表示）
 application/x-www-form-urlencoded（使用HTTP的POST方法提交的表单）
 multipart/form-data
 * */
public class SearchActivity extends BindingActivity<ActivitySearchBinding, SearchPresenter> implements
        SearchContract.IView, View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        binding.btnSearch.setOnClickListener(this);
        binding.btnScan.setOnClickListener(this);
    }

    @Override
    protected SearchPresenter createPresenter() {
        return new SearchPresenter(this);
    }

    @Override
    protected int createLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_search:
                //doSearch();//一这种多线程去遍历本地文件的 方式获取本地某种文件格式的搜索 至少要耗费3秒以上的时间
                doTestScan();
                break;
            case R.id.btn_scan:
                doScan();
                break;
        }
    }

    private void doSearch(){
         //按照普通的文件查询的方式去遍历整个路径下的文件
        scanFile();//安卓Java的方式去查询本地文件夹中 存在的text文件 已经不太合适了
    }

    private long startTime = 0l;
    private ArrayList<String> mTxtFileNames = new ArrayList<>();
    private ArrayList<String> mTxtFilePaths = new ArrayList<>();
    private ArrayList<File> mTxtFiles = new ArrayList<>();
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };
    private String mCurrentFilePath = Environment.getExternalStorageDirectory().toString();//获取手机中SDCard的文件路径
    private void scanFile() {//这里竟然还是使用 Java中的线程池去查找本地文件，真是不是适合开发安卓，
        //在安卓中 本来就提供查询本地文件的系统方法，但是他们却不适用。类似长征开始时
        // 启动异步扫描任务
        final GenericTask scanTask = new GenericTask() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                startTime = System.currentTimeMillis();
                Log.e("search","startTime:" + startTime);
            }

            @Override
            protected TaskResult doInBackground(TaskParams... params) {
                refreshScanResults(mCurrentFilePath);
                return null;
            }

            private void refreshScanResults(final String nowDir) {
                final File file = new File(nowDir);
                final File[] txtOrEpubOrDirFiles = listFiles(file, new TXTOrEPUBOrDIRLengthLimitSelector());

                for (final File f : txtOrEpubOrDirFiles) {//获取文件名字的list
                    if (!f.canRead()) {
                        continue;
                    }
                    if (f.isDirectory()) {
                        // 过滤软件自己的文件夹
                        File homePath = new File(StorageUtil.EXTERNAL_STORAGE + StorageUtil.DIR_HOME);
                        if (!isCancelled() && !f.equals(homePath)) {
                            refreshScanResults(f.getAbsolutePath());
                        }
                    } else {
                        mHandler.post(new Runnable() {

                            @Override
                            public void run() {
                                if (f.getName().toLowerCase(Locale.CHINA).endsWith(".txt")) {
                                    mTxtFileNames.add(f.getName());
                                    mTxtFiles.add(f);
                                }
                                // 根据大小对集合进行排序，大的排前面  (每次查询到一个文件之后都进行一次排序 真以为在子线程中执行这样的操作 不会耗时间啊)
                                Collections.sort(mTxtFiles, new Comparator<File>() {
                                    @Override
                                    public int compare(File o1, File o2) {
                                        if (o1.length() > o2.length()) {
                                            return -1;
                                        } else if (o1.length() < o2.length()) {
                                            return 1;
                                        } else {
                                            return 0;
                                        }
                                    }
                                });
                            }
                        });
                    }
                }
            }

            protected void onPostExecute(TaskResult result) {
                /*DialogManager.dismissProgressDialog();*/
                int size = mTxtFileNames.size();
                long stopTime = System.currentTimeMillis();
                Log.e("search","stopTime:" + stopTime );
                Log.e("search","duration:" + (stopTime - startTime) + "  size:" + size);
                for(int i=0;i<size ;i++){
                    Log.e("search","" + mTxtFileNames.get(i));
                }
            }
        };
        scanTask.execute();
        // 显示进度对话框，设置为可以取消
       /* DialogManager.showProgressDialog(SearchActivity.this,
                String.format("扫描结果：%$1", 0), true, true,
                new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        scanTask.cancel(true);
                    }

                }, null);*/
    }

    private File[] listFiles(File file, FileFilter fileFilter) {
        File[] listFiles = file.listFiles(fileFilter);
        if (listFiles != null) {
            return listFiles;
        }
        return new File[0];
    }

    private static final int SCAN_FILE_SIZE_LIMIT = 5 * 1024;//文件大于5K的TXT 可以被扫描进来
    class TXTOrEPUBOrDIRLengthLimitSelector implements FileFilter {

        /**
         * The txt end.
         */
        String txtEnd = ".txt";

        /**
         * The epub end.
         *//*
        String epubEnd = ".epub";*/

        @Override
        public boolean accept(File pathname) {
            if(!pathname.canRead()) return false;
            if (pathname.isDirectory() || (pathname.length() > SCAN_FILE_SIZE_LIMIT
                    && pathname.getName().toLowerCase(Locale.CHINA).endsWith(txtEnd))) {
                return true;
            }
            return false;
        }
    }

    private void doScan(){//使用安卓原生提供的资源文件查询 提升查询速度
        mTxtFileNames.clear();
        startTime = System.currentTimeMillis();
        Log.e("scan","start:" + startTime);
        ContentResolver resolver = getContentResolver();
        //查询的参数 uri 查询的字段 查询的条件名 查询的条件值 排序
        //resolver.query(MediaStore.Files.getContentUri("str"),null,null,null,null);
        Cursor cursor = resolver.query(MediaStore.Files.getContentUri("external"),null,"mime_type=\"text/plain\"",null,null);
        if (cursor != null) {
            int size = cursor.getColumnCount();
            String[] names = cursor.getColumnNames();
            for(int i=0;i<size;i++){
                Log.e("scan","name:" + names[i]);
            }

            while (cursor.moveToNext()) {
                String title = cursor.getString(cursor.getColumnIndex(MediaStore.Files.FileColumns.TITLE));
                String path = cursor.getString(cursor.getColumnIndex(MediaStore.Files.FileColumns.DATA));
                int fileLength = cursor.getInt(cursor.getColumnIndex(MediaStore.Files.FileColumns.SIZE));
                //这里添加自己想要进行的操作可以了
                if(fileLength >= SCAN_FILE_SIZE_LIMIT){
                    mTxtFileNames.add(title);//添加名字
                    mTxtFilePaths.add(path);//添加路径
                }
            }
        }

        int size =  mTxtFileNames.size();
        long stopTime = System.currentTimeMillis();
        Log.e("scan","stopTime:" + stopTime);
        Log.e("scan","duration:" + (stopTime - startTime) + "   size:" + size);
        for(int i=0;i<size ;i++){
            Log.e("scan","" + mTxtFileNames.get(i));
        }

        size = mTxtFilePaths.size();
        for(int i=0;i<size;i++){
            Log.e("scan","" + mTxtFilePaths.get(i));
        }
    }


    //使用jar包中的方法 来循环遍历子文件夹中的文件 //跟子线程中遍历文件夹的时间一样 秒级别，安卓中查询文件的 时间是十毫秒级别
    private void doTestScan(){
    }
}
