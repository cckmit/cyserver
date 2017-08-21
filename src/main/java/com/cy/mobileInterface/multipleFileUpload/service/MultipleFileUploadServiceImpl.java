package com.cy.mobileInterface.multipleFileUpload.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ResourceBundle;
import java.util.UUID;

import com.cy.util.image.ImageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.cy.base.entity.Message;
import com.cy.core.userProfile.dao.UserProfileMapper;
import com.cy.core.userProfile.entity.UserProfile;
import com.cy.mobileInterface.multipleFileUpload.entity.MultipleFileUpload;
import com.cy.util.file.DefaultFileUpload;
import com.cy.util.file.FileResult;

@Service("multipleFileUploadService")
public class MultipleFileUploadServiceImpl implements MultipleFileUploadService {
	@Autowired
	private UserProfileMapper userProfileMapper;

	@Override
	public void addFile(Message message, String content, File[] upload, String[] uploadFileName) throws FileNotFoundException, IOException {
		MultipleFileUpload multipleFileUpload = JSON.parseObject(content, MultipleFileUpload.class);
		if (multipleFileUpload.getType() == null || multipleFileUpload.getType().length() == 0) {
			message.setMsg("请填写type!");
			message.setSuccess(false);
			return;
		}
		if (upload == null || upload.length == 0) {
			message.setMsg("请上传文件!");
			message.setSuccess(false);
			return;
		}

		if(!"tmpUser".equals(multipleFileUpload.getAccountNum())){
			UserProfile userProfile = userProfileMapper.selectByAccountNum(multipleFileUpload.getAccountNum());
			if (userProfile == null) {
				message.setMsg("账号错误!");
				message.setSuccess(false);
				return;
			}
		}

//			if (!userProfile.getPassword().equals(multipleFileUpload.getPassword())) {
//				message.setMsg("账号/密码错误!");
//				message.setSuccess(false);
//				return;
//			}
		DefaultFileUpload defaultFileUpload = new DefaultFileUpload();
		defaultFileUpload.setFileDir("all_user_files/" + multipleFileUpload.getAccountNum() + "/" + multipleFileUpload.getType());
		StringBuffer urlsb = new StringBuffer();
		StringBuffer msgsb = new StringBuffer();
		for (int i = 0; i < upload.length; i++) {
			FileResult fileResult = defaultFileUpload.uploadFile(upload[i], uploadFileName[i]);
			urlsb.append(fileResult.getFileUrl());
			urlsb.append("|");
			msgsb.append(fileResult.getMsg());
			msgsb.append("|");
		}
		if (urlsb.length() > 0) {
			urlsb.deleteCharAt(urlsb.length() - 1);
			message.setObj(urlsb.toString());
		}
		if (msgsb.length() > 0) {
			msgsb.deleteCharAt(msgsb.length() - 1);
			message.setMsg(msgsb.toString());
		}
		message.setSuccess(true);
	}

	public File changeFromBase64ToFile(String fileBase64 ,String fileName) {
		if(fileBase64 != null) {
			File dir = new File(ResourceBundle.getBundle("file").getString("saveDir") + File.separator + "tmp" + File.separator + "base" ) ;
			if(!dir.exists() || !dir.isDirectory()) {
				dir.mkdirs() ;
			}
			String filePath = ResourceBundle.getBundle("file").getString("saveDir") + File.separator + "tmp" + File.separator + "base" + File.separator + "upload_" + UUID.randomUUID() + "_"+fileName;
			boolean success = ImageUtils.GenerateImage(fileBase64,filePath) ;
			if(success) {
				return new File(filePath) ;
			}
		}
		return null ;
	}
	public File[] changeFromBase64ToFile(String[] fileBase64 ,String[] fileNames) {
		File[] files = new File[fileBase64.length] ;
		for (int i = 0 ; i < fileBase64.length ; i++) {
			files[i] = changeFromBase64ToFile(fileBase64[i],fileNames[i]) ;
		}
		return files ;
	}
}
