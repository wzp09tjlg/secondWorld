package com.example.wuzp.secondworld.view.widget.MsgShow;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wuzp.secondworld.R;


/**
 * Created by wuzp on 2017/4/26.
 * 经过观察 发现掌阅和塔读的弹框都非常好看 自己在以前看搜狐新闻客户端时  也发现他们的弹框很不错
 * 现在 自己开始接手做阅读器  希望自己在控件方面多下功夫 实现不错的toast 弹窗
 */
public class MsgView {
    private Toast toast;
    private static MsgView msgView;

    private MsgView(){}

    public static MsgView getInstance(Context context){
        if(msgView == null){
            msgView = new MsgView();
        }
        return msgView;
    }

    public void show(Context context,String msg){
        Toast result = new Toast(context);

        LayoutInflater inflate = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflate.inflate(R.layout.view_msg,null);
        Animation animation = AnimationUtils.loadAnimation(context,R.anim.anim_msg);
        v.startAnimation(animation);
        TextView tv = (TextView)v.findViewById(R.id.text_msg);
        tv.setText(msg);

        result.setView(v);
        result.setDuration(Toast.LENGTH_SHORT);
        result.show();
    }

    public void show(String msg,int gravity){

    }
}
