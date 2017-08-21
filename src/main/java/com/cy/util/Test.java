package com.cy.util;

import java.awt.Image;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * 本地文件批处理
 * @author Administrator
 *
 */
public class Test {

	
	/**
	 * 读取某个文件夹下的所有文件
	 */
	public static boolean readfile(String filepath)
			throws FileNotFoundException, IOException {
		try {
			File file = new File(filepath);
			if (!file.isDirectory()) {
				String path = file.getAbsolutePath();
				System.out.println("path=" + file.getAbsolutePath());
				String fileExt = path.substring(path.lastIndexOf("."));
				String temp = path.substring(0, path.lastIndexOf("."));
			} else if (file.isDirectory()) {
				//System.out.println("文件夹");
				String[] filelist = file.list();
				for (int i = 0; i < filelist.length; i++) {
					File readfile = new File(filepath + "\\" + filelist[i]);
					if (!readfile.isDirectory()) {
						String path = readfile.getAbsolutePath();
						if(path.indexOf("_MAX")!=-1 || path.indexOf("_MIN")!=-1){
							continue;
						}
						System.out.println("path=" + readfile.getAbsolutePath());
						String fileExt = path.substring(path.lastIndexOf("."));
						String temp = path.substring(0, path.lastIndexOf("."));
						
						File file1 = new File(path);
						String size = WebUtil.getImgeWidthAndHeight(file1);
						
						File file_max = null;
						String size_max = "";
						File file_min = null;
						String size_min = "";
						
						//大图文件存在
						if(new File(temp+"_MAX"+fileExt).exists() && new File(temp+"_MIN"+fileExt).exists()){
							file_max = new File(temp+"_MAX"+fileExt);
							size_max = WebUtil.getImgeWidthAndHeight(file_max);
							file_min = new File(temp+"_MIN"+fileExt);
							size_min = WebUtil.getImgeWidthAndHeight(file_min);
							String newFileName = temp+"_"+size+"_"+size_max+"_"+size_min;	//原图名字
							//修改文件名
							file1.renameTo(new File(newFileName+fileExt));
							file_max.renameTo(new File(newFileName+"_MIN"+fileExt));
							file_min.renameTo(new File(newFileName+"_MAX"+fileExt));
							
						}else{
							
						}	
					} else if (readfile.isDirectory()) {
						readfile(filepath + "\\" + filelist[i]);
					}
				}
			}

		} catch (FileNotFoundException e) {
			System.out.println("readfile()   Exception:" + e.getMessage());
		}
		return true;
	}
	
	/**
	 * @param args
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException, IOException {
		// TODO Auto-generated method stub
		Test.readfile("D:/image");
	}

}
