package com.tfx.shoppingmall.home.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tfx.shoppingmall.R;
import com.tfx.shoppingmall.home.bean.ResultBeanData;
import com.tfx.shoppingmall.utils.Constant;

import java.util.List;

/**
 * Created by Tfx on 2016/12/2.
 * 推荐条目的GridView适配器
 */
public class HotGridViewAdapter extends BaseAdapter {
    private final Context mContext;
    private final List<ResultBeanData.ResultBean.HotInfoBean> mData;

    public HotGridViewAdapter(Context context, List<ResultBeanData.ResultBean.HotInfoBean> data) {
        this.mData = data;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        if (mData != null) {
            return mData.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return mData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_hot, null);
            holder = new ViewHolder();
            holder.ivImg = (ImageView) convertView.findViewById(R.id.iv_hot);
            holder.tvName = (TextView) convertView.findViewById(R.id.tv_hot_name);
            holder.tvPrice = (TextView) convertView.findViewById(R.id.tv_hot_price);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        //绑定数据
        ResultBeanData.ResultBean.HotInfoBean ben = mData.get(position);
        holder.tvName.setText(ben.getName());
        holder.tvPrice.setText("￥" + ben.getCover_price());
        Glide.with(mContext).load(Constant.IMGURL + ben.getFigure()).into(holder.ivImg);

        return convertView;
    }

    static class ViewHolder {
        ImageView ivImg;
        TextView tvName;
        TextView tvPrice;
    }
}
