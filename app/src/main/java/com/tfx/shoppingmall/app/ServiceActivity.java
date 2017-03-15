package com.tfx.shoppingmall.app;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.tfx.shoppingmall.R;

/**
 * @author Tfx
 * @date 2017/3/5 17:13
 * @company 有梦不难
 * @email tfx919@163.com
 * @desc xxx
 */
public class ServiceActivity extends Activity {
    WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
        initView();
        initWebView();
    }

    private void initWebView() {
        //WebView加载web资源
        mWebView.loadUrl("http://www6.53kf.com/webCompany.php?arg=10007377&style=2&kflist=off&kf=info@atguigu.com,video@atguigu.com,public@atguigu.com,3069368606@qq.com,215648937@qq.com,sudan@atguigu.com,wangya@atguigu.com&zdkf_type=1&language=zh-cn&charset=gbk&referer=http%3A%2F%2Fwww.atguigu.com%2F&keyword=https%3A%2F%2Fwww.baidu.com%2Flink%3Furl%3DzeCGq6Hqqh-VmRO0rl6x1OIqHQOu8bj0YxuetTqGsDM-ttp7sMAqU8SsE2Wtav9N%26wd%3D%26eqid%3Dcb6b5f64003506c30000000358bbd8d1&tfrom=1&tpl=crystal_blue&uid=1f9319bc6419f411a2a3ca8257deba6c&timeStamp=1488705977852&ucust_id=");

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

        //自适应屏幕
        mWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        mWebView.getSettings().setLoadWithOverviewMode(true);
    }

    private void initView() {
        mWebView = (WebView) findViewById(R.id.wb_service);
    }
}
