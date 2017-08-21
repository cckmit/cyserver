package com.cy.core.weiXin.entity;

import com.cy.base.entity.DataEntity;

/**
 * Created by cha0res on 12/13/16.
 */
public class WeiXinMenu extends DataEntity {
    private static final long serialVersionUID = 1L;
    private String name;		// 菜单名称
    private String parentId;		// 父级编号
    private String parentIds;		// 所有父级编号
    private String menuKey;		// 菜单唯一标识
    private String msgType;		// 消息类型（10：文本；20：图文；30：扩展 ）
    private String templateId;// 模板编号
    private String catalogId;
    private String type;		// 类型（10：消息触发类；20：网页链接类）
    private String url;		// 网页链接
    private String fatherId;		// 父级菜单编号
    private String accountId;		// account_id
    private String sort;		// 排序
    private String parentName;  //上级菜单
    private String accountAppId;  //公众帐号APPID
    private String accountName;  // 公众号名称
    private String content;        // 文本内容
    private String isNull;   //是否可以修改为空标志 1 是 其他不可以
    private String isOutSide;   //是否外部連接

    public String getAccountAppId() {
        return accountAppId;
    }

    public void setAccountAppId(String accountAppId) {
        this.accountAppId = accountAppId;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getParentIds() {
        return parentIds;
    }

    public void setParentIds(String parentIds) {
        this.parentIds = parentIds;
    }

    public String getMenuKey() {
        return menuKey;
    }

    public void setMenuKey(String menuKey) {
        this.menuKey = menuKey;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
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

    public String getFatherId() {
        return fatherId;
    }

    public void setFatherId(String fatherId) {
        this.fatherId = fatherId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getCatalogId() {
        return catalogId;
    }

    public void setCatalogId(String catalogId) {
        this.catalogId = catalogId;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getIsNull() {
        return isNull;
    }

    public void setIsNull(String isNull) {
        this.isNull = isNull;
    }

    public String getIsOutSide() {
        return isOutSide;
    }

    public void setIsOutSide(String isOutSide) {
        this.isOutSide = isOutSide;
    }
}
