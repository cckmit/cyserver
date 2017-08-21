package com.cy.util.file;

import java.io.File;
import java.io.IOException;

/**
 * 服务器自动生成三种尺寸大小图片
 * 
 * @author joe
 * 
 */
public class ThreeImageFileUpload extends ImageFileUpload {

	public static final String MAX = "320*200";

	public static final String MIN = "100*80";

	public FileResult uploadImageAndCompress(File file, String fileName) throws IOException {
		FileResult fileResult = uploadFile(file, fileName);
		if (fileResult.isResult()) {
			String saveUrl = fileResult.getSaveUrl();
			String fileUrl = fileResult.getFileUrl();
			String[] max = MAX.split("*");
			width = Integer.parseInt(max[0]);
			height = Integer.parseInt(max[1]);
			String saveMaxUrl = saveUrl.substring(0, saveUrl.lastIndexOf(".")) + "_MAX" + saveUrl.substring(saveUrl.lastIndexOf("."));
			String fileMaxUrl = fileUrl.substring(0, fileUrl.lastIndexOf(".")) + "_MAX" + fileUrl.substring(fileUrl.lastIndexOf("."));
			compress(false, saveUrl, saveMaxUrl);
			String[] min = MIN.split("*");
			width = Integer.parseInt(min[0]);
			height = Integer.parseInt(min[1]);
			String saveMinUrl = saveUrl.substring(0, saveUrl.lastIndexOf(".")) + "_MIN" + saveUrl.substring(saveUrl.lastIndexOf("."));
			String fileMinUrl = fileUrl.substring(0, fileUrl.lastIndexOf(".")) + "_MIN" + fileUrl.substring(fileUrl.lastIndexOf("."));
			compress(false, saveUrl, saveMinUrl);
			String dir = saveMaxUrl + "," + saveUrl + "," + saveMinUrl;
			String url = fileMaxUrl + "," + fileUrl + "," + fileMinUrl;
			fileResult.setFileUrl(url);
			fileResult.setSaveUrl(dir);
		}
		return fileResult;
	}
}
