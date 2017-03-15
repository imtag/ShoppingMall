package com.tfx.shoppingmall.home.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tfx.shoppingmall.R;
import com.tfx.shoppingmall.home.bean.ResultBeanData;
import com.tfx.shoppingmall.utils.Constant;

import java.util.List;

/**
 * Created by Tfx on 2016/12/2.
 */
public class SeckillRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final Context mContext;
    private final List<ResultBeanData.ResultBean.SeckillInfoBean.ListBean> seckill_info;

    public SeckillRecyclerViewAdapter(Context context, ResultBeanData.ResultBean.SeckillInfoBean seckill_info) {
        this.mContext = context;
        this.seckill_info = seckill_info.getList();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_seckill, null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        //绑定数据
        MyViewHolder myViewHolder = (MyViewHolder) holder;
        myViewHolder.tvOriginPrice.setText(seckill_info.get(position).getOrigin_price());
        myViewHolder.tvCoverPrice.setText(seckill_info.get(position).getCover_price());
        Glide.with(mContext).load(Constant.IMGURL + seckill_info.get(position).getFigure()).into(myViewHolder.ivFigure);

        //图片点击事件
        myViewHolder.ivFigure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //回调position给首页RecycleView
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemCLick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return seckill_info.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView ivFigure;
        TextView tvCoverPrice;
        TextView tvOriginPrice;

        public MyViewHolder(View itemView) {
            super(itemView);
            ivFigure = (ImageView) itemView.findViewById(R.id.iv_figure);
            tvCoverPrice = (TextView) itemView.findViewById(R.id.tv_cover_price);
            tvOriginPrice = (TextView) itemView.findViewById(R.id.tv_origin_price);
        }
    }

    public interface OnItemClickListener {
        void onItemCLick(int position);
    }

    private OnItemClickListener mOnItemClickListener;

    //条目点击回调给首页SeckillViewHolder
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }
}
