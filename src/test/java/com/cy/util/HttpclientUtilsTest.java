package com.cy.util;

import com.cy.util.image.ImageUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Kentun on 2015-08-28.
 */
public class HttpclientUtilsTest {
    @Test
    public void testPost(){
//        try {
//            List<NameValuePair> formparams= new ArrayList<NameValuePair>();
//            formparams.add(new BasicNameValuePair("username","NTY000000"));
//            formparams.add(new BasicNameValuePair("scode","123456"));
//            formparams.add(new BasicNameValuePair("mobile","13805100000"));
//            formparams.add(new BasicNameValuePair("content","你好101540"));
//            String content = HttpclientUtils.post("http://222.185.228.25:8000/msm/sdk/http/sendsms.jsp", formparams);
//            System.out.println(content);
//        } catch (NetServiceException e) {
//            e.printStackTrace();
//        }
    }
    @Test
    public void testPostSMS(){
//        try {
//            List<NameValuePair> formparams= new ArrayList<NameValuePair>();
//            formparams.add(new BasicNameValuePair("action","send"));
//            formparams.add(new BasicNameValuePair("account","xiaokedou"));
//            formparams.add(new BasicNameValuePair("password","200036"));
//            formparams.add(new BasicNameValuePair("mobile","15311412612"));
//            formparams.add(new BasicNameValuePair("content","欢迎注册口腔圈APP,您的验证码为12323,请在30分钟内使用此验证码【小蝌蚪】"));
//            String content = HttpclientUtils.post("http://sms.chanzor.com:8001/sms.aspx", formparams);
//            System.out.println(content);
//        } catch (NetServiceException e) {
//            e.printStackTrace();
//        }
    }
    @Test
    public void testPostSMS2(){
//        try {
//            List<NameValuePair> formparams= new ArrayList<NameValuePair>();
//            formparams.add(new BasicNameValuePair("action","query"));
//            formparams.add(new BasicNameValuePair("account","xiaokedou"));
//            formparams.add(new BasicNameValuePair("password","200036"));
//            String content = HttpclientUtils.post("http://sms.chanzor.com:8001/statusApi.aspx", formparams);
//            System.out.println(content);
//        } catch (NetServiceException e) {
//            e.printStackTrace();
//        }
    }
    @Test
    public void testGet(){
//        try {
////            HttpEntity httpEntity = HttpclientUtils.get("http://www.ifeng.com");
//            String content = HttpclientUtils.get("http://www.ifeng.com");
//            System.out.println(content);
//        } catch (NetServiceException e) {
//            e.printStackTrace();
//        }

    }
    
    @Test
    public void testUpload() throws ClientProtocolException, IOException{
    	String url="http://192.168.0.24:8080/cy/userProfile/userProfileAction!doNotNeedSessionAndSecurity_userProfileHandler.action";
//    	String url="http://114.215.194.18/userProfile/userProfileAction!doNotNeedSessionAndSecurity_userProfileHandler.action";
//    	String path="/Users/liuzhen/Documents/temp2.jpg";
//    	Map<String,String> params=new HashMap<String, String>();
//		String jsonData = "{\n" +
//				"    \"command\": \"1\",\n" +
//				"    \"content\": {\n" +
//				"        \"accountNum\": \"448\",\n" +
//				"        \"password\": \"111111\",\n" +
//				"        \"type\": \"1\"\n" +
//				"    }\n" +
//				"}";
//        System.out.println(jsonData);
//    	params.put("jsonStr", jsonData);    	//
//
//    	HttpClient client=new DefaultHttpClient();// 开启一个客户端 HTTP 请求
//    	HttpPost post = new HttpPost(url);//创建 HTTP POST 请求
//    	MultipartEntityBuilder builder = MultipartEntityBuilder.create();
//    	builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);//设置浏览器兼容模式
//    	int count=0;
//    	builder.addTextBody("uploadFileBase64", ImageUtils.GetImageStr("/Users/liuzhen/Documents/temp1.jpg"));//设置请求参数
//    	builder.addTextBody("uploadFileName", "temp1.jpg");//设置请求参数
//    	builder.addTextBody("jsonStr", params.get("jsonStr"));//设置请求参数
//    	HttpEntity entity = builder.build();// 生成 HTTP POST 实体
//    	post.setEntity(entity);//设置请求参数
//    	HttpResponse response = client.execute(post);// 发起请求 并返回请求的响应
//    	if (response.getStatusLine().getStatusCode()==200) {
//    	    HttpEntity httpEntity = response.getEntity();
//    	    System.out.println(EntityUtils.toString(httpEntity, "UTF-8"));
//    	}else{
//    		System.out.println("失败");
//    	}
	}
//    @Test
//    public void testUpload() throws ClientProtocolException, IOException{
//    	String url="http://114.215.194.18/userProfile/userProfileAction!doNotNeedSessionAndSecurity_userProfileHandler.action";
//    	String path="/Users/liuzhen/Documents/temp2.jpg";
//    	Map<String,String> params=new HashMap<String, String>();
//		String jsonData = "{\n" +
//				"    \"command\": \"1\",\n" +
//				"    \"content\": {\n" +
//				"        \"accountNum\": \"448\",\n" +
//				"        \"password\": \"111111\",\n" +
//				"        \"type\": \"1\"\n" +
//				"    }\n" +
//				"}";
////		String jsonData = "{\n" +
////                "    \"command\": \"1\",\n" +
////                "    \"content\": {\n" +
////                "        \"accountNum\": \"448\",\n" +
////                "        \"password\": \"111111\",\n" +
////                "        \"type\": \"1\"\n" +
////                "    }\n" +
////                "}";
//        System.out.println(jsonData);
//    	params.put("jsonStr", jsonData);    	//
//
//    	ArrayList<File> files = new ArrayList<File>();
//    	File file = new File(path);
////    	File[] tempList = file.listFiles();
////    	for(File f:tempList){
////    		files.add(f);
////    	}
//        files.add(file);
//    	HttpClient client=new DefaultHttpClient();// 开启一个客户端 HTTP 请求
//    	HttpPost post = new HttpPost(url);//创建 HTTP POST 请求
//    	MultipartEntityBuilder builder = MultipartEntityBuilder.create();
//    	builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);//设置浏览器兼容模式
//    	int count=0;
//    	for (File f:files) {
//    	    builder.addBinaryBody("upload", f);
//    	    count++;
//    	}
//    	builder.addTextBody("jsonStr", params.get("jsonStr"));//设置请求参数
//    	HttpEntity entity = builder.build();// 生成 HTTP POST 实体
//    	post.setEntity(entity);//设置请求参数
//    	HttpResponse response = client.execute(post);// 发起请求 并返回请求的响应
//    	if (response.getStatusLine().getStatusCode()==200) {
//    	    HttpEntity httpEntity = response.getEntity();
//    	    System.out.println(EntityUtils.toString(httpEntity, "UTF-8"));
//    	}else{
//    		System.out.println("失败");
//    	}
//	}

    public static void main(String[] args) throws Exception {
//        new HttpclientUtilsTest().testUpload() ;

		String jsonData = "{\n" +
				"    \"command\": \"1\",\n" +
				"    \"content\": {\n" +
				"        \"accountNum\": \"448\",\n" +
				"        \"password\": \"111111\",\n" +
				"        \"type\": \"1\"\n" +
				"    }\n" +
				"}";
        System.out.println(jsonData);
    }
}
