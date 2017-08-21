package com.cy.core.mobserv.action;


import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.alibaba.fastjson.JSON;
import com.cy.common.utils.JsonUtils;
import com.cy.common.utils.StringUtils;
import com.cy.core.alumni.entity.Alumni;
import com.cy.core.alumni.service.AlumniService;
import com.google.common.collect.Lists;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.aspectj.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;

import com.cy.base.action.AdminBaseAction;
import com.cy.base.entity.Message;
import com.cy.core.mobevent.entity.CyEvent;
import com.cy.core.mobevent.service.MobEventService;
import com.cy.core.mobserv.entity.CyContact;
import com.cy.core.mobserv.entity.CyServ;
import com.cy.core.mobserv.entity.CyServComment;
import com.cy.core.mobserv.entity.CyServComplaint;
import com.cy.core.mobserv.entity.CyServFavorite;
import com.cy.core.mobserv.entity.CyServPic;
import com.cy.core.mobserv.entity.CyServPraise;
import com.cy.core.mobserv.service.MobServService;
import com.cy.system.Global;
import com.cy.util.WebUtil;


@Namespace("/mobile/serv")
@Action(value = "mobServAction")
public class MobServAction extends AdminBaseAction
{
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(MobServAction.class);

	@Autowired
	private MobServService service;

	@Autowired
	private MobEventService eventService;

	@Autowired
	private AlumniService alumniService;

	private String isWhat;////0或空:本地，1:所有，2:我的收藏，3:我的发帖

	private String jsonStr;

	private CyServ cyServ;

	private CyServPic cyServPic;

	private CyServComment cyServComment;

	private CyServComplaint cyServComplaint;

	private CyServFavorite cyServFavorite;

	private CyServPraise cyServPraise;

	private CyContact cyContact;

	private File[] uploadArr;


	/**--小图尺寸--**/
	private static final String MIN_IMG_SIZE = "100*80";

	/**--大图尺寸--**/
	private static final String MAX_IMG_SIZE = "320*200";

	public void doNotNeedSessionAndSecurity_selectAlumni() {
		Message message = new Message();

		boolean isWlight = false;

		try{
			isWlight = Boolean.parseBoolean(getRequest().getParameter("isWlight"));
		}catch(Exception e){
			isWlight = false;
		}

		try {

			if(!isWlight){
				if (WebUtil.isEmpty(jsonStr)) {
					message.setMsg("jsonStr is empty!");
					message.setSuccess(false);
					super.writeJson(message);
					return;
				}

				JSONObject jsonObject = JSONObject.fromObject(jsonStr);
				if (jsonObject == null) {
					message.setMsg("jsonStr格式错误!");
					message.setSuccess(false);
					super.writeJson(message);
					return;
				}

				Map<String, Object> map = JSON.parseObject(jsonStr, Map.class);

				List<Alumni> list = Lists.newArrayList() ;

				if ("-1".equals(map.get("type"))) {
					map.put("type","1") ;
					List<Alumni> collegeList = alumniService.queryList(map);
					if(collegeList != null) {
						list.addAll(collegeList) ;
					}

					map.put("type","2") ;
					List<Alumni>  areaList = alumniService.queryList(map);
					if(areaList != null) {
						list.addAll(areaList) ;
					}
					map.put("type","3") ;
					List<Alumni> industryList = alumniService.queryList(map);
					if(industryList != null) {
						list.addAll(industryList) ;
					}
				} else {
					list = alumniService.queryList(map);
				}

//				super.writeJson(alumniList);
				message.setObj(list);
			}

			message.setMsg("创建成功，请等待审核");
			message.setSuccess(true);
			super.writeJson(message);
			return;
		}catch(Exception e){
			logger.error(e, e);
			message.setMsg("创建失败");
			message.setSuccess(false);
			logger.info("cyContact="+cyContact.toString());
			super.writeJson(message);
			return;
		}

	}

	public void doNotNeedSessionAndSecurity_insertContact() {
		Message message = new Message();

		boolean isWlight = false;

		try{
			isWlight = Boolean.parseBoolean(getRequest().getParameter("isWlight"));
		}catch(Exception e){
			isWlight = false;
		}

		try {

			if(!isWlight){
				if (WebUtil.isEmpty(jsonStr)) {
					message.setMsg("jsonStr is empty!");
					message.setSuccess(false);
					super.writeJson(message);
					return;
				}

				JSONObject jsonObject = JSONObject.fromObject(jsonStr);
				if (jsonObject == null) {
					message.setMsg("jsonStr格式错误!");
					message.setSuccess(false);
					super.writeJson(message);
					return;
				}

				cyContact = (CyContact)JSONObject.toBean(jsonObject, CyContact.class);
			}


			service.insertContact(cyContact);


			message.setMsg("创建成功，请等待审核");
			message.setSuccess(true);
			message.setReturnId(String.valueOf(cyContact.getId()));
			super.writeJson(message);
			return;
		}catch(Exception e){
			logger.error(e, e);
			message.setMsg("创建失败");
			message.setSuccess(false);
			logger.info("cyContact="+cyContact.toString());
			super.writeJson(message);
			return;
		}

	}

	public void doNotNeedSessionAndSecurity_getContactList(){

		if ((cyContact.getPage()!=0) &&(cyContact.getRows()!=0)) {
			int start = (cyContact.getPage() - 1) * cyContact.getRows();
			cyContact.setCurrentRow(start);
			cyContact.setIncremental(cyContact.getRows());
		}
		List<CyContact> tmpList = service.getContactList(cyContact);
		long count = service.getContactListCount(cyContact);

		if(tmpList != null){

			for (CyContact cyCon : tmpList) {

				if(cyCon.getReplyContent()==null || cyCon.getReplyContent().equals("")){
					cyCon.setReplyContent("暂无回复");
				}

				if(WebUtil.isNumeric(cyCon.getUserAvatar())){
					cyCon.setUserAvatar(WebUtil.getIcon(Integer.parseInt(cyCon.getUserAvatar())));
				}

				cyCon.setCreateTimeStr(WebUtil.pastTime(cyCon.getCreateTime()));

			}
		}

		Map<String, Object> response = new HashMap<String, Object>();
		response.put("lists", tmpList);
		response.put("total", count);
		super.writeJson(response);

	}



	public void doNotNeedSessionAndSecurity_insertServ() {
		Message message = new Message();

		boolean isWlight = false;

		try{
			isWlight = Boolean.parseBoolean(getRequest().getParameter("isWlight"));
		}catch(Exception e){
			isWlight = false;
		}

		try {

			if(!isWlight){

				if (WebUtil.isEmpty(jsonStr)) {
					message.setMsg("jsonStr is empty!");
					message.setSuccess(false);
					super.writeJson(message);
					return;
				}

				JSONObject jsonObject = JSONObject.fromObject(jsonStr);
				if (jsonObject == null) {
					message.setMsg("jsonStr格式错误!");
					message.setSuccess(false);
					super.writeJson(message);
					return;
				}

				cyServ = (CyServ)JSONObject.toBean(jsonObject, CyServ.class);
			}
			cyServ.setAuditStatus(1);
			service.insertServ(cyServ);

			message.setMsg("创建成功");
			message.setSuccess(true);
			message.setReturnId(String.valueOf(cyServ.getId()));
			super.writeJson(message);
			return;
		}catch(Exception e){
			logger.error(e, e);
			message.setMsg("创建失败");
			message.setSuccess(false);
			logger.info("cyServ="+cyServ.toString());
			super.writeJson(message);
			return;
		}

	}

	public void doNotNeedSessionAndSecurity_insertServPic() {
		Message message = new Message();

		boolean isSuc = false;

		String tmpMsg = "提示信息";

		try{

			if (WebUtil.isEmpty(jsonStr)) {
				message.setMsg("jsonStr is empty!");
				message.setSuccess(false);
				super.writeJson(message);
				return;
			}

			JSONObject jsonObject = JSONObject.fromObject(jsonStr);
			if (jsonObject == null) {
				message.setMsg("jsonStr格式错误!");
				message.setSuccess(false);
				super.writeJson(message);
				return;
			}

			cyServPic = (CyServPic)JSONObject.toBean(jsonObject, CyServPic.class);
			logger.info("cyServPic="+cyServPic.toString());
			String[] picFileName = cyServPic.getPicStr().split(",");
			cyServPic.setPicStr("");

			Date tmpDate = new Date();

			HashMap<String,String> fileMap = checkFileFolderAndCreateIfNotExsit();

			String savePath = fileMap.get("savePath");
			String saveUrl = fileMap.get("saveUrl");


			for (int i = 0; i < picFileName.length; i++) {

				logger.info("picname="+picFileName[i]);

				if(picFileName[i] != null && picFileName[i].lastIndexOf(".") != -1){

					picFileName[i] = "mob" + tmpDate.getTime() + new Random().nextInt(1000) + picFileName[i].substring(picFileName[i].lastIndexOf("."));

					if(i < uploadArr.length){
						if(GenerateImageForMob(uploadArr[i], savePath + picFileName[i])){

							logger.info("Success pic=" + savePath + picFileName[i] + " and pic url="+ saveUrl + picFileName[i]);

							cyServPic.setPic(saveUrl + picFileName[i]);

						}else{
							logger.info("Failure pic=" + savePath + picFileName[i] + " and pic url="+ saveUrl + picFileName[i]);
							tmpMsg += ";图片上传失败:"+picFileName[i];
							continue;
						}
					}else{
						logger.info("Failure pic=" + savePath + picFileName[i] + " and pic url="+ saveUrl + picFileName[i]);
						tmpMsg += ";找不到对应的图片对象:"+picFileName[i];
						continue;
					}



				}else{
					tmpMsg += ";图片文件名不正确:"+picFileName[i];
					continue;
				}


				service.insertServPic(cyServPic);
				tmpMsg += ";图片上传成功:"+picFileName[i];
				isSuc = true;
			}


		}catch(Exception e){
			logger.error(e, e);
			message.setMsg("发生错误,上传图片失败");
			message.setSuccess(false);
			logger.info("cyServPic="+cyServPic.toString());
			super.writeJson(message);
			return;
		}

		if(!isSuc){
			tmpMsg = "上传图片失败";
		}

		message.setMsg(tmpMsg);
		message.setSuccess(isSuc);

		super.writeJson(message);
		return;

	}

	public boolean GenerateImageForMob(File tmpUpload, String imgFilePath) {
		if (tmpUpload == null) // 图像数据为空  
			return false;

		File uploadedFile = new File(imgFilePath);

		try {
			FileUtil.copyFile(tmpUpload, uploadedFile);

			String fileExt = imgFilePath.substring(imgFilePath.lastIndexOf("."));
			String fileUrl = imgFilePath.substring(0, imgFilePath.lastIndexOf("."));
			//小图图
			WebUtil.getThumb(MIN_IMG_SIZE, true, imgFilePath, fileUrl+"_MIN"+fileExt);
			//高清图
			WebUtil.getThumb(MAX_IMG_SIZE,true, imgFilePath, fileUrl+"_MAX"+fileExt);

		} catch (IOException e) {
			return false;
		}

		return true;
	}

	public HashMap<String,String> checkFileFolderAndCreateIfNotExsit(){

		HashMap<String,String> hm = new HashMap<String,String>();

		// 文件保存目录路径
		String savePath = Global.DISK_PATH;

		// 文件保存目录URL
		String saveUrl = Global.URL_DOMAIN;

		String dirName = "image";
		// 创建文件夹
		savePath += dirName + "/";
		saveUrl += dirName + "/";
		File saveDirFile = new File(savePath);
		if (!saveDirFile.exists())
		{
			saveDirFile.mkdirs();
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String ymd = sdf.format(new Date());
		savePath += ymd + "/";
		saveUrl += ymd + "/";
		File dirFile = new File(savePath);
		if (!dirFile.exists())
		{
			dirFile.mkdirs();
		}

		hm.put("savePath", savePath);
		hm.put("saveUrl", saveUrl);

		return hm;
	}



	public void doNotNeedSessionAndSecurity_getServList()
	{


		if("0".equalsIgnoreCase(isWhat) || WebUtil.isEmpty(isWhat))
		{
			CyEvent eventData = new CyEvent();
			eventData.setUserInfoId(String.valueOf(cyServ.getAccountNum()));

			String regionStr = eventService.getRegionFromAlumniByAccountNum(eventData);
			cyServ.setRegion(regionStr);
		}

		long accountNum = cyServ.getAccountNum();
		Map<String, Object> response = new HashMap<String, Object>();
		response.put("countServ", service.countServ(cyServ));

		cyServ.setAccountNum(accountNum);
		response.put("servList", service.getServList(cyServ));
		super.writeJson(response);
	}


	public void doNotNeedSessionAndSecurity_getServCommentListWithServ()
	{
		if(cyServComment == null)
		{
			cyServComment = new CyServComment();
			cyServComment.setServiceId(cyServ.getId());
		}
		else
		{
			cyServComment.setServiceId(cyServ.getId());
		}

		super.writeJson(service.getServCommentListWithServ(cyServ, cyServComment));
	}


	public void doNotNeedSessionAndSecurity_getServCommentList()
	{
		if(cyServComment == null)
		{
			cyServComment = new CyServComment();
			cyServComment.setServiceId(cyServ.getId());
		}
		else
		{
			cyServComment.setServiceId(cyServ.getId());
		}

		super.writeJson(service.getServCommentListWithServ(cyServ, cyServComment));
	}


	public void doNotNeedSessionAndSecurity_praise()
	{
		Map<String, Object> response = new HashMap<String, Object>();

		try
		{
			service.servPraise(cyServPraise);
			response.put("status", true);
		}
		catch (Exception e)
		{
			logger.error(e, e);
			response.put("status", false);
		}

		super.writeJson(response);
	}



	public void doNotNeedSessionAndSecurity_favorite()
	{
		Map<String, Object> response = new HashMap<String, Object>();

		try
		{
			service.servFavorite(cyServFavorite);
			response.put("status", true);
		}
		catch (Exception e)
		{
			logger.error(e, e);
			response.put("status", false);
		}

		super.writeJson(response);
	}


	public void doNotNeedSessionAndSecurity_comment()
	{
		Map<String, Object> response = new HashMap<String, Object>();

		try
		{
			service.insertServComment(cyServComment);
			response.put("status", true);
		}
		catch (Exception e)
		{
			logger.error(e, e);
			response.put("status", false);
		}

		super.writeJson(response);
	}



	public void doNotNeedSessionAndSecurity_complaint()
	{
		Map<String, Object> response = new HashMap<String, Object>();

		try
		{
			long complaintCount = service.countServComplaint(cyServComplaint);

			if(complaintCount > 0)
			{
				response.put("status", false);
			}
			else
			{
				service.insertServComplaint(cyServComplaint);
				response.put("status", true);
			}



		}
		catch (Exception e)
		{
			logger.error(e, e);
			response.put("status", false);
		}

		super.writeJson(response);
	}



	public void doNotNeedSessionAndSecurity_deleteComment()
	{
		Map<String, Object> response = new HashMap<String, Object>();

		try
		{
			service.deleteServComment(cyServComment);
			response.put("status", true);
		}
		catch (Exception e)
		{
			logger.error(e, e);
			response.put("status", false);
		}

		super.writeJson(response);
	}





	public void doNotNeedSessionAndSecurity_deleteServ()
	{
		Map<String, Object> response = new HashMap<String, Object>();

		try
		{
			service.deleteServ(cyServ);
			response.put("status", true);
		}
		catch (Exception e)
		{
			logger.error(e, e);
			response.put("status", false);
		}

		super.writeJson(response);
	}


	public void doNotNeedSessionAndSecurity_getMobProvinceAndCapital(){


		List<Map<String,String>> tmpList = service.getMobProvinceCapital();

		Map<String, Object> response = new HashMap<String, Object>();


		if(tmpList != null){

			response.put("ProvinceCapital", tmpList);

		}

		tmpList = service.getMobProvince();

		if(tmpList != null){

			response.put("Province", tmpList);

		}

		super.writeJson(response);

	}

	public void doNotNeedSessionAndSecurity_getMobProvinceAndId(){


		List<Map<String,String>> tmpList = service.getMobProvinceAndId();

		super.writeJson(tmpList);

	}

	public void doNotNeedSessionAndSecurity_getMobCapitalFromProvince(){

		int provinceId = 0;

		Map<String, Object> requestMap = new HashMap<String, Object>();

		try{
			provinceId = Integer.parseInt(getRequest().getParameter("provinceId"));
		}catch(Exception e){
			provinceId = 0;
		}

		requestMap.put("provinceId", provinceId);

		List<Map<String,String>> tmpList = service.getMobCapitalFromProvince(requestMap);

		super.writeJson(tmpList);



	}


	public void doNotNeedSessionAndSecurity_addQzzp() {
		Message message = new Message();
		try {
			String accountNum = getRequest().getParameter("accountNum");
			String category = getRequest().getParameter("category");

			String title = getRequest().getParameter("title");
			String address = getRequest().getParameter("address");
			String type = getRequest().getParameter("type");
			String description = getRequest().getParameter("description");
			String requirement = getRequest().getParameter("requirement");

			Map<String,String> map = new HashMap<String,String>();
			map.put("title",title);
			map.put("address",address);
			map.put("type",type);
			map.put("description",description);
			map.put("requirement",requirement);
			String contentJson = JsonUtils.obj2json(map);

			CyServ cyServ = new CyServ();
			cyServ.setAccountNum(Long.parseLong(accountNum));
			cyServ.setCategory(Integer.parseInt(category));
			cyServ.setType(9);
			cyServ.setAuditStatus(0);
			cyServ.setContent(contentJson);

			service.insertServ(cyServ);

			message.setMsg("创建成功，请等待审核");
			message.setSuccess(true);
			message.setReturnId(String.valueOf(cyServ.getId()));
			super.writeJson(message);
			return;
		} catch (Exception e) {
			logger.error(e, e);
			message.setMsg("创建失败");
			message.setSuccess(false);
			super.writeJson(message);
			return;
		}

	}

	public void doNotNeedSessionAndSecurity_selectAlumniByUserId(){

		String userId = getRequest().getParameter("userId");

		List<Alumni> list=alumniService.findAlumniListFromAccountNum(userId);

		super.writeJson(list);
	}


	public String getJsonStr() {
		return jsonStr;
	}

	public void setJsonStr(String jsonStr) {
		this.jsonStr = jsonStr;
	}

	public CyServ getCyServ() {
		return cyServ;
	}

	public void setCyServ(CyServ cyServ) {
		this.cyServ = cyServ;
	}

	public CyServPic getCyServPic() {
		return cyServPic;
	}

	public void setCyServPic(CyServPic cyServPic) {
		this.cyServPic = cyServPic;
	}

	public File[] getUploadArr() {
		return uploadArr;
	}

	public void setUploadArr(File[] uploadArr) {
		this.uploadArr = uploadArr;
	}

	public CyServComment getCyServComment() {
		return cyServComment;
	}

	public CyServComplaint getCyServComplaint() {
		return cyServComplaint;
	}

	public CyServFavorite getCyServFavorite() {
		return cyServFavorite;
	}

	public CyServPraise getCyServPraise() {
		return cyServPraise;
	}

	public void setCyServComment(CyServComment cyServComment) {
		this.cyServComment = cyServComment;
	}

	public void setCyServComplaint(CyServComplaint cyServComplaint) {
		this.cyServComplaint = cyServComplaint;
	}

	public void setCyServFavorite(CyServFavorite cyServFavorite) {
		this.cyServFavorite = cyServFavorite;
	}

	public void setCyServPraise(CyServPraise cyServPraise) {
		this.cyServPraise = cyServPraise;
	}

	public CyContact getCyContact() {
		return cyContact;
	}

	public void setCyContact(CyContact cyContact) {
		this.cyContact = cyContact;
	}

	public String getIsWhat() {
		return isWhat;
	}

	public void setIsWhat(String isWhat) {
		this.isWhat = isWhat;
	}




}
