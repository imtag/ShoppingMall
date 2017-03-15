package com.tfx.shoppingmall.cart.fragment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tfx.shoppingmall.R;
import com.tfx.shoppingmall.app.MainActivity;
import com.tfx.shoppingmall.base.BaseFragment;
import com.tfx.shoppingmall.cart.adapter.MyCartRecycleViewAdapter;
import com.tfx.shoppingmall.cart.utils.CartProvider;
import com.tfx.shoppingmall.home.bean.GoodBean;

import java.util.List;

/**
 * Created by Tfx on 2016/12/1.
 */

public class CartFragment extends BaseFragment {
    TextView mTvEdit;
    RecyclerView mCartRecycleView;
    LinearLayout mLlCheckAll;
    CheckBox mCheckboxAll;
    TextView mTvShopcartTotal;
    Button mBtnCheckOut;
    LinearLayout mLlDelete;
    CheckBox mCbAll;
    Button mBtnDelete;
    Button mBtnCollection;
    private LinearLayout mLlEmpty;
    private TextView mEmptyTobuy;
    private MyCartRecycleViewAdapter mAdapter;
    private static final int ACTION_EDIT = 1;
    private static final int ACTION_COMPLATE = 2;
    private List<GoodBean> mAll;

    @Override
    protected View initView() {
        View view = View.inflate(mContext, R.layout.fragment_cart, null);
        mCartRecycleView = (RecyclerView) view.findViewById(R.id.cart_recycleView);
        mTvEdit = (TextView) view.findViewById(R.id.cart_tv_edit);
        mLlCheckAll = (LinearLayout) view.findViewById(R.id.ll_check_all);
        mCheckboxAll = (CheckBox) view.findViewById(R.id.checkbox_all);
        mTvShopcartTotal = (TextView) view.findViewById(R.id.tv_shopcart_total);
        mBtnCheckOut = (Button) view.findViewById(R.id.btn_check_out);
        mLlDelete = (LinearLayout) view.findViewById(R.id.ll_delete);
        mCbAll = (CheckBox) view.findViewById(R.id.cb_all);
        mBtnDelete = (Button) view.findViewById(R.id.btn_delete);
        mBtnCollection = (Button) view.findViewById(R.id.btn_collection);
        mLlEmpty = (LinearLayout) view.findViewById(R.id.ll_empty_shopcart);
        mEmptyTobuy = (TextView) view.findViewById(R.id.tv_empty_cart_tobuy);
        return view;
    }

    @Override
    protected void initData() {
        //获取购物车所有数据
        mLlCheckAll.setVisibility(View.VISIBLE);
        mAll = CartProvider.getInstance().getAllFromLocal();
        if (mAll.size() > 0) {
            //有数据
            mLlEmpty.setVisibility(View.GONE);

            mTvEdit.setVisibility(View.VISIBLE);

            //绑定recycleview适配器 数据传递过去
            mAdapter = new MyCartRecycleViewAdapter(mContext, mAll, mCheckboxAll, mTvShopcartTotal, mCbAll);
            mCartRecycleView.setAdapter(mAdapter);
            mCartRecycleView.setLayoutManager(new LinearLayoutManager(mContext));
        } else {
            //没数据
            showEmpety();
        }

        initEvent();
    }

    private void showEmpety() {
        mLlEmpty.setVisibility(View.VISIBLE);
        mLlCheckAll.setVisibility(View.GONE);
        mTvEdit.setVisibility(View.GONE);
    }

    private void initEvent() {
        //默认显示编辑状态
        mTvEdit.setTag(ACTION_EDIT);

        //编辑点击事件
        mTvEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int action = (int) v.getTag();
                if (action == ACTION_EDIT) {
                    //显示完成
                    v.setTag(ACTION_COMPLATE);
                    mTvEdit.setText("完成");
                    mLlCheckAll.setVisibility(View.GONE);
                    mLlDelete.setVisibility(View.VISIBLE);
                    mAdapter.checkAll_None();
                } else {
                    //显示编辑
                    v.setTag(ACTION_EDIT);
                    mTvEdit.setText("编辑");
                    mLlDelete.setVisibility(View.GONE);
                    mLlCheckAll.setVisibility(View.VISIBLE);
                }

                if (mAll.size() == 0) {
                    showEmpety();
                }
            }
        });

        //删除按钮
        mBtnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdapter.deleteGoods();
            }
        });

        //去逛逛按钮
        mEmptyTobuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, MainActivity.class);
                startActivity(intent);
            }
        });

    }

    //因为fragment只初始化一次，所有每次进入购物车都要重新加载购物车数据
    @Override
    public void onResume() {
        super.onResume();
        initData();
    }
}
