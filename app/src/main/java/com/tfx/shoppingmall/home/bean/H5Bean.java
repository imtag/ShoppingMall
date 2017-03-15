package com.tfx.shoppingmall.home.bean;

import java.io.Serializable;

/**
 * @author Tfx
 * @date 2017/1/5 20:57
 * @company 有梦不难
 * @email tfx919@163.com
 * @desc xxx
 */
public class H5Bean implements Serializable {
    String title;
    String url;

    public String getTitle() {
        return title;
    }

    public void setTitle(String titel) {
        this.title = titel;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
