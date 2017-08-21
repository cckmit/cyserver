package com.cy.web.donation.action;

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;

import org.springframework.beans.factory.annotation.Autowired;

import com.cy.base.action.WebBaseAction;
import com.cy.core.donation.entity.Donation;
import com.cy.core.donation.service.DonationService;
import com.cy.system.SystemUtil;

@Namespace("/_donation")
@Action(value = "_donationAction", results = {
		@Result(name = "jz_save", location = "/_donation/_donationAction!doNotNeedSessionAndSecurity_donationConfirm.action", type = "redirect", params = {
				"id", "${id}" }), @Result(name = "jz_confirm", location = "/web/donateStep2.jsp")
		,@Result(name="query",location="/web/donateQueryDetail.jsp")})
public class DonationAction extends WebBaseAction {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(DonationAction.class);

	private Donation donation;

	private String l = "";

	private List<Donation> donations;

	@Autowired
	private DonationService donationService;

	public String doNotNeedSessionAndSecurity_donationSave() {
		try {
			donation.setPayStatus(0);
			donation.setOrderNo(SystemUtil.getOrderNo());
			donationService.save(donation);
//			l = ConfigTools.encrypt(String.valueOf(donation.getDonationId()));
			id=donation.getDonationId();
		} catch (Exception e) {
			logger.error(e, e);
		}
		return "jz_save";
	}

	public String doNotNeedSessionAndSecurity_donationConfirm() {
		try {
//			id = Long.parseLong(ConfigTools.decrypt(l));
			donation = donationService.selectById(id);
		} catch (NumberFormatException e) {
			logger.error(e, e);
		} catch (Exception e) {
			logger.error(e, e);
		}
		return "jz_confirm";
	}

	public String donateQuery(){
		donations = donationService.selectByNameAndPhone(donation);
		return "query";
	}

	public Donation getDonation() {
		return donation;
	}

	public void setDonation(Donation donation) {
		this.donation = donation;
	}

	public String getL() {
		return l;
	}

	public void setL(String l) {
		this.l = l;
	}

	public List<Donation> getDonations() {
		return donations;
	}

	public void setDonations(List<Donation> donations) {
		this.donations = donations;
	}

}
