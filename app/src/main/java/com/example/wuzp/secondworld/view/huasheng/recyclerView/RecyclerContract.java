package com.example.wuzp.secondworld.view.huasheng.recyclerView;


import com.example.wuzp.secondworld.network.parse.SelectBean;

import java.util.List;

/**
 * Created by wuzp on 2017/3/30.
 */

public class RecyclerContract {
    public interface IView{
        void setData(List<SelectBean.Storys.Story> data);
    }

    public interface IPresenter{
        void start(int page);
    }
}
