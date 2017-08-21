package com.cy.core.cloudEnterprise.entity;

import com.cy.base.entity.DataEntity;
import com.cy.core.enterprise.entity.EnterpriseProduct;
import com.cy.system.Global;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 *
 * <p>Title: ChatContacts</p>
 * <p>Description: 云平台企业信息(实体类)</p>
 *
 * @author 王傲辉
 * @Company 博视创诚
 * @data 2017-08-05
 */
public class CloudEnterprise extends DataEntity<CloudEnterprise> {
    private static final long serialVersionUID = 1L;

    private String name;        //企业名称
    private String logo;        //企业logo路径
    private String posterPic;   //海报图路径
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
    private String industry;     //所属行业
    private List<CloudEnterpriseProduct> productList;   //產品列表
    private String longitude;   //经度
    private String latitude;      //纬度
    private String locationDesc;      //位置描述
    private String province;      //所在城市
    private String city;      //中心城市
    private String recruitEmail;      //企业邮箱
    private String financingStage;//融资阶段
    private String status;          //认证状态
    private String busLicenseUrl;   //营业执照

    private String cloudId; //云平台企业编号

    private String countEntrepreneur; //该企业认证通过的校友企业家



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getPosterPic() {
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

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
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

    public List<CloudEnterpriseProduct> getProductList() {
        return productList;
    }

    public void setProductList(List<CloudEnterpriseProduct> productList) {
        this.productList = productList;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getCountEntrepreneur() {
        return countEntrepreneur;
    }

    public void setCountEntrepreneur(String countEntrepreneur) {
        this.countEntrepreneur = countEntrepreneur;
    }
}
