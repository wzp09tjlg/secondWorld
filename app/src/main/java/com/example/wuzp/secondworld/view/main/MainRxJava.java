package com.example.wuzp.secondworld.view.main;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.concurrent.TimeUnit;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by wuzp on 2017/3/23.
 * 1.简单的使用rxjava的操作符(在这里主要使用的是支持被压的Flowable，不支持被压的Observable方法是一样的)
 * 2.理解各个操作符的使用及demo的输出结果
 */
public class MainRxJava {


    public static void main(String args[]) {
        Runtime runtime = null;
        try{
            Process process = Runtime.getRuntime().exec("");
        }catch (Exception e){}


        //from
        //testFrom();

        //just
        //testJust();

        //interval
        //testInterval();

        //timer
        //testTimer();

        //range
        //testRange();

        //map
        //testMap();

        //flatmap
        testFlatMap();

//        int sum = ToolUtil.add(1,2);

    }

    private static void show(String msg) {
        System.out.println(msg);
    }

    private static void testFrom() {
        Flowable
                .fromArray("1", "2", "3", "4", "5", "6")
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        System.out.println("s:" + s);
                    }
                });
    }

    private static void testJust() {
        //观察者是Subscriber
        Flowable.just("hello", "world").subscribe(new Subscriber<String>() {
            @Override
            public void onSubscribe(Subscription s) {
                show("subscription before request");
                s.request(Long.MAX_VALUE);
                show("subscription after request");
            }

            @Override
            public void onNext(String s) {
                show("onNext: s:" + s);
            }

            @Override
            public void onError(Throwable t) {
                show("error: t.msg:" + t.getMessage());
            }

            @Override
            public void onComplete() {
                show("onComplete");
            }
        });

        //观察者是Consumer
        Flowable.just("hello", "world")
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        show("consumer:s:" + s);
                    }
                });
    }

    private static void testCreate() {
        Flowable<String> flowable = Flowable.create(new FlowableOnSubscribe<String>() {
            @Override
            public void subscribe(FlowableEmitter<String> e) throws Exception {
                e.onNext("hello");
                e.onComplete();
            }
        }, BackpressureStrategy.BUFFER);

        Subscriber<String> subscrible = new Subscriber<String>() {
            @Override
            public void onSubscribe(Subscription s) {
                s.request(Long.MAX_VALUE);
            }

            @Override
            public void onNext(String s) {
                show("s:" + s);
            }

            @Override
            public void onError(Throwable t) {
                show("error:" + t.getMessage());
            }

            @Override
            public void onComplete() {
                show("complete");
            }
        };

        //进行绑定操作
        flowable.subscribe(subscrible);
    }

    private static void testInterval() {
        //调用的观察者是在一段时间之后执行
        //固定时间间隔发送整数序列的Observable,类似一个计数器
        //（调用方式是interval(time,TimeUtil.SECOND,Schedulers.io()),
        // 三个参数分别是间隔时间，间隔的时间单位,执行的线程）
        show("" + System.currentTimeMillis());
        Flowable
                .interval(5, TimeUnit.SECONDS, Schedulers.io())
                .just("1", "2", "3", "4", "5")
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        show("" + System.currentTimeMillis());
                        show("s:" + s);
                    }
                });
    }

    private static void testTimer() {
        //timer 是针对观察者的
        Flowable.timer(3, TimeUnit.SECONDS)
                .just("1", "2", "3", "4", "5")
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        s.request(Long.MAX_VALUE);
                        show("onSubscrible");
                    }

                    @Override
                    public void onNext(String s) {
                        show("next: s:" + s);
                    }

                    @Override
                    public void onError(Throwable t) {
                        show("error:" + t.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        show("complete");
                    }
                });
    }

    private static void testRange() {
        Flowable.range(10, 5)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        show("integer:" + integer);
                    }
                });
    }

    private static void testMap() {
        Flowable.just("1", "23", "3343", "3423424", "32423325")
                .map(new Function<String, Integer>() {
                    @Override
                    public Integer apply(String s) throws Exception {
                        show("s:" + s);
                        return s.length();
                    }
                }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                show("integer:" + integer);
            }
        });
    }

    private static void testFlatMap() {
        Flowable.just("123", "321321321", "32131", "2", "321324321")
                .flatMap(new Function<String, Publisher<Integer>>() {
                    @Override
                    public Publisher<Integer> apply(String s) throws Exception {
                        show("flatmap");
                        //这里转换
                        return Flowable.just(s.length());
                    }
                }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                show("integer：" + integer);
            }
        });

        Flowable<String> flowable = Flowable.create(new FlowableOnSubscribe<String>() {
            @Override
            public void subscribe(FlowableEmitter<String> e) throws Exception {
                e.onNext("hello world");
                e.onComplete();
            }
        },BackpressureStrategy.BUFFER);
       /* flowable//.subscribeOn(Schedulers.io())
                //因为不是在Android的平台 所以这里不能使用安卓的主线程进行处理业务逻辑
                //.observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                       show("s:" + s);
                    }
                });*/

        flowable.map(new Function<String, User>() {
            @Override
            public User apply(String s) throws Exception {
                User user = new User();
                user.setName(s);
                user.setAddress("beijing");
                user.setSex(1);
                user.setAge(100);
                return user;
            }
        })//.subscribeOn(Schedulers.io())
                //.observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<User>() {
                    @Override
                    public void accept(User user) throws Exception {
                        Flowable.just(user)
                                //.subscribeOn(Schedulers.io())
                                //.observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Consumer<User>() {
                                    @Override
                                    public void accept(User user) throws Exception {
                                       show(user.getName());
                                        show(user.getAddress());
                                        show("" + user.getAge());
                                        show("" + user.getSex());
                                    }
                                });
                    }
                });
    }

    private static void testForRxjava(){

        //这里专门说明下 支持背压的被观察者 flowable ,在Rxjava2.0中 Observable 是不支持背压处理。
        //什么是背压处理：这里简单的介绍下 看一个例子是
        /***
         * Flowable<Long> flowable =
         Flowable.create((FlowableOnSubscribe<Long>) e -> {
         Observable.interval(10, TimeUnit.MILLISECONDS)
         .take(Integer.MAX_VALUE)
         .subscribe(e::onNext);
         }, FlowableEmitter.BackpressureMode.DROP);


         Observable<Long> observable =
         Observable.create((ObservableOnSubscribe<Long>) e -> {
         Observable.interval(10, TimeUnit.MILLISECONDS)
         .take(Integer.MAX_VALUE)
         .subscribe(e::onNext);
         });
         * */
        //两个对象都以10毫秒一次派发数据，假设订阅他们的方法都是
        /**
         * i -> {
         Thread.sleep(100);
         Log.e("lzx", "out : " + i);
         }
         * */
        //以100毫秒一次消费数据，消费数据的效率是生产的1/10。那么
        //
        //对于observable
        //他会按照0,1,2,3,4…的顺序依次消费，并输出log，而没有消费的数据将会都存在内存中。
        // 如果在RxJava1中，内存数据超过128个时将会抛出MissingBackpressureException错误；
        // 而在RxJava2中并不会报错，数据会一直放到内存中，直到发生OutOfMemoryError。
        //对于flowable, 在创建时我们设定了FlowableEmitter.BackpressureMode.DROP，
        // 一开始他会输出0,1,2,3….127但之后会忽然跳跃到966,967,968 …。
        // 中间的部分数据由于缓存不了，被抛弃掉了。

        //支持被压的被观察者 flowable
        Flowable<String> flowable = Flowable.create(new FlowableOnSubscribe<String>() {
            @Override
            public void subscribe(FlowableEmitter<String> e) throws Exception {
               e.onNext("hello world");
                e.onComplete();
            }
        },BackpressureStrategy.BUFFER);

        //不支持被压的Observable
        Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                 e.onNext("hello world");
                e.onComplete();
            }
        });

        //只支持一次Single
        //和Observable，Flowable一样会发送数据，不同的是订阅后只能接受到一次:
        Single<Long> single = Single.just(1l);
        single.subscribe(new SingleObserver<Long>() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onSuccess(Long value) {
                // 和onNext是一样的
                show("" + value);
            }

            @Override
            public void onError(Throwable e) {
                show("" + e.getMessage());
            }
        });
        //普通Observable可以使用toSingle转换:Observable.just(1).toSingle()。

        //Completable
        //与Single类似，只能接受到完成(onComplete)和错误(onError)
        //同样也可以由普通的Observable转换而来:Observable.just(1).toCompletable()

        //RxJava1.0 VS RxJava2.0
        //可订阅的对象在RxJava1中只有Observable一种，之前我们经常会直接把数据源称作Observable。而在RxJava2中扩充成了4种，因此在之后还是把他们统称为数据源为宜。

        //在RxJava2.0 中四种数据源实现的接口分别是
        //Flowable implement Publisher<T>
        //Observable implement ObservableSource<T>
        //Single implement SingleSource<T>
        //Completable implement CompletableSource<T>

        //所以在很多的方法调用时都是传递的是接口对象,不再是具体的实现类
        /**
         * Flowable<R> flatMap(
         Function<? super T, ? extends Publisher<? extends R>> mapper
         );
         Observable<R> flatMap(
         Function<? super T, ? extends ObservableSource<? extends R>> mapper
         );
         --------------------------------------
         // 以前
         Observable<R> flatMap(Func1<? super T, ? extends Observable<? extends R>> func) {
         * */

        //对于被观察者的比较
        //Subscriber 是观察者
        //RxJava1里Subscriber只是一个空接口，在新版里Subscriber被赋予了更多的作用，有几个实现类可以供我们使用
        /**
         * ResourceSubscriber<Integer> subscriber = new ResourceSubscriber<Integer>() {
        @Override
        public void onStart() {
        request(Long.MAX_VALUE);
        }

        @Override
        public void onNext(Integer t) {
        System.out.println(t);
        }

        @Override
        public void onError(Throwable t) {
        t.printStackTrace();
        }

        @Override
        public void onComplete() {
        System.out.println("Done");
        }
        };
         * */

        //React Native 是什么技术 自己在待会可以查询一下 看看这是和Hybrid 有什么不同
        //React Native 是一种结合前端的技术 FaceBook 推出的一种在Learn Once WriteEveryWhere,
        //为解决客户端更新问题,及降低学习成本问题，只需要在一个地方编写，能在多端运行。
        // 当然就目前来讲在IOS上支持这样的技术，技术的成熟度 比安卓会更成熟些

        //React Native 是在前端编译一次，然后在各个端进行运行。不需要再进行编写和编译，
        // 对于版本更新要比native的更灵活，因为只需要在服务端进行发布一下，客户端就能更新，
        // 不像传统的客户端还需要进行版本的更新迭代
        //HyBird 是一种使用WebView和Native的混合型qpp。灵活性能相对传统的Native的有提升，
        // 因为在WebView中的更新会比Native的更灵活一些，当然这里的更新只是针对简单的展示，
        //对于WebView于本地的交互,那还是需要客户端的版本更新迭代。
        //传统的Native 和 HyBird 及React Native的优势劣势如下
        /***
         *               优势                                                    劣势
         *    native  比较快,无网络运行正常,体验好                        版本更新慢,麻烦
         *    Hybird  相对较快,无网络native运行正常,WebView显示无网络,    版本更新对于WebView展示的内容快,其他的内容和native一致
         * React Native 相对较慢,无网络时体验不好,                        版本更新很快
         * */

        //技术系统 自己有时间需要再研究看看 React系与Vue系：两大前端生态
        //生态的意义就是覆盖全面，几乎没有短板，React和Vue已经覆盖了目前主流的系统平台，
        // 并且可以用React Native、Weex等框架进行原生开发，相较于其它技术有很强的优势

        //这里简单的记录下ReactNative的东西，简单的理解客户端的主线程和JsThread的关系。
        //看来以前学习的CS模型还是有作用的，很多的东西只是没有时间被用到，
        // 还是应该努力的继续的学习
        /**
         * 对于 Android 开发者来说， RN是一个普通的安卓程序加上一堆事件响应，
         * 事件来源主要是JS的命令。主要有二个线程，UI main thread, JS thread。
         * UI thread创建一个APP的事件循环后，就挂在looper等待事件 ,
         * 事件驱动各自的对象执行命令。 JS thread 运行的脚本相当于底层数据采集器，
         * 不断上传数据，转化成UI 事件， 通过bridge转发到UI thread,
         * 从而改变真实的View。 后面再深一层发现，
         * UI main thread 跟 JS thread更像是CS 模型，JS thread更像服务端，
         * UI main thread是客户端， UI main thread 不断询问JS thread并且请求数据，
         * 如果数据有变，则更新UI界面。
         * */

        //在安卓开发中 系统提供了很多的工具类，自己在有时间的时候记得将这些工具类 简单的看下，
        // 然后如果可以 整合一些这些快捷的工具类

        //在很多时候 对数据的请求,如果包含了很多的模块数据,但是接口就只是一个。在需要更新局部的view时
        //请求的接口可以将回调的listener 传入,可以实现多个回调的listener，这样就能区分开同一个请求
        //但是不同的回调更新不同的区域View.避免传入的同一个回调listener 将全部的数据都更新。
        // 这样耗费资源的事儿
    }
}
