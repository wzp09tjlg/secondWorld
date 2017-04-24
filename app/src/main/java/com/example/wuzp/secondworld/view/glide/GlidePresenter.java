package com.example.wuzp.secondworld.view.glide;

import com.apkfuns.logutils.LogUtils;
import com.example.wuzp.secondworld.network.ApiCallback;
import com.example.wuzp.secondworld.network.Error;
import com.example.wuzp.secondworld.network.parse.TopicBean;
import com.example.wuzp.secondworld.view.base.BasePresenter;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wuzp on 2017/4/24.
 */

public class GlidePresenter extends BasePresenter<GlideContract.IView> implements GlideContract.IPresenter {

    public GlidePresenter(GlideContract.IView view){
        super(view);
    }

    @Override
    public void start() {
        Map<String,String> params = new HashMap<>();
        params.put("position","start_topic");
        addSubscription(apiStores.getTopicList(params), new ApiCallback<TopicBean>() {
            @Override
            public void onSuccess(TopicBean model) {
                LogUtils.e("model:" + model.getItems().size());
              mvpView.setData(model);
            }

            @Override
            public void onFailure(Error error) {
                LogUtils.e("error:" + error.getMessage());
            }
        });
    }

    @Override
    public void getUrl() {

    }
}
