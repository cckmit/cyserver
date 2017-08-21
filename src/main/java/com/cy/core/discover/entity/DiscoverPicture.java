package com.cy.core.discover.entity;

import java.io.Serializable;

/**
 * Created by Mr.wu on 2017/3/14.
 */
public class DiscoverPicture implements Serializable {
    private static final long serialVersionUID = 1L;
    /*
    * url 图片url
    */
    private String url;

    /*
    * url_xd 图片url_xd
    */
    private String url_xd;

    /*
    * width 图片宽度
    */
    private int width;
    /*
    * height 图片高度
    */
    private int height;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getUrl_xd() {
        return url_xd;
    }

    public void setUrl_xd(String url_xd) {
        this.url_xd = url_xd;
    }
}
