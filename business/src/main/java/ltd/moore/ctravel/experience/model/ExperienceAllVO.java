package ltd.moore.ctravel.experience.model;

import com.sun.star.util.DateTime;

import java.io.Serializable;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by Administrator on 2017/6/19 0019.
 */
public class ExperienceAllVO implements Serializable {
    /*
	 *体验明细ID
	 */
    private String experienceDetailId;

    /*
     *体验ID
     */
    private String experienceId;

    /*
     *目的地ID
     */
    private String destinationId;
    /*
    *服务时间ID
    */
    private String serviceTimeId;
    /*
    *用户ID
    */
    private String customerId;
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
     *人数
     */
    private String peopleNumberS;
    /*
     *排序
     */
    private String sortNo;
    /*
    *排序
    */
    private String sortNoS;
    /*
     *货币类型
     */
    private String currencyType;

    /*
     *价格
     */
    private String price;
    /**
     *
     * 价格
     */
    private String priceStr;
    /*
     *激活标志符
     */
    private int enabled;
    /*
	 *服务日期
	 */
    private Date serviceDate;

    /*
     *服务开始时间
     */
    private Timestamp startTime;
    /*
    *服务结束时间
     */
    private Timestamp  endTime;
	/*
	 *服务结束时间数组
	 */
    private String  endTimeArray;
    /*
    *服务开始时间数组
    */
    private String startTimeArray;
    /*
    *服务时间ID数组
    */
    private String serviceTimeIdArray;
    /*
	 *服务类型ID
	 */
    private String serviceTypeId;
	/*
	 *服务名称
	 */
    private String serviceName;
    public String getExperienceDetailId() {
        return experienceDetailId;
    }

    public void setExperienceDetailId(String experienceDetailId) {
        this.experienceDetailId = experienceDetailId;
    }

    public String getExperienceId() {
        return experienceId;
    }

    public void setExperienceId(String experienceId) {
        this.experienceId = experienceId;
    }

    public String getDestinationId() {
        return destinationId;
    }

    public void setDestinationId(String destinationId) {
        this.destinationId = destinationId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

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

    public String getPeopleNumberS() {
        return peopleNumberS;
    }

    public void setPeopleNumberS(String peopleNumberS) {
        this.peopleNumberS = peopleNumberS;
    }

    public String getSortNoS() {
        return sortNoS;
    }

    public void setSortNoS(String sortNoS) {
        this.sortNoS = sortNoS;
    }

    public String getPriceStr() {
        return priceStr;
    }

    public void setPriceStr(String priceStr) {
        this.priceStr = priceStr;
    }

    public int getEnabled() {
        return enabled;
    }

    public void setEnabled(int enabled) {
        this.enabled = enabled;
    }

    public Date getServiceDate() {
        return serviceDate;
    }

    public void setServiceDate(Date serviceDate) {
        this.serviceDate = serviceDate;
    }

    public Timestamp  getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp  startTime) {
        this.startTime = startTime;
    }

    public Timestamp  getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp  endTime) {
        this.endTime = endTime;
    }

    public String getEndTimeArray() {
        return endTimeArray;
    }

    public void setEndTimeArray(String endTimeArray) {
        this.endTimeArray = endTimeArray;
    }

    public String getStartTimeArray() {
        return startTimeArray;
    }

    public void setStartTimeArray(String startTimeArray) {
        this.startTimeArray = startTimeArray;
    }

    public String getServiceTimeIdArray() {
        return serviceTimeIdArray;
    }

    public void setServiceTimeIdArray(String serviceTimeIdArray) {
        this.serviceTimeIdArray = serviceTimeIdArray;
    }

    public String getServiceTypeId() {
        return serviceTypeId;
    }

    public void setServiceTypeId(String serviceTypeId) {
        this.serviceTypeId = serviceTypeId;
    }

    public String getServiceTimeId() {
        return serviceTimeId;
    }

    public void setServiceTimeId(String serviceTimeId) {
        this.serviceTimeId = serviceTimeId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    @Override
    public String toString() {
        return "ExperienceAllVO{" +
                "experienceDetailId='" + experienceDetailId + '\'' +
                ", experienceId='" + experienceId + '\'' +
                ", destinationId='" + destinationId + '\'' +
                ", title='" + title + '\'' +
                ", subtitle='" + subtitle + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", contentDescription='" + contentDescription + '\'' +
                ", destination='" + destination + '\'' +
                ", rendezvous='" + rendezvous + '\'' +
                ", contentDetails='" + contentDetails + '\'' +
                ", comment='" + comment + '\'' +
                ", requirement='" + requirement + '\'' +
                ", peopleNumber=" + peopleNumber +
                ", sortNo=" + sortNo +
                ", currencyType='" + currencyType + '\'' +
                ", price=" + price +
                ", enabled=" + enabled +
                ", serviceDate=" + serviceDate +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", serviceTypeId='" + serviceTypeId + '\'' +
                ", serviceName='" + serviceName + '\'' +
                '}';
    }
}
