package com.example.wuzp.secondworld.utils;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.view.Display;
import android.view.WindowManager;
import android.widget.ImageView;

import com.apkfuns.logutils.LogUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.wuzp.secondworld.HApplication;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;

/**
 * Created by wu on 2017/1/9.
 */
@SuppressWarnings("All")
public class AppUtil {
    /**
     * 获取程序的所有权限，用于6.0之上的动态获取
     *
     * @return 动态获取的所有权限
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static String[] getPermissions() {
        return new String[]{
                Manifest.permission.CAMERA,
                Manifest.permission.INTERNET,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE};

    }

    //获取应用的VersionCode
    public static int getVersionCode(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 1;
    }

    //获取应用的VersionName
    public static String getVersionName(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return "";
    }

    //获取应用的渠道名字
    public static String getChannelName(Context context) {
        String value = "HuaSheng";
        try {
            ApplicationInfo ainfo = context.getPackageManager()
                    .getApplicationInfo(context.getPackageName()
                            , PackageManager.GET_META_DATA);
            value = ainfo.metaData.getString("UMENG_CHANNEL");
        } catch (Exception e) {
        }
        return value;
    }

    //实现从dip到px的转换
    public static int dpToPx(Context context, int dps) {
        return Math.round(context.getResources().getDisplayMetrics().density * (float) dps);
    }

    public static int pxToDp(Context context, float px) {
        return Math.round(px / context.getResources().getDisplayMetrics().density);
    }

    public static Display screenSize() {
        WindowManager wm = (WindowManager) HApplication.getContext().getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay();
    }


    public static String imageZoom(String path, int degree) {
        //图片允许最大空间   单位：KB
        float maxSize = 500.00f;
        int maxWidth = 1080;

        File file = new File(path);
        if (file != null && file.exists()) {
            FileInputStream fis = null;
            try {
                fis = new FileInputStream(file);

                // 取得图片
                BitmapFactory.Options options = new BitmapFactory.Options();
                // 这个参数代表，不为bitmap分配内存空间，只记录一些该图片的信息（例如图片大小），
                // 说白了就是为了内存优化
                options.inJustDecodeBounds = true;
                // 通过创建图片的方式，取得options的内容（这里就是利用了java的地址传递来赋值）
                BitmapFactory.decodeStream(fis, null, options);
                fis.close();

                int srcWidth;
                if (degree == 90 || degree == 270) {
                    srcWidth = options.outHeight;
                } else {
                    srcWidth = options.outWidth;
                }


                // 先根据宽度缩放图片
                int i = 0;
                Bitmap scaleBitmap = null;
                while (true) {
                    // 这一步是根据要设置的大小，使宽和高都能满足
                    if (srcWidth >> i <= maxWidth) {
//                    if ((options.outWidth >> i <= maxWidth)
//                            && (options.outHeight >> i <= maxWidth)) {
                        // 重新取得流，注意：这里一定要再次加载，不能二次使用之前的流！
                        fis = new FileInputStream(file);

                        // 这个参数表示 新生成的图片为原始图片的几分之一。
                        options.inSampleSize = (int) Math.pow(2.0D, i);
                        // 这里之前设置为了true，所以要改为false，否则就创建不出图片
                        options.inJustDecodeBounds = false;
                        scaleBitmap = BitmapFactory.decodeStream(fis, null, options);
                        fis.close();
                        break;
                    }
                    i += 1;
                }

                // 旋转图片
                Bitmap descBitmap = null;
                if (degree > 0) {
                    descBitmap = rotateBitmap(scaleBitmap, degree);
                } else {
                    descBitmap = scaleBitmap;
                }
                if (scaleBitmap != null && scaleBitmap != descBitmap) {
                    scaleBitmap.recycle();
                }

                // JPG压缩图片
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                descBitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
                byte[] data = baos.toByteArray();

                //将字节换成KB
                double mid = data.length / 1024;
                if (mid > maxSize) {
                    double scale = mid / maxSize;
                    //开始压缩  此处用到平方根 将宽带和高度压缩掉对应的平方根倍
                    // （1.保持刻度和高度和原bitmap比率一致，压缩后也达到了最大大小占用空间的大小）
                    Bitmap newBitMap = zoomImage(descBitmap, descBitmap.getWidth() / Math.sqrt(scale),
                            descBitmap.getHeight() / Math.sqrt(scale));
                    if (descBitmap != null && descBitmap != newBitMap) {
                        descBitmap.recycle();
                    }

                    baos.close();
                    baos = new ByteArrayOutputStream();
                    newBitMap.compress(Bitmap.CompressFormat.JPEG, 80, baos);
                    data = baos.toByteArray();
                    if (newBitMap != null) {
                        newBitMap.recycle();
                    }
                } else {
                    if (descBitmap != null) {
                        descBitmap.recycle();
                    }
                }

                baos.close();
                if (data != null) {
                    String base64ImgStr = Base64.encodeToString(data, Base64.NO_WRAP);
                    return base64ImgStr;
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    //缓存图片转成base64
    public static String Url2Base64(Context context, String path) {
        try {
            byte[] bytes = Glide.with(context)
                    .load(path)
                    .asBitmap()
                    .toBytes(Bitmap.CompressFormat.JPEG, 90)//图片显示的质量
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .listener(new RequestListener<String, byte[]>() {
                        @Override
                        public boolean onException(Exception e, String model, Target<byte[]> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(byte[] resource, String model, Target<byte[]> target, boolean isFromMemoryCache, boolean isFirstResource) {
                            return false;
                        }
                    })
                    .into(1080, 1080)
                    .get();
            if (bytes != null) {
                String base64ImgStr = Base64.encodeToString(bytes, Base64.NO_WRAP);
                return base64ImgStr;
            }
        } catch (Exception e) {
        }
        return "";
    }

    public static Bitmap zoomImage(Bitmap bgimage, double newWidth,
                                   double newHeight) {
        // 获取这个图片的宽和高
        float width = bgimage.getWidth();
        float height = bgimage.getHeight();
        // 创建操作图片用的matrix对象
        Matrix matrix = new Matrix();
        // 计算宽高缩放率
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 缩放图片动作
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap bitmap = Bitmap.createBitmap(bgimage, 0, 0, (int) width,
                (int) height, matrix, true);
        return bitmap;
    }

    // 旋转图片
    public static Bitmap rotateBitmap(Bitmap bitmap, int rotate) {
        if (bitmap == null)
            return null;

        int w = bitmap.getWidth();
        int h = bitmap.getHeight();

        // Setting post rotate to 90
        Matrix mtx = new Matrix();
        mtx.postRotate(rotate);
        return Bitmap.createBitmap(bitmap, 0, 0, w, h, mtx, true);
    }

    // 由byte转换成bitmap
    public static Bitmap Str2Bitmap(ImageView imageView, Context context, String strData) {
        if (!TextUtils.isEmpty(strData)) {
            byte[] bitmapArray;
            Bitmap tempBitmap = null;
            Bitmap bitmap = null;
            try {

                //要按照比例缩小或放大
                bitmapArray = Base64.decode(strData, Base64.DEFAULT);
                tempBitmap = BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);

                int dwidth = tempBitmap.getWidth();
                int height = tempBitmap.getHeight();

                //屏幕的宽度
                WindowManager wm = ((Activity) context).getWindowManager();

                int vWidth = wm.getDefaultDisplay().getWidth();

                final float scale;

                int boaderPx = dip2px(context, 20);
                scale = ((float) (vWidth - boaderPx)) / (float) dwidth;
                Matrix matrix = new Matrix();
                matrix.postScale(scale, scale);

                /*float scaleWidth = 1f;//((float) newWidth) / width;
                float scaleHeight = 1f;//((float) newHeight) / height;*/

               /* Matrix matrix = new Matrix();
                matrix.postScale(scaleWidth, scaleHeight);*/
                imageView.setImageMatrix(matrix);
                bitmap = Bitmap.createBitmap(tempBitmap, 0, 0,
                        dwidth, height, matrix, true);
            } catch (Exception e) {
                LogUtils.e("加入连载,string转换bitmap异常.");
            } finally {
                return bitmap;
            }
        }
        return null;
    }

    public static int calculateInSampleSize(BitmapFactory.Options options,
                                            int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height
                    / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? widthRatio : heightRatio;
        }
        return inSampleSize;
    }


    public static void saveImage(Bitmap photo, String spath) {
        try {
            BufferedOutputStream bos = new BufferedOutputStream(
                    new FileOutputStream(spath, false));
            photo.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 读取图片旋转角度
    public static int readPictureDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    public static String getRealFilePath(Context context, final Uri uri) {
        if (null == uri) {
            return "";
        }

        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            /*Cursor cursor = context.getContentResolver().query(uri,
                    new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }*/
            data = getPath(context, uri);
        }
        return data;
    }


    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


    /**
     * Get a file path from a Uri. This will get the the path for Storage Access
     * Framework Documents, as well as the _data field for the MediaStore and
     * other file-based ContentProviders.
     *
     * @param context The context.
     * @param uri     The Uri to query.
     * @author paulburke
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }


    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context       The context.
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    private static long lastClickTime;

    /**
     * 防止快速点击
     */
    public synchronized static boolean isFastClick() {
        long time = System.currentTimeMillis();
        if (time - lastClickTime < 500) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

    /**
     * 获取一个全局的定时器，用于完成程序内的打点等工作
     *
     * @return 全局心跳
     */
    public static Flowable getGlobalTimer() {
        return Flowable.interval(3, TimeUnit.SECONDS).share();
    }

    /**
     * 获取状态栏的高度
     */
    public static int getStatusHeight(Context context) {
        int statusBarHeight = 0;
        //获取status_bar_height资源的ID
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            //根据资源ID获取响应的尺寸值
            statusBarHeight = context.getResources().getDimensionPixelSize(resourceId);
        }

        return statusBarHeight;
    }
}
