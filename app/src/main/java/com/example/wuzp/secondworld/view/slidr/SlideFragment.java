package com.example.wuzp.secondworld.view.slidr;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.wuzp.secondworld.R;

/**
 * Created by wuzp on 2017/6/14.
 */
public class SlideFragment extends Fragment {

    private ImageView imgSrc ;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_slide,null);
        initView(view);
        return view;
    }

    private void initView(View view){
        imgSrc = (ImageView)view.findViewById(R.id.img_slide);
        imgSrc.setImageResource(R.drawable.icon_road);
    }

}
