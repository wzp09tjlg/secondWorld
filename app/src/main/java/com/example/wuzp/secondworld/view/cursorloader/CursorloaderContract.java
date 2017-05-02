package com.example.wuzp.secondworld.view.cursorloader;

import android.view.View;

/**
 * Created by wuzp on 2017/4/28.
 */
public class CursorloaderContract {
    public interface IView{}

    public interface IPresenter{}

    public interface OnItemClick{
        void onClick(View view);
    }
}
