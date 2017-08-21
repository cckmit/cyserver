package com.cy.util.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.ResourceBundle;

public abstract class FileManager {

	protected static final String defaultDir = "file";

	protected static String saveDir;

	protected static String fileUrl;

	static {
		ResourceBundle bundle = ResourceBundle.getBundle("file");
		saveDir = bundle.getString("saveDir");
		fileUrl = bundle.getString("fileUrl");
	}

	/**
	 * 是否需要检查文件后缀
	 */
	protected boolean checkSuffix;

	/**
	 * 检查文件后缀字符串，用逗号隔开
	 */
	protected String suffix;

	/**
	 * 自定义文件存储目录
	 */
	protected String fileDir;

	/**
	 * 文件限制,默认10M
	 */
	protected long maxSize = 10485760;

	public boolean isCheckSuffix() {
		return checkSuffix;
	}

	public void setCheckSuffix(boolean checkSuffix) {
		this.checkSuffix = checkSuffix;
	}

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	public String getFileDir() {
		return fileDir;
	}

	public void setFileDir(String fileDir) {
		this.fileDir = fileDir;
	}

	public long getMaxSize() {
		return maxSize;
	}

	public void setMaxSize(long maxSize) {
		this.maxSize = maxSize;
	}

	protected String checkFile(File file, String fileName) throws FileNotFoundException, IOException {
		if (saveDir == null || saveDir.length() == 0) {
			String path = this.getClass().getClassLoader().getResource("").getPath();
			saveDir = path.substring(1, path.indexOf("webapps") + 8);
		}
		if (fileUrl == null || fileUrl.length() == 0) {
			return "文件访问路径未设置";
		}
		if (maxSize > 0 && new FileInputStream(file).available() > maxSize) {
			return "文件超过最大限制";
		}
		String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
		if (checkSuffix && !Arrays.<String> asList(suffix.split(",")).contains(fileExt)) {
			return "上传文件扩展名是不允许的扩展名。\n只允许" + suffix + "格式。";
		}
		return null;
	}

	public abstract FileResult uploadFile(File file, String fileName) throws FileNotFoundException, IOException;

}
