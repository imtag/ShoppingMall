package com.tfx.shoppingmall.app;

import android.app.Application;
import android.content.Context;

import com.zhy.http.okhttp.OkHttpUtils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by Tfx on 2016/12/1.
 */

public class MyApplication extends Application {
    private static Context mContext;

    @Override
    public void onCreate() {
        this.mContext = getApplicationContext();

        //okhttp初始化
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                //其他配置
                .build();
        OkHttpUtils.initClient(okHttpClient);
    }

    public static Context getAppContext() {
        return mContext;
    }
}
