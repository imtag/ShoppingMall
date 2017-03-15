package com.tfx.shoppingmall.cart.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tfx.shoppingmall.R;
import com.tfx.shoppingmall.cart.utils.CartProvider;
import com.tfx.shoppingmall.home.bean.GoodBean;
import com.tfx.shoppingmall.utils.Constant;
import com.tfx.shoppingmall.view.NumberAddSubView;

import java.util.List;

/**
 * Created by Tfx on 2016/12/4.
 */
public class MyCartRecycleViewAdapter extends RecyclerView.Adapter<MyCartRecycleViewAdapter.MyViewHolder> {
    private final List<GoodBean> carts;
    private final Context mContext;
    private final CheckBox checkboxAll;
    private final TextView tvShopcartTotal;
    private final CheckBox cbAll;

    public MyCartRecycleViewAdapter(Context context, List<GoodBean> all, CheckBox checkboxAll, TextView tvShopcartTotal, CheckBox cbAll) {
        this.mContext = context;
        this.carts = all;
        this.checkboxAll = checkboxAll;
        this.tvShopcartTotal = tvShopcartTotal;
        this.cbAll = cbAll;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //创建viewholder
        return new MyViewHolder(View.inflate(mContext, R.layout.item_cart, null));
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        holder.setData(position);
    }

    @Override
    public int getItemCount() {
        return carts.size();
    }

    public void deleteGoods() {
        if (carts.size() > 0 && carts != null) {
            for (int i = 0; i < carts.size(); i++) {
                //遍历所有条目 设置为选中
                GoodBean bean = carts.get(i);
                if (bean.isCheckd()) {
                    //从内存中删除
                    carts.remove(i);

                    //本地同步
                    CartProvider.getInstance().delete(bean);

                    //刷新当前条目显示
                    notifyItemRemoved(i);

                    i--;
                }
            }
        }
        tvShopcartTotal.setText(getTotalPrice() + "");
    }

    //viewholder
    class MyViewHolder extends RecyclerView.ViewHolder {
        CheckBox mCbGov;
        ImageView mIvGov;
        TextView mTvDescGov;
        TextView mTvPriceGov;
        NumberAddSubView mNumberAddSubView;
        private GoodBean mBean;

        public MyViewHolder(View itemView) {
            super(itemView);
            mCbGov = (CheckBox) itemView.findViewById(R.id.cb_gov);
            mIvGov = (ImageView) itemView.findViewById(R.id.iv_gov);
            mTvDescGov = (TextView) itemView.findViewById(R.id.tv_desc_gov);
            mTvPriceGov = (TextView) itemView.findViewById(R.id.tv_price_gov);
            mNumberAddSubView = (NumberAddSubView) itemView.findViewById(R.id.numberAddSubView);

            //item的点击事件
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(view, getLayoutPosition());
                    }
                }
            });
        }

        //绑定适配器数据
        public void setData(final int position) {
            //绑定数据
            mBean = carts.get(position);
            mTvPriceGov.setText("￥" + mBean.getCover_price());
            mTvDescGov.setText(mBean.getName());
            Glide.with(mContext).load(Constant.IMGURL + mBean.getFigure()).into(mIvGov);
            mNumberAddSubView.setValue(mBean.getNumber()); //加入购物车数量
            mNumberAddSubView.setMaxValue(mBean.getMaxNumber()); //库存
            mNumberAddSubView.setMinValue(1); //最小值
            mCbGov.setChecked(mBean.isCheckd());

            //删除里的全选默认非选中
            cbAll.setChecked(false);

            //默认全选
            checkboxAll.setChecked(true);

            //遍历当前购物车商品 累加价格
            showTotalPrice();

            //监听
            initEvent(position);

            //检测当前是否全部选中
            checkAll();
        }

        private void initEvent(final int position) {
            //recycleview条目点击事件（自定义）
            setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    //状态取反
                    GoodBean good = carts.get(position);
                    good.setCheckd(!good.isCheckd());

                    //刷新条目显示
                    notifyItemChanged(position);

                    //重新计算
                    showTotalPrice();
                }
            });

            //全选按钮监听
            checkboxAll.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            boolean b = checkboxAll.isChecked();
                            if (b) {
                                for (int i = 0; i < carts.size(); i++) {
                                    //遍历所有条目 设置为选中
                                    GoodBean bean = carts.get(i);
                                    bean.setCheckd(true);

                                    //刷新当前条目显示
                                    notifyItemChanged(i);
                                }

                                //显示总价
                                showTotalPrice();
                            } else {
                                checkAll_None();

                            }
                        }
                    }

            );

            //删除里的全选
            cbAll.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            boolean b = cbAll.isChecked();
                            if (b) {
                                for (int i = 0; i < carts.size(); i++) {
                                    //遍历所有条目 设置为选中
                                    GoodBean bean = carts.get(i);
                                    bean.setCheckd(true);

                                    //刷新当前条目显示
                                    notifyItemChanged(i);
                                }
                            } else {
                                checkAll_None();
                            }
                        }
                    }

            );

            //数量改变监听
            mNumberAddSubView.setOnNumberChangeListener(
                    new NumberAddSubView.OnNumberChangeListener() {
                        @Override
                        public void onNumberChange(View view, int number) {
                            //数量增加
                            numberChange(number, position);
                        }
                    }

            );
        }


        private void numberChange(int number, int position) {
            //更新当前bean的number数量
            mBean.setNumber(number);

            //更新SparseArray中的当前bean
            CartProvider.getInstance().update(mBean);

            //显示总价
            showTotalPrice();
        }

        //显示所有价格 调用getTotalPrice，遍历购物车所有商品 累加价格
        private void showTotalPrice() {
            if (checkboxAll.isChecked()) {
                tvShopcartTotal.setText("￥" + getTotalPrice());
            }
        }
    }

    //设置全部未选中
    public void checkAll_None() {
        for (int i = 0; i < carts.size(); i++) {
            //遍历全部条目 设置为未选中
            GoodBean bean = carts.get(i);
            bean.setCheckd(false);

            //刷新条目显示
            notifyItemChanged(i);
        }
        tvShopcartTotal.setText("￥0.0");
    }

    //遍历当前选中的商品 累加价格
    public double getTotalPrice() {
        double totalPrice = 0;
        for (int i = 0; i < carts.size(); i++) {
            GoodBean bean = carts.get(i);
            if (bean.isCheckd()) {
                //当前选中的条目才去累加价格
                totalPrice = totalPrice + Double.parseDouble(bean.getCover_price()) * bean.getNumber();
            }
        }
        return totalPrice;
    }

    public void checkAll() {
        int number = 0;
        if (carts != null && carts.size() > 0) {
            for (int i = 0; i < carts.size(); i++) {
                GoodBean bean = carts.get(i);
                if (!bean.isCheckd()) {
                    //有一个未选中就设置全选为未选中
                    checkboxAll.setChecked(false);
                    cbAll.setChecked(false);
                } else {
                    //记录当前选中的条目
                    number++;
                }
            }
        } else {
            checkboxAll.setChecked(false);
            cbAll.setChecked(false);
        }

        //如果当前选中条目为总大小，则设置全选为选中
        if (number == carts.size()) {
            checkboxAll.setChecked(true);
            cbAll.setChecked(true);
        }
    }

    //item点击事件
    public interface OnItemClickListener {
        void onItemClick(View view, int position);

    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    private OnItemClickListener mOnItemClickListener;
}
