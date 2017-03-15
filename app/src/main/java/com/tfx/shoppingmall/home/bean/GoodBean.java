package com.tfx.shoppingmall.home.bean;

import java.io.Serializable;

/**
 * Created by Tfx on 2016/12/2.
 * 商品信息bean对象 实现Serializable为了传递消息直接传递GoodBean对象
 */

public class GoodBean implements Serializable {
    String cover_price;
    String figure;
    String name;
    int maxNumber = 5;
    int product_id;
    int number = 1;
    boolean isCheckd = true;

    public boolean isCheckd() {
        return isCheckd;
    }

    public void setCheckd(boolean checkd) {
        isCheckd = checkd;
    }

    public int getMaxNumber() {
        return maxNumber;
    }

    public void setMaxNumber(int maxNumber) {
        this.maxNumber = maxNumber;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getCover_price() {
        return cover_price;
    }

    public void setCover_price(String cover_price) {
        this.cover_price = cover_price;
    }

    public String getFigure() {
        return figure;
    }

    public void setFigure(String figure) {
        this.figure = figure;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    @Override
    public String toString() {
        return "GoodBean{" +
                "cover_price='" + cover_price + '\'' +
                ", figure='" + figure + '\'' +
                ", name='" + name + '\'' +
                ", product_id='" + product_id + '\'' +
                ", number=" + number +
                '}';
    }
}
