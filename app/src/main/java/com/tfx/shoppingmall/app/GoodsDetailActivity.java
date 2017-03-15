package com.tfx.shoppingmall.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tfx.shoppingmall.R;
import com.tfx.shoppingmall.cart.utils.CartProvider;
import com.tfx.shoppingmall.home.bean.GoodBean;
import com.tfx.shoppingmall.utils.Constant;

/**
 * Created by Tfx on 2016/12/2.
 * 商品详细页面
 */
public class GoodsDetailActivity extends Activity implements View.OnClickListener {
    private ImageButton ibGoodInfoBack;
    private ImageButton ibGoodInfoMore;
    private ImageView ivGoodInfoImage;
    private TextView tvGoodInfoName;
    private TextView tvGoodInfoDesc;
    private TextView tvGoodInfoPrice;
    private TextView tvGoodInfoStore;
    private TextView tvGoodInfoStyle;
    private WebView wbGoodInfoMore;
    private LinearLayout llGoodsRoot;
    private TextView tvGoodInfoCallcenter;
    private TextView tvGoodInfoCollection;
    private TextView tvGoodInfoCart;
    private Button btnGoodInfoAddcart;
    private GoodBean mGoodBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }

    private void initData() {
        //获取传递的数据
        mGoodBean = (GoodBean) getIntent().getSerializableExtra("goodbean");
        //设置数据
        tvGoodInfoName.setText(mGoodBean.getName());
        tvGoodInfoPrice.setText("￥" + mGoodBean.getCover_price());
        Glide.with(getApplicationContext()).load(Constant.IMGURL + mGoodBean.getFigure()).into(ivGoodInfoImage);
        initWebView();
    }


    private void initView() {
        setContentView(R.layout.activity_gooddetail);
        ibGoodInfoBack = (ImageButton) findViewById(R.id.ib_good_info_back);
        ibGoodInfoMore = (ImageButton) findViewById(R.id.ib_good_info_more);
        ivGoodInfoImage = (ImageView) findViewById(R.id.iv_good_info_image);
        tvGoodInfoName = (TextView) findViewById(R.id.tv_good_info_name);
        tvGoodInfoDesc = (TextView) findViewById(R.id.tv_good_info_desc);
        tvGoodInfoPrice = (TextView) findViewById(R.id.tv_good_info_price);
        tvGoodInfoStore = (TextView) findViewById(R.id.tv_good_info_store);
        tvGoodInfoStyle = (TextView) findViewById(R.id.tv_good_info_style);
        wbGoodInfoMore = (WebView) findViewById(R.id.wb_good_info_more);
        llGoodsRoot = (LinearLayout) findViewById(R.id.ll_goods_root);
        tvGoodInfoCallcenter = (TextView) findViewById(R.id.tv_good_info_callcenter);
        tvGoodInfoCollection = (TextView) findViewById(R.id.tv_good_info_collection);
        tvGoodInfoCart = (TextView) findViewById(R.id.tv_good_info_cart);
        btnGoodInfoAddcart = (Button) findViewById(R.id.btn_good_info_addcart);

        ibGoodInfoBack.setOnClickListener(this);
        ibGoodInfoMore.setOnClickListener(this);
        btnGoodInfoAddcart.setOnClickListener(this);
        tvGoodInfoCallcenter.setOnClickListener(this);
    }

    //点击事件
    @Override
    public void onClick(View v) {
        if (v == ibGoodInfoBack) {
            //返回
            finish();
        } else if (v == ibGoodInfoMore) {
            //更多
            llGoodsRoot.setVisibility(View.VISIBLE);
        } else if (v == btnGoodInfoAddcart) {
            //加入购物车
            CartProvider cart = CartProvider.getInstance();
            cart.add(mGoodBean);
        } else if (v == tvGoodInfoCallcenter) {
            //客服
            startActivity(new Intent(this, ServiceActivity.class));
        } else if (v == tvGoodInfoCallcenter) {
            //收藏
        } else if (v == tvGoodInfoCart) {
            //购物车
        }
    }

    private void initWebView() {
        //WebView加载web资源
        wbGoodInfoMore.loadUrl(Constant.GoodDetail);
        //覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
        wbGoodInfoMore.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }
        });
        //启用支持javascript
        WebSettings settings = wbGoodInfoMore.getSettings();
        settings.setJavaScriptEnabled(true);
        //优先使用缓存
        wbGoodInfoMore.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        // 设置可以支持缩放
        wbGoodInfoMore.getSettings().setSupportZoom(true);
        // 设置出现缩放工具
        wbGoodInfoMore.getSettings().setBuiltInZoomControls(true);
        //扩大比例的缩放
        wbGoodInfoMore.getSettings().setUseWideViewPort(true);
        //自适应屏幕
        wbGoodInfoMore.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        wbGoodInfoMore.getSettings().setLoadWithOverviewMode(true);
    }
}
