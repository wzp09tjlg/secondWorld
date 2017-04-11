package com.example.wuzp.secondworld.view.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.wuzp.secondworld.R;

/**
 * Created by wuzp on 2017/3/29.
 *  显示在右上角的菜单popupwindow 采用构建者模式
 */
public class MenuPopupWindow extends PopupWindow {
    private final int TYPE_ITEM_SINGLE = 1;//单个选项菜单
    private final int TYPE_ITEM_DOUBLE = 2;//两个选项菜单
    private final int TYPE_ITEM_COMPLEX = 3;//三个及三个选项菜单

    /** View */
    private ViewGroup viewRoot;

    /** Data */
    private Context mContext;
    private int[] iconIds;
    private String[] menuNames;
    private MenuItemClickListener itemClickListener;
    private LayoutInflater inflater = null;

    private MenuPopupWindow(Builder builder){
        super(builder.mContext);
        this.mContext = builder.mContext;
        this.iconIds = builder.iconIds;
        this.menuNames = builder.itemNames;
        this.itemClickListener = builder.itemListener;
        initView(builder.mContext);
        initData();
    }

   private void initView(Context context){
       inflater = LayoutInflater.from(context);
       setBackgroundDrawable(context.getResources().getDrawable(R.drawable.drawable_transparent));
       viewRoot = (ViewGroup) inflater.inflate(R.layout.view_base_popupwindow,null);
       ViewGroup tempContent = (ViewGroup) viewRoot.findViewById(R.id.layout_content);
       int tempItemCount = iconIds.length;
       int tempType = TYPE_ITEM_SINGLE;
       if(tempItemCount == 1){
           tempType = TYPE_ITEM_SINGLE;
       }else if(tempItemCount == 2){
           tempType = TYPE_ITEM_DOUBLE;
       }else{
           tempType = TYPE_ITEM_COMPLEX;
       }

       switch (tempType){
           case TYPE_ITEM_SINGLE:
               initSingleItem(tempContent);
               break;
           case TYPE_ITEM_DOUBLE:
               initTopItem(tempContent);
               initBottomItem(tempContent);
               break;
           case TYPE_ITEM_COMPLEX:
               initTopItem(tempContent);
               initNormalItem(tempContent);
               initBottomItem(tempContent);
               break;
       }
       setContentView(viewRoot);
   }

   //单个的menu 无divider
   private void initSingleItem(ViewGroup content){
       View tempView = inflateView(0);
       tempView.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.menu_item_normal_selecor));
       content.addView(tempView);
   }

   //顶部的menu 有divider
   private void initTopItem(ViewGroup content){
       View tempView = inflateView(0);
       tempView.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.menu_item_top_selecor));
       content.addView(tempView);
       View divider = inflater.inflate(R.layout.divider_menu,content,false);
       content.addView(divider);
   }

   //正常的item 有divider
   private void initNormalItem(ViewGroup content){
       int tempCount = iconIds.length;
       for(int i=1;i<tempCount - 1;i++){
           View tempView = inflateView(i);
           tempView.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.menu_item_normal_selecor));
           content.addView(tempView);
           View divider = inflater.inflate(R.layout.divider_menu,content,false);
           content.addView(divider);
       }
   }

   //底部的item 无divider
   private void initBottomItem(ViewGroup content){
       int tempCount = iconIds.length;
       View tempView = inflateView(tempCount - 1);
       tempView.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.menu_item_bottom_selecor));
       content.addView(tempView);
   }

   //item的view一致 只是背景色不一样
   private View inflateView(final int position){
       ViewGroup viewItem = (ViewGroup)inflater.inflate(R.layout.item_base_popupwindow,null);
       ((ImageView)viewItem.findViewById(R.id.img_icon)).setImageDrawable(mContext.getResources().getDrawable(iconIds[position]));
       ((TextView)viewItem.findViewById(R.id.text_name)).setText(menuNames[position]);
       viewItem.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if(itemClickListener != null){
                   itemClickListener.onMenuItemClickListener(v,position);
                   dismiss();
               }
           }
       });
       return viewItem;
   }

   private void initData(){
       setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
       setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
       setFocusable(true);
       setTouchable(true);
       setOutsideTouchable(true);
   }

   public void show(View view){
       if(iconIds == null || iconIds.length == 0) return;
       showAsDropDown(view);
   }

   /*****************************************************/
   //Builder 类
   /*****************************************************/
   public static class Builder{
       private Context mContext;
       private int[] iconIds;
       private String[] itemNames;
       private MenuItemClickListener itemListener;

       public Builder(Context context){
           mContext = context;
       }

       public Builder setIcons(int[] icons){
           iconIds = icons;
           return this;
       }

       public Builder setNames(String[] names){
           itemNames = names;
           return this;
       }

       public Builder setClickListener(MenuItemClickListener listener){
           itemListener = listener;
           return this;
       }

       public MenuPopupWindow build(){
           return new MenuPopupWindow(this);
       }
   }

   public interface MenuItemClickListener{
       void onMenuItemClickListener(View view, int position);
   }
}
