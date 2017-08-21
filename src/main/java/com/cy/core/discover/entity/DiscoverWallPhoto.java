package com.cy.core.discover.entity;

import java.io.Serializable;

/**
 * Created by Mr.wu on 2017/3/14.
 */
public class DiscoverWallPhoto implements Serializable {
    private static final long serialVersionUID = 1L;

    /*
    * wallPhoto 用户背景墙图片
    */
    private String wallPhoto;

    public String getWallPhoto() {
        return wallPhoto;
    }

    public void setWallPhoto(String wallPhoto) {
        this.wallPhoto = wallPhoto;
    }
}
