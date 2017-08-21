package com.cy.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Test1 {
	
	
	
	/**
	 * 读取某个文件夹下的所有文件
	 */
	public static boolean readfile(String filepath)
			throws FileNotFoundException, IOException {
		try {
			File file = new File(filepath);
			if (!file.isDirectory()) {
				String path = file.getAbsolutePath();
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
						String fileExt = path.substring(path.lastIndexOf("."));
						String temp = path.substring(0, path.lastIndexOf("."));
						//大图文件存在
						if(new File(temp+"_MAX"+fileExt).exists() && new File(temp+"_MIN"+fileExt).exists()){
							System.out.println("path=" + readfile.getAbsolutePath());	
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
		Test1.readfile("D:/image");
	}

}
