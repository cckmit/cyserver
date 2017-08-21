package com.cy.core.news.entity;

import com.cy.base.entity.DataGrid;

/**
 * Created by cha0res on 5/8/17.
 */
public class DataGridNews<T> extends DataGrid<T> {
    private String categoryCode;
    private String categoryId;
    private String categoryName;

    public String getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
