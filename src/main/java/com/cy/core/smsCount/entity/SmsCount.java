package com.cy.core.smsCount.entity;

import java.io.Serializable;

/**
 * Created by Cha0res on 2016/8/28.
 */
public class SmsCount implements Serializable {
    private static final long serialVersionUID = 1L;

    private String title;
    private String value;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
