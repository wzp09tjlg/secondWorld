package com.example.wuzp.secondworld.view.SelfView.viewWidgetAct;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import com.example.wuzp.secondworld.R;
import com.example.wuzp.secondworld.view.SelfView.viewWidget.ViewCombat;

/**
 * Created by wuzp on 2017/7/6.
 */
public class ViewCombatActivity extends AppCompatActivity {
    private ViewCombat viewCombat;
    private ImageView imgSky;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewcombat);

        initView();
    }

    private void initView(){
        viewCombat = (ViewCombat)findViewById(R.id.view_combat);
        imgSky = (ImageView)findViewById(R.id.img_sky);

        initData();
    }

    private void initData(){
        viewCombat.setmType(ViewCombat.TYPE_MENU_TXT);
        viewCombat.setCenterTxt("东北大板");
        viewCombat.setMenuText("点我");
        viewCombat.addBackClickListener(new ViewCombat.OnBackClickListener() {
            @Override
            public void onBackListener() {
                Log.e("wzp","this is back img enter");
            }
        });
        viewCombat.addMenuClickListener(new ViewCombat.OnMenuClickListener() {
            @Override
            public void onMenuListener() {
                Log.e("wzp","this is menu text enter");
            }
        });
    }
}
