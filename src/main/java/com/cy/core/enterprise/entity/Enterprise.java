package com.cy.core.enterprise.entity;

import com.cy.base.entity.DataEntity;
import com.cy.system.Global;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 *
 * <p>Title: ChatContacts</p>
 * <p>Description: 企业信息(实体类)</p>
 *
 * @author 王傲辉
 * @Company 博视创诚
 * @data 2016-1-6
 */
public class Enterprise extends DataEntity<Enterprise> {
    private static final long serialVersionUID = 1L;

    private String name;        //企业名称
    private String logo;        //企业logo路径
    private String logo_xd;        //新加企业logo路径
    private String posterPic;   //海报图路径
    private String posterPic_xd;   //新加海报图路径
    private String slogan;      //标语
    private String description; //图文详情
    private String type;        //企业类型
    private String mainBusiness;    //主营业务
    private String address;     //企业地址
    private String website;     //网址
    private String summary;     //公司简介（限长500）
    private String serviceArea; //业务范围
    private String linkman;     //联系人名称
    private String contactNumber;//联系电话
    private String clickNumber;    //点击量
    private String typeName;        //类型名称
    private List<EnterpriseProduct> productList;   //產品列表

    private String logoUrl;        //企业logo存储地址
    private String posterPicUrl;   //海报存储地址
    private String praiseNumber;   //点赞数量
    private String hasPraised;      //是否点赞
    private String schoolLogo;      //学校logo

    private String longitude;   //经度
    private String latitude;      //纬度
    private String locationDesc;      //位置描述
    private double distance; // 距离用户位置的距离

    private String city;      //中心城市
    private String recruitEmail;      //企业邮箱
    private String enterpriseUrl;//企业详情url
    private String enterpriseUrl_xd;//企业详情相对url
    private String financingStage;//融资阶段
    private String status;          //认证状态
    private String busLicenseUrl;   //营业执照

    private String cloudId; //云平台企业编号



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogo() {
        if(StringUtils.isBlank(logo) && StringUtils.isNotBlank(logoUrl) && StringUtils.isNotBlank(Global.URL_DOMAIN)) {
            if(logoUrl.indexOf("http") < 0) {
                if (!logoUrl.trim().startsWith("/")){
                    logoUrl="/"+logoUrl;
                }
                logo = Global.URL_DOMAIN + logoUrl ;
            }else{
                logo=logoUrl;
            }
        }
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
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

    public String getMainBusiness() {
        return mainBusiness;
    }

    public void setMainBusiness(String mainBusiness) {
        this.mainBusiness = mainBusiness;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getServiceArea() {
        return serviceArea;
    }

    public void setServiceArea(String serviceArea) {
        this.serviceArea = serviceArea;
    }

    public String getLinkman() {
        return linkman;
    }

    public void setLinkman(String linkman) {
        this.linkman = linkman;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getClickNumber() {
        return clickNumber;
    }

    public void setClickNumber(String clickNumber) {
        this.clickNumber = clickNumber;
    }

    public String getLogoUrl() {
        if(StringUtils.isBlank(logoUrl) && StringUtils.isNotBlank(logo) && StringUtils.isNotBlank(Global.URL_DOMAIN)) {
            if(logo.indexOf(Global.URL_DOMAIN) == 0) {
                logoUrl = logo.substring(Global.URL_DOMAIN.length()) ;
            }else{
                logoUrl=logo;
            }
        }
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getPosterPicUrl() {
        if(com.cy.common.utils.StringUtils.isBlank(posterPicUrl) && com.cy.common.utils.StringUtils.isNotBlank(posterPic) && com.cy.common.utils.StringUtils.isNotBlank(Global.URL_DOMAIN)) {
            if(posterPic.indexOf(Global.URL_DOMAIN) == 0) {
                posterPicUrl = posterPic.substring(Global.URL_DOMAIN.length()) ;
                if (!posterPicUrl.trim().startsWith("/")){
                    posterPicUrl="/"+posterPicUrl;
                }
            }else{
                posterPicUrl=posterPic;
            }
        }
        return posterPicUrl;
    }

    public void setPosterPicUrl(String posterPicUrl) {
        this.posterPicUrl = posterPicUrl;
    }

    public List<EnterpriseProduct> getProductList() {
        return productList;
    }

    public void setProductList(List<EnterpriseProduct> productList) {
        this.productList = productList;
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

    public String getLogo_xd() {
        if(StringUtils.isBlank(logo_xd) && StringUtils.isNotBlank(logo) && StringUtils.isNotBlank(Global.URL_DOMAIN)) {
            if(logo.indexOf(Global.URL_DOMAIN) == 0) {
                logo_xd = logo.substring(Global.URL_DOMAIN.length()) ;
            }else{
                logo_xd=logo;
            }
        }
        return logo_xd;
    }

    public void setLogo_xd(String logo_xd) {
        this.logo_xd = logo_xd;
    }

    public String getPosterPic_xd() {
        if(com.cy.common.utils.StringUtils.isBlank(posterPic_xd) && com.cy.common.utils.StringUtils.isNotBlank(posterPic) && com.cy.common.utils.StringUtils.isNotBlank(Global.URL_DOMAIN)) {
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


    public String getSchoolLogo() {
        if(StringUtils.isNotBlank(Global.schoolLogo)){
            if("000440".equals(Global.deptNo)){
                //燕山的用基金會的logo
                schoolLogo = Global.FOUNDATION_LOGO;
            }else{
                schoolLogo = Global.schoolLogo;
            }
        }
        return schoolLogo;
    }

    public void setSchoolLogo(String schoolLogo) {
        this.schoolLogo = schoolLogo;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLocationDesc() {
        return locationDesc;
    }

    public void setLocationDesc(String locationDesc) {
        this.locationDesc = locationDesc;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEnterpriseUrl() {
        return enterpriseUrl;
    }

    public void setEnterpriseUrl(String enterpriseUrl) {
        this.enterpriseUrl = enterpriseUrl;
    }

    public String getEnterpriseUrl_xd() {
        return enterpriseUrl_xd;
    }

    public void setEnterpriseUrl_xd(String enterpriseUrl_xd) {
        this.enterpriseUrl_xd = enterpriseUrl_xd;
    }

    public String getRecruitEmail() {
        return recruitEmail;
    }

    public void setRecruitEmail(String recruitEmail) {
        this.recruitEmail = recruitEmail;
    }

    public String getFinancingStage() {
        return financingStage;
    }

    public void setFinancingStage(String financingStage) {
        this.financingStage = financingStage;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBusLicenseUrl() {
        return busLicenseUrl;
    }

    public void setBusLicenseUrl(String busLicenseUrl) {
        this.busLicenseUrl = busLicenseUrl;
    }

    public String getCloudId() {
        return cloudId;
    }

    public void setCloudId(String cloudId) {
        this.cloudId = cloudId;
    }
}
