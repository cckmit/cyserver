package com.cy.core.news.entity;

import com.cy.base.entity.TreeString;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * USER jiangling
 * DATE 29/03/2017
 * To change this template use File | Settings |File Templates.
 */
public class TreeForNewsType extends TreeString {
    private String type;
    private String newsId;
    private String url;
    private String code;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNewsId() {
        return newsId;
    }

    public void setNewsId(String newsId) {
        this.newsId = newsId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
