package com.example.wuzp.secondworld.view.base;

import android.util.Log;

import com.example.wuzp.secondworld.HApplication;
import com.example.wuzp.secondworld.network.ApiCallback;
import com.example.wuzp.secondworld.network.ApiStores;
import com.example.wuzp.secondworld.network.AppClient;
import com.example.wuzp.secondworld.network.parse.HttpBase;

import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

@SuppressWarnings("All")
public class BasePresenter<V> {

    private static final String TAG = BasePresenter.class.getSimpleName();

    protected V mvpView;
    protected ApiStores apiStores = new AppClient().create();
    private CompositeDisposable mCompositeDisposable;

    public BasePresenter(V view) {
        attachView(view);
    }

    public void attachView(V mvpView) {
        this.mvpView = mvpView;
        apiStores = AppClient.create();
    }

    public void detachView() {
        this.mvpView = null;
        onUnsubscribe();
    }

    //RXjava取消注册，以避免内存泄露
    protected final void onUnsubscribe() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();
        }
    }

    protected final void addSubscription(Disposable disposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(disposable);
    }

    protected final <T> void addSubscription(Flowable flowable, final ApiCallback<T> apiCallback) {
        addSubscription(flowable, apiCallback, 0);
    }

    protected final <T> void addSubscription(Flowable flowable, final ApiCallback<T> apiCallback, int delay) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        if (apiCallback == null) {
            Log.e(TAG, "callback can not be null");
            return;
        }

        Disposable disposable = flowable
                .delay(delay, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<HttpBase<T>>() {
                    @Override
                    public void accept(HttpBase<T> o) throws Exception {
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

    protected String getString(int id) {
        return HApplication.getContext().getResources().getString(id);
    }
}
