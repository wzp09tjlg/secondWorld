package com.example.wuzp.secondworld.view.tts;


import com.apkfuns.logutils.LogUtils;
import com.baidu.tts.client.SpeechError;
import com.baidu.tts.client.SpeechSynthesizerListener;

/**
 * Created by wuzp on 2017/5/9.
 * 实现百度语音的设置接口
 */
public class HSpeechSynthesizerListener implements SpeechSynthesizerListener {

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
        //mHandler.sendMessage(mHandler.obtainMessage(UI_CHANGE_SYNTHES_TEXT_SELECTION, progress, 0));
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
       // mHandler.sendMessage(mHandler.obtainMessage(UI_CHANGE_INPUT_TEXT_SELECTION, progress, 0));
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
