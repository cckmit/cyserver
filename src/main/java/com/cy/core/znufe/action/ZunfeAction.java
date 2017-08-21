package com.cy.core.znufe.action;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.alibaba.druid.filter.config.ConfigTools;
import com.cy.base.action.AdminBaseAction;
import com.cy.common.utils.TimeZoneUtils;
import com.cy.core.activity.entity.Activity;
import com.cy.core.activity.service.ActivityService;
import com.cy.core.alumnicard.entity.AlumniCard;
import com.cy.core.alumnicard.service.AlumniCardService;
import com.cy.core.campuscard.entity.CampusCard;
import com.cy.core.campuscard.service.CampusCardService;
import com.cy.core.donation.entity.Donation;
import com.cy.core.donation.service.DonationService;
import com.cy.core.register.entity.Register;
import com.cy.core.register.service.RegisterService;
import com.cy.core.znufe.global.Global;
import com.cy.system.SecretUtil;
import com.cy.system.SystemUtil;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Administrator
 * 
 */
@Namespace("/znufe")
@Action(value = "znufeAction", results = {
		@Result(name = "jz_save", location = "/znufe/znufeAction!doNotNeedSessionAndSecurity_donationConfirm.action", type = "redirect", params = { "l", "${l}" }),
		@Result(name = "jz_confirm", location = "/znufe/donationConfirm2.jsp"), @Result(name = "success", location = "/znufe/success.jsp", type = "redirect"),
		@Result(name = "getMobNew", location = "/mobile/news/show.jsp") })
public class ZunfeAction extends AdminBaseAction {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(ZunfeAction.class);

	@Autowired
	private DonationService donationService;

	@Autowired
	private RegisterService registerService;

	@Autowired
	private ActivityService activityService;

	@Autowired
	private AlumniCardService service;

	@Autowired
	private CampusCardService cservice;

	private Donation donation;

	private Register register;

	private Activity activity;

	private String x_name;

	private String x_sex;

	// 校园卡
	private AlumniCard formData1;
	// 商户校园卡
	private CampusCard formData2;

	private String l = "";

	private String province;

	private String city;

	public Donation getDonation() {
		return donation;
	}

	public void setDonation(Donation donation) {
		this.donation = donation;
	}

	public Register getRegister() {
		return register;
	}

	public void setRegister(Register register) {
		this.register = register;
	}

	public AlumniCard getFormData1() {
		return formData1;
	}

	public void setFormData1(AlumniCard formData1) {
		this.formData1 = formData1;
	}

	public CampusCard getFormData2() {
		return formData2;
	}

	public void setFormData2(CampusCard formData2) {
		this.formData2 = formData2;
	}

	public String doNotNeedSessionAndSecurity_donationConfirm() {
		try {
			id = Long.parseLong(ConfigTools.decrypt(l));
			donation = donationService.selectById(id);
			DateFormat format = new SimpleDateFormat("yyyyMMdd");
			String v_ymd = format.format(new Date());
			String v_md5info = SecretUtil.encodeByHMAC(Global.v_moneytype + v_ymd + donation.getMoney() + Global.v_rcvname + donation.getOrderNo()
					+ Global.v_mid + Global.v_url, Global.KEY_MD5);
			donation.setV_md5info(v_md5info);
			donation.setV_ymd(v_ymd);
		} catch (NumberFormatException e) {
			logger.error(e, e);
		} catch (Exception e) {
			logger.error(e, e);
		}
		return "jz_confirm";
	}

	/** --捐赠保存-- **/
	public String doNotNeedSessionAndSecurity_donationSave() {
		try {
			donation.setPayStatus(0);
			donation.setOrderNo(SystemUtil.getOrderNo());
			if (x_name != null && x_name.length() > 0) {
				donation.setX_name(x_name);
			}
			if (x_sex != null && x_sex.length() > 0) {
				donation.setX_sex(x_sex);
			}
			donationService.save(donation);
			l = ConfigTools.encrypt(String.valueOf(donation.getDonationId()));
		} catch (Exception e) {
			logger.error(e, e);
		}
		return "jz_save";
	}

	/** --返校报名保存-- **/
	public String doNotNeedSessionAndSecurity_registerSave() {
		registerService.save(register);
		return "success";
	}

	public String doNotNeedSessionAndSecurity_activitySave() {
		activityService.save(activity);
		return "success";
	}

	/** --校友卡保存操作-- **/
	public String doNotNeedSessionAndSecurity_alumniCardSave() {
		formData1.setStatus(0);
		formData1.setApplyTime(TimeZoneUtils.getFormatDate());
		service.save(formData1);
		return "success";
	}

	/** --商户校园卡保存-- **/
	public String doNotNeedSessionAndSecurity_campusCardSave() {
		formData2.setStatus(0);
		formData2.setApplyTime(new Date());
		String location = "";
		if (province != null && province.length() > 0) {
			location += province;
		}
		if (city != null && city.length() > 0) {
			location += " " + city;
		}
		formData2.setLocation(location);
		cservice.save(formData2);
		return "success";
	}

	public String getL() {
		return l;
	}

	public void setL(String l) {
		this.l = l;
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

	public Activity getActivity() {
		return activity;
	}

	public void setActivity(Activity activity) {
		this.activity = activity;
	}

	public String getX_name() {
		return x_name;
	}

	public void setX_name(String x_name) {
		this.x_name = x_name;
	}

	public String getX_sex() {
		return x_sex;
	}

	public void setX_sex(String x_sex) {
		this.x_sex = x_sex;
	}

}
