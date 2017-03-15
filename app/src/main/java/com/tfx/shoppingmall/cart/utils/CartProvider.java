package com.tfx.shoppingmall.cart.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.SparseArray;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tfx.shoppingmall.app.MyApplication;
import com.tfx.shoppingmall.home.bean.GoodBean;
import com.tfx.shoppingmall.utils.CacheUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tfx on 2016/12/2.
 */

public class CartProvider {
    public static final String CART_JSON = "json_cart";
    private static CartProvider instance = null;
    private static Context mContext = null;
    //优化的集合 类似map集合
    private SparseArray<GoodBean> mData;

    private CartProvider(Context context) {
        mContext = context;
        mData = new SparseArray<>(100);
        listToSparse();
    }

    //单例获取CartProvider实例
    public static CartProvider getInstance() {
        if (instance == null) {
            //使用Application的context，不用activity的context，避免内存泄漏
            instance = new CartProvider(MyApplication.getAppContext());
        }
        return instance;
    }

    //将所有本地商品添加到SparseArray
    //因为程序关闭后再次打开，SparseArray会被清空，需要把缓存的购物车json转成List再存到SparseArray中，
    //程序重新启动，add数据时，同步的时候是去取sparseArray中的数据，转成list再转成json缓存到sp，所以需要这步
    private void listToSparse() {
        //遍历所有数据 添加到parseArray中
        List<GoodBean> beans = getAllFromLocal();
        if (beans != null && beans.size() > 0) {
            for (int i = 0; i < beans.size(); i++) {
                //key：int型  value：商品bean
                GoodBean bean = beans.get(i);
                mData.put(bean.getProduct_id(), bean);
            }
        }
    }

    //从本地获取所有购物车商品
    public List<GoodBean> getAllFromLocal() {
        //保存所有商品的list集合
        List<GoodBean> list = new ArrayList<>();

        //从本地sp获取缓存
        String saveJson = CacheUtils.getString(CART_JSON);
        if (!TextUtils.isEmpty(saveJson)) {
            //Gson泛型解析 json字符串转成list集合
            list = new Gson().fromJson(saveJson, new TypeToken<List<GoodBean>>() {
            }.getType());
        }
        return list;
    }

    //添加商品
    public void add(GoodBean bean) {
        //通过id在ParserArray中找goodbean
        GoodBean tempBean = mData.get(bean.getProduct_id());

        //判断当前商品是否添加过 是否已经存在SparseArray
        if (tempBean != null) {
            //添加过 数量+1
            bean.setNumber(tempBean.getNumber() + 1);
        }

        //添加到内存中（parseArray）  key 商品id  value GoodBean
        mData.put(bean.getProduct_id(), bean);

        //同步修改后的SparseArray到本地
        saveLocal();
    }

    //移除商品
    public void delete(GoodBean bean) {
        //内存中删除
        mData.delete(bean.getProduct_id());

        //同步修改后的SparseArray到本地
        saveLocal();
    }

    //修改商品
    public void update(GoodBean bean) {
        //修改
        mData.put(bean.getProduct_id(), bean);

        //同步修改后的SparseArray到本地
        saveLocal();
    }

    //同步修改后的SparseArray到本地  步骤：将sparse转成list 将list转成json json保存到sp
    private void saveLocal() {
        //1.遍历sparseArray 转成list
        List<GoodBean> carts = sparseToList();

        //使用Gson将list集合转成json字符串
        String json = new Gson().toJson(carts);

        //保存json
        CacheUtils.putString(CART_JSON, json);
    }

    //SparseArray转list
    private List<GoodBean> sparseToList() {
        //遍历sparseArray元素 添加到list集合
        List<GoodBean> lists = new ArrayList<>();
        for (int i = 0; i < mData.size(); i++) {
            GoodBean bean = mData.valueAt(i);
            lists.add(bean);
        }
        return lists;
    }
}
