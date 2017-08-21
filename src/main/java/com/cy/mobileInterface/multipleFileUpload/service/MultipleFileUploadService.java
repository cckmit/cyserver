package com.cy.mobileInterface.multipleFileUpload.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.cy.base.entity.Message;

public interface MultipleFileUploadService {
	/**
	 * 1号接口文件上传
	 * 
	 * @param message
	 * @param content
	 * @param upload
	 * @param uploadFileName
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	void addFile(Message message, String content, File[] upload, String[] uploadFileName) throws FileNotFoundException, IOException;

	File changeFromBase64ToFile( String fileBase64 ,String fileName );

	File[] changeFromBase64ToFile(String[] fileBase64 ,String[] fileName) ;
}
