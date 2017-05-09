package com.example.wuzp.secondworld.view.tts;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.Layout;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.apkfuns.logutils.LogUtils;
import com.baidu.tts.auth.AuthInfo;
import com.baidu.tts.client.SpeechError;
import com.baidu.tts.client.SpeechSynthesizeBag;
import com.baidu.tts.client.SpeechSynthesizer;
import com.baidu.tts.client.SpeechSynthesizerListener;
import com.baidu.tts.client.SynthesizerTool;
import com.baidu.tts.client.TtsMode;
import com.example.wuzp.secondworld.R;
import com.example.wuzp.secondworld.databinding.ActivityTtsBinding;
import com.example.wuzp.secondworld.view.base.BindingActivity;

import java.util.ArrayList;
import java.util.List;

import static com.example.wuzp.secondworld.view.tts.FileUtil.makeDir;

/**
 * Created by wuzp on 2017/5/9.
 * 还是在使用MVP的简单模式
 * 集成百度语音的sdk
 */
public class TtsActivity extends BindingActivity<ActivityTtsBinding,TtsPresenter> implements TtsContract.IView,View.OnClickListener {
    private TtsViewWrapper viewWrapper = new TtsViewWrapper();


    private SpeechSynthesizer mSpeechSynthesizer;
    private String mSampleDirPath;
    private static final String SAMPLE_DIR_NAME = "baiduTTS";
    private static final String SPEECH_FEMALE_MODEL_NAME = "bd_etts_speech_female.dat";
    private static final String SPEECH_MALE_MODEL_NAME = "bd_etts_speech_male.dat";
    private static final String TEXT_MODEL_NAME = "bd_etts_text.dat";
    private static final String LICENSE_FILE_NAME = "temp_license";
    private static final String ENGLISH_SPEECH_FEMALE_MODEL_NAME = "bd_etts_speech_female_en.dat";
    private static final String ENGLISH_SPEECH_MALE_MODEL_NAME = "bd_etts_speech_male_en.dat";
    private static final String ENGLISH_TEXT_MODEL_NAME = "bd_etts_text_en.dat";

    private static final int PRINT = 0;
    private static final int UI_CHANGE_INPUT_TEXT_SELECTION = 1;
    private static final int UI_CHANGE_SYNTHES_TEXT_SELECTION = 2;
    private static final String TAG = "MainActivity";

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int what = msg.what;
            switch (what) {
                case PRINT:
                    print(msg);
                    break;
                case UI_CHANGE_INPUT_TEXT_SELECTION:
                    if (msg.arg1 <= binding.edtContent.getText().length()) {
                        binding.edtContent.setSelection(0,msg.arg1);
                    }
                    break;
                case UI_CHANGE_SYNTHES_TEXT_SELECTION:
                    SpannableString colorfulText = new SpannableString(binding.edtContent.getText().toString());
                    if (msg.arg1 <= colorfulText.toString().length()) {
                        colorfulText.setSpan(new ForegroundColorSpan(Color.GRAY), 0, msg.arg1, Spannable
                                .SPAN_EXCLUSIVE_EXCLUSIVE);
                        binding.edtContent.setText(colorfulText);
                    }
                    break;
                default:
                    break;
            }
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewWrapper.addBinding(binding);
        initialEnv();
        initialTts();
    }


    @Override
    protected TtsPresenter createPresenter() {
        return new TtsPresenter(this);
    }

    @Override
    protected int createLayoutId() {
        return R.layout.activity_tts;
    }

    private void initialEnv() {
        if (mSampleDirPath == null) {
            String sdcardPath = Environment.getExternalStorageDirectory().toString();
            mSampleDirPath = sdcardPath + "/" + SAMPLE_DIR_NAME;
        }
        makeDir(mSampleDirPath);
        FileUtil.copyFromAssetsToSdcard(this,false, "baiduyuyin/" + SPEECH_FEMALE_MODEL_NAME, mSampleDirPath + "/" + SPEECH_FEMALE_MODEL_NAME);
        FileUtil.copyFromAssetsToSdcard(this,false, "baiduyuyin/" +SPEECH_MALE_MODEL_NAME, mSampleDirPath + "/" + SPEECH_MALE_MODEL_NAME);
        FileUtil.copyFromAssetsToSdcard(this,false, "baiduyuyin/" + TEXT_MODEL_NAME, mSampleDirPath + "/" + TEXT_MODEL_NAME);
        FileUtil.copyFromAssetsToSdcard(this,false, "baiduyuyin/" + LICENSE_FILE_NAME, mSampleDirPath + "/" + LICENSE_FILE_NAME);
        FileUtil.copyFromAssetsToSdcard(this,false, "baiduyuyin/english/" + ENGLISH_SPEECH_FEMALE_MODEL_NAME, mSampleDirPath + "/"
                + ENGLISH_SPEECH_FEMALE_MODEL_NAME);
        FileUtil.copyFromAssetsToSdcard(this,false, "baiduyuyin/english/" + ENGLISH_SPEECH_MALE_MODEL_NAME, mSampleDirPath + "/"
                + ENGLISH_SPEECH_MALE_MODEL_NAME);
        FileUtil.copyFromAssetsToSdcard(this,false, "baiduyuyin/english/" + ENGLISH_TEXT_MODEL_NAME, mSampleDirPath + "/"
                + ENGLISH_TEXT_MODEL_NAME);
    }

    private void initialTts() {
        this.mSpeechSynthesizer = SpeechSynthesizer.getInstance();
        this.mSpeechSynthesizer.setContext(this);
        this.mSpeechSynthesizer.setSpeechSynthesizerListener(new HSpeechSynthesizerListener());
        // 文本模型文件路径 (离线引擎使用)
        this.mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_TTS_TEXT_MODEL_FILE, mSampleDirPath + "/"
                + TEXT_MODEL_NAME);
        // 声学模型文件路径 (离线引擎使用)
        this.mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_TTS_SPEECH_MODEL_FILE, mSampleDirPath + "/"
                + SPEECH_FEMALE_MODEL_NAME);
        // 本地授权文件路径,如未设置将使用默认路径.设置临时授权文件路径，LICENCE_FILE_NAME请替换成临时授权文件的实际路径，仅在使用临时license文件时需要进行设置，如果在[应用管理]中开通了正式离线授权，不需要设置该参数，建议将该行代码删除（离线引擎）
        // 如果合成结果出现临时授权文件将要到期的提示，说明使用了临时授权文件，请删除临时授权即可。
        this.mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_TTS_LICENCE_FILE, mSampleDirPath + "/"
                + LICENSE_FILE_NAME);
        // 请替换为语音开发者平台上注册应用得到的App ID (离线授权)
        this.mSpeechSynthesizer.setAppId("8535996"/*这里只是为了让Demo运行使用的APPID,请替换成自己的id。*/);
        // 请替换为语音开发者平台注册应用得到的apikey和secretkey (在线授权)
        this.mSpeechSynthesizer.setApiKey("MxPpf3nF5QX0pndKKhS7IXcB",
                "7226e84664474aa098296da5eb2aa434"/*这里只是为了让Demo正常运行使用APIKey,请替换成自己的APIKey*/);
        // 发音人（在线引擎），可用参数为0,1,2,3。。。（服务器端会动态增加，各值含义参考文档，以文档说明为准。0--普通女声，1--普通男声，2--特别男声，3--情感男声。。。）
        this.mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_SPEAKER, "0");
        // 设置Mix模式的合成策略
        this.mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_MIX_MODE, SpeechSynthesizer.MIX_MODE_DEFAULT);
        // 授权检测接口(只是通过AuthInfo进行检验授权是否成功。)
        // AuthInfo接口用于测试开发者是否成功申请了在线或者离线授权，如果测试授权成功了，可以删除AuthInfo部分的代码（该接口首次验证时比较耗时），不会影响正常使用（合成使用时SDK内部会自动验证授权）
        AuthInfo authInfo = this.mSpeechSynthesizer.auth(TtsMode.MIX);

        if (authInfo.isSuccess()) {
            toPrint("auth success");
        } else {
            String errorMsg = authInfo.getTtsError().getDetailMessage();
            toPrint("auth failed errorMsg=" + errorMsg);
        }

        // 初始化tts
        mSpeechSynthesizer.initTts(TtsMode.MIX);
        // 加载离线英文资源（提供离线英文合成功能）
        int result =
                mSpeechSynthesizer.loadEnglishModel(mSampleDirPath + "/" + ENGLISH_TEXT_MODEL_NAME, mSampleDirPath
                        + "/" + ENGLISH_SPEECH_FEMALE_MODEL_NAME);
        toPrint("loadEnglishModel result=" + result);

        //打印引擎信息和model基本信息
        printEngineInfo();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_speak:
                speak();
                break;
            case R.id.btn_pause:
                pause();
                break;
            case R.id.btn_resume:
                resume();
                break;
            case R.id.btn_stop:
                stop();
                break;
            case R.id.btn_synthesize:
                synthesize();
                break;
            case R.id.btn_play:
                break;
            case R.id.btn_batchSpeak:
                batchSpeak();
                break;
            case R.id.btn_nextActivity:
                nextActivity();
                break;
        }
    }

    private void nextActivity() {
       // startActivity(new Intent(this, ModelManagerActivity.class));
    }

    private void speak() {
        String text = binding.edtContent.getText().toString();
        //需要合成的文本text的长度不能超过1024个GBK字节。
        if (TextUtils.isEmpty(binding.edtContent.getText())) {
            text = "欢迎使用百度语音合成SDK,百度语音为你提供支持。";
            binding.edtContent.setText(text);
        }
        int result = this.mSpeechSynthesizer.speak(text);
        if (result < 0) {
            toPrint("error,please look up error code in doc or URL:http://yuyin.baidu.com/docs/tts/122 ");
        }
    }

    private void pause() {
        this.mSpeechSynthesizer.pause();
    }

    private void resume() {
        this.mSpeechSynthesizer.resume();
    }

    private void stop() {
        this.mSpeechSynthesizer.stop();
    }

    private void synthesize() {
        String text = binding.edtContent.getText().toString();
        //需要合成的文本text的长度不能超过1024个GBK字节。
        int result = this.mSpeechSynthesizer.synthesize(text);
        if (result < 0) {
            toPrint("error,please look up error code in doc or URL:http://yuyin.baidu.com/docs/tts/122 ");
        }
    }

    private void batchSpeak() {
        List<SpeechSynthesizeBag> bags = new ArrayList<SpeechSynthesizeBag>();
        bags.add(getSpeechSynthesizeBag("123456", "0"));
        bags.add(getSpeechSynthesizeBag("你好", "1"));
        bags.add(getSpeechSynthesizeBag("使用百度语音合成SDK", "2"));
        bags.add(getSpeechSynthesizeBag("hello", "3"));
        bags.add(getSpeechSynthesizeBag("这是一个demo工程", "4"));
        int result = this.mSpeechSynthesizer.batchSpeak(bags);
        if (result < 0) {
            toPrint("error,please look up error code in doc or URL:http://yuyin.baidu.com/docs/tts/122 ");
        }
    }

    private SpeechSynthesizeBag getSpeechSynthesizeBag(String text, String utteranceId) {
        SpeechSynthesizeBag speechSynthesizeBag = new SpeechSynthesizeBag();
        //需要合成的文本text的长度不能超过1024个GBK字节。
        speechSynthesizeBag.setText(text);
        speechSynthesizeBag.setUtteranceId(utteranceId);
        return speechSynthesizeBag;
    }

    private void toPrint(String str) {
        Message msg = Message.obtain();
        msg.obj = str;
        this.mHandler.sendMessage(msg);
    }

    private void print(Message msg) {
        String message = (String) msg.obj;
        if (message != null) {
            Log.w(TAG, message);
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            scrollLog(message);
        }
    }

    private void scrollLog(String message) {
        Spannable colorMessage = new SpannableString(message + "\n");
        colorMessage.setSpan(new ForegroundColorSpan(0xff0000ff), 0, message.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        binding.textShow.append(colorMessage);
        Layout layout =  binding.textShow.getLayout();
        if (layout != null) {
            int scrollAmount = layout.getLineTop( binding.textShow.getLineCount()) -  binding.textShow.getHeight();
            if (scrollAmount > 0) {
                binding.textShow.scrollTo(0, scrollAmount +  binding.textShow.getCompoundPaddingBottom());
            } else {
                binding.textShow.scrollTo(0, 0);
            }
        }
    }

    class HSpeechSynthesizerListener implements SpeechSynthesizerListener {

        @Override
        public void onSynthesizeStart(String s) {
            LogUtils.e("onSynthesizeStart utteranceId=" + s);
        }

        /**
         * 合成数据和进度的回调接口，分多次回调
         * @param utteranceId
         * @param data 合成的音频数据。该音频数据是采样率为16K，2字节精度，单声道的pcm数据。
         * @param progress 文本按字符划分的进度，比如:你好啊 进度是0-3
         */
        @Override
        public void onSynthesizeDataArrived(String utteranceId, byte[] data, int progress) {
            LogUtils.e("onSynthesizeDataArrived utteranceId=" + utteranceId + "   progress:" + progress);
            mHandler.sendMessage(mHandler.obtainMessage(UI_CHANGE_SYNTHES_TEXT_SELECTION, progress, 0));
        }

        /**
         * 合成正常结束，每句合成正常结束都会回调，如果过程中出错，则回调onError，不再回调此接口
         * @param utteranceId
         */
        @Override
        public void onSynthesizeFinish(String utteranceId) {
            LogUtils.e("onSynthesizeFinish utteranceId=" + utteranceId);
        }

        /**
         * 播放开始，每句播放开始都会回调
         *
         * @param utteranceId
         */
        @Override
        public void onSpeechStart(String utteranceId) {
            LogUtils.e("onSpeechStart utteranceId=" + utteranceId);
        }

        /**
         * 播放进度回调接口，分多次回调
         *
         * @param utteranceId
         * @param progress 文本按字符划分的进度，比如:你好啊 进度是0-3
         */
        @Override
        public void onSpeechProgressChanged(String utteranceId, int progress) {
            LogUtils.e("onSpeechProgressChanged  utteranceId:" + utteranceId  + "    progress:" + progress);
             mHandler.sendMessage(mHandler.obtainMessage(UI_CHANGE_INPUT_TEXT_SELECTION, progress, 0));
        }

        /**
         * 播放正常结束，每句播放正常结束都会回调，如果过程中出错，则回调onError,不再回调此接口
         *
         * @param utteranceId
         */
        @Override
        public void onSpeechFinish(String utteranceId) {
            LogUtils.e("onSpeechFinish utteranceId=" + utteranceId);
        }

        /**
         * 当合成或者播放过程中出错时回调此接口
         *
         * @param utteranceId
         * @param error 包含错误码和错误信息
         */
        @Override
        public void onError(String utteranceId, SpeechError error) {
            LogUtils.e("onError error=" + "(" + error.code + ")" + error.description + "--utteranceId=" + utteranceId);
        }
    }

    /**
     * 打印引擎so库版本号及基本信息和model文件的基本信息
     */
    private void printEngineInfo() {
        toPrint("EngineVersioin=" + SynthesizerTool.getEngineVersion());
        toPrint("EngineInfo=" + SynthesizerTool.getEngineInfo());
        String textModelInfo = SynthesizerTool.getModelInfo(mSampleDirPath + "/" + TEXT_MODEL_NAME);
        toPrint("textModelInfo=" + textModelInfo);
        String speechModelInfo = SynthesizerTool.getModelInfo(mSampleDirPath + "/" + SPEECH_FEMALE_MODEL_NAME);
        toPrint("speechModelInfo=" + speechModelInfo);
    }
}
