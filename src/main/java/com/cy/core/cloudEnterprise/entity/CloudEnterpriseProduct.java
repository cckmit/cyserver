package com.cy.core.cloudEnterprise.entity;

import com.cy.base.entity.DataEntity;
import com.cy.common.utils.StringUtils;
import com.cy.core.share.entity.File;
import com.cy.system.Global;

import java.util.List;

/**
 * Created by cha0res on 1/6/17.
 */
public class CloudEnterpriseProduct extends DataEntity<CloudEnterpriseProduct> {
    private static final long serialVersionUID = 1L;

    private String name;            // 产品名称
    private String enterpriseId;    // 所属校企编号
    private String posterPic;       // 海报图路径
    private String slogan;          // 标语
    private String description	;   // 产品详情
    private String type;            // 产品类型
    private String summary;         // 产品简介（限长500)
    private String clickNumber	;   // 点击量（浏览数)
    private String enterpriseName;  // 企业名称
    private String typeName;        // 类型名称
    private String praiseNumber;   //点赞数量
    private String hasPraised;      //是否点赞
    private String enterpriseProductUrl;//企业产品详情url
    private List<File> fileList;  // 页面图片列表
    private String status;   //产品状态
    private String posterIds;  //海报编号集合
    private String cloudProductId; //云平台企业产品编号

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(String enterpriseId) {
        this.enterpriseId = enterpriseId;
    }


    public String getSlogan() {
        return slogan;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getClickNumber() {
        if(StringUtils.isBlank(clickNumber)){
            clickNumber = "0";
        }
        return clickNumber;
    }

    public void setClickNumber(String clickNumber) {
        this.clickNumber = clickNumber;
    }

    public String getEnterpriseName() {
        return enterpriseName;
    }

    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }


    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getPraiseNumber() {
        return praiseNumber;
    }

    public void setPraiseNumber(String praiseNumber) {
        this.praiseNumber = praiseNumber;
    }

    public String getHasPraised() {
        return hasPraised;
    }

    public void setHasPraised(String hasPraised) {
        this.hasPraised = hasPraised;
    }




    public String getEnterpriseProductUrl() {
        return enterpriseProductUrl;
    }

    public void setEnterpriseProductUrl(String enterpriseProductUrl) {
        this.enterpriseProductUrl = enterpriseProductUrl;
    }


    public List<File> getFileList() {
        return fileList;
    }

    public void setFileList(List<File> fileList) {
        this.fileList = fileList;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPosterIds() {
        return posterIds;
    }

    public void setPosterIds(String posterIds) {
        this.posterIds = posterIds;
    }

    public String getCloudProductId() {
        return cloudProductId;
    }

    public void setCloudProductId(String cloudProductId) {
        this.cloudProductId = cloudProductId;
    }

    public String getPosterPic() {
        return posterPic;
    }

    public void setPosterPic(String posterPic) {
        this.posterPic = posterPic;
    }
}
