package com.example.wuzp.secondworld.view.wangdou.action.taskstatistic;


import java.util.ArrayList;
import java.util.List;


/**
 * Created by zhouyibo on 2017/4/25.
 */

public class TaskTrackList {
//    private static List<Tasktrack.DataBean> trackList = new ArrayList<>();

    /**
     * 初始化任务条件
     */
    public static void initTaskTrackList() {
//        trackList.clear();
/*        ModelFactory.getTaskTrackModel().getTaskTrack(new CallBack<Tasktrack>() {
            @Override
            public void success(Call<Tasktrack> call, Response<Tasktrack> response) {
                trackList.clear();
                trackList.addAll(response.body().getData());
            }

            @Override
            public void unKnowCode(Call<Tasktrack> call, Response<Tasktrack> response) {
                //获取失败时候处理
            }

            @Override
            public void onFailure(Call<Tasktrack> call, Throwable t) {
                //获取失败时候处理
            }
        });*/
    }

    /**
     * 对比用户行为栈 是否有新完成  完成则提交后台
     */
    public static void compareTaskEvent() {
       /* List<TaskEvent> taskEvents = TaskHelp.getEventHeap().getTaskEvents();
        for (TaskEvent event : taskEvents) {
            for (final Tasktrack.DataBean bean : trackList) {
                if (event.getTaskType() == (bean.getTask_type()) && event.isAccomplish(bean.getComplete_condition()) && !bean.isReceive()) {
                    bean.setReceive(true);
                    ModelFactory.getConditionCompleteModel()
                            .sendConditionComplete(bean.getTask_id(), bean.getCondition_id(), new CallBack<TaskConditionComplete>() {
                                @Override
                                public void success(Call<TaskConditionComplete> call, Response<TaskConditionComplete> response) {
                                    TaskConditionComplete.DataBean dataBean = response.body().getData();
                                    if (dataBean == null) {
                                        return;
                                    }
                                    if (dataBean.getTask_status() == 2) {
                                        //eventbus传递消息 在需要处理弹窗的页面处理
                                        EventBus.getDefault().post(new com.sina.book.widget.eventbus.TaskEvent(2, bean.getTask_id(), dataBean.getTask_name(), bean.getTask_type()));
                                    }
                                }

                                @Override
                                public void unKnowCode(Call<TaskConditionComplete> call, Response<TaskConditionComplete> response) {

                                }

                                @Override
                                public void onFailure(Call<TaskConditionComplete> call, Throwable t) {

                                }
                            });
                }
            }
        }*/
    }


    public static void updateList() {
   /*     if (trackList.size() == 0) {
            ModelFactory.getTaskTrackModel().getTaskTrack(new CallBack<Tasktrack>() {
                @Override
                public void success(Call<Tasktrack> call, final Response<Tasktrack> response) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            trackList.addAll(response.body().getData());
                            List<TaskEvent> taskEvents = TaskHelp.getEventHeap().getTaskEvents();
                            for (TaskEvent event : taskEvents) {
                                for (final Tasktrack.DataBean bean : trackList) {
                                    if (event.getTaskType() == (bean.getTask_type()) && event.isAccomplish(bean.getComplete_condition()) && !bean.isReceive()) {
                                        bean.setReceive(true);
                                        ModelFactory.getConditionCompleteModel()
                                                .sendConditionComplete(bean.getTask_id(), bean.getCondition_id(), new CallBack<TaskConditionComplete>() {
                                                    @Override
                                                    public void success(Call<TaskConditionComplete> call, Response<TaskConditionComplete> response) {
                                                    }

                                                    @Override
                                                    public void unKnowCode(Call<TaskConditionComplete> call, Response<TaskConditionComplete> response) {
                                                    }

                                                    @Override
                                                    public void onFailure(Call<TaskConditionComplete> call, Throwable t) {
                                                    }
                                                });
                                    }
                                }
                            }
                        }
                    }).start();
                }

                @Override
                public void unKnowCode(Call<Tasktrack> call, Response<Tasktrack> response) {
                    //获取失败时候处理
                }

                @Override
                public void onFailure(Call<Tasktrack> call, Throwable t) {
                    //获取失败时候处理
                }
            });
        }*/
    }

    /**
     * 根据任务类型获取任务值
     */
    public static List<Integer> getConditionList(int type) {
        List<Integer> lists = new ArrayList<>();
        /*for (Tasktrack.DataBean bean : trackList) {
            if (bean.getTask_type() == type && !bean.isReceive()) {
                lists.add(bean.getComplete_condition());
            }
        }*/
        return lists;
    }
}
