package com.example.wuzp.secondworld.utils.crashUtils;

import java.io.FilenameFilter;

/**
 * Created by wuzp on 2017/5/9.
 */

public class FileUtils {

    public static boolean hasSdcard(){
        if (android.os.Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED)) {
            return true;
        } else
            return false;
    }


    public static void delete(String path, FilenameFilter filter){

    }

    public static String getFileNameWithoutExtension(String filename){
       return "";
    }


}
