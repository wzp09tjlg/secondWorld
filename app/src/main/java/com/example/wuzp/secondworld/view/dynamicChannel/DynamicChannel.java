package com.example.wuzp.secondworld.view.dynamicChannel;

import android.content.Context;
import android.content.pm.ApplicationInfo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * Created by wuzp on 2017/6/6.
 * 动态的去修改 meta 中的文件夹，然后使用三方渠道统计 动态设置各个渠道号(针对于友盟)
 */
public class DynamicChannel {

    public static final String CHNANNEL_NAME = "Channel_";

    public static void addUmengChannel(String filepath, String channel) {
        String channel_title = "Channel_";
        if(filepath.substring(filepath.lastIndexOf(".") + 1).toLowerCase().equals("apk")) {
            String path2 = "";
            if(filepath.lastIndexOf(File.separator) >= 0) {
                path2 = filepath.substring(0, filepath.lastIndexOf(File.separator) + 1);//得到父路径
            }

            if(path2.length() != 0) {
                File s = new File(filepath);//原始的apk
                File t = new File(filepath.substring(0, filepath.lastIndexOf(".")) + "_" + channel + ".apk");//目标apk
                if(!t.exists()) {//不存在就创建
                    try {
                        t.createNewFile();
                    } catch (IOException var12) {
                        var12.printStackTrace();
                    }
                }

                fileCopy(s, t);//拷贝原始apk到目标apk
                File addFile = new File(path2 + channel_title + channel);//需要添加的渠道文件
                if(!addFile.exists()) {
                    try {
                        addFile.createNewFile();
                    } catch (IOException var11) {
                        var11.printStackTrace();
                    }
                }

                try {
                    addFileToExistingZip(t, addFile);//将新加的渠道文件添加到目标apk文件中
                    addFile.delete();
                } catch (IOException var10) {
                    var10.printStackTrace();
                }

            }
        }
    }


    public static void addFileToExistingZip(File zipFile, File file) throws IOException {
        File tempFile = File.createTempFile(zipFile.getName(), (String)null);
        tempFile.delete();
        boolean renameOk = zipFile.renameTo(tempFile);//拷贝
        if(!renameOk) {
            throw new RuntimeException("could not rename the file " + zipFile.getAbsolutePath() + " to " + tempFile.getAbsolutePath());
        } else {
            byte[] buf = new byte[1024];
            ZipInputStream zin = new ZipInputStream(new FileInputStream(tempFile));
            ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipFile));

            for(ZipEntry entry = zin.getNextEntry(); entry != null; entry = zin.getNextEntry()) {
                String in = entry.getName();
                if(in.contains("umengchannel")) {//如果有重复的就不复制回去了！
                    continue;
                }

                out.putNextEntry(new ZipEntry(in));

                int len1;
                while((len1 = zin.read(buf)) > 0) {
                    out.write(buf, 0, len1);
                }
            }

            zin.close();
            FileInputStream in1 = new FileInputStream(file);
            out.putNextEntry(new ZipEntry("META-INF/" + file.getName()));//创建对应的渠道文件

            int len2;
            while((len2 = in1.read(buf)) > 0) {
                out.write(buf, 0, len2);
            }

            out.closeEntry();
            in1.close();
            out.close();
            tempFile.delete();
        }
    }
    public static String getChannel(Context context) {
        ApplicationInfo appinfo = context.getApplicationInfo();
        String sourceDir = appinfo.sourceDir;
        String ret = "";
        ZipFile zipfile = null;
        try {
            zipfile = new ZipFile(sourceDir);
            Enumeration<?> entries = zipfile.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = ((ZipEntry) entries.nextElement());
                String entryName = entry.getName();
                //这里需要替换成你的那个key
                if (entryName.startsWith(CHNANNEL_NAME)) {
                    ret = entryName;
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (zipfile != null) {
                try {
                    zipfile.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        String[] split = ret.split("_");
        if (split != null && split.length >= 2) {
            return ret.substring(split[0].length() + 1);

        } else {
            return "";
        }
    }

    public static void fileCopy(File source, File dest) {
            InputStream is = null;
            FileOutputStream fos = null;
            try {
                is = new FileInputStream(source);
                fos = new FileOutputStream(dest);
                byte[] buffer = new byte[1024];
                int size = 0;
                while ((size = is.read(buffer, 0, 1024)) >= 0) {
                    fos.write(buffer, 0, size);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (fos != null) {
                    try {
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    if (is != null) {
                        is.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
    }
}
