package com.example.wuzp.secondworld.view.muchItem;

import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.wuzp.secondworld.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wuzp on 2017/6/27.
 */
public class MuchItemActivity extends AppCompatActivity {
    private Button btnChange;
    private RecyclerView recyclerView;

    private MuchItemAdapter adapter;
    private List<MuchItem> mData = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_much_item);

        initView();
    }

    private void initView(){
        btnChange = (Button)findViewById(R.id.btn_change);
        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("wzp","start time:" + System.currentTimeMillis());
                getData();
                //这里的优化知识 九牛一毛，真正的卡顿的地方还是在数据那儿，与其在小地方花时间 还不如把大的东西好好整理下 然后用快捷的速度去完成整体的优化
                adapter.notifyItemRangeChanged(0,15);
                //adapter.notifyDataSetChanged();//会不会慢
                Log.e("wzp","notify time:" + System.currentTimeMillis());
            }
        });
        recyclerView = (RecyclerView)findViewById(R.id.content);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        RecyclerView.ItemDecoration itemDecoration = new RecyclerView.ItemDecoration() {
            @Override
            public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
                super.onDrawOver(c, parent, state);
                //c.clipRect(0,0,100,2);
            }
        };
        RecyclerView.ItemAnimator itemAnimator = new RecyclerView.ItemAnimator() {
            @Override
            public boolean animateDisappearance(@NonNull RecyclerView.ViewHolder viewHolder, @NonNull ItemHolderInfo preLayoutInfo, @Nullable ItemHolderInfo postLayoutInfo) {
                return false;
            }

            @Override
            public boolean animateAppearance(@NonNull RecyclerView.ViewHolder viewHolder, @Nullable ItemHolderInfo preLayoutInfo, @NonNull ItemHolderInfo postLayoutInfo) {
                return false;
            }

            @Override
            public boolean animatePersistence(@NonNull RecyclerView.ViewHolder viewHolder, @NonNull ItemHolderInfo preLayoutInfo, @NonNull ItemHolderInfo postLayoutInfo) {
                return false;
            }

            @Override
            public boolean animateChange(@NonNull RecyclerView.ViewHolder oldHolder, @NonNull RecyclerView.ViewHolder newHolder, @NonNull ItemHolderInfo preLayoutInfo, @NonNull ItemHolderInfo postLayoutInfo) {
                return false;
            }

            @Override
            public void runPendingAnimations() {

            }

            @Override
            public void endAnimation(RecyclerView.ViewHolder item) {

            }

            @Override
            public void endAnimations() {

            }

            @Override
            public boolean isRunning() {
                return false;
            }
        };
        //recyclerView.addItemDecoration(itemDecoration);
        //recyclerView.setItemAnimator(itemAnimator);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new MuchItemAdapter(mData);
        recyclerView.setAdapter(adapter);
    }

    private void getData(){
       for(int i=0;i<10000;i++){
           MuchItem item = new MuchItem();
           item.setNum("" + i);
           item.setName("item name is:" + i);
           item.setDate("2017.6.28  random" + Math.random());
           mData.add(item);
       }
    }
}
