package com.tfx.shoppingmall.home.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.tfx.shoppingmall.R;
import com.tfx.shoppingmall.app.GoodsDetailActivity;
import com.tfx.shoppingmall.app.WebViewActivity;
import com.tfx.shoppingmall.home.bean.GoodBean;
import com.tfx.shoppingmall.home.bean.H5Bean;
import com.tfx.shoppingmall.home.bean.ResultBeanData;
import com.tfx.shoppingmall.utils.Constant;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerClickListener;
import com.youth.banner.listener.OnLoadImageListener;
import com.zhy.magicviewpager.transformer.AlphaPageTransformer;
import com.zhy.magicviewpager.transformer.ScaleInTransformer;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tfx on 2016/12/1.
 * 首页Fragment的recycleIVew的适配器
 */
public class HomeRecyleViewAdapter extends RecyclerView.Adapter {
    private final ResultBeanData.ResultBean mResultBean;
    private final Context mContext;
    private int currentType = BANNER; //当前类型
    /**
     * 横幅广告
     */
    public static final int BANNER = 0;
    /**
     * 频道
     */
    public static final int CHANNEL = 1;

    /**
     * 活动
     */
    public static final int ACT = 2;

    /**
     * 秒杀
     */
    public static final int SECKILL = 3;
    /**
     * 推荐
     */
    public static final int RECOMMEND = 4;
    /**
     * 热卖
     */
    public static final int HOT = 5;
    private final LayoutInflater mLayoutInflater;

    public HomeRecyleViewAdapter(Context context, ResultBeanData.ResultBean resultBean) {
        this.mContext = context;
        this.mResultBean = resultBean;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    //根据position得到类型
    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case BANNER:
                currentType = BANNER;
                break;
            case CHANNEL:
                currentType = CHANNEL;
                break;
            case ACT:
                currentType = ACT;
                break;
            case SECKILL:
                currentType = SECKILL;
                break;
            case RECOMMEND:
                currentType = RECOMMEND;
                break;
            case HOT:
                currentType = HOT;
                break;
        }
        return currentType;
    }

    //条目数量,总共6种类型
    @Override
    public int getItemCount() {
        return 6;
    }

    //类似getView方法 返回自定义的VIewHolder viewType当前类型
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == BANNER) {
            return new BannerViewHolder(mLayoutInflater.inflate(R.layout.banner_layout, null));
        } else if (viewType == CHANNEL) {
            return new ChannelViewHolder(mLayoutInflater.inflate(R.layout.channel_layout, null));
        } else if (viewType == ACT) {
            return new ActViewHolder(mLayoutInflater.inflate(R.layout.act_layout, null));
        } else if (viewType == SECKILL) {
            return new SeckillViewHolder(mLayoutInflater.inflate(R.layout.seckill_layout, null));
        } else if (viewType == RECOMMEND) {
            return new RecommendViewHolder(mLayoutInflater.inflate(R.layout.recommend_layout, null));
        } else if (viewType == HOT) {
            return new HotViewHolder(mLayoutInflater.inflate(R.layout.hot_layout, null));
        }
        return null;
    }

    //绑定viewholder数据 holder:当前类型的ViewHolder positon：当前类型的位置
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == BANNER) {
            BannerViewHolder bannerViewHolder = (BannerViewHolder) holder;
            bannerViewHolder.setData(mResultBean.getBanner_info());
        } else if (getItemViewType(position) == CHANNEL) {
            ChannelViewHolder bannerViewHolder = (ChannelViewHolder) holder;
            bannerViewHolder.setData(mResultBean.getChannel_info());
        } else if (getItemViewType(position) == ACT) {
            ActViewHolder actViewHolder = (ActViewHolder) holder;
            actViewHolder.setData(mResultBean.getAct_info());
        } else if (getItemViewType(position) == SECKILL) {
            SeckillViewHolder seckillViewHolder = (SeckillViewHolder) holder;
            seckillViewHolder.setData(mResultBean.getSeckill_info());
        } else if (getItemViewType(position) == RECOMMEND) {
            RecommendViewHolder recommendViewHolder = (RecommendViewHolder) holder;
            recommendViewHolder.setData(mResultBean.getRecommend_info());
        } else if (getItemViewType(position) == HOT) {
            HotViewHolder hotViewHolder = (HotViewHolder) holder;
            hotViewHolder.setData(mResultBean.getHot_info());
        }
    }

    //hot的viewholder
    private class HotViewHolder extends RecyclerView.ViewHolder {

        private final GridView mGv;
        private final TextView mTv;

        public HotViewHolder(View itemView) {
            super(itemView);
            mGv = (GridView) itemView.findViewById(R.id.gv_hot);
            mTv = (TextView) itemView.findViewById(R.id.tv_more_hot);
        }

        public void setData(final List<ResultBeanData.ResultBean.HotInfoBean> hot_info) {

            mGv.setAdapter(new HotGridViewAdapter(mContext, hot_info));
            //条目点击事件
            mGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    //封装商品详细信息bean
                    GoodBean bean = new GoodBean();
                    ResultBeanData.ResultBean.HotInfoBean infoBean = hot_info.get(position);
                    bean.setFigure(infoBean.getFigure());
                    bean.setName(infoBean.getName());
                    bean.setCover_price(infoBean.getCover_price());
                    bean.setProduct_id(infoBean.getProduct_id());

                    //开启activity 并携带数据
                    Intent intent = new Intent(mContext, GoodsDetailActivity.class);
                    intent.putExtra("goodbean", bean);
                    mContext.startActivity(intent);
                }
            });
        }

    }

    //recommend的viewholder
    private class RecommendViewHolder extends RecyclerView.ViewHolder {

        private final GridView mGv;
        private final TextView mTvMore;

        public RecommendViewHolder(View itemView) {
            super(itemView);
            mGv = (GridView) itemView.findViewById(R.id.gv_recommend);
            mTvMore = (TextView) itemView.findViewById(R.id.tv_more_recommend);
        }

        public void setData(final List<ResultBeanData.ResultBean.RecommendInfoBean> data) {
            mGv.setAdapter(new RecommendGridViewAdapter(mContext, data));
            //条目点击
            mGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    //封装商品详细信息bean
                    GoodBean bean = new GoodBean();
                    ResultBeanData.ResultBean.RecommendInfoBean infoBean = data.get(position);
                    bean.setFigure(infoBean.getFigure());
                    bean.setName(infoBean.getName());
                    bean.setCover_price(infoBean.getCover_price());
                    bean.setProduct_id(infoBean.getProduct_id());

                    //开启activity 并携带数据
                    Intent intent = new Intent(mContext, GoodsDetailActivity.class);
                    intent.putExtra("goodbean", bean);
                    mContext.startActivity(intent);
                }
            });
            //更多
            mTvMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(mContext, "更多推荐商品", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    //seckill的viewholder
    private class SeckillViewHolder extends RecyclerView.ViewHolder {
        ResultBeanData.ResultBean.SeckillInfoBean seckill_infos;
        private final TextView mTvMore;
        private final TextView mTime;
        private final RecyclerView mRv;

        public SeckillViewHolder(View itemView) {
            super(itemView);
            mTvMore = (TextView) itemView.findViewById(R.id.tv_more_seckill);
            mTime = (TextView) itemView.findViewById(R.id.tv_time_seckill);
            mRv = (RecyclerView) itemView.findViewById(R.id.rv_seckill);
        }

        public void setData(final ResultBeanData.ResultBean.SeckillInfoBean seckill_info) {
            seckill_infos = seckill_info;
            //更多按钮
            mTvMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(mContext, "更多", Toast.LENGTH_SHORT).show();
                }
            });

            //recycleView布局管理器 参数三：是否倒序
            mRv.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
            //recycleVIew适配器
            SeckillRecyclerViewAdapter adapter = new SeckillRecyclerViewAdapter(mContext, seckill_info);
            mRv.setAdapter(adapter);

            adapter.setOnItemClickListener(new SeckillRecyclerViewAdapter.OnItemClickListener() {
                @Override
                public void onItemCLick(int position) {
                    ResultBeanData.ResultBean.SeckillInfoBean.ListBean bean = seckill_info.getList().get(position);
                    GoodBean goodBean = new GoodBean();
                    goodBean.setProduct_id(bean.getProduct_id());
                    goodBean.setName(bean.getName());
                    goodBean.setCover_price(bean.getCover_price());
                    goodBean.setFigure(bean.getFigure());
                    mContext.startActivity(new Intent(mContext, GoodsDetailActivity.class).putExtra("goodbean", goodBean));
                }
            });

            //秒杀倒计时触发
            td = Integer.valueOf(seckill_infos.getEnd_time()) - Integer.valueOf(seckill_infos.getEnd_time());
            mHandler.sendEmptyMessageDelayed(0, 1000);
        }

        int td;
        Handler mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 0) {
                    td = td - 1000;
                    //格式化时间
                    SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
                    String timeStr = sdf.format(td);
                    mTime.setText(timeStr);
                    //移除历史消息 继续发送消息
                    mHandler.removeCallbacksAndMessages(0);
                    mHandler.sendEmptyMessageDelayed(0, 1000);
                }
            }
        };
    }

    //act的viewholder
    private class ActViewHolder extends RecyclerView.ViewHolder {

        private final ViewPager mVp;

        public ActViewHolder(View itemView) {
            super(itemView);
            mVp = (ViewPager) itemView.findViewById(R.id.vp_act);
        }

        public void setData(final List<ResultBeanData.ResultBean.ActInfoBean> act_info) {
            MyViewPagerAdapter adapter = new MyViewPagerAdapter(mContext, act_info);
            mVp.setAdapter(adapter);

            //使用maigicviewpager 实现图片切换动画
            mVp.setPageMargin(20);//设置page间间距，自行根据需求设置
            mVp.setOffscreenPageLimit(3);//>=3
            //setPageTransformer 决定动画效果  这里有scale和alpha两个效果
            mVp.setPageTransformer(true, new ScaleInTransformer(new AlphaPageTransformer()));

            //自定义条目点击事件
            adapter.setOnItemClickListener(new MyViewPagerAdapter.OnItemClickListener() {
                @Override
                public void onItemCLick(int position) {
                    ResultBeanData.ResultBean.ActInfoBean bean = act_info.get(position);
                    H5Bean h5Bean = new H5Bean();
                    h5Bean.setTitle(bean.getName());
                    h5Bean.setUrl(bean.getUrl());
                    mContext.startActivity(new Intent(mContext, WebViewActivity.class).putExtra("h5", h5Bean));
                }
            });
        }
    }

    //channel的viewholder
    private class ChannelViewHolder extends RecyclerView.ViewHolder {

        private final GridView mGv;
        private List<ResultBeanData.ResultBean.ChannelInfoBean> channel_info;

        public ChannelViewHolder(View itemView) {
            super(itemView);
            mGv = (GridView) itemView.findViewById(R.id.gv_channel);
        }

        public void setData(final List<ResultBeanData.ResultBean.ChannelInfoBean> channel_info) {
            this.channel_info = channel_info;
            mGv.setAdapter(new MyGridViewAdapter());
            //条目点击事件
            mGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    Toast.makeText(mContext, "position:" + position, Toast.LENGTH_SHORT).show();
                }
            });
        }

        //gridview适配器
        class MyGridViewAdapter extends BaseAdapter {
            @Override
            public int getCount() {
                return channel_info.size();
            }

            @Override
            public Object getItem(int i) {
                return null;
            }

            @Override
            public long getItemId(int i) {
                return 0;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup viewGroup) {
                ViewHolder holder = null;
                if (convertView == null) {
                    //没缓存
                    convertView = mLayoutInflater.inflate(R.layout.item_channel, null);
                    holder = new ViewHolder();
                    holder.iv = (ImageView) convertView.findViewById(R.id.iv_channel);
                    holder.tv = (TextView) convertView.findViewById(R.id.tv_channel);
                    convertView.setTag(holder);
                } else {
                    //有缓存
                    holder = (ViewHolder) convertView.getTag();
                }
                //绑定viewholder数据
                holder.tv.setText(channel_info.get(position).getChannel_name());
                Glide.with(mContext).load(Constant.IMGURL + channel_info.get(position).getImage()).into(holder.iv);
                return convertView;
            }
        }

        class ViewHolder {
            ImageView iv;
            TextView tv;
        }

    }

    //banner的viewholder
    private class BannerViewHolder extends RecyclerView.ViewHolder {
        private final Banner banner;

        public BannerViewHolder(View itemView) {
            super(itemView);
            banner = (Banner) itemView.findViewById(R.id.banner);
        }

        public void setData(final List<ResultBeanData.ResultBean.BannerInfoBean> banner_info) {
            //图片url集合
            List<String> imageUris = new ArrayList<>();
            for (int i = 0; i < banner_info.size(); i++) {
                imageUris.add(banner_info.get(i).getImage());
            }
            banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
            banner.isAutoPlay(true);
            banner.setBannerAnimation(Transformer.Accordion);
            banner.setDelayTime(2500);

            //设置图片集合并加载图片
            banner.setImages(imageUris, new OnLoadImageListener() {
                @Override
                public void OnLoadImage(ImageView view, Object url) {
                    Glide.with(mContext).load(Constant.IMGURL + url).into(view);

                    System.out.println(Constant.IMGURL + url);

                }
            });

            //banner单击事件
            banner.setOnBannerClickListener(
                    new OnBannerClickListener() {
                        @Override
                        public void OnBannerClick(int position) {
                            //索引从1开始的 真实索引减一
                            int relPosition = position - 1;
                            GoodBean bean = new GoodBean();
                            //做假数据
                            if (relPosition == 0) {
                                bean.setProduct_id(101);
                                bean.setCover_price("1001");
                                bean.setName("在线课堂");
                            } else if (relPosition == 1) {
                                bean.setProduct_id(102);
                                bean.setCover_price("1002");
                                bean.setName("对不起没坐了");
                            } else if (relPosition == 2) {
                                bean.setProduct_id(103);
                                bean.setCover_price("1003");
                                bean.setName("撒贝宁主持");
                            }
                            bean.setFigure(banner_info.get(relPosition).getImage() + "");
                            mContext.startActivity(new Intent(mContext, GoodsDetailActivity.class).putExtra("goodbean", bean));
                        }
                    }
            );
        }
    }
}
