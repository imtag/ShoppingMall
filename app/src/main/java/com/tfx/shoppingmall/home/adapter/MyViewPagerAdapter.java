package com.tfx.shoppingmall.home.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.tfx.shoppingmall.home.bean.ResultBeanData;
import com.tfx.shoppingmall.utils.Constant;

import java.util.List;

/**
 * @author Tfx
 * @date 2017/1/5 20:49
 * @company 有梦不难
 * @email tfx919@163.com
 * @desc xxx
 */
//viewpager适配器
class MyViewPagerAdapter extends PagerAdapter {

    private final List<ResultBeanData.ResultBean.ActInfoBean> act_info;
    private final Context mContext;

    public MyViewPagerAdapter(Context context, List<ResultBeanData.ResultBean.ActInfoBean> act_info) {
        this.act_info = act_info;
        mContext = context;
    }

    @Override
    public int getCount() {
        if (act_info != null) {
            return act_info.size();
        }
        return 0;
    }

    //判断instantiateItem的view是否是当前要显示的
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    //初始化条目
    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        ImageView iv = new ImageView(mContext);
        iv.setScaleType(ImageView.ScaleType.FIT_XY);
        container.addView(iv);
        Glide.with(mContext).load(Constant.IMGURL + act_info.get(position).getIcon_url()).into(iv);

        //把索引传给自定义接口回调
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemCLick(position);
                }
            }
        });

        return iv;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }


    /*
     * 自定义接口回调
     */
    public interface OnItemClickListener {
        void onItemCLick(int position);
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }
}