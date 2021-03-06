package com.example.wuzp.secondworld.view.base;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.wuzp.secondworld.R;
import com.example.wuzp.secondworld.network.ApiCallback;
import com.example.wuzp.secondworld.network.parse.HttpBase_j;
import com.example.wuzp.secondworld.utils.Night;
import com.example.wuzp.secondworld.view.widget.CommonDialog;
import com.umeng.analytics.MobclickAgent;

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
public class BaseFragment extends Fragment implements
        Night.NightModeChangeListener {
    private static final String TAG = BaseFragment.class.getSimpleName();

    private CompositeDisposable mCompositeDisposable;
    private Dialog mProgressDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Night.getInstance().addListener(this);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(TAG);
        //MobclickAgent.onResume(getContext()); //这个处理应该放置在承载碎片的activity中
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(TAG);
        //MobclickAgent.onPause(getContext());//这个处理应该放置在承载碎片的activity中
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        onUnsubscribe();
        Night.getInstance().removeListener(this);
    }

    protected final void addSubscription(Disposable disposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(disposable);
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

    protected final void onUnsubscribe() {
        //取消注册，以避免内存泄露
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();
        }
    }


    /**
     * 显示进度框
     *
     * @param resId      信息资源ID
     * @param cancelAble 是否可取消
     */
    public void showProgress(int resId, boolean cancelAble) {
        showProgress(getString(resId), cancelAble, false, null);
    }

    /**
     * 显示进度框
     *
     * @param message    信息
     * @param cancelAble 是否可取消
     */
    public void showProgress(String message, boolean cancelAble) {
        showProgress(message, cancelAble, false, null);
    }

    /**
     * 显示进度框
     *
     * @param resId      信息资源ID
     * @param cancelAble 是否可取消
     * @param listener   DialogInterface.OnKeyListener
     */
    public void showProgress(int resId, boolean cancelAble, DialogInterface.OnKeyListener listener) {
        showProgress(getString(resId), cancelAble, false, listener);
    }

    public void showProgress(String message, boolean cancelAble, DialogInterface.OnKeyListener listener) {
        showProgress(message, cancelAble, false, listener);
    }

    /**
     * 显示进度框
     *
     * @param message    信息
     * @param cancelAble 是否可取消
     * @param listener   DialogInterface.OnKeyListener
     */
    public void showProgress(String message, boolean cancelAble, boolean cancelTouch, DialogInterface.OnKeyListener listener) {
        if (mProgressDialog == null) {
            mProgressDialog = new CommonDialog(getActivity());
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_progress, null);
            if (!TextUtils.isEmpty(message)) {
                ((TextView) view.findViewById(R.id.progress_text)).setText(message);
            }
            mProgressDialog.setContentView(view);
            mProgressDialog.setCancelable(cancelAble);
            mProgressDialog.setCanceledOnTouchOutside(cancelTouch);
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
    }
}