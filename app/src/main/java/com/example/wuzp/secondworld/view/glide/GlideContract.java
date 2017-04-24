package com.example.wuzp.secondworld.view.glide;

import android.view.View;

import com.example.wuzp.secondworld.network.parse.TopicBean;

/**
 * Created by wuzp on 2017/4/24.
 */

public class GlideContract {

    public interface IView{
        void setData(TopicBean bean);
    }

    public interface IPresenter{
        void start();

        void getUrl();
    }

    public interface OnItemClick{
        void onItemClick(View view);
    }
}
