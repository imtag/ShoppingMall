package com.tfx.shoppingmall.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tfx.shoppingmall.app.MainActivity;

/**
 * Created by Tfx on 2016/12/1.
 */

public abstract class BaseFragment extends Fragment {

    protected Context mContext;
    protected MainActivity activity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
        activity = (MainActivity) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //创建fragment视图回调 初始化布局
        return initView();
    }

    //视图初始化，子类必须实现
    protected abstract View initView();

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //当activity的oncreate方法返回时回调 初始化数据
        initData();
    }

    //初始化数据 子类选择实现
    protected void initData() {
    }
}
