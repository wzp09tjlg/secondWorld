package com.example.wuzp.secondworld.view.retrofit.rxjava2;

import com.apkfuns.logutils.LogUtils;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/**
 * Created by wuzp on 2017/3/19.
 * RxJava的认识
 */
public class RxJavaUtil {
    //上游 被观察者(创建一种订阅者与被订阅者的关系)
    Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
        @Override
        public void subscribe(ObservableEmitter e) throws Exception {
            LogUtils.e("observable");
            LogUtils.e("next:1");
            e.onNext("1");
            LogUtils.e("complete");
            e.onComplete();
            //e.onError();
            //一个订阅者可以发射很多的next 但是只能发射一个onComplete 或者onError
        }
    });
    //下游 观察者
    Observer<String> observer = new Observer<String>() {
        @Override
        public void onSubscribe(Disposable d) {
         LogUtils.e("observer: onSubscribe");
        }

        @Override
        public void onNext(String value) {
           LogUtils.e("next :value:" + value);
        }

        @Override
        public void onError(Throwable e) {
          LogUtils.e("error: e.msg:" + e.getMessage());
        }

        @Override
        public void onComplete() {
          LogUtils.e("complete");
        }
    };

    public void getSomeRelation(){
        //观察者和被观察者之间的绑定关系
        LogUtils.e(" observable get subscribe observer");
        observable.subscribe(observer);
    }

    //在rxjava中连起来写就是这样
    public void doSomeMethod2(){
        LogUtils.e("doSomeMethod2");
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                LogUtils.e("subscribe");
                e.onNext("hello rxjava");
                e.onComplete();
            }
        }).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
               LogUtils.e("onSubscribe d:" + d.toString());
            }

            @Override
            public void onNext(String value) {
             LogUtils.e("NEXT: VALUE:" + value);
            }

            @Override
            public void onError(Throwable e) {
             LogUtils.e("error:" + e.getMessage());
            }

            @Override
            public void onComplete() {
             LogUtils.e("complete");
            }
        });

        LogUtils.e("get banding a relationship");
    }

    public void doSomeTest3(){
        //解释两个概念
        //ObservableEmitter 发射器
        //发射事件是按照一定的规则去发射的 next 可以发射很多个 但是只能发射一个complete 或者error ,
        // 发射complete 或者error之后 接受者就不能接收到消息 但是发射者还是可以继续发射消息

        //Disposable 一次性消费
        //当调用dispose() 时就能解除观察者和被观察者之间的绑定关系
        //达到解除

        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
              LogUtils.e("emitter1");
                e.onNext("1");
              LogUtils.e("emitter2");
                e.onNext("2");
                LogUtils.e("emitter3");
                e.onNext("3");
              LogUtils.e("emitter4");
                e.onNext("4");
              LogUtils.e("emitter5");
                e.onComplete();
              LogUtils.e("emitter6");
                e.onComplete();
             //   Error error = new Error();
              //  e.onError(error);
            }
        }).subscribe(new Observer<String>() {
            Disposable disposable = null;
            int i = 0;

            @Override
            public void onSubscribe(Disposable d) {
              LogUtils.e("onSubscribe d:" + d.isDisposed());
                disposable = d;
            }

            @Override
            public void onNext(String value) {
               LogUtils.e("next value:" + value);
                i++;
                if(i == 2){
                    disposable.dispose();//这里解除绑定关系
                }
            }

            @Override
            public void onError(Throwable e) {
                LogUtils.e("error: e.msg:" + e.getMessage());
            }

            @Override
            public void onComplete() {
              LogUtils.e("complete");
            }
        });
    }

    public void doSomeTest4(){
        //观察者只是关心上游发射数据 自己不接受
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                LogUtils.e("subscribe1");
                e.onNext("1");
                LogUtils.e("subscribe2");
                e.onNext("2");
                LogUtils.e("subscribe3");
                e.onNext("3");
                LogUtils.e("subscribe4");
                e.onNext("4");
                LogUtils.e("subscribe5");
                e.onNext("5");
                LogUtils.e("subscribe6");
                e.onNext("6");
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                LogUtils.e("s:" + s);
            }
        });
    }

    Disposable dis = null;
    public void doSomeTest5(){
        //如果观察者是Observer 那么就不会返回Disposable
        //如果观察者是Consumer 那么就会返回Disposable 一次消费，能够解除观察者对被观察者的绑定

        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                e.onNext("11");
            }
        }).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                 dis = d;
            }

            @Override
            public void onNext(String value) {
              LogUtils.e("value:" + value);
            }

            @Override
            public void onError(Throwable e) {
              LogUtils.e("e.msg:" + e.getMessage());
            }

            @Override
            public void onComplete() {
              LogUtils.e("complete");
            }
        });

        LogUtils.e("------------------------------------------------");

        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                LogUtils.e("emitter");
                e.onNext("one");
            }
        }).subscribe(new Consumer<String>() { //这里可以传三个参数 第一个是next 第二个是error 第三个是complete
            @Override
            public void accept(String s) throws Exception {
                LogUtils.e("s:" + s);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                LogUtils.e("e:" + throwable.getMessage());
            }
        }, new Action() { //consumer 是带参数的方法 但是Action是不带参数的方法
            @Override
            public void run() throws Exception {
                LogUtils.e("complete");
            }
        });
    }
}
