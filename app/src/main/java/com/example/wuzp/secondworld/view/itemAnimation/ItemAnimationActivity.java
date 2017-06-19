package com.example.wuzp.secondworld.view.itemAnimation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.wuzp.secondworld.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

/**
 * Created by wuzp on 2017/6/16.
 * 这里添加的是item的动画处理
 */
public class ItemAnimationActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnChange;
    private RecyclerView recyclerView;

    private List<ItemBean> mData;
    private ItemAnimationAdapter adapter;

    private int randomRow = 1;

    private int[] icons = {
            R.drawable.book_0,R.drawable.book_2,R.drawable.book_3,
            R.drawable.book_4,R.drawable.book_5,R.drawable.book_6,
            R.drawable.book_7,R.drawable.book_8,R.drawable.book_9,
            R.drawable.book_0,R.drawable.book_2,R.drawable.book_3,
            R.drawable.book_4,R.drawable.book_5,R.drawable.book_6,
            R.drawable.book_7,R.drawable.book_8,R.drawable.book_9,
            R.drawable.book_0,R.drawable.book_2,R.drawable.book_3,
            R.drawable.book_4,R.drawable.book_5,R.drawable.book_6,
            R.drawable.book_7,R.drawable.book_8,R.drawable.book_9,
    };
    private String[] names = {
            "熊云久","苏金翠","杨虎城",
            "陈红英","魏志敏","吴艳琴",
            "吴久云","吴龙生","吴世友",
            "陈善海","黄清炎","向君",
            "许文龙","姚静","苟长义",
            "徐淑华","夏长义","魏静",
            "熊云久","苏金翠","杨虎城",
            "陈红英","魏志敏","吴艳琴",
            "吴久云","吴龙生","吴世友",
            "陈善海","黄清炎","向君",
            "许文龙","姚静","苟长义",
            "徐淑华","夏长义","魏静",
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_animation);

        initView();
    }

    private void initView(){
        btnChange = (Button)findViewById(R.id.btn_change);
        recyclerView = (RecyclerView)findViewById(R.id.grid);

        initData();
    }

    private void initData(){
        mData = getData();

        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL);

        adapter = new ItemAnimationAdapter(this,mData);
        recyclerView.setLayoutManager(manager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        btnChange.setOnClickListener(this);
    }

    private List<ItemBean> getData(){
        mData = new ArrayList<>();

        int len = icons.length;
        for(int i=0;i<len;i++){
            ItemBean bean = new ItemBean(i,icons[i],names[i]);
            mData.add(bean);
        }

        return mData;
    }

    Random random = new Random(100);

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_change:
                int num =random.nextInt();
                Log.e("wzp","random:" + num);
                randomRow = (randomRow + num ) % (mData.size() / 3);
                for(int i=0;i<3;i++){
                    mData.get(randomRow*3 + i).setSort(num % 10);
                }
                doSort();
                adapter.notifyDataSetChanged();
                break;
        }
    }

    private void doSort(){
        Collections.sort(mData, new Comparator<ItemBean>() {
            @Override
            public int compare(ItemBean o1, ItemBean o2) {
                if(o1.getSort() > o2.getSort())
                    return 1;
                else if(o1.getSort() == o2.getSort())
                    return 0;
                else
                    return -1;
            }
        });
    }


}
