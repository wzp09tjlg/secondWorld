package com.example.wuzp.secondworld.view.FragementLife;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by wuzp on 2017/6/30.
 *
 */
public class VAdapter extends FragmentPagerAdapter {
    private List<Fragment> mData;

    public VAdapter(FragmentManager manager,List<Fragment > data){
        super(manager);
        mData = data;
    }

    @Override
    public Fragment getItem(int position) {
        return mData.get(position);
    }

    @Override
    public int getCount() {
        return mData == null ? 0 : mData.size();
    }
}
