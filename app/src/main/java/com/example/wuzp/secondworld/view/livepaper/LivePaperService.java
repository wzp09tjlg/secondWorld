package com.example.wuzp.secondworld.view.livepaper;

import android.app.WallpaperManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.service.wallpaper.WallpaperService;
import android.util.Log;
import android.view.SurfaceHolder;

import java.io.IOException;

public class LivePaperService extends WallpaperService {
    public static final String VIDEO_PARAMS_CONTROL_ACTION = "com.zhy.livewallpaper";
    public static final String KEY_ACTION = "action";
    public static final int ACTION_VOICE_SILENCE = 110;
    public static final int ACTION_VOICE_NORMAL = 111;

    public Engine onCreateEngine() {
        return new VideoEngine();
    }

    public static void voiceSilence(Context context) {
        Intent intent = new Intent(LivePaperService.VIDEO_PARAMS_CONTROL_ACTION);
        intent.putExtra(LivePaperService.KEY_ACTION, LivePaperService.ACTION_VOICE_SILENCE);
        context.sendBroadcast(intent);
    }

    public static void voiceNormal(Context context) {
        Intent intent = new Intent(LivePaperService.VIDEO_PARAMS_CONTROL_ACTION);
        intent.putExtra(LivePaperService.KEY_ACTION, LivePaperService.ACTION_VOICE_NORMAL);
        context.sendBroadcast(intent);
    }

    public static void setToWallPaper(Context context) {
        final Intent intent = new Intent(WallpaperManager.ACTION_CHANGE_LIVE_WALLPAPER);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(WallpaperManager.EXTRA_LIVE_WALLPAPER_COMPONENT,
                new ComponentName(context, LivePaperService.class));
        try{
            context.startActivity(intent);
        }catch (Exception e){
            Log.e("wzp","msg:" + e.getMessage());
        }
    }

    class VideoEngine extends Engine {

        private MediaPlayer mMediaPlayer;

        private BroadcastReceiver mVideoParamsControlReceiver;

        @Override
        public void onCreate(SurfaceHolder surfaceHolder) {
            super.onCreate(surfaceHolder);
            Log.d("wzp","VideoEngine#onCreate");

            IntentFilter intentFilter = new IntentFilter(VIDEO_PARAMS_CONTROL_ACTION);
            registerReceiver(mVideoParamsControlReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    Log.d("wzp","onReceive");
                    int action = intent.getIntExtra(KEY_ACTION, -1);

                    switch (action) {
                        case ACTION_VOICE_NORMAL:
                            mMediaPlayer.setVolume(1.0f, 1.0f);
                            break;
                        case ACTION_VOICE_SILENCE:
                            mMediaPlayer.setVolume(0, 0);
                            break;
                    }
                }
            }, intentFilter);
        }

        @Override
        public void onDestroy() {
            Log.d("wzp","VideoEngine#onDestroy");
            unregisterReceiver(mVideoParamsControlReceiver);
            super.onDestroy();
        }

        @Override
        public void onVisibilityChanged(boolean visible) {
            Log.d("wzp","VideoEngine#onVisibilityChanged visible = " + visible);
            if (visible) {
                mMediaPlayer.start();
            } else {
                mMediaPlayer.pause();
            }
        }

        @Override
        public void onSurfaceCreated(SurfaceHolder holder) {
            Log.d("wzp","VideoEngine#onSurfaceCreated ");
            super.onSurfaceCreated(holder);
            mMediaPlayer = new MediaPlayer();
            mMediaPlayer.setSurface(holder.getSurface());
            try {
                AssetManager assetMg = getApplicationContext().getAssets();
                AssetFileDescriptor fileDescriptor = assetMg.openFd("test1.mp4");
                mMediaPlayer.setDataSource(fileDescriptor.getFileDescriptor(),
                        fileDescriptor.getStartOffset(), fileDescriptor.getLength());
                mMediaPlayer.setLooping(true);
                mMediaPlayer.setVolume(0, 0);
                mMediaPlayer.prepare();
                mMediaPlayer.start();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onSurfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            Log.d("wzp","VideoEngine#onSurfaceChanged ");
            super.onSurfaceChanged(holder, format, width, height);
        }

        @Override
        public void onSurfaceDestroyed(SurfaceHolder holder) {
            Log.d("wzp","VideoEngine#onSurfaceDestroyed ");
            super.onSurfaceDestroyed(holder);
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }
}