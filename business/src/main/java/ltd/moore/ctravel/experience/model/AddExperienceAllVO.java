package ltd.moore.ctravel.experience.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by Administrator on 2017/6/19 0019.
 */
public class AddExperienceAllVO implements Serializable {
    /*
     *标题
     */
    private String title;

    /*
     *副标题
     */
    private String subtitle;

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    /*
         *体验图片详情ID
         */
    private String imageId;
    /*
     *体验图片详情URL
     */
    private String imageUrl;
    /*
     *内容描述
     */
    private String contentDescription;

    /*
     *目的地
     */
    private String destination;

    /*
     *集合地
     */
    private String rendezvous;

    /*
     *体验内容明细
     */
    private String contentDetails;

    /*
     *备注
     */
    private String comment;

    /*
     *要求
     */
    private String requirement;

    /*
     *人数
     */
    private String peopleNumber;
    /*
     *排序
     */
    private String sortNo;
    /*
     *货币类型
     */
    private String currencyType;

    /*
     *价格
     */
    private String price;
	/*
	 *服务名称
	 */
    private String serviceName;
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getContentDescription() {
        return contentDescription;
    }

    public void setContentDescription(String contentDescription) {
        this.contentDescription = contentDescription;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getRendezvous() {
        return rendezvous;
    }

    public void setRendezvous(String rendezvous) {
        this.rendezvous = rendezvous;
    }

    public String getContentDetails() {
        return contentDetails;
    }

    public void setContentDetails(String contentDetails) {
        this.contentDetails = contentDetails;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getRequirement() {
        return requirement;
    }

    public void setRequirement(String requirement) {
        this.requirement = requirement;
    }

    public String getPeopleNumber() {
        return peopleNumber;
    }

    public void setPeopleNumber(String peopleNumber) {
        this.peopleNumber = peopleNumber;
    }

    public String getSortNo() {
        return sortNo;
    }

    public void setSortNo(String sortNo) {
        this.sortNo = sortNo;
    }

    public String getCurrencyType() {
        return currencyType;
    }

    public void setCurrencyType(String currencyType) {
        this.currencyType = currencyType;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    @Override
    public String toString() {
        return "AddExperienceAllVO{" +
                "title='" + title + '\'' +
                ", subtitle='" + subtitle + '\'' +
                ", imageId='" + imageId + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", contentDescription='" + contentDescription + '\'' +
                ", destination='" + destination + '\'' +
                ", rendezvous='" + rendezvous + '\'' +
                ", contentDetails='" + contentDetails + '\'' +
                ", comment='" + comment + '\'' +
                ", requirement='" + requirement + '\'' +
                ", peopleNumber='" + peopleNumber + '\'' +
                ", sortNo='" + sortNo + '\'' +
                ", currencyType='" + currencyType + '\'' +
                ", price='" + price + '\'' +
                '}';
    }
}