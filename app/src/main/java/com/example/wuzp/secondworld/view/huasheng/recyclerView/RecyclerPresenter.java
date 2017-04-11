package com.example.wuzp.secondworld.view.huasheng.recyclerView;

import com.apkfuns.logutils.LogUtils;
import com.example.wuzp.secondworld.network.ApiCallback;
import com.example.wuzp.secondworld.network.Error;
import com.example.wuzp.secondworld.network.parse.SelectBean;
import com.example.wuzp.secondworld.view.base.BasePresenter;

import java.util.HashMap;

/**
 * Created by wuzp on 2017/3/30.
 */

public class RecyclerPresenter extends BasePresenter<RecyclerContract.IView> implements RecyclerContract.IPresenter {
    public RecyclerPresenter(RecyclerContract.IView view){
        super(view);
    }

    @Override
    public void start(int page) {
        load(page);
    }

    public void load(int page){
        HashMap<String,String> params = new HashMap<>();
        params.put("position","top_tab_data");
        params.put("id","4");
        params.put("page","" + page);
        params.put("page_size","20");
       addSubscription(apiStores.getData(params), new ApiCallback<SelectBean>() {
           @Override
           public void onSuccess(SelectBean model) {
               SelectBean.Imgs imgs = model.getImgs();
               SelectBean.Storys storys = model.getStorys();
               LogUtils.e("imgs size:" + imgs.getData().size());
               LogUtils.e("storys size:" + storys.getData().size());
               LogUtils.e("imgs:" + imgs.getData().toString());
               LogUtils.e("data:" + model.getStorys().getData().toString());
               mvpView.setData(storys.getData());
           }

           @Override
           public void onFailure(Error error) {
                  LogUtils.e("get data is failure");
           }
       });
    }
}
