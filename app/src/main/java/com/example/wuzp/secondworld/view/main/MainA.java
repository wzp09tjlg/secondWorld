package com.example.wuzp.secondworld.view.main;

import com.apkfuns.logutils.LogUtils;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by wuzp on 2017/3/22.
 */

public class MainA {
    public static void main(String[] args)
    {
        Flowable f = Flowable.create(new FlowableOnSubscribe<String>() {
            @Override
            public void subscribe(FlowableEmitter<String> e) throws Exception {
                e.onNext("1");
                e.onNext("2");
                e.onNext("3");
            }
        }, BackpressureStrategy.BUFFER);

        f.skip(1).subscribe(new Consumer() {
                    @Override
                    public void accept(Object o) throws Exception {

                    }
                });
    }

    private void testOne(){
       Observable<String> observable =  Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                e.onNext("hello one");
                e.onComplete();
            }
        });

        Observer<String> observer = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String value) {
                LogUtils.e("value:" + value);
            }

            @Override
            public void onError(Throwable e) {
              LogUtils.e("e:" + e.getMessage());
            }

            @Override
            public void onComplete() {
                 LogUtils.e("complete");
            }
        };

        observable.subscribe(observer);
    }

    private void testTwo(){
        Flowable<String> flowable = Flowable.create(new FlowableOnSubscribe<String>() {
            @Override
            public void subscribe(FlowableEmitter<String> e) throws Exception {
                  e.onNext("hello two");
                e.onComplete();
            }
        },BackpressureStrategy.BUFFER);

        flowable.subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {

            }
        });
        flowable.subscribe(new Subscriber<String>() {
            @Override
            public void onSubscribe(Subscription s) {

            }

            @Override
            public void onNext(String s) {

            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onComplete() {

            }
        });
    }


}
