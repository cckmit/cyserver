package com.cy.core.discover.entity;

import java.io.Serializable;

/**
 * Created by Mr.wu on 2017/6/10.
 */
public class DiscoverComplaint implements Serializable {
    private static final long serialVersionUID = 1L;
    /*
    * complaintId 举报ID
    */
    private String complaintId;
    /*
    * contentId 说说ID
    */
    private String contentId;

    /*
    * complaintUserId 举报人的ID
    */
    private String complaintUserId;

    /*
    * beComplaintUserId 被举报人的ID
    */
    private String beComplaintUserId;

    /*
    * complaintType 举报类型
    * 1：色情低俗
    * 2：政治敏感
    * 3：违法
    * 4：广告
    * 5：病毒木马
    * 6：其他
    */
    private String complaintType;

    /*
    * complaintReason 举报原因
    */
    private String complaintReason;

    /*
    * createTime 举报时间
    */
    private String createTime;


    public String getComplaintId() {
        return complaintId;
    }

    public void setComplaintId(String complaintId) {
        this.complaintId = complaintId;
    }

    public String getContentId() {
        return contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }

    public String getComplaintUserId() {
        return complaintUserId;
    }

    public void setComplaintUserId(String complaintUserId) {
        this.complaintUserId = complaintUserId;
    }

    public String getBeComplaintUserId() {
        return beComplaintUserId;
    }

    public void setBeComplaintUserId(String beComplaintUserId) {
        this.beComplaintUserId = beComplaintUserId;
    }

    public String getComplaintType() {
        return complaintType;
    }

    public void setComplaintType(String complaintType) {
        this.complaintType = complaintType;
    }

    public String getComplaintReason() {
        return complaintReason;
    }

    public void setComplaintReason(String complaintReason) {
        this.complaintReason = complaintReason;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
