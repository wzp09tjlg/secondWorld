package com.example.wuzp.secondworld.view.rx;

import com.apkfuns.logutils.LogUtils;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by wuzp on 2017/5/7.
 * 简单的学习RxJava的常用操作符
 */
public class RxBean {
    // 一些基本的知识,关于RxJava的概念 及操作符
    //关于Flowable的创建 create 是直接方式创建的一种(你创建一种被观察者,你需要说明这个被观察者的订阅的规律,不然别人都已经订阅了你再说规律,是不是有悖于常识)，
    // 下边还存在其他的创建被观察者的方式
    // just 貌似只是常用数据类型
    // fromIterable 参数是迭代器的 数据集合
    // defer
    // interval
    // range
    // timer
    // repeat

    //被订阅者 和 订阅者
    //在RxJava2.0 中存在两种订阅者
    //Observable 被订阅者 (不支持背压)
    // Observer  订阅者   (不支持背压)
    // subscribe 被订阅者和订阅者 之间的订阅关系
    // Flowable 被订阅者  (支持背压)
    // Subscriber 订阅者  (支持背压)

    //常见的其他操作符
    // map
    // flatmap
    // filter
    // take
    // doOnNext

    //Scheduler 执行线程
    // immediate 直接在当前线程运行
    // newThread 总是启用新线程，并在新线程执行操作
    // io        行为模式和 newThread() 差不多，区别在于 io() 的内部实现是用一个无数量上限的线程池，可以重用空闲的线程，因此多数情况下 io() 比 newThread() 更有效率。不要把计算工作放在 io() 中，可以避免创建不必要的线程
    // computation 这个计算指的是 CPU 密集型计算，即不会被 I/O 等操作限制性能的操作
    //  AndroidSchedulers.mainThread() 它指定的操作将在 Android 主线程运行
    //  subscribeOn(): 指定Observable(被观察者)所在的线程，或者叫做事件产生的线程。
    //  observeOn(): 指定 Observer(观察者)所运行在的线程，或者叫做事件消费的线程。

    //Disposable, 这个单词的字面意思是一次性用品,用完即可丢弃的。
    // 在RxJava中,用它来切断Observer(观察者)与Observable(被观察者)之间的连接，
    // 当调用它的dispose()方法时,
    // 它就会将Observer(观察者)与Observable(被观察者)之间的连接切断,
    // 从而导致Observer(观察者)收不到事件。

    //这里简单说下支持被压的缓存策略
    //Flowable 是存在默认缓存，缓存个数是128个在事件个数，当上游的事件发送过于频繁时 超过了缓存个数，就得根据
    //缓存策略来处理事件
    // MISSING 丢失,超过的事件直接丢失
    // LATEST 保存最新的事件,丢失之前老的事件
    // ERROR  报错,当缓存的个数超过缓存池的个数 就直接报错
    // BUFFER 缓存，直到内存oom
    // DROP   丢弃 (和Missing的区别 暂时不知道，待查验之后再这里补充说明)

    /**
     * Flowable
     */
    public void testCreate() {
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add("wuzp no:" + i);
        }
        Flowable<String> flowable = Flowable.create(new FlowableOnSubscribe<String>() {
            @Override
            public void subscribe(FlowableEmitter<String> e) throws Exception {
                for (int i = 0; i < 100; i++) {
                    e.onNext("no:" + i);
                    if (i % 10 == 0)
                        e.onComplete();
                }
            }
        }, BackpressureStrategy.MISSING.LATEST.ERROR.BUFFER.DROP);

        final Subscriber<Integer> subscriber = new Subscriber<Integer>() {
            @Override
            public void onSubscribe(Subscription s) {
                s.request(Long.MAX_VALUE);
            }

            @Override
            public void onNext(Integer integer) {
                LogUtils.e("integer:" + integer);
            }

            @Override
            public void onError(Throwable t) {
                LogUtils.e("error:" + t.getMessage());
            }

            @Override
            public void onComplete() {
                LogUtils.e("com");
            }
        };

        //Flowable 创建  create just map  take doOnNext filter (直接可使用Flowable 创建被观察者的操作符是just fromArray defer)
        //使用just 来创建被观察者 然后与观察者进行订阅 使用map 是为了转换数据，将数据可以转换成你任意希望的数据
        // subscrible 是为了绑定 被观察者和观察者 是一种协议的绑定
        Flowable.just("wzp").map(new Function<String, Publisher<Integer>>() {
            @Override
            public Publisher<Integer> apply(String s) throws Exception {
                return Flowable.just(s.length());
            }
        }).subscribe(new Consumer<Publisher<Integer>>() {
            @Override
            public void accept(Publisher<Integer> integerPublisher) throws Exception {
                integerPublisher.subscribe(subscriber);//这是被观察者   subscribe是订阅观察者和被观察者的订阅关系
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                LogUtils.e("wuzp" + throwable.getMessage());
            }
        });

        //使用FromArray操作符是创建一个被观察者使用map是为了转换数据类型
        Flowable
                .fromArray(list)
                .map(new Function<ArrayList<String>, String>() {
                    @Override
                    public String apply(ArrayList<String> strings) throws Exception {
                        StringBuilder tempStr = new StringBuilder();
                        for (String str : strings)
                            tempStr = tempStr.append(str);
                        return tempStr.toString();
                    }
                })
                .observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        LogUtils.e("next:" + s);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        LogUtils.e("error:" + throwable.getMessage());
                    }
                });

        // 使用defer 来创建Flowable 被观察者 然后指定执行的线程，最后再绑定观察者
        Flowable.defer(new Callable<Publisher<String>>() {
            @Override
            public Publisher<String> call() throws Exception {
                return Flowable.just("wzp");
            }
        }).observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        LogUtils.e("next:" + s);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        LogUtils.e("error:" + throwable.getMessage());
                    }
                });

        Flowable<String> flowableString = Flowable.create(new FlowableOnSubscribe<String>() {
            @Override
            public void subscribe(FlowableEmitter<String> e) throws Exception {
                e.onNext("test");
                e.onComplete();
            }
        }, BackpressureStrategy.LATEST);

        flowableString.
                take(1, TimeUnit.SECONDS)
                .observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        LogUtils.e("next:" + s);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        LogUtils.e("error:" + throwable.getMessage());
                    }
                });

        //测试使用just 来创建被观察者 然后使用切换线程的操作符 最后将被观察者绑定观察者
        Flowable.just("wzp").observeOn(Schedulers.io()).subscribeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {

            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {

            }
        });

        //使用fromIterable 来创建被观察者 然后使用切换线程的操作符 之后和观察者进行绑定
        Flowable.fromIterable(list).observeOn(Schedulers.io()).subscribeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {

            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {

            }
        });

        //使用defer 来创建被观察者 然后使用切换线程的操作符 之后与观察者进行绑定
        Flowable.defer(new Callable<Publisher<String>>() {
            @Override
            public Publisher<String> call() throws Exception {
                return Flowable.just("wzp");
            }
        }).observeOn(Schedulers.io()).subscribeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {

            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {

            }
        });

        //使用interval 创建被观察者 然后切换线程,之后与观察者进行绑定
        Flowable.interval(1, TimeUnit.SECONDS).observeOn(Schedulers.io()).subscribeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Long>() {
            @Override
            public void accept(Long aLong) throws Exception {

            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {

            }
        });

        //使用range  创建被观察者 然后切换线程，让被观察的动作在子线程中执行，观察者的动作在主线程中执行 之后与观察者进行绑定
        Flowable.range(1, 100).observeOn(Schedulers.io()).subscribeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {

            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {

            }
        });

        //使用timer 创建被观察者 然后切换线程 让被观察者的动作在子线程中执行 观察者的动作在主线程中执行，之后与观察者进行绑定
        Flowable.timer(2, TimeUnit.SECONDS).observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });


        //使用range 创建被观察者 然后切换线程，让被观察者在子线程中执行，观察者在主线程中执行。之后切换线程
        Flowable.range(1, 100).repeat(10).observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });

        //其他RxJava的操作符 map 操作符是可以将数据进行转换
        Flowable.just("wzp").map(new Function<String, Integer>() {
            @Override
            public Integer apply(String s) throws Exception {
                return s.hashCode();
            }
        }).observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });

        Flowable.fromIterable(list).map(new Function<String, Publisher<String>>() {
            @Override
            public Publisher<String> apply(String s) throws Exception {
                return Flowable.just(s);
            }
        }).observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Publisher<String>>() {
                    @Override
                    public void accept(Publisher<String> stringPublisher) throws Exception {
                        stringPublisher.subscribe(new Subscriber<String>() { //Publisher 这里是新的观察者
                            @Override
                            public void onSubscribe(Subscription s) {
                             s.request(Long.MAX_VALUE);
                            }

                            @Override
                            public void onNext(String s) {
                               LogUtils.e("next:" + s);
                            }

                            @Override
                            public void onError(Throwable t) {
                                 LogUtils.e("error:" + t.getMessage());
                            }

                            @Override
                            public void onComplete() {
                                   LogUtils.e("complete");
                            }
                        });
                       LogUtils.e("next" );
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                         LogUtils.e("error:" + throwable.getMessage());
                    }
                });

        //flatmap 的数据转换 将数据再转换成被观察者
        Flowable.fromIterable(list).flatMap(new Function<String, Publisher<String>>() {
            @Override
            public Publisher<String> apply(String s) throws Exception {
                return Flowable.just(s);
            }
        }).observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                       LogUtils.e("next:" + s);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                       LogUtils.e("error:" + throwable.getMessage());
                    }
                });

        //使用filter 来处理判断条件，但是如果我是一个list<String> 集合，应该怎么处理呢
        Flowable.range(10,20).filter(new Predicate<Integer>() {
            @Override
            public boolean test(Integer integer) throws Exception {
                return integer % 2 == 0;
            }
        }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                LogUtils.e("next:" + integer);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                LogUtils.e("error:" + throwable.getMessage());
            }
        });

        Flowable.just(list).take(2,TimeUnit.SECONDS).subscribe(new Consumer<ArrayList<String>>() {
            @Override
            public void accept(ArrayList<String> strings) throws Exception {
                final StringBuilder builder = new StringBuilder();
                //当前不支持lamada的表达式
                for(String str:strings)
                    builder.append(str);
                  LogUtils.e("list:" + builder.toString());
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                 LogUtils.e("error:" + throwable.getMessage());
            }
        });

        //号称是 在onNext之前执行.可以改变数据流中的某些数据
        Flowable.just("wpz").doOnNext(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                 LogUtils.e("next:" + s);
                 s = "wuzpzpzppz";
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
              LogUtils.e("next:" + s);
            }
        });
    }

    /** Observable*/
}
