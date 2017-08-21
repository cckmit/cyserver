package com.cy.core.donation.entity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.cy.common.utils.TimeZoneUtils;
import com.cy.core.project.entity.Project;
import com.cy.core.share.entity.File;
import com.cy.core.user.entity.User;
import com.cy.core.userinfo.entity.UserInfo;
import com.cy.system.Global;
import org.apache.commons.lang3.StringUtils;

public class Donation implements Serializable {
	private static final long serialVersionUID = 1L;
	private long donationId;
	private String userId;		//捐赠人
	private String orderNo;		//订单编号
	private long projectId;		//捐赠项目
	private String donationType;	//捐赠物类型（10：捐款；20：捐物）
	private String donationCourier;	//捐物快递公司
	private String donationCourierNumber;//捐物快递单号
	private String needInvoice;		//需要发票（10：需要；20：不需要）
	private String messageIsOpen;	//留言是否公开

	private double money;
	private Date donationTime;		//捐赠时间
	private double payMoney;
	private Date payTime;
	private int payStatus;			// 支付状态:0:待支付;1:已支付;2:已退款
	private int confirmStatus;		//确认状态
	private Date confirmTime;		//确认时间
	private String confirmerName;	//确认人名
	private long confirmId;
	private String remark;
	private String message;			//捐赠留言
	private String payMode; //支付方式（10:支付宝支付；20:微信支付）
	private String payDetail;
	private int flag;
	private String picture;		//用户头像
	private String payType;  //支付方式（10：校友会公众号【默认】；20：基金会公众号；25：基金会网站；30:后台添加；40:历史数据导入；50：基金会系统同步）
	private String payTypes;//支付方式集合

	private String credentialsCourier;  // 证书或发票的快递公司
	private String credentialsCourierNumber;//证书或发票的快递单号


	private String x_name;
	private String x_phone;
	private String x_email;
	private String x_address;
	private String x_school;
	private String x_depart;
	private String x_grade;
	private String x_clazz;
	private String x_sex;		//性别（0：男；1：女）
	private String x_workunit;
	private String x_post_code;	//邮编
	private String x_position;
	private String x_major;
	private String x_fullname;

	private String v_ymd;
	private String v_md5info;

	private long majorId;

	private int totalMoney;
	private long totalPeople;
	private int totalDonationMoney;

	private String alipayNumber;
	private Long accountNum;
	private String payMethod; //支付途径（10:手机APP；20:网站；30:微信（校友会）；35:微信（基金会））

	private User user;
	private UserInfo userInfo;
	private Project project;

	private short anonymous;

	private String content;

	private List<File> fileList;  // 页面图片列表
	private String pictures;	//图片
	private String picturesUrl;	//图片地址 相对路径

	private String groupName;   //学习经历

	private String deliveryAddress;	//收件地址
	private String certificatePic;	//证书图片
	private String certificatePicUrl;	//证书图片相对路径
	private String openId; 			//微信openID
	private String accountAppId;	//微信公眾號appID
	private String weixinUserId;	//微信用戶ID

	private String donorType;   //捐赠方类型(10:个人；20:单位；30:团体)
	private String donorName;  //捐赠方名称
	private String itemType;  //物品类型
	private String itemName;  //物品名称
	private String itemNum;  //物品数量：


	private String relativeTime; //相对时间
	private String absoluteTime; //绝对时间


	private String founProject;//基金会项目名

	private String messageIsNotEmpty; //消息是否不为空

	private String incomeId;//基金会系统传过来的收入Id


	private String floorAmount;//下限金额；

	private String inputClassInfo;//手动输入的班级信息


	public String getFounProject() {
		return founProject;
	}

	public void setFounProject(String founProject) {
		this.founProject = founProject;
	}

	public long getDonationId() {
		return donationId;
	}

	public void setDonationId(long donationId) {
		this.donationId = donationId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public long getProjectId() {
		return projectId;
	}

	public void setProjectId(long projectId) {
		this.projectId = projectId;
	}

	public double getMoney() {
		return money;
	}

	public void setMoney(double money) {
		this.money = money;
	}

	public Date getDonationTime() {
		return donationTime;
	}

	public void setDonationTime(Date donationTime) {
		this.donationTime = donationTime;
	}

	public double getPayMoney() {
		return payMoney;
	}

	public void setPayMoney(double payMoney) {
		this.payMoney = payMoney;
	}

	public Date getPayTime() {
		return payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}

	public int getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(int payStatus) {
		this.payStatus = payStatus;
	}

	public int getConfirmStatus() {
		return confirmStatus;
	}

	public void setConfirmStatus(int confirmStatus) {
		this.confirmStatus = confirmStatus;
	}

	public Date getConfirmTime() {
		return confirmTime;
	}

	public void setConfirmTime(Date confirmTime) {
		this.confirmTime = confirmTime;
	}

	public long getConfirmId() {
		return confirmId;
	}

	public void setConfirmId(long confirmId) {
		this.confirmId = confirmId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getMessage() {
		if(StringUtils.isBlank(message)){
			message = "";
		}
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public UserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public int getTotalMoney() {
		return totalMoney;
	}

	public void setTotalMoney(int totalMoney) {
		this.totalMoney = totalMoney;
	}

	public long getTotalPeople() {
		return totalPeople;
	}

	public void setTotalPeople(long totalPeople) {
		this.totalPeople = totalPeople;
	}

	public long getMajorId() {
		return majorId;
	}

	public void setMajorId(long majorId) {
		this.majorId = majorId;
	}

	public String getX_name() {
		return x_name;
	}

	public void setX_name(String x_name) {
		this.x_name = x_name;
	}

	public String getX_phone() {
		return x_phone;
	}

	public void setX_phone(String x_phone) {
		this.x_phone = x_phone;
	}

	public String getX_email() {
		return x_email;
	}

	public void setX_email(String x_email) {
		this.x_email = x_email;
	}

	public String getX_address() {
		return x_address;
	}

	public void setX_address(String x_address) {
		this.x_address = x_address;
	}

	public String getX_school() {
		return x_school;
	}

	public void setX_school(String x_school) {
		this.x_school = x_school;
	}

	public String getX_depart() {
		return x_depart;
	}

	public void setX_depart(String x_depart) {
		this.x_depart = x_depart;
	}

	public String getX_grade() {
		return x_grade;
	}

	public void setX_grade(String x_grade) {
		this.x_grade = x_grade;
	}

	public String getX_clazz() {
		return x_clazz;
	}

	public void setX_clazz(String x_clazz) {
		this.x_clazz = x_clazz;
	}

	public String getX_sex() {
		return x_sex;
	}

	public void setX_sex(String x_sex) {
		this.x_sex = x_sex;
	}

	public String getX_workunit() {
		return x_workunit;
	}

	public void setX_workunit(String x_workunit) {
		this.x_workunit = x_workunit;
	}

	public String getX_position() {
		return x_position;
	}

	public void setX_position(String x_position) {
		this.x_position = x_position;
	}

	public String getX_major() {
		return x_major;
	}

	public void setX_major(String x_major) {
		this.x_major = x_major;
	}

	public String getV_ymd() {
		return v_ymd;
	}

	public void setV_ymd(String v_ymd) {
		this.v_ymd = v_ymd;
	}

	public String getV_md5info() {
		return v_md5info;
	}

	public void setV_md5info(String v_md5info) {
		this.v_md5info = v_md5info;
	}

	public String getPayMode() {
		return payMode;
	}

	public void setPayMode(String payMode) {
		this.payMode = payMode;
	}

	public String getPayDetail() {
		return payDetail;
	}

	public void setPayDetail(String payDetail) {
		this.payDetail = payDetail;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public int getTotalDonationMoney() {
		return totalDonationMoney;
	}

	public void setTotalDonationMoney(int totalDonationMoney) {
		this.totalDonationMoney = totalDonationMoney;
	}

	public String getAlipayNumber() {
		return alipayNumber;
	}

	public void setAlipayNumber(String alipayNumber) {
		this.alipayNumber = alipayNumber;
	}

	public Long getAccountNum() {
		return accountNum;
	}

	public void setAccountNum(Long accountNum) {
		this.accountNum = accountNum;
	}

	public String getPayMethod() {
		return payMethod;
	}

	public void setPayMethod(String payMethod) {
		this.payMethod = payMethod;
	}

	public short getAnonymous() {
		return anonymous;
	}

	public void setAnonymous(short anonymous) {
		this.anonymous = anonymous;
	}

	public String getPicture() {
		if(StringUtils.isNotBlank(Global.FOUNDATION_LOGO)){
			if(StringUtils.isBlank(picture)){
				picture = Global.FOUNDATION_LOGO;
			}else if(anonymous == 1){
				picture = Global.FOUNDATION_LOGO;
			}
		}
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getX_fullname() {
		return x_fullname;
	}

	public void setX_fullname(String x_fullname) {
		this.x_fullname = x_fullname;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getX_post_code() {
		return x_post_code;
	}

	public void setX_post_code(String x_post_code) {
		this.x_post_code = x_post_code;
	}

	public String getCredentialsCourierNumber() {
		return credentialsCourierNumber;
	}

	public void setCredentialsCourierNumber(String credentialsCourierNumber) {
		this.credentialsCourierNumber = credentialsCourierNumber;
	}

	public String getDonationType() {
		return donationType;
	}

	public void setDonationType(String donationType) {
		this.donationType = donationType;
	}

	public String getDonationCourier() {
		return donationCourier;
	}

	public void setDonationCourier(String donationCourier) {
		this.donationCourier = donationCourier;
	}

	public String getDonationCourierNumber() {
		return donationCourierNumber;
	}

	public void setDonationCourierNumber(String donationCourierNumber) {
		this.donationCourierNumber = donationCourierNumber;
	}

	public String getNeedInvoice() {
		return needInvoice;
	}

	public void setNeedInvoice(String needInvoice) {
		this.needInvoice = needInvoice;
	}

	public String getCredentialsCourier() {
		return credentialsCourier;
	}

	public void setCredentialsCourier(String credentialsCourier) {
		this.credentialsCourier = credentialsCourier;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public List<File> getFileList() {
		return fileList;
	}

	public void setFileList(List<File> fileList) {
		this.fileList = fileList;
	}

	public String getPicturesUrl() {
		return picturesUrl;
	}

	public void setPicturesUrl(String picturesUrl) {
		this.picturesUrl = picturesUrl;
	}

	public String getPictures() {
		return pictures;
	}

	public void setPictures(String pictures) {
		this.pictures = pictures;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getDeliveryAddress() {
		return deliveryAddress;
	}

	public void setDeliveryAddress(String deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}

	public String getCertificatePic() {
		if(StringUtils.isBlank(certificatePic) && StringUtils.isNotBlank(certificatePicUrl) && StringUtils.isNotBlank(Global.URL_DOMAIN)) {
			if(certificatePicUrl.indexOf("http") < 0) {
				certificatePic = Global.URL_DOMAIN + certificatePicUrl ;
			}else{
				certificatePic=certificatePicUrl;
			}
		}
		return certificatePic;
	}

	public void setCertificatePic(String certificatePic) {
		this.certificatePic = certificatePic;
	}

	public String getCertificatePicUrl() {
		if(StringUtils.isBlank(certificatePicUrl) && StringUtils.isNotBlank(certificatePic) && StringUtils.isNotBlank(Global.URL_DOMAIN)) {
			if(certificatePic.indexOf(Global.URL_DOMAIN) == 0) {
				certificatePicUrl = certificatePic.substring(Global.URL_DOMAIN.length()) ;
				if(!"/".equals(certificatePicUrl.substring(0,1))){
					certificatePicUrl = "/"+certificatePicUrl;
				}
			}else{
				certificatePicUrl = certificatePic;
			}
		}
		return certificatePicUrl;
	}

	public void setCertificatePicUrl(String certificatePicUrl) {
		this.certificatePicUrl = certificatePicUrl;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getWeixinUserId() {
		return weixinUserId;
	}

	public void setWeixinUserId(String weixinUserId) {
		this.weixinUserId = weixinUserId;
	}

	public String getAccountAppId() {
		return accountAppId;
	}

	public void setAccountAppId(String accountAppId) {
		this.accountAppId = accountAppId;
	}

	public String getConfirmerName() {
		return confirmerName;
	}

	public void setConfirmerName(String confirmerName) {
		this.confirmerName = confirmerName;
	}

	public String getDonorType() {
		return donorType;
	}

	public void setDonorType(String donorType) {
		this.donorType = donorType;
	}

	public String getDonorName() {
		return donorName;
	}

	public void setDonorName(String donorName) {
		this.donorName = donorName;
	}

	public String getItemType() {
		return itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	public String getItemNum() {
		return itemNum;
	}

	public void setItemNum(String itemNum) {
		this.itemNum = itemNum;
	}

	public String getPayTypes() {
		return payTypes;
	}

	public void setPayTypes(String payTypes) {
		this.payTypes = payTypes;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getMessageIsOpen() {
		return messageIsOpen;
	}

	public void setMessageIsOpen(String messageIsOpen) {
		this.messageIsOpen = messageIsOpen;
	}

	public String getRelativeTime() {
		if("20".equals(donationType) && confirmTime != null){
			relativeTime = TimeZoneUtils.relativeDateFormat(confirmTime);
		}else if(payTime != null){
			relativeTime = TimeZoneUtils.relativeDateFormat(payTime);
		}else{
			relativeTime = "";
		}

		return relativeTime;
	}


	public void setRelativeTime(String relativeTime) {
		this.relativeTime = relativeTime;
	}

	public String getAbsoluteTime() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		if("20".equals(donationType) && confirmTime != null){
			absoluteTime = formatter.format(confirmTime);
		}else if(payTime != null){
			absoluteTime = formatter.format(payTime);
		}else{
			absoluteTime = "";
		}
		return absoluteTime;
	}

	public void setAbsoluteTime(String absoluteTime) {
		this.absoluteTime = absoluteTime;
	}

	public String getMessageIsNotEmpty() {
		if(StringUtils.isBlank(message) || "".equals(message.trim()))
			messageIsNotEmpty = "0";
		else
			messageIsNotEmpty = "1";

		return messageIsNotEmpty;
	}

	public void setMessageIsNotEmpty(String messageIsNotEmpty) {
		this.messageIsNotEmpty = messageIsNotEmpty;
	}

	public String getIncomeId() {
		return incomeId;
	}

	public void setIncomeId(String incomeId) {
		this.incomeId = incomeId;
	}

	public String getFloorAmount() {
		return floorAmount;
	}

	public void setFloorAmount(String floorAmount) {
		this.floorAmount = floorAmount;
	}

	public String getInputClassInfo() {
		return inputClassInfo;
	}

	public void setInputClassInfo(String inputClassInfo) {
		this.inputClassInfo = inputClassInfo;
	}
}
