package com.cy.util.file;

import java.io.File;
import java.io.IOException;

/**
 * 图片上传
 * 
 * @author joe
 * 
 */
public class ImageFileUpload extends DefaultFileUpload {
	
	protected int width;
	protected int height;

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public FileResult compressWidthAndHeight(boolean zoomPlus, File file, String fileName) throws IOException {
		FileResult fileResult = uploadFile(file, fileName);
		if (fileResult.isResult()) {
			String saveCompressUrl = fileResult.getSaveUrl().substring(0, fileResult.getSaveUrl().lastIndexOf(".")) + "_" + width + "*" + height
					+ fileResult.getSaveUrl().substring(fileResult.getSaveUrl().lastIndexOf("."));
			String fileCompressUrl = fileResult.getFileUrl().substring(0, fileResult.getFileUrl().lastIndexOf(".")) + "_" + width + "*" + height
					+ fileResult.getFileUrl().substring(fileResult.getFileUrl().lastIndexOf("."));
			ImageHelper.createThumb(width, height, zoomPlus, fileResult.getSaveUrl(), saveCompressUrl);
			fileResult.setFileUrl(fileResult.getFileUrl() + "," + fileCompressUrl);
			fileResult.setSaveUrl(fileResult.getSaveUrl() + "," + saveCompressUrl);
		}
		return fileResult;
	}

	public void compress(boolean zoomPlus, String inputPath, String outPath) throws IOException {
		ImageHelper.createThumb(width, height, zoomPlus, inputPath, outPath);
	}

}
