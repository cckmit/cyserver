package com.cy.core.enterprise.entity;

import com.cy.base.entity.DataEntity;
import com.cy.common.utils.StringUtils;

import com.cy.core.share.entity.File;
import com.cy.system.Global;
import java.util.List;

/**
 * Created by cha0res on 1/6/17.
 */
public class EnterpriseProduct extends DataEntity<EnterpriseProduct> {
    private static final long serialVersionUID = 1L;

    private String name;            // 产品名称
    private String enterpriseId;    // 所属校企编号
    private String posterPic;       // 海报图路径
    private String posterPic_xd;       // 新加海报图路径
    private String posterPicUrl;    // 海报相对路径
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
    private String enterpriseProductUrl_xd;//企业产品详情相对url
    private List<File> fileList;  // 页面图片列表
    private String status;   //产品状态
    private String posterIds;  //海报编号集合
    private String cloudProductId; //云平台企业产品编号
    private String isCloud;      //条件查询（1：表示只查云平台同步过来的数据）

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

    public String getPosterPic() {
        if(org.apache.commons.lang3.StringUtils.isBlank(posterPic) && org.apache.commons.lang3.StringUtils.isNotBlank(posterPicUrl) && org.apache.commons.lang3.StringUtils.isNotBlank(Global.URL_DOMAIN)) {
            if(posterPicUrl.indexOf("http") < 0) {
                if (!posterPicUrl.trim().startsWith("/")){
                    posterPicUrl="/"+posterPicUrl;
                }
                posterPic = Global.URL_DOMAIN + posterPicUrl ;
            }else{
                posterPic=posterPicUrl;
            }
        }
        return posterPic;
    }

    public void setPosterPic(String posterPic) {
        this.posterPic = posterPic;
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

    public String getPosterPicUrl() {
        if(StringUtils.isBlank(posterPicUrl) && StringUtils.isNotBlank(posterPic) &&StringUtils.isNotBlank(Global.URL_DOMAIN)) {
            if(posterPic.indexOf(Global.URL_DOMAIN) == 0) {
                if (!posterPic.trim().startsWith("/")){
                    posterPic="/"+posterPic;
                }
                posterPicUrl = posterPic.substring(Global.URL_DOMAIN.length()) ;
            }else{
                posterPicUrl=posterPic;
            }
        }
        return posterPicUrl;
    }

    public void setPosterPicUrl(String posterPicUrl) {
        this.posterPicUrl = posterPicUrl;
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

    public String getPosterPic_xd() {
        if(StringUtils.isBlank(posterPic_xd) && StringUtils.isNotBlank(posterPic) &&StringUtils.isNotBlank(Global.URL_DOMAIN)) {
            if(posterPic.indexOf(Global.URL_DOMAIN) == 0) {
                posterPic_xd = posterPic.substring(Global.URL_DOMAIN.length()) ;
            }else{
                posterPic_xd=posterPic;
            }
        }
        return posterPic_xd;
    }

    public void setPosterPic_xd(String posterPic_xd) {
        this.posterPic_xd = posterPic_xd;
    }

    public String getEnterpriseProductUrl() {
        return enterpriseProductUrl;
    }

    public void setEnterpriseProductUrl(String enterpriseProductUrl) {
        this.enterpriseProductUrl = enterpriseProductUrl;
    }

    public String getEnterpriseProductUrl_xd() {
        return enterpriseProductUrl_xd;
    }

    public void setEnterpriseProductUrl_xd(String enterpriseProductUrl_xd) {
        this.enterpriseProductUrl_xd = enterpriseProductUrl_xd;
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

    public String getIsCloud() {
        return isCloud;
    }

    public void setIsCloud(String isCloud) {
        this.isCloud = isCloud;
    }
}
