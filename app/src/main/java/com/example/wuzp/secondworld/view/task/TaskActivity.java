package com.example.wuzp.secondworld.view.task;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;

import com.example.wuzp.secondworld.R;
import com.example.wuzp.secondworld.databinding.ActivityTaskBinding;
import com.example.wuzp.secondworld.view.base.BindingActivity;

import java.util.Locale;

/**
 * Created by wuzp on 2017/4/27.
 */

public class TaskActivity extends BindingActivity<ActivityTaskBinding,TaskPresenter> implements
        TaskContract.IView, TextToSpeech.OnInitListener  //语音识别
{
    private TextToSpeech textToSpeech;
    private TaskViewWrapper viewWrapper = new TaskViewWrapper();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewWrapper.addBinding(binding);
        textToSpeech = new TextToSpeech(this,this);
        binding.btnSpeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              speakOut("this is a test sounds");
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(textToSpeech != null){
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
    }

    @Override
    protected TaskPresenter createPresenter() {
        return new TaskPresenter(this);
    }

    @Override
    protected int createLayoutId() {
        return R.layout.activity_task;
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {

            int result = textToSpeech.setLanguage(Locale.US);

            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "This Language is not supported");
            } else {
                binding.btnSpeak.setEnabled(true);
                speakOut("This Language is not supported");
            }

        } else {
            Log.e("TTS", "Initilization Failed!");
        }
    }

    private void speakOut(String text){
        textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }
}
