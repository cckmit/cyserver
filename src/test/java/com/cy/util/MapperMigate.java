package com.cy.util;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by kentun on 2016/7/14.
 */
public class MapperMigate {
    private static ArrayList<String> filelist = new ArrayList<String>();
    public static void main(String[] args){
        String path = (MapperMigate.class.getResource("/").toString().split("/target")[0]+"/src/main/java/com/cy/").substring(6);
        getFiles(path);
    }

    /*
  * 通过递归得到某一路径下所有的目录及其文件
  */
    public static void getFiles(String filePath){
        File root = new File(filePath);
        File[] files = root.listFiles();
        for(File file:files){
            if(file.isDirectory()){
                /*
                * 递归调用
                */
                getFiles(file.getAbsolutePath());
                filelist.add(file.getAbsolutePath());
                //System.out.println("显示"+filePath+"下所有子目录及其文件"+file.getAbsolutePath());
            }else{
                if(file.getAbsolutePath().substring(file.getAbsolutePath().length()-4).equalsIgnoreCase(".xml")) {
                    String toPath=filePath.replace("main\\java","main\\resources");
                    File toFile= new File(toPath);
                    if(!toFile.exists()){
                        toFile.mkdirs();
                    }
                    try {
                        if (file.renameTo(new File(toPath +"\\"+ file.getName()))) {
                            System.out.println(file.getAbsolutePath()+"移动成功");
                        } else {
                            System.out.println(file.getAbsolutePath()+"移动失败");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
