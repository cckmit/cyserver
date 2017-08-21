package com.cy.core.file.action;

import com.cy.common.utils.StringUtils;
import com.cy.mobileInterface.multipleFileUpload.service.MultipleFileUploadService;
import com.cy.system.SystemUtil;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.aspectj.util.FileUtil;

import com.alibaba.fastjson.JSONObject;
import com.cy.base.action.AdminBaseAction;
import com.cy.system.Global;
import com.cy.util.WebUtil;
import com.cy.util.file.DefaultFileUpload;
import com.cy.util.file.FileResult;
import org.springframework.beans.factory.annotation.Autowired;

@Namespace("/fileUpload")
@Action(value = "fileUploadAction")
public class FileUploadAction extends AdminBaseAction {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(FileUploadAction.class);

	private File upload;
	private String uploadFileName;
	private File imgFile;
	private String imgFileFileName;
	private String uploadFileBase64;

	@Autowired
	private MultipleFileUploadService multipleFileUploadService;

	/** --小图尺寸-- **/
//	private static final String MIN_IMG_SIZE = "100*80";
	private static final String MIN_IMG_SIZE = "450*360";

	/** --大图尺寸-- **/
//	private static final String MAX_IMG_SIZE = "320*200";
	private static final String MAX_IMG_SIZE = "800*500";

	public void doNotNeedSecurity_fileUpload() {
		try {
			// 文件保存目录路径
			String savePath = Global.DISK_PATH;

			// 文件保存目录URL
			String saveUrl = Global.URL_DOMAIN;

			// 定义允许上传的文件扩展名
			HashMap<String, String> extMap = new HashMap<String, String>();
			extMap.put("image", "gif,jpg,jpeg,png,bmp");
			extMap.put("flash", "swf,flv");
			extMap.put("media", "swf,flv,mp3,wav,wma,wmv,mid,avi,mpg,asf,rm,rmvb");
			extMap.put("file", "doc,docx,xls,xlsx,ppt,pdf,htm,html,txt,zip,rar,gz,bz2,apk");

			// 最大文件大小50M
			long maxSize = 1048576 * 22;

			getResponse().setContentType("text/html; charset=UTF-8");

			if (!ServletFileUpload.isMultipartContent(getRequest())) {
				getPrintWriter().println(getError("请选择文件。"));
				return;
			}
			// 检查目录
			File uploadDir = new File(savePath);
			if (!uploadDir.isDirectory()) {
				getPrintWriter().println(getError("上传目录不存在。path = " + uploadDir.getPath()));
				return;
			}
			// 检查目录写权限
			if (!uploadDir.canWrite()) {
				getPrintWriter().println(getError("上传目录没有写权限。"));
				return;
			}

			String dirName = getRequest().getParameter("dir");
			if (dirName == null) {
				dirName = "image";
			}
			if (!extMap.containsKey(dirName)) {
				getPrintWriter().println(getError("目录名不正确。"));
				return;
			}
			// 创建文件夹
			savePath += dirName + "/";
			saveUrl += dirName + "/";
			File saveDirFile = new File(savePath);
			if (!saveDirFile.exists()) {
				saveDirFile.mkdirs();
			}
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			String ymd = sdf.format(new Date());
			savePath += ymd + "/";
			saveUrl += ymd + "/";
			File dirFile = new File(savePath);
			if (!dirFile.exists()) {
				dirFile.mkdirs();
			}
			// 检查文件大小
			if (new FileInputStream(upload).available() > maxSize) {
				getPrintWriter().println(getError("上传文件大小超过限制。"));
				return;
			}
			// 检查扩展名
			String fileExt = uploadFileName.substring(uploadFileName.lastIndexOf(".") + 1).toLowerCase();
			if (!Arrays.<String> asList(extMap.get(dirName).split(",")).contains(fileExt)) {
				getPrintWriter().println(getError("上传文件扩展名是不允许的扩展名。\n只允许" + extMap.get(dirName) + "格式。"));
				return;
			}
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
			// String newFileName = df.format(new Date()) + "_" + new
			// Random().nextInt(1000) + "." + fileExt;
			String newFileName = df.format(new Date()) + "_" + new Random().nextInt(1000);
			// String newFileName =WebUtil.getPrimaryKey();
			File uploadedFile = new File(savePath, newFileName + "." + fileExt);
			FileUtil.copyFile(upload, uploadedFile);
			// 生成缩微图
			// FileUtil.copyFile(upload, uploadedFile);
			// extMap.put("image", "gif,jpg,jpeg,png,bmp");
			JSONObject obj = new JSONObject();
			/*
			 * if(fileExt.equals("gif")||fileExt.equals("jpg")||fileExt.equals(
			 * "jpeg")||fileExt.equals("png")||fileExt.equals("bmp")){
			 * WebUtil.getThumb(MIN_IMG_SIZE, true, savePath+newFileName+"." +
			 * fileExt, savePath+newFileName+"_MIN"+"." + fileExt); //高清图
			 * WebUtil.getThumb(MAX_IMG_SIZE,true, savePath+newFileName+"." +
			 * fileExt, savePath+newFileName+"_MAX"+"." + fileExt);
			 * obj.put("error", 0); obj.put("url", saveUrl +
			 * newFileName+"_MIN"+"." + fileExt); }else{ obj.put("error", 0);
			 * obj.put("url", saveUrl + newFileName+"." + fileExt); }
			 */
			obj.put("error", 0);
			obj.put("url", saveUrl + newFileName + "." + fileExt);
			getPrintWriter().println(obj.toJSONString());

		} catch (Exception e) {
			getPrintWriter().println(getError("系统异常"));
			logger.error(e, e);
		}
	}

	/**
	 * 外网使用
	 */
	public void doNotNeedSessionAndSecurity_fileUpload() {
		try {
			// 文件保存目录路径
			String savePath = Global.DISK_PATH;

			// 文件保存目录URL
			String saveUrl = Global.URL_DOMAIN;

			// 定义允许上传的文件扩展名
			HashMap<String, String> extMap = new HashMap<String, String>();
			extMap.put("image", "gif,jpg,jpeg,png,bmp");
			extMap.put("flash", "swf,flv");
			extMap.put("media", "swf,flv,mp3,wav,wma,wmv,mid,avi,mpg,asf,rm,rmvb");
			extMap.put("file", "doc,docx,xls,xlsx,ppt,htm,html,txt,zip,rar,gz,bz2,apk");

			// 最大文件大小50M
			long maxSize = 1048576 * 1;

			getResponse().setContentType("text/html; charset=UTF-8");

			if (!ServletFileUpload.isMultipartContent(getRequest())) {
				getPrintWriter().println(getError("请选择文件。"));
				return;
			}
			// 检查目录
			File uploadDir = new File(savePath);
			if (!uploadDir.isDirectory()) {
				getPrintWriter().println(getError("上传目录不存在。"));
				return;
			}
			// 检查目录写权限
			if (!uploadDir.canWrite()) {
				getPrintWriter().println(getError("上传目录没有写权限。"));
				return;
			}

			String dirName = getRequest().getParameter("dir");
			if (dirName == null) {
				dirName = "image";
			}
			if (!extMap.containsKey(dirName)) {
				getPrintWriter().println(getError("目录名不正确。"));
				return;
			}
			// 创建文件夹
			savePath += dirName + "/";
			saveUrl += dirName + "/";
			File saveDirFile = new File(savePath);
			if (!saveDirFile.exists()) {
				saveDirFile.mkdirs();
			}
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			String ymd = sdf.format(new Date());
			savePath += ymd + "/";
			saveUrl += ymd + "/";
			File dirFile = new File(savePath);
			if (!dirFile.exists()) {
				dirFile.mkdirs();
			}
			// 检查文件大小
			if (new FileInputStream(upload).available() > maxSize) {
				getPrintWriter().println(getError("上传文件大小超过限制。"));
				return;
			}
			// 检查扩展名
			String fileExt = uploadFileName.substring(uploadFileName.lastIndexOf(".") + 1).toLowerCase();
			if (!Arrays.<String> asList(extMap.get(dirName).split(",")).contains(fileExt)) {
				getPrintWriter().println(getError("上传文件扩展名是不允许的扩展名。\n只允许" + extMap.get(dirName) + "格式。"));
				return;
			}
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
			String newFileName = df.format(new Date()) + "_" + new Random().nextInt(1000);
			File uploadedFile = new File(savePath, newFileName + "." + fileExt);
			FileUtil.copyFile(upload, uploadedFile);
			JSONObject obj = new JSONObject();
			obj.put("error", 0);
			obj.put("url", saveUrl + newFileName + "." + fileExt);
			getPrintWriter().println(obj.toJSONString());
		} catch (Exception e) {
			getPrintWriter().println(getError("系统异常"));
			logger.error(e, e);
		}
	}

	/** --新闻封面图的上传-- **/
	public void doNotNeedSecurity_fileUploadNews() {
		try {
			// 文件保存目录路径
			String savePath = Global.DISK_PATH;

			// 文件保存目录URL
			String saveUrl = Global.URL_DOMAIN;
			//String saveUrl ="";

			// 定义允许上传的文件扩展名
			HashMap<String, String> extMap = new HashMap<String, String>();
			extMap.put("image", "gif,jpg,jpeg,png,bmp");

			// 最大图片文件大小5M
			long maxSize = 1048576 * 5;
//			long maxSize = 1024 * 200;
			getResponse().setContentType("text/html; charset=UTF-8");

			if (!ServletFileUpload.isMultipartContent(getRequest())) {
				getPrintWriter().println(getError("请选择文件。"));
				return;
			}
			// 检查目录
			logger.info("----> 文件路径: " + savePath);
			File uploadDir = new File(savePath);
			if (!uploadDir.isDirectory()) {
				getPrintWriter().println(getError("上传目录不存在。path = " + uploadDir.getPath()));
				return;
			}
			// 检查目录写权限
			if (!uploadDir.canWrite()) {
				getPrintWriter().println(getError("上传目录没有写权限。"));
				return;
			}

			String dirName = getRequest().getParameter("dir");
			if (dirName == null) {
				dirName = "image";
			}
			if (!extMap.containsKey(dirName)) {
				getPrintWriter().println(getError("目录名不正确。"));
				return;
			}
			// 创建文件夹
			savePath += "/"+ dirName + "/";
			saveUrl += "/"+ dirName + "/";
			File saveDirFile = new File(savePath);
			if (!saveDirFile.exists()) {
				saveDirFile.mkdirs();
			}
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			String ymd = sdf.format(new Date());
			savePath += ymd + "/";
			saveUrl += ymd + "/";
			File dirFile = new File(savePath);
			if (!dirFile.exists()) {
				dirFile.mkdirs();
			}
			// 检查文件大小
			if (new FileInputStream(upload).available() > maxSize) {
				getPrintWriter().println(getError("上传文件大小超过限制。"));
				return;
			}
			// 检查扩展名
			String fileExt = uploadFileName.substring(uploadFileName.lastIndexOf(".") + 1).toLowerCase();
			if (!Arrays.<String> asList(extMap.get(dirName).split(",")).contains(fileExt)) {
				getPrintWriter().println(getError("上传文件扩展名是不允许的扩展名。\n只允许" + extMap.get(dirName) + "格式。"));
				return;
			}
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
			// String newFileName = df.format(new Date()) + "_" + new
			// Random().nextInt(1000) + "." + fileExt;
			String newFileName = df.format(new Date()) + "_" + new Random().nextInt(1000);
			// String newFileName =WebUtil.getPrimaryKey();
			File uploadedFile = new File(savePath, newFileName + "." + fileExt);
			FileUtil.copyFile(upload, uploadedFile);
			// 生成缩微图
			// FileUtil.copyFile(upload, uploadedFile);
			// extMap.put("image", "gif,jpg,jpeg,png,bmp");
			JSONObject obj = new JSONObject();
			WebUtil.getThumb(MIN_IMG_SIZE, true, savePath + newFileName + "." + fileExt, savePath + newFileName + "_MIN" + "." + fileExt);
			// 高清图
			WebUtil.getThumb(MAX_IMG_SIZE, true, savePath + newFileName + "." + fileExt, savePath + newFileName + "_MAX" + "." + fileExt);
			obj.put("error", 0);
			obj.put("url", saveUrl + newFileName + "." + fileExt);
			getPrintWriter().println(obj.toJSONString());

		} catch (Exception e) {
			getPrintWriter().println(getError("系统异常"));
			logger.error(e, e);
		}
	}
	/** --新的新闻封面图的上传-- **/
	public void doNotNeedSecurity_fileUploadBase64News() {
		try {
			// 文件保存目录路径
			String savePath = Global.DISK_PATH;

			// 文件保存目录URL
			String saveUrl = "";

            //外部訪問路徑
            String getUrl = Global.URL_DOMAIN;

			// 定义允许上传的文件扩展名
			HashMap<String, String> extMap = new HashMap<String, String>();
			extMap.put("image", "gif,jpg,jpeg,png,bmp");

			// 最大图片文件大小2M
			long maxSize = 1048576 * 2;
//			long maxSize = 1024 * 200;

			if(StringUtils.isBlank(uploadFileBase64)){
                getPrintWriter().println(getError("请选择文件"));
                return;
            }

            upload = multipleFileUploadService.changeFromBase64ToFile(uploadFileBase64,uploadFileName) ;

			// 检查目录
			logger.info("----> 文件路径: " + savePath);
			File uploadDir = new File(savePath);
			if (!uploadDir.isDirectory()) {
				getPrintWriter().println(getError("上传目录不存在。path = " + uploadDir.getPath()));
				return;
			}
			// 检查目录写权限
			if (!uploadDir.canWrite()) {
				getPrintWriter().println(getError("上传目录没有写权限。"));
				return;
			}

			String dirName = getRequest().getParameter("dir");
			if (dirName == null) {
				dirName = "image";
			}
			if (!extMap.containsKey(dirName)) {
				getPrintWriter().println(getError("目录名不正确。"));
				return;
			}
			// 创建文件夹
			savePath += dirName + "/";
			saveUrl += dirName + "/";
			File saveDirFile = new File(savePath);
			if (!saveDirFile.exists()) {
				saveDirFile.mkdirs();
			}
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			String ymd = sdf.format(new Date());
			savePath += ymd + "/";
			saveUrl += ymd + "/";
			File dirFile = new File(savePath);
			if (!dirFile.exists()) {
				dirFile.mkdirs();
			}


			// 检查文件大小
			if (new FileInputStream(upload).available() > maxSize) {
				getPrintWriter().println(getError("上传文件大小超过限制。"));
				return;
			}
			// 检查扩展名
			String fileExt = uploadFileName.substring(uploadFileName.lastIndexOf(".") + 1).toLowerCase();
			if (!Arrays.<String> asList(extMap.get(dirName).split(",")).contains(fileExt)) {
				getPrintWriter().println(getError("上传文件扩展名是不允许的扩展名。\n只允许" + extMap.get(dirName) + "格式。"));
				return;
			}
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");

			String newFileName = df.format(new Date()) + "_" + new Random().nextInt(1000);
			//System.out.println(fileExt);
			File uploadedFile = new File(savePath, newFileName + "." + fileExt);
			FileUtil.copyFile(upload, uploadedFile);

			JSONObject obj = new JSONObject();
			if(!"gif".equals(fileExt)){
				WebUtil.getThumb("450*250", true, savePath + newFileName + "." + fileExt, savePath + newFileName + "_MIN" + "." + fileExt);
				// 高清图
				WebUtil.getThumb("900*500", true, savePath + newFileName + "." + fileExt, savePath + newFileName + "_MAX" + "." + fileExt);
			}else {
				File minFile = new File(savePath + newFileName + "_MIN" + "." + fileExt);
				File maxFile = new File(savePath + newFileName + "_MAX" + "." + fileExt);
				FileUtil.copyFile(upload, minFile);
				FileUtil.copyFile(upload, maxFile);
			}
			obj.put("error", 0);
			//obj.put("saveUrl", saveUrl + newFileName + "." + fileExt);
			obj.put("saveUrl", getUrl + saveUrl + newFileName + "." + fileExt);
            obj.put("url", getUrl + saveUrl + newFileName + "." + fileExt );
			getPrintWriter().println(obj.toJSONString());

		} catch (Exception e) {
			getPrintWriter().println(getError("系统异常"));
			logger.error(e, e);
		}
	}
	public void doNotNeedSecurity_fileUploadK() {
		try {
			// 文件保存目录路径
			String savePath = Global.DISK_PATH;

			// 文件保存目录URL
			String saveUrl = Global.URL_DOMAIN;

			// 定义允许上传的文件扩展名
			HashMap<String, String> extMap = new HashMap<String, String>();
			extMap.put("image", "gif,jpg,jpeg,png,bmp");
			extMap.put("flash", "swf,flv");
			extMap.put("media", "swf,flv,mp3,wav,wma,wmv,mid,avi,mpg,asf,rm,rmvb");
			extMap.put("file", "doc,docx,xls,xlsx,ppt,htm,html,txt,zip,rar,gz,bz2,apk");

			// 最大文件大小5M
			long maxSize = 1048576 * 5;
//			long maxSize = 1024 * 500;

			getResponse().setContentType("text/html; charset=UTF-8");

			if (!ServletFileUpload.isMultipartContent(getRequest())) {
				getPrintWriter().println(getError("请选择文件。"));
				return;
			}
			// 检查目录
			File uploadDir = new File(savePath);
			if (!uploadDir.isDirectory()) {
				getPrintWriter().println(getError("上传目录不存在。path = " + uploadDir.getPath()));
				return;
			}
			// 检查目录写权限
			if (!uploadDir.canWrite()) {
				getPrintWriter().println(getError("上传目录没有写权限。"));
				return;
			}

			String dirName = getRequest().getParameter("dir");
			if (dirName == null) {
				dirName = "image";
			}
			if (!extMap.containsKey(dirName)) {
				getPrintWriter().println(getError("目录名不正确。"));
				return;
			}
			// 创建文件夹
			savePath += dirName + "/";
			saveUrl += dirName + "/";
			File saveDirFile = new File(savePath);
			if (!saveDirFile.exists()) {
				saveDirFile.mkdirs();
			}
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			String ymd = sdf.format(new Date());
			savePath += ymd + "/";
			saveUrl += ymd + "/";
			File dirFile = new File(savePath);
			if (!dirFile.exists()) {
				dirFile.mkdirs();
			}
			// 检查文件大小
			if (new FileInputStream(imgFile).available() > maxSize) {
				getPrintWriter().println(getError("上传文件大小超过限制。"));
				return;
			}
			// 检查扩展名
			String fileExt = imgFileFileName.substring(imgFileFileName.lastIndexOf(".") + 1).toLowerCase();
			if (!Arrays.<String> asList(extMap.get(dirName).split(",")).contains(fileExt)) {
				getPrintWriter().println(getError("上传文件扩展名是不允许的扩展名。\n只允许" + extMap.get(dirName) + "格式。"));
				return;
			}
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
			String newFileName = df.format(new Date()) + "_" + new Random().nextInt(1000);
			File uploadedFile = new File(savePath, newFileName + "." + fileExt);
			FileUtil.copyFile(imgFile, uploadedFile);
			JSONObject obj = new JSONObject();

			obj.put("error", 0);
			obj.put("url", saveUrl + newFileName + "." + fileExt);
			getPrintWriter().println(obj.toJSONString());

		} catch (Exception e) {
			getPrintWriter().println(getError("系统异常"));
			logger.error(e, e);
		}
	}

	public void doNotNeedSecurity_apkFileUpload() {
		try {
			// 文件保存目录路径
			String savePath = Global.DISK_PATH;

			// 文件保存目录URL
			String saveUrl = Global.URL_DOMAIN;

			// 定义允许上传的文件扩展名
			HashMap<String, String> extMap = new HashMap<String, String>();
			extMap.put("release_apk_file", "apk");

			// 最大文件大小
			long maxSize = 1048576 * 50;

			getResponse().setContentType("text/html; charset=UTF-8");

			if (!ServletFileUpload.isMultipartContent(getRequest())) {
				getPrintWriter().println(getError("请选择文件。"));
				return;
			}
			// 检查目录
			File uploadDir = new File(savePath);
			if (!uploadDir.isDirectory()) {
				getPrintWriter().println(getError("上传目录不存在。path = " + uploadDir.getPath()));
				return;
			}
			// 检查目录写权限
			if (!uploadDir.canWrite()) {
				getPrintWriter().println(getError("上传目录没有写权限。"));
				return;
			}

			String dirName = getRequest().getParameter("dir");
			if (dirName == null) {
				dirName = "release_apk_file";
			}
			if (!extMap.containsKey(dirName)) {
				getPrintWriter().println(getError("目录名不正确。"));
				return;
			}
			// 创建文件夹
			savePath += dirName + "/";
			saveUrl += dirName + "/";
			File saveDirFile = new File(savePath);
			if (!saveDirFile.exists()) {
				saveDirFile.mkdirs();
			}
			// 检查文件大小
			if (new FileInputStream(upload).available() > maxSize) {
				getPrintWriter().println(getError("上传文件大小超过限制。"));
				return;
			}
			// 检查扩展名
			String fileExt = uploadFileName.substring(uploadFileName.lastIndexOf(".") + 1).toLowerCase();
			if (!Arrays.<String> asList(extMap.get(dirName).split(",")).contains(fileExt)) {
				getPrintWriter().println(getError("上传文件扩展名是不允许的扩展名。\n只允许" + extMap.get(dirName) + "格式。"));
				return;
			}
			File uploadedFile = new File(savePath, uploadFileName);
			FileUtil.copyFile(upload, uploadedFile);
			JSONObject obj = new JSONObject();
			obj.put("error", 0);
			obj.put("url", saveUrl + uploadFileName);
			getPrintWriter().println(obj.toJSONString());

		} catch (Exception e) {
			getPrintWriter().println(getError("系统异常"));
			logger.error(e, e);
		}
	}

	public void doNotNeedSecurity_apkFileUploadK() {
		try {
			// 文件保存目录路径
			String savePath = Global.DISK_PATH;

			// 文件保存目录URL
			String saveUrl = Global.URL_DOMAIN;

			// 定义允许上传的文件扩展名
			HashMap<String, String> extMap = new HashMap<String, String>();
			extMap.put("release_apk_file", "apk");

			// 最大文件大小
			long maxSize = 1048576;

			getResponse().setContentType("text/html; charset=UTF-8");

			if (!ServletFileUpload.isMultipartContent(getRequest())) {
				getPrintWriter().println(getError("请选择文件。"));
				return;
			}
			// 检查目录
			File uploadDir = new File(savePath);
			if (!uploadDir.isDirectory()) {
				getPrintWriter().println(getError("上传目录不存在。path = " + uploadDir.getPath()));
				return;
			}
			// 检查目录写权限
			if (!uploadDir.canWrite()) {
				getPrintWriter().println(getError("上传目录没有写权限。"));
				return;
			}

			String dirName = getRequest().getParameter("dir");
			if (dirName == null) {
				dirName = "release_apk_file";
			}
			if (!extMap.containsKey(dirName)) {
				getPrintWriter().println(getError("目录名不正确。"));
				return;
			}
			// 创建文件夹
			savePath += dirName + "/";
			saveUrl += dirName + "/";
			File saveDirFile = new File(savePath);
			if (!saveDirFile.exists()) {
				saveDirFile.mkdirs();
			}
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			String ymd = sdf.format(new Date());
			savePath += ymd + "/";
			saveUrl += ymd + "/";
			File dirFile = new File(savePath);
			if (!dirFile.exists()) {
				dirFile.mkdirs();
			}
			// 检查文件大小
			if (new FileInputStream(imgFile).available() > maxSize) {
				getPrintWriter().println(getError("上传文件大小超过限制。"));
				return;
			}
			// 检查扩展名
			String fileExt = imgFileFileName.substring(imgFileFileName.lastIndexOf(".") + 1).toLowerCase();
			if (!Arrays.<String> asList(extMap.get(dirName).split(",")).contains(fileExt)) {
				getPrintWriter().println(getError("上传文件扩展名是不允许的扩展名。\n只允许" + extMap.get(dirName) + "格式。"));
				return;
			}
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
			String newFileName = df.format(new Date()) + "_" + new Random().nextInt(1000) + "." + fileExt;
			File uploadedFile = new File(savePath, newFileName);
			FileUtil.copyFile(imgFile, uploadedFile);
			JSONObject obj = new JSONObject();
			obj.put("error", 0);
			obj.put("url", saveUrl + newFileName);
			getPrintWriter().println(obj.toJSONString());

		} catch (Exception e) {
			getPrintWriter().println(getError("系统异常"));
			logger.error(e, e);
		}
	}

	public void doNotNeedSecurity_fileUpload2Email() {
		try {
			// 最大文件大小50M
			long maxSize = 1048576 * 50;
//			long maxSize = 1024 * 500;

			getResponse().setContentType("text/html; charset=UTF-8");

			if (!ServletFileUpload.isMultipartContent(getRequest())) {
				getPrintWriter().println(getError("请选择文件。"));
				return;
			}

			DefaultFileUpload defaultFileUpload = new DefaultFileUpload();
			defaultFileUpload.setMaxSize(maxSize);
			defaultFileUpload.setFileDir("email");
			FileResult fileResult = defaultFileUpload.uploadFile(upload, uploadFileName);

			JSONObject obj = new JSONObject();

			if (fileResult.isResult() && (fileResult.getMsg() == null || fileResult.getMsg().length() == 0)) {
				obj.put("error", 0);
				obj.put("url", fileResult.getFileUrl());
				getPrintWriter().println(obj.toJSONString());
			} else {
				getPrintWriter().println(getError(fileResult.getMsg()));
			}

		} catch (Exception e) {
			logger.error(e, e);
			getPrintWriter().println(getError("系统异常"));
		}
	}

	private String getError(String message) {
		JSONObject obj = new JSONObject();
		obj.put("error", 1);
		obj.put("message", message);
		return obj.toJSONString();
	}

	public File getUpload() {
		return upload;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}

	public String getUploadFileName() {
		return uploadFileName;
	}

	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	public File getImgFile() {
		return imgFile;
	}

	public void setImgFile(File imgFile) {
		this.imgFile = imgFile;
	}

	public String getImgFileFileName() {
		return imgFileFileName;
	}

	public void setImgFileFileName(String imgFileFileName) {
		this.imgFileFileName = imgFileFileName;
	}

	public String getUploadFileBase64() {
		return uploadFileBase64;
	}

	public void setUploadFileBase64(String uploadFileBase64) {
		this.uploadFileBase64 = uploadFileBase64;
	}
}
