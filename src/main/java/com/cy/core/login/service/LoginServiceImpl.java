package com.cy.core.login.service;

import java.util.*;

import com.cy.common.utils.CacheUtils;
import com.cy.core.classHandle.dao.ClassHandleMapper;
import com.cy.core.contact.dao.ContactMapper;
import com.cy.core.deptInfo.dao.DeptInfoMapper;
import com.cy.core.donation.dao.DonationMapper;
import com.cy.core.event.dao.EventMapper;
import com.cy.core.messageboard.dao.MessageBoardMapper;
import com.cy.core.news.dao.NewsMapper;
import com.cy.core.resource.entity.Resource;
import com.cy.core.role.entity.Role;
import com.cy.core.userProfile.dao.UserProfileMapper;
import com.cy.core.userinfo.dao.UserInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cy.base.entity.Message;
import com.cy.core.sms.service.MsgSendService;
import com.cy.core.smsCode.entity.SmsCode;
import com.cy.core.smsCode.service.SmsCodeService;
import com.cy.core.user.entity.User;
import com.cy.system.Global;
import com.cy.system.SystemUtil;

@Service("loginService")
public class LoginServiceImpl implements LoginService {
	@Autowired
	private MsgSendService msgSendService;
	@Autowired
	private SmsCodeService smsCodeService;
	@Autowired
	private NewsMapper newsMapper;
	@Autowired
	private UserInfoMapper userInfoMapper;
	@Autowired
	private ClassHandleMapper classHandleMapper;
	@Autowired
	private ContactMapper contactMapper;
	@Autowired
	private DonationMapper donationMapper;
	@Autowired
	private MessageBoardMapper messageBoardMapper;
	@Autowired
	private UserProfileMapper userProfileMapper;
	@Autowired
	private DeptInfoMapper deptInfoMapper;
	@Autowired
	private EventMapper eventMapper;

	/**
	 * 通过手机号发送手机验证码
	 * @param message
	 * @param telephone
     */
	@Override
	public void getSMSCodeByTelephone(Message message, String telephone){
		if (Global.smsCodeTemplate != null && Global.smsCodeTemplate.length() > 0) {
			String code = SystemUtil.getSixNumber();
			String msg = Global.smsCodeTemplate;
			msg = msg.replace("${0}", code);
			if (Global.sign != null && Global.sign.length() > 0) {
//				msg = "【" + Global.sign + "】" + msg;
				if (Global.sendType != null && Global.sendType.equals("CLOUD")) {
					msg += "（" + Global.sign + "）";

				} else {
					msg = "【" + Global.sign + "】" + msg;
				}
			}
			int result = msgSendService.sendRegisterCode(telephone, msg, code);
			if (result == 0) {
				return;
			} else {
				message.setMsg("发送验证码到您的手机【" + telephone + "】时，发送失败了：错误码" + result);
				message.setReturnId("500");
				return;
			}
		} else {
			message.setMsg("发送验证码到您的手机【" + telephone + "】时，发送失败了：验证码模板未设置。");
			message.setReturnId("500");
			return;
		}
	}

	@Override
	public void sendSMSCodeForWebLogin(Message message, User user) {
		// 没有电话号码，消息提示：联系管理员，在数据库中为您录入电话号码信息。
		String telephone = user.getTelephone();
		if (telephone == null || telephone.trim().length() <= 0) {
			message.setMsg("系统数据库中尚未录入您的手机号码，请数据库管理员为您录入手机号码后，再尝试登录！");
			message.setReturnId("500");
			return;
		}
		// 有电话号码，发送短信
		else {
			if (!SystemUtil.isMobileNO(telephone)) {
				message.setMsg("系统中保存的手机号码错误("+telephone+")，请数据库管理员录入您真实的手机号码后，再尝试登录！");
				message.setReturnId("500");
				return;
			}
			if (Global.smsCodeTemplate != null && Global.smsCodeTemplate.length() > 0) {
				String code = SystemUtil.getSixNumber();
				String msg = Global.smsCodeTemplate;
				msg = msg.replace("${0}", code);
				if (Global.sign != null && Global.sign.length() > 0) {
//					msg = "【" + Global.sign + "】" + msg;
					if (Global.sendType != null && Global.sendType.equals("CLOUD")) {
						msg += "（" + Global.sign + "）";

					} else {
						msg = "【" + Global.sign + "】" + msg;
					}
				}
				int result = msgSendService.sendRegisterCode(telephone, msg, code);
				if (result == 0) {
					message.setMsg("已发送验证码到您的手机【" + telephone.substring(0,3)+ "****" + telephone.substring(7,telephone.length())+ "】，请您输入手机收到的短信验证码，以便继续登录系统！");
					message.setReturnId("200");
					return;
				} else {
					message.setMsg("发送验证码到您的手机【" + telephone.substring(0,3)+ "****" + telephone.substring(7,telephone.length()) + "】时，发送失败了：错误码" + result);
					message.setReturnId("500");
					return;
				}
			} else {
				message.setMsg("发送验证码到您的手机【" + telephone.substring(0,3)+ "****" + telephone.substring(7,telephone.length()) + "】时，发送失败了：验证码模板未设置。");
				message.setReturnId("500");
				return;
			}
		}
	}

	public boolean checkSMSCode(Message message, String phoneNumber, String smscode) {
		SmsCode smsCode = smsCodeService.selectByTelId(phoneNumber, smscode);
		if (smsCode == null || !smsCode.getSmsCode().trim().equals(smscode)) {
			message.setMsg("手机短信验证码错误!");
			return false;
		}

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(smsCode.getCreateTime());
		calendar.add(Calendar.MINUTE, 10); // 10分钟
		Date valid = calendar.getTime();
		if (valid.before(new Date())) {
			message.setMsg("手机短信验证码已过期，请重新发送短信验证码!");
			return false;
		} else {
			return true;
		}
	}

	public void sendSMSRemindForWebLogin(Message message, User user, String userAgent){
		String telephone = user.getTelephone();
		String code = "";
		String msg = user.getUserName()+"您好，您的账号"+ user.getUserAccount() + "于" + (new java.text.SimpleDateFormat("MM月dd日 HH:mm")).format(new Date()) + "在"+ userAgent +"登陆，如果非本人操作，请及时更换密码";
		if (Global.sign != null && Global.sign.length() > 0) {
//			msg = "【" + Global.sign + "】" + msg;
			if (Global.sendType != null && Global.sendType.equals("CLOUD")) {
				msg += "（" + Global.sign + "）";

			} else {
				msg = "【" + Global.sign + "】" + msg;
			}
		}
		msgSendService.sendRegisterCode(telephone, msg, code);
	}

	public Map<String, Object> toDoList(User user)
	{
		Map<String, Object> map = new HashMap<>();
		List<Map<String, Object>> children = new ArrayList<>();

		@SuppressWarnings("unchecked")
		List<Resource> list = (List<Resource>) CacheUtils.get("resources");

		long rTotal =0, rNewsId=0, rUserInfoId=0, rClassHandleId=0, rContactId=0, rDonationId=0, rMessageBoardId=0, rOrgMembersId=0, rPersonalEventsId=0;

		for(Resource tmp:list)
		{
			if(tmp.getName().equals("快捷审批"))
			{
				rTotal = tmp.getId();
			}
			else if(tmp.getName().equals("新闻管理_快捷审批"))
			{
				rNewsId = tmp.getId();
			}
			else if(tmp.getName().equals("校友审核_快捷审批"))
			{
				rUserInfoId = tmp.getId();
			}
			else if(tmp.getName().equals("班级审核_快捷审批"))
			{
				rClassHandleId = tmp.getId();
			}
			else if(tmp.getName().equals("联系学校_快捷审批"))
			{
				rContactId = tmp.getId();
			}
			else if(tmp.getName().equals("意见反馈_快捷审批"))
			{
				rMessageBoardId = tmp.getId();
			}
			else if(tmp.getName().equals("入会审核_快捷审批"))
			{
				rOrgMembersId = tmp.getId();
			}
			else if(tmp.getName().equals("活动审核_快捷审批"))
			{
				rPersonalEventsId = tmp.getId();
			}
			else continue;
		}

		//查询总会待审项目
		long total, countNews, countUserInfo, countClassHandle, countContact, countMessageBoard, countOrgMembers, countPersonalEvents;
		if(user != null && user.getDeptId()!=0)
		{
			long deptId = user.getDeptId();

			//查询待审新闻数量
			Map<String, Object> newsMap = new HashMap<>();
			newsMap.put("currDeptId", deptId);
			newsMap.put("isFromCheckPage", "1");
			countNews = newsMapper.countNewsNew(newsMap);
			newsMap.clear();
			newsMap.put("id", rNewsId);
			newsMap.put("num", countNews);
			newsMap.put("name","新闻管理");
			children.add(newsMap);

			//查询待审校友数量
			Map<String, Object> userInfoMap = new HashMap<>();
			userInfoMap.put("checkPage", "1");
//			userInfoMap.put( "deptId", deptId );
			countUserInfo = userInfoMapper.countByDeptIdNew(userInfoMap);
			userInfoMap.clear();
			userInfoMap.put("id", rUserInfoId);
			userInfoMap.put("num", countUserInfo);
			userInfoMap.put("name", "校友审核");
			children.add(userInfoMap);


			//查询待审班级审核数量
			Map<String, Object> classHandleMap = new HashMap<>();
			classHandleMap.put("currdeptId",deptInfoMapper.getAcademyId(deptId));
			classHandleMap.put("status", "10");
			countClassHandle = classHandleMapper.countByDeptId(classHandleMap);
			classHandleMap.clear();
			classHandleMap.put("id", rClassHandleId);
			classHandleMap.put("num", countClassHandle);
			classHandleMap.put("name", "班级审核");
			children.add(classHandleMap);

			//查询待回复联系学校数量
			Map<String, Object> contactMap = new HashMap<>();
			contactMap.put("alumniId", deptId);
			contactMap.put("category", "2");
			contactMap.put("checkPage", "1");
			countContact = contactMapper.count(contactMap);
			contactMap.clear();
			contactMap.put("id", rContactId);
			contactMap.put("num", countContact);
			contactMap.put("name", "联系学校");
			children.add(contactMap);


			//查询待审意见数量
			Map<String, Object> replyMap = new HashMap<>();
			replyMap.put("messageType", "404");
			replyMap.put("checkStatus", "0");
			countMessageBoard = messageBoardMapper.countMessage(replyMap);
			replyMap.clear();
			replyMap.put("id", rMessageBoardId);
			replyMap.put("num", countMessageBoard);
			replyMap.put("name", "意见反馈");
			children.add(replyMap);

			//查询待审会员数量
			Map<String, Object> orgMemberMap = new HashMap<>();
			orgMemberMap.put("userAlStatus", "10");
			orgMemberMap.put("currAlumniId", deptId );
			countOrgMembers = userProfileMapper.countByDeptFormUserProfile(orgMemberMap);
			orgMemberMap.clear();
			orgMemberMap.put("id", rOrgMembersId);
			orgMemberMap.put("num", countOrgMembers);
			orgMemberMap.put("name", "入会审核");
			children.add(orgMemberMap);

			//查詢待審核活動數量
			Map<String, Object> personalEventMap = new HashMap<>();
			personalEventMap.put("type", "9");
			personalEventMap.put("auditStatus", "0");
			personalEventMap.put("authorityAlumniId",deptId);
			countPersonalEvents = eventMapper.count(personalEventMap) ;
			personalEventMap.clear();
			personalEventMap.put("id", rPersonalEventsId);
			personalEventMap.put("num", countPersonalEvents);
			personalEventMap.put("name", "活动审核");
			children.add(personalEventMap);

			Map<String, Object> totalMap = new HashMap<>();
			int hasRole = 0;
			if(user.getRoles() != null){
				for(Role r: user.getRoles()){
					if(r.getRoleId() == 14){
						hasRole++;
					}
					if(r.getRoleId() == 15){
						hasRole+=2;
					}
				}
			}
			switch (hasRole){
				case 1: total = countClassHandle+ countContact+countMessageBoard+countNews+countUserInfo;
					break;
				case 2: total = countOrgMembers;
					break;
				case 3: total = countClassHandle+ countContact+countMessageBoard+countNews+countUserInfo+countOrgMembers;
					break;
				default: total = 0;
			}

			totalMap.put("id", rTotal);
			totalMap.put("num", total);
			totalMap.put("name", "总计");

			map.put("total", totalMap);
			map.put("children", children);
		}

		return map;
	}
}
