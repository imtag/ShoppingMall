package com.tfx.shoppingmall.type.fragment;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.tfx.shoppingmall.base.BaseFragment;

/**
 * Created by Tfx on 2016/12/1.
 */

public class TypeFragment extends BaseFragment {

    private TextView mTv;

    @Override
    protected View initView() {
        mTv = new TextView(super.mContext);
        mTv.setTextSize(30);
        mTv.setTextColor(Color.BLACK);
        return mTv;
    }

    @Override
    protected void initData() {
        super.initData();
        mTv.setText(this.getClass().getSimpleName());
        System.out.println(this.getClass().getSimpleName() + "初始化了");
    }
}
