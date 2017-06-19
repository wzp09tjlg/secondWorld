package com.example.wuzp.secondworld.view.huasheng.recyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.apkfuns.logutils.LogUtils;
import com.example.wuzp.secondworld.R;
import com.example.wuzp.secondworld.databinding.ActivityRecyclerBinding;
import com.example.wuzp.secondworld.network.parse.SelectBean;
import com.example.wuzp.secondworld.view.base.BindingActivity;
import com.example.wuzp.secondworld.view.widget.MenuPopupWindow;

import java.util.List;

/**
 * Created by wuzp on 2017/3/30.
 常用的MVP框架的基本构建
 对于使用Retrofit + RxJava的基本使用
 对于Glide的基本封装和使用
 */
public class RecyclerActivity extends BindingActivity<ActivityRecyclerBinding,RecyclerPresenter> implements
        RecyclerContract.IView,
        RecyclerViewWrapper.ClickListener
{
    //菜单类型
    private static final int TYPE_OWNER = 1;//作者
    private static final int TYPE_USER = 2; //用户
    private static final int TYPE_POST = 3; //发帖人

    private int mType = TYPE_OWNER;
    private RecyclerViewWrapper viewWrapper = new RecyclerViewWrapper();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewWrapper.addBinding(binding);
        viewWrapper.addClickListener(this);
        mvpPresenter.start(1);
    }

    @Override
    protected RecyclerPresenter createPresenter() {
        return new RecyclerPresenter(this);
    }

    @Override
    protected int createLayoutId() {
        return R.layout.activity_recycler;
    }

    @Override
    public void setData(List<SelectBean.Storys.Story> data) {
       viewWrapper.setData(data);
    }

    @Override
    public void onClickListener(View view) {
        switch (view.getId()){
            case R.id.title_left_img:
                finish();
                break;
            case R.id.title_right_img:
                showMenu(view);
                break;
        }
    }

    int tempMenuType = 0;//加精(取消加精) 或者 置顶(取消置顶)
    private void showMenu(View view){
        //作者:精选 置顶 分享 删除
        //用户:分享 举报
        //发帖人:分享 删除
        int[] menuIconId = null;
        String[] menuNames = null;
        mType = TYPE_OWNER;
        if(mType == TYPE_OWNER){
            if(tempMenuType == 0){
                menuIconId = new int[]{
                        R.drawable.icon_menu_top,
                        R.drawable.icon_menu_select,
                        R.drawable.icon_menu_share,
                        R.drawable.icon_menu_delete};
                menuNames = new String[]{"置顶","加精","分享","删除"};
            }else if(tempMenuType == 1){
                menuIconId = new int[]{
                        R.drawable.icon_menu_top,
                        R.drawable.icon_menu_unselect,
                        R.drawable.icon_menu_share,
                        R.drawable.icon_menu_delete};
                menuNames = new String[]{"置顶","取消加精","分享","删除"};
            }else if(tempMenuType == 2){
                menuIconId = new int[]{
                        R.drawable.icon_menu_untop,
                        R.drawable.icon_menu_select,
                        R.drawable.icon_menu_share,
                        R.drawable.icon_menu_delete};
                menuNames = new String[]{"取消置顶","加精","分享","删除"};
            }else{
                menuIconId = new int[]{
                        R.drawable.icon_menu_untop,
                        R.drawable.icon_menu_unselect,
                        R.drawable.icon_menu_share,
                        R.drawable.icon_menu_delete};
                menuNames = new String[]{"取消置顶","取消加精","分享","删除"};
            }
        }else if(mType == TYPE_USER){
            menuIconId = new int[]{R.drawable.icon_menu_share,R.drawable.icon_menu_report};
            menuNames = new String[]{"分享","举报"};
        } else{
            menuIconId = new int[]{R.drawable.icon_menu_share,R.drawable.icon_menu_delete};
            menuNames = new String[]{"分享","删除"};
        }
        MenuPopupWindow menuPopupWindow = new MenuPopupWindow
                .Builder(this)
                .setNames(menuNames)
                .setIcons(menuIconId)
                .setClickListener(getMenuItemClickListener())
                .build();
        menuPopupWindow.show(view);
    }

    private MenuPopupWindow.MenuItemClickListener getMenuItemClickListener(){
        MenuPopupWindow.MenuItemClickListener listener = new MenuPopupWindow.MenuItemClickListener() {
            @Override
            public void onMenuItemClickListener(View view, int position) {
                showMenuType(mType,position);
                  switch (mType){
                      case TYPE_OWNER:
                          switch (position){
                              case 0:
                                  tempMenuType = 1;
                                  writeLog(mType,position);
                                  break;
                              case 1:
                                  tempMenuType = 2;
                                  writeLog(mType,position);
                                  break;
                              case 2:
                                  tempMenuType = 3;
                                  writeLog(mType,position);
                                  break;
                              case 3:
                                  tempMenuType = 0;
                                  writeLog(mType,position);
                                  break;
                          }
                          break;
                      case TYPE_USER:
                          switch (position){
                              case 0:
                                  writeLog(mType,position);
                                  break;
                              case 1:
                                  writeLog(mType,position);
                                  break;
                          }
                          break;
                      case TYPE_POST:
                          writeLog(mType,position);
                          switch (position){
                              case 0:
                                  writeLog(mType,position);
                                  break;
                              case 1:
                                  writeLog(mType,position);
                                  break;
                          }
                          break;
                  }
            }
        };
        return listener;
    }

    private void showMenuType(int type,int position){
        Toast.makeText(this,"TYPE:" + type + "  position:" + position,Toast.LENGTH_SHORT).show();
    }

    private void writeLog(int type,int position){
        String tempType = "";
        if(type == TYPE_OWNER){
            tempType = "TYPE_OWNER";
        }else if(type == TYPE_USER){
            tempType = "TYPE_USER";
        }else{
            tempType = "TYPE_POST";
        }
        LogUtils.e("TYPE:" + tempType + "  position:" + position);
    }
}
