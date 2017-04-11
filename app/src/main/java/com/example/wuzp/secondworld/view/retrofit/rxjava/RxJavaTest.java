package com.example.wuzp.secondworld.view.retrofit.rxjava;

import com.apkfuns.logutils.LogUtils;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;

/**
 * Created by wuzp on 2017/3/21.
  检测rxjava2的基本属性和使用
 */
public class RxJavaTest {

    //使用flowable 之后需要制定被压的策略
    public void testOne(){
       Flowable<String> flowable = Flowable.create(new FlowableOnSubscribe<String>() {
           @Override
           public void subscribe(FlowableEmitter<String> e) throws Exception {
                 e.onNext("hello world");
               e.onComplete();
           }
       }, BackpressureStrategy.BUFFER);
        //Rxjava2.0中的观察者的实现
        Subscriber<String> stringSubscriber = new Subscriber<String>() {
            @Override
            public void onSubscribe(Subscription s) {
                LogUtils.e("onSubscrible");
                //这个方法是在绑定时就会调用
                s.request(Long.MAX_VALUE);//这里需要申请request 的是多少数据
            }

            @Override
            public void onNext(String s) {
              //发射之后调用的方法
                LogUtils.e("onNext: " + s);
            }

            @Override
            public void onError(Throwable t) {
               //出现错误时调用的方法
                LogUtils.e("onError");
            }

            @Override
            public void onComplete() {
                LogUtils.e("complete");
                //调用完成之后 调用(这种调用不是必须的)
            }
        };

        //这样就完成了观察者和被观察者之间的调用
        Flowable.just("1").subscribe(stringSubscriber);
        //在RxJava2.0 中flowable 类似于Observable 被观察者
        //上边的这句话就是表示一个绑定
    }

    public void testTwo(){
        //更简洁的rxjava的实现方式
        Flowable<String> flowable = Flowable.just("hello world");
        //就能实现一个观察者和一个被观察者之间的绑定
        //最后输出这句话

        //对于观察者 只是关心next的方法的调用 所以一般简介的观察者都会写成这样的方式
        Consumer<String> consumer = new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                LogUtils.e("consumer s:" + s);
            }
        };

        //简单的订阅是这样实现的
        flowable.subscribe(consumer);

        //当然如果是直接实现 更简洁的方式如下
        Flowable.just("hello world").subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                LogUtils.e("consumer s:" + s);
            }
        });
    }

    //rxjava2 to map
    public void testForMap(){
        //map的操作是是实现对操作过程中对数据的转换的这样一种方式
        //map 是对转换过程中 数据的转换，但是在操作中 还是需要针对设置观察者，所以在最后的时候 设置了观察者consumer
        Flowable.just("hello world").map(new Function<String, String>() {
            @Override
            public String apply(String s) throws Exception {
                return "s:" + s +"  is the One,and OK";
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                LogUtils.e("consumer s:" + s);
            }
        });

        // map的多次操作
        Flowable.just("sdas","dsajkdss","32iooodwoe")
                .map(new Function<String, String>() {
                    @Override
                    public String apply(String s) throws Exception {
                        return "s:" + s + "  the lenght is:";
                    }
                }).map(new Function<String, Integer>() {
            @Override
            public Integer apply(String s) throws Exception {
                return (Integer)s.length();
            }
        }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                LogUtils.e("consumer :" + integer);
            }
        });
    }

    public void testForMap2(){
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        list.add("5");

        Flowable.just(list).subscribe(new Consumer<List<String>>() {
            @Override
            public void accept(List<String> strings) throws Exception {
                for(String str:strings)
                    LogUtils.e("str:" + str);
            }
        });

        //上边的这种方式 在consumer中 会有循环，所以这里不是很方便
        Flowable.just(list).flatMap(new Function<List<String>, Publisher<String>>() {
            @Override
            public Publisher<String> apply(List<String> strings) throws Exception {
                return Flowable.fromIterable(strings);
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                     LogUtils.e("s:" + s);
            }
        });
        //上边的这种方式 就是针对map中存在list的改进 的确是比较方便
        //能够在consumer中很好的做到单个的控制

        Flowable.fromArray(1,2,34,5,6,7,8,9)
                //这里进行筛选 只有偶数才能进入观察者
                .filter(new Predicate<Integer>() {
                    @Override
                    public boolean test(Integer integer) throws Exception {
                        return integer % 2 == 0;
                    }
                })
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        LogUtils.e("integer:" + integer);
                    }
                });

        Flowable.fromArray(1,2,3,4,5,6,7,8,9,9,9,00,0)
                .take(3)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        LogUtils.e("integer:" + integer);
                        //这里实现了对数组中获取的个数的限制
                    }
                });

        Flowable.fromArray(1,2,3,4,5,5)
                .doOnNext(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        LogUtils.e("before consumer do something");
                    }
                }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                LogUtils.e("to do some really thing here integer:" + integer);
            }
        });
    }

    public void doSomeTestForRxJava(){
        //当然这里还有很多的东西是针对 rxjava对Android上的使用
        //明天再继续研究
    }
}
