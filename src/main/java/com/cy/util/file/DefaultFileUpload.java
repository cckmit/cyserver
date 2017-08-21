package com.cy.util.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.aspectj.util.FileUtil;

/**
 * 文件上传
 * 
 * @author joe
 * 
 */
public class DefaultFileUpload extends FileManager {
	@Override
	public FileResult uploadFile(File file, String fileName) throws FileNotFoundException, IOException {
		FileResult fileResult = new FileResult();
		String checkFileUrl = checkFile(file, fileName);
		if (checkFileUrl != null && checkFileUrl.length() > 0) {
			fileResult.setResult(false);
			fileResult.setMsg(checkFileUrl);
			return fileResult;
		}
		// 创建上传文件默认路径
		StringBuffer dirSb = new StringBuffer();
		dirSb.append(saveDir);
		dirSb.append(defaultDir);
		dirSb.append("/");
		StringBuffer urlSb = new StringBuffer();
		urlSb.append(fileUrl);
		urlSb.append(defaultDir);
		urlSb.append("/");
		File saveDirFile = new File(dirSb.toString());
		if (!saveDirFile.exists()) {
			saveDirFile.mkdirs();
		}
		// 创建自由目录
		if (fileDir != null && fileDir.length() > 0) {
			dirSb.append(fileDir);
			dirSb.append("/");
			urlSb.append(fileDir);
			urlSb.append("/");
			File freeDirFile = new File(dirSb.toString());
			if (!freeDirFile.exists()) {
				freeDirFile.mkdirs();
			}
		}
		// 文件最终存储目录
		String seq = FileSeq.getSeq();
		fileName=seq+"_"+fileName;
		File newFile = new File(dirSb.toString(),fileName);
		FileUtil.copyFile(file, newFile);
		urlSb.append(fileName);
		fileResult.setFileUrl(urlSb.toString());
		dirSb.append(fileName);
		fileResult.setSaveUrl(dirSb.toString());
		fileResult.setResult(true);
		fileResult.setMsg("文件上传成功");
		return fileResult;

	}
}
