package com.tfx.shoppingmall.home.fragment;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.tfx.shoppingmall.R;
import com.tfx.shoppingmall.base.BaseFragment;
import com.tfx.shoppingmall.home.adapter.HomeRecyleViewAdapter;
import com.tfx.shoppingmall.home.bean.ResultBeanData;
import com.tfx.shoppingmall.utils.Constant;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

import static com.tfx.shoppingmall.R.id.ib_top;

/**
 * Created by Tfx on 2016/12/1.
 */

public class HomeFragment extends BaseFragment {

    private RecyclerView mRv;
    private ImageButton mIbTop;

    @Override
    protected View initView() {
        View view = View.inflate(mContext, R.layout.fragment_home, null);
        mRv = (RecyclerView) view.findViewById(R.id.rv_home);
        mIbTop = (ImageButton) view.findViewById(ib_top);
        mIbTop.setOnClickListener(listener);
        view.findViewById(R.id.tv_message_home).setOnClickListener(listener);
        view.findViewById(R.id.tv_search_home).setOnClickListener(listener);
        return view;
    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                //到顶部按钮
                case ib_top:
                    mRv.scrollToPosition(0);
                    break;
                case R.id.tv_message_home:
                    Toast.makeText(mContext, "消息", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.tv_search_home:
                    Toast.makeText(mContext, "搜索", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    protected void initData() {
        getDataFromNet(Constant.HOMEURL);
    }

    public void getDataFromNet(String url) {
        OkHttpUtils
                .get()
                .url(url)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        //请求失败
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        //请求成功
                        processData(response);
                    }
                });
    }

    private void processData(String json) {
        ResultBeanData.ResultBean resultBean = JSON.parseObject(json, ResultBeanData.class).getResult();
        mRv.setAdapter(new HomeRecyleViewAdapter(mContext, resultBean));
        GridLayoutManager layoutManager = new GridLayoutManager(mContext, 1);
        //监听跨度大小 大于等于三时显示到顶部按钮 否则隐藏
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (position <= 3) {
                    mIbTop.setVisibility(View.GONE);
                } else {
                    mIbTop.setVisibility(View.VISIBLE);
                }
                return 1;
            }
        });
        mRv.setLayoutManager(layoutManager);
    }
}
