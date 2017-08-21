package com.cy.core.discover.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Mr.wu on 2017/3/14.
 */
public class DiscoverContent implements Serializable {
    private static final long serialVersionUID = 1L;
    /*
    * id 用户发送的消息的id
    */
    private String id;
    /*
    * comment 用户发送的消息
    */
    private String discoverContent;

    /*
    * contentType 发表内容的可见性
    */
    private String contentType;

    /*
    * discoverId 用户id
    */
    private String discoverId;

    /*
    * createTime 发表时间
    */
    private String createTime;
    /*
    * pic 图片url
    */
    private String discoverPic;

    /*
    * discoverType 发现的数据类型
    * 1：朋友圈
    * 2：活动
    * 3：招聘
    * 4：其他
    */
    private String discoverType;

    /*
    * delFlag 删除标识
    */
    private String delFlag;


    /*
    * userPic 用户图像
    */
    private String picture;

    /*
    * userName 用户姓名
    */
    private String name;

    /*
    * praiseNum 点赞数
    */
    private long praiseNum;

    /*
    * praiseFlag 是否点赞 true 已赞， false 未赞
    */
    private boolean praiseFlag;

    /*
    * discoverCommentList 评论列表
    */
//    private List<DiscoverComment> discoverCommentList;//评论列表

    /*
    * discoverReplyCommentList 评论回复列表
    */
    private List<DiscoverReplyComment> discoverReplyCommentList;//评论回复列表

    /*
    * discoverPic 照片列表
    */
    private List<DiscoverPicture> discoverPicList;//照片列表

    public String getDiscoverId() {
        return discoverId;
    }

    public void setDiscoverId(String discoverId) {
        this.discoverId = discoverId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getDiscoverType() {
        return discoverType;
    }

    public void setDiscoverType(String discoverType) {
        this.discoverType = discoverType;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDiscoverPic() {
        return discoverPic;
    }

    public void setDiscoverPic(String discoverPic) {
        this.discoverPic = discoverPic;
    }

    public String getDiscoverContent() {
        return discoverContent;
    }

    public void setDiscoverContent(String discoverContent) {
        this.discoverContent = discoverContent;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getPraiseNum() {
        return praiseNum;
    }

    public void setPraiseNum(long praiseNum) {
        this.praiseNum = praiseNum;
    }

//    public List<DiscoverComment> getDiscoverCommentList() {
//        return discoverCommentList;
//    }
//
//    public void setDiscoverCommentList(List<DiscoverComment> discoverCommentList) {
//        this.discoverCommentList = discoverCommentList;
//    }

    public List<DiscoverPicture> getDiscoverPicList() {
        return discoverPicList;
    }

    public void setDiscoverPicList(List<DiscoverPicture> discoverPicList) {
        this.discoverPicList = discoverPicList;
    }

    public boolean isPraiseFlag() {
        return praiseFlag;
    }

    public void setPraiseFlag(boolean praiseFlag) {
        this.praiseFlag = praiseFlag;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public List<DiscoverReplyComment> getDiscoverReplyCommentList() {
        return discoverReplyCommentList;
    }

    public void setDiscoverReplyCommentList(List<DiscoverReplyComment> discoverReplyCommentList) {
        this.discoverReplyCommentList = discoverReplyCommentList;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }
}
