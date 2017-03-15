package com.tfx.shoppingmall.app;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.tfx.shoppingmall.R;
import com.tfx.shoppingmall.home.bean.H5Bean;
import com.tfx.shoppingmall.utils.Constant;

/**
 * @author Tfx
 * @date 2017/1/6 12:40
 * @company 有梦不难
 * @email tfx919@163.com
 * @desc xxx
 */
public class WebViewActivity extends Activity {
    private WebView mWebView;

    private ImageView mWebviewIv;
    private TextView mWebviewTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
        initEvent();
    }

    private void initEvent() {
        mWebviewIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initData() {
        H5Bean h5Data = (H5Bean) getIntent().getSerializableExtra("h5");
        mWebviewTv.setText(h5Data.getTitle());
        initWebView(h5Data);
    }

    private void initView() {
        setContentView(R.layout.activity_webview);
        mWebviewIv = (ImageView) findViewById(R.id.webview_iv);
        mWebviewTv = (TextView) findViewById(R.id.webview_tv);
        mWebView = (WebView) findViewById(R.id.webview_wv);
    }

    private void initWebView(H5Bean h5Data) {
        //WebView加载web资源
        mWebView.loadUrl(Constant.IMGURL + h5Data.getUrl());
        //覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }
        });
        //启用支持javascript
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        //优先使用缓存
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        // 设置可以支持缩放
        mWebView.getSettings().setSupportZoom(true);
        // 设置出现缩放工具
        mWebView.getSettings().setBuiltInZoomControls(true);
        //扩大比例的缩放
        mWebView.getSettings().setUseWideViewPort(true);
        //自适应屏幕
        mWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        mWebView.getSettings().setLoadWithOverviewMode(true);
    }
}
