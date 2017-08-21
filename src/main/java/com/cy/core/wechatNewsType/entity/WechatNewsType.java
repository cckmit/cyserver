package com.cy.core.wechatNewsType.entity;

import java.io.Serializable;

/**
 * <p>Title: 微信新闻类</p>
 * <p>Description: </p>
 * USER jiangling
 * DATE 8/9/16
 * To change this template use File | Settings |File Templates.
 */
public class WechatNewsType implements Serializable {

    private static final long serialVersionUID = 1L;

    private long id;            //微信新闻id
    private long parentId;      //父id: 上级栏目
    private String name;        //新闻名称
    private String type;        //??(1.新闻类别,2.链接，3.单页面)
    private String url;         //新闻网址
    private String orderNum;     //排序编号
    private String origin;         //新闻来源
    private String cityName;	//新增所在地
    private int isNavigation; // 是否上导航（0：不上导航， 1：上导航）
    private String parentName;   //父级的名字
    private String deptId;      //创建组织Id
    private String deptName;    //创建组织

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getParentId() {
        return parentId;
    }

    public void setParentId(long parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public int getIsNavigation() {
        return isNavigation;
    }

    public void setIsNavigation(int isNavigation) {
        this.isNavigation = isNavigation;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }
}
