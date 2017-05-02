package com.example.wuzp.secondworld.view.base;

import android.app.Dialog;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.example.wuzp.secondworld.R;
import com.example.wuzp.secondworld.databinding.DialogProgressBinding;
import com.example.wuzp.secondworld.network.ApiCallback;
import com.example.wuzp.secondworld.network.ApiStores;
import com.example.wuzp.secondworld.network.AppClient;
import com.example.wuzp.secondworld.network.parse.HttpBase_j;
import com.example.wuzp.secondworld.utils.Night;
import com.example.wuzp.secondworld.view.widget.CommonDialog;
import com.umeng.analytics.MobclickAgent;
import com.umeng.analytics.MobclickAgent.EScenarioType;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * by tiny
 */
@SuppressWarnings("All")
public class BaseActivity extends FragmentActivity implements Night.NightModeChangeListener {
    private static final String TAG = BaseActivity.class.getSimpleName();

    public ApiStores apiStores = AppClient.create();
    private CompositeDisposable mCompositeDisposable;
    private Dialog mProgressDialog;
    protected onKeyDownListener mOnKeyDownListener = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Night.getInstance().addListener(this);
        Night.setWindowStatusBarColor(this);

        //以下的开关是打开测试模式的
        MobclickAgent.setDebugMode(false);
        // SDK在统计Fragment时，需要关闭Activity自带的页面统计，
        // 然后在每个页面中重新集成页面统计的代码(包括调用了 onResume 和 onPause 的Activity)。
        MobclickAgent.openActivityDurationTrack(false);
        MobclickAgent.setScenarioType(this, EScenarioType.E_UM_NORMAL);
    }

    @Override
    protected void onStart() {
        super.onStart();
        MobclickAgent.onPageStart(TAG);
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        MobclickAgent.onPageEnd(TAG);
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        onUnsubscribe();
        Night.getInstance().removeListener(this);
    }

    protected final <T> void addSubscription(Flowable flowable, final ApiCallback<T> apiCallback) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        if (apiCallback == null) {
            Log.e(TAG, "callback can not be null");
            return;
        }

        Disposable disposable = flowable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<HttpBase_j<T>>() {
                    @Override
                    public void accept(HttpBase_j<T> o) throws Exception {
                        apiCallback.onNext(o);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        apiCallback.onError(throwable);
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        apiCallback.onComplete();
                    }
                });
        mCompositeDisposable.add(disposable);
    }

    protected final void addSubscription(Disposable disposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(disposable);
    }

    protected final void onUnsubscribe() {
        if (mCompositeDisposable != null)
            mCompositeDisposable.clear();
    }

    public void addFragment(int containerViewId, Fragment fragment) {
        addFragment(containerViewId, fragment, false);
    }

    public void addFragment(int containerViewId, Fragment fragment, boolean addBackStack) {
        FragmentTransaction fragmentTransaction = null;
        fragmentTransaction = this.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(containerViewId, fragment, fragment.getClass().getSimpleName());
        if (addBackStack)
            fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    /**
     * 显示进度框
     *
     * @param resId      信息资源ID
     * @param cancelAble 是否可取消
     */
    public void showProgress(int resId, boolean cancelAble) {
        showProgress(getString(resId), cancelAble, null);
    }

    /**
     * 显示进度框
     *
     * @param message    信息
     * @param cancelAble 是否可取消
     */
    public void showProgress(String message, boolean cancelAble) {
        showProgress(message, cancelAble, null);
    }

    /**
     * 显示进度框
     *
     * @param resId      信息资源ID
     * @param cancelAble 是否可取消
     * @param listener   DialogInterface.OnKeyListener
     */
    public void showProgress(int resId, boolean cancelAble, DialogInterface.OnKeyListener listener) {
        showProgress(getString(resId), cancelAble, listener);
    }

    /**
     * 显示进度框
     *
     * @param message    信息
     * @param cancelAble 是否可取消
     * @param listener   DialogInterface.OnKeyListener
     */
    public void showProgress(String message, boolean cancelAble, DialogInterface.OnKeyListener listener) {
        if (mProgressDialog == null) {
            mProgressDialog = new CommonDialog(this);
            DialogProgressBinding viewBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.dialog_progress, null, false);
            if (!TextUtils.isEmpty(message)) {
                viewBinding.progressText.setText(message.trim());
            }
            mProgressDialog.setContentView(viewBinding.getRoot());
            mProgressDialog.setCancelable(cancelAble);
            mProgressDialog.setOnKeyListener(listener);
        }
        if (!mProgressDialog.isShowing()) {
            mProgressDialog.findViewById(R.id.root_view).setBackgroundResource(R.drawable.dialog_custom_bg);
            Night.setTextColor((TextView) mProgressDialog.findViewById(R.id.progress_text), "h3");
            try {
                mProgressDialog.show();
            } catch (Exception e) {
            }
        }
    }

    /**
     * 进度框是否正显示
     *
     * @return
     */
    protected boolean progressShowing() {
        return mProgressDialog == null && mProgressDialog.isShowing();
    }

    /**
     * 隐藏进度框
     */
    public void dismissProgress() {
        if (null != mProgressDialog && mProgressDialog.isShowing()) {
            try {
                mProgressDialog.dismiss();
                mProgressDialog = null;
            } catch (Exception e) {
            }
        }
    }

    @Override
    public void onNightChange() {
        Night.setWindowStatusBarColor(this);
    }

    public interface onKeyDownListener {
        void onKeyDown(int keyCode);
    }

    public void setonKeyDownListener(onKeyDownListener l) {
        mOnKeyDownListener = l;

    }
}
