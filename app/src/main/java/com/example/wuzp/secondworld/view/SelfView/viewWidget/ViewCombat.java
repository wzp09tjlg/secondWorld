package com.example.wuzp.secondworld.view.SelfView.viewWidget;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.wuzp.secondworld.R;

/**
 * Created by wuzp on 2017/7/6.
 *  自定义View 采用组合的方式来
 */
public class ViewCombat extends LinearLayout implements View.OnClickListener {
    public static final int TYPE_MENU_TXT = 1;//显示文字
    public static final int TYPE_MENU_IMG = 2;//显示图片

    private ImageView imgBack;
    private TextView  textCenter;
    private TextView  textMenu;
    private ImageView imgMenu;

    private int mType = TYPE_MENU_TXT;
    private OnBackClickListener backClickListener;
    private OnMenuClickListener menuClickListener;

    public ViewCombat(Context context){
        super(context);
        init(context);
    }

    public ViewCombat(Context context, AttributeSet attrs){
        super(context,attrs);
        init(context);
    }

    public ViewCombat(Context context, AttributeSet attrs, int flag){
        super(context,attrs,flag);
        init(context);
    }

    private void init(Context context){
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.view_combat,this,true);//这里就是将布局加载到ViewGroup上

        initView(view);
    }

    private void initView(View view){
        imgBack = (ImageView)view.findViewById(R.id.img_back);
        textCenter = (TextView)view.findViewById(R.id.text_center_text);
        textMenu = (TextView)view.findViewById(R.id.text_menu);
        imgMenu = (ImageView)view.findViewById(R.id.img_menu);

        imgBack.setOnClickListener(this);
        imgMenu.setOnClickListener(this);

        setmType(TYPE_MENU_TXT);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_back:
                if(backClickListener != null)
                    backClickListener.onBackListener();
                break;
            case R.id.img_menu:
                if(menuClickListener != null)
                    menuClickListener.onMenuListener();
                break;
        }
    }

    /***************************************************/
    //对外暴露的方法
    public void setmType(int mType) {
        this.mType = mType;
        if(TYPE_MENU_IMG == mType){
            textMenu.setVisibility(GONE);
            imgMenu.setVisibility(VISIBLE);
        }else{
            textMenu.setVisibility(VISIBLE);
            imgMenu.setVisibility(GONE);
        }
    }

    public void setCenterTxt(String centerTxt){
        if(TextUtils.isEmpty(centerTxt)) return;
        textCenter.setText(centerTxt);
    }

    public void setMenuText(String menuTxt){
        if(TextUtils.isEmpty(menuTxt)) return;
        textMenu.setText(menuTxt);
    }

    public void addBackClickListener(OnBackClickListener listener){
        if(listener != null)
            backClickListener = listener;
    }

    public void addMenuClickListener(OnMenuClickListener listener){
        if(listener != null)
            menuClickListener = listener;
    }

    public interface OnBackClickListener{
        void onBackListener();
    }

    public interface OnMenuClickListener{
        void onMenuListener();
    }
}
