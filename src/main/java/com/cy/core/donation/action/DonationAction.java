package com.cy.core.donation.action;

import com.cy.core.dict.entity.Dict;
import com.cy.core.dict.utils.DictUtils;
import com.cy.system.SystemUtil;
import com.cy.util.DateUtils;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.cy.base.action.AdminBaseAction;
import com.cy.base.entity.Message;
import com.cy.core.donation.entity.Donation;
import com.cy.core.donation.service.DonationService;
import com.cy.system.WebUtil;

@Namespace("/donation")
@Action(value = "donationAction", results = {
		@Result(name = "donateSave", location = "/donation/donationAction!doNotNeedSessionAndSecurity_donationConfirm.action", type = "redirect", params = {
				"id", "${id}" }),
		@Result(name = "donateConfirm", location = "/mobile/donate/donatePay.jsp" ),
		@Result(name="donateHistoryDetail",location="/mobile/donate/donateHistoryDetail.jsp")})
public class DonationAction extends AdminBaseAction {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(DonationAction.class);

	@Autowired
	private DonationService donationService;
	private Donation donation;
	private Date startTime;
	private Date endTime;
	private double startMoney;
	private double endMoney;
	private String departId;
	private String gradeId;
	private String classId;
	private String schoolId;
	private String checkPage;

	private String payStatus;
	private String flag;
	private String needInvoice;

	private int countMethod;

	private String accountNum;

	private String messageIsNotEmpty;

	public Donation getDonation() {
		return donation;
	}

	public void setDonation(Donation donation) {
		this.donation = donation;
	}

	/** --捐赠管理列表数据查询-- **/
	public void dataGrid() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("startMoney", startMoney);
		map.put("endMoney", endMoney);
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		if (!WebUtil.isEmpty(schoolId)) {
			map.put("deptId", schoolId);
		}
		if (!WebUtil.isEmpty(departId)) {
			map.put("deptId", departId);
		}
		if (!WebUtil.isEmpty(gradeId)) {
			map.put("deptId", gradeId);
		}
		if (!WebUtil.isEmpty(classId)) {
			map.put("deptId", classId);
		}
		if (donation != null) {
			if (donation.getUserInfo() != null) {
				map.put("userName", donation.getUserInfo().getUserName());
				map.put("studentType", donation.getUserInfo().getStudentType());
			}
			map.put("projectId", donation.getProjectId());
			if (donation.getProject()!=null) {
				map.put("projectName", donation.getProject().getProjectName());
			}
			map.put("confirmStatus", donation.getConfirmStatus());
			map.put("payStatus", payStatus);
			map.put("majorId", donation.getMajorId());
			map.put("needInvoice",needInvoice );

			map.put("donorName",donation.getDonorName());
			map.put("donorType",donation.getDonorType());
			map.put("donationType",donation.getDonationType());
			map.put("itemType",donation.getItemType());
			map.put("flag",flag);
			map.put("messageIsNotEmpty", messageIsNotEmpty);

		}

		if(StringUtils.isNotBlank(checkPage) && checkPage.equals("1")){
			map.put("confirmStatus", "10,20");
		}

		map.put("page", page);
		map.put("rows", rows);
		super.writeJson(donationService.dataGrid(map));
	}

	public String doNotNeedSessionAndSecurity_donationSave() {
		try {
			int code = donationService.saveFromMobile(donation);
			if (code == 0) {
				id = donation.getDonationId();
				return "donateSave";
			} else {
				return "donatieSaveFail" ;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null ;
	}

	public String doNotNeedSessionAndSecurity_donationConfirm() {
		donation = donationService.selectById(id);
		return "donateConfirm";
	}

	public String doNotNeedSessionAndSecurity_getById() {
		donation = donationService.selectById(id);
		return "donateHistoryDetail";
	}

	public void doNotNeedSessionAndSecurity_listAll() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("page", page);
		map.put("rows", rows);
		map.put("accountNum", accountNum);
		super.writeJson(donationService.listAll(map));
	}

	public void doNotNeedSessionAndSecurity_listNew() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("page", page);
		map.put("rows", rows);
		map.put("accountNum", accountNum);
		super.writeJson(donationService.listNew(map));
	}

	/** --捐赠统计列表数据-- **/
	public void dataGridForCount() {
		Map<String, Object> map = new HashMap<String, Object>();

		if (!WebUtil.isEmpty(schoolId)) {
			map.put("deptId", schoolId);
		}
		if (!WebUtil.isEmpty(departId)) {
			map.put("deptId", departId);
		}
		if (!WebUtil.isEmpty(gradeId)) {
			map.put("deptId", gradeId);
		}
		if (!WebUtil.isEmpty(classId)) {
			map.put("deptId", classId);
		}

		if (donation != null) {
			if (donation.getUserInfo() != null) {
				map.put("studentType", donation.getUserInfo().getStudentType());
				map.put("sex", donation.getUserInfo().getSex());
				map.put("entranceTime", donation.getUserInfo().getEntranceTime());
			}
			map.put("projectId", donation.getProjectId());
			map.put("majorId", donation.getMajorId());
		}
		if (countMethod == 0) {
			// 默认按照学校统计
			countMethod = 4;
		}
		map.put("countMethod", countMethod);
		map.put("page", page);
		map.put("rows", rows);
		super.writeJson(donationService.dataGridForCount(map));
	}

	public void save() {
		Message message = new Message();
		try {
			//捐款将捐赠金额置为支付金额
			if (StringUtils.isNotBlank(donation.getDonorType())&& "10".equals(donation.getDonationType())){
				donation.setMoney(donation.getPayMoney());
				donation.setPayStatus(1);
			}
			//确认状态：赋值确认时间和确认人
			if (donation.getConfirmStatus()==30 ||donation.getConfirmStatus()==40 ||donation.getConfirmStatus()==45){
				donation.setConfirmTime(new Date());
				donation.setConfirmId(getUser().getUserId());

			}
			donation.setPayTime(donation.getDonationTime());
			donation.setOrderNo(SystemUtil.getOrderNo());
			donation.setPayType("30");
			donation.setAnonymous(Short.parseShort("0"));
			donationService.save(donation);
			message.setMsg("新增成功");
			message.setSuccess(true);
		} catch (Exception e) {
			logger.error(e, e);
			message.setMsg("新增失败");
			message.setSuccess(false);
		}
		super.writeJson(message);
	}

	public void update() {
		Message message = new Message();
		try {
			donation.setConfirmId(getUser().getUserId());
			donation.setConfirmTime(new Date());
			donationService.update(donation);
			message.setMsg("修改成功");
			message.setSuccess(true);
		} catch (Exception e) {
			logger.error(e, e);
			message.setMsg("修改失败");
			message.setSuccess(false);
		}
		super.writeJson(message);
	}

	public void delete() {
		Message message = new Message();
		try {
			donationService.delete(ids);
			message.setMsg("删除成功");
			message.setSuccess(true);
		} catch (Exception e) {
			logger.error(e, e);
			message.setMsg("删除失败");
			message.setSuccess(false);
		}
		super.writeJson(message);
	}

	public void getById() {
		Donation donation = donationService.selectById(id);
		super.writeJson(donation);
	}

	public void doNotNeedSecurity_getById() {
		super.writeJson(donationService.selectById(id));
	}

	public void doNotNeedSecurity_createCertificate() {
		Message message = new Message();

		int code = donationService.createCertificate(id);
		if(code == 0 ){
			message.init(true, "生成成功",null);
		}else{
			message.init(true, "生成失败",null);
		}

		super.writeJson(message);
	}

	/**
	 * 方法 doNotNeedSecurity_donorType 的功能描述：获取捐赠方字典
	 * @createAuthor niu
	 * @createDate 2017-04-19 15:56:12
	 * @param
	 * @return
	 * @throw
	 *
	*/

	public void doNotNeedSecurity_donorType(){
		Map<String,String> map = Maps.newHashMap();
		map.put("dictTypeValue","28");
		List<Dict> dictList = DictUtils.findDictList(map);
		super.writeJson(dictList);
	}

	/**
	 * 方法doNotNeedSecurity_itemType 的功能描述：获取物品类型字典
	 * @createAuthor niu
	 * @createDate 2017-04-19 15:55:30
	 * @param
	 * @return void
	 * @throw
	 *
	 */
	public void doNotNeedSecurity_itemType(){


		Map<String,String> map = Maps.newHashMap();
		map.put("dictTypeValue","29");
		List<Dict> dictList = DictUtils.findDictList(map);
		super.writeJson(dictList);
	}

	/**
	 * 方法 doNotNeedSecurity_payModel 的功能描述：获取支付方式字典
	 * @createAuthor niu
	 * @createDate 2017-04-19 15:57:06
	 * @param
	 * @return
	 * @throw
	 *
	*/
	public void doNotNeedSecurity_payModel(){


		Map<String,String> map = Maps.newHashMap();
		map.put("dictTypeValue","30");
		List<Dict> dictList = DictUtils.findDictList(map);
		super.writeJson(dictList);
	}


	public DonationService getDonationService() {
		return donationService;
	}

	public void setDonationService(DonationService donationService) {
		this.donationService = donationService;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public double getStartMoney() {
		return startMoney;
	}

	public void setStartMoney(double startMoney) {
		this.startMoney = startMoney;
	}

	public double getEndMoney() {
		return endMoney;
	}

	public void setEndMoney(double endMoney) {
		this.endMoney = endMoney;
	}

	public String getDepartId() {
		return departId;
	}

	public void setDepartId(String departId) {
		this.departId = departId;
	}

	public String getGradeId() {
		return gradeId;
	}

	public void setGradeId(String gradeId) {
		this.gradeId = gradeId;
	}

	public String getClassId() {
		return classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
	}

	public int getCountMethod() {
		return countMethod;
	}

	public void setCountMethod(int countMethod) {
		this.countMethod = countMethod;
	}

	public String getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(String schoolId) {
		this.schoolId = schoolId;
	}

	public String getAccountNum() {
		return accountNum;
	}

	public void setAccountNum(String accountNum) {
		this.accountNum = accountNum;
	}

	public String getCheckPage() {
		return checkPage;
	}

	public void setCheckPage(String checkPage) {
		this.checkPage = checkPage;
	}


	public String getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getNeedInvoice() {
		return needInvoice;
	}

	public void setNeedInvoice(String needInvoice) {
		this.needInvoice = needInvoice;
	}

	public String getMessageIsNotEmpty() {
		return messageIsNotEmpty;
	}

	public void setMessageIsNotEmpty(String messageIsNotEmpty) {
		this.messageIsNotEmpty = messageIsNotEmpty;
	}
}
