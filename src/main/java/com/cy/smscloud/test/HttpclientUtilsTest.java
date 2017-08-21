package com.cy.smscloud.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.message.BasicNameValuePair;
import org.junit.Test;

import com.cy.smscloud.entity.Body;
import com.cy.smscloud.entity.Header;
import com.cy.smscloud.entity.RequestEntity;
import com.cy.common.utils.HttpclientUtils;
import com.cy.common.utils.JsonUtils;

/**
 * Created by Kentun on 2015-08-28.
 */
public class HttpclientUtilsTest {
    @Test
    public void testPost(){
        try {
            List<NameValuePair> formparams= new ArrayList<NameValuePair>();
            Header header = new Header() ;
            header.setModule("cloud");
            header.setAction("SmsCloudService");
            header.setMethod("findSmsAppCount");
            header.setAppAccount("KED123121");
            header.setAppKey("123456");
            header.setDeviceType("3");
            header.setDeviceId("0000000");
            RequestEntity entity = new RequestEntity() ;
            entity.setHeader(header);
            entity.setBody(new Body());
            String jsonData = JsonUtils.obj2json(entity) ;
//            String jsonData = "{\n" +
//                    "    \"header\": {\n" +
//                    "        \"module\": \"cloud\",\n" +
//                    "        \"action\": \"SmsCloudService\",\n" +
//                    "        \"method\": \"findSmsAppCount\",\n" +
//                    "        \"apiToken\": \"d390d6893715333546ca67ea1ce03b\",\n" +
//                    "        \"deviceId\": \"5d317e1717934e9926882f79fb19f6\",\n" +
//                    "        \"deviceType\": \"1\",\n" +
//                    "        \"appAccount\": \"KED123121\",\n" +
//                    "        \"appKey\": \"123456\"\n" +
//                    "    },\n" +
//                    "    \"body\": {\n" +
//                    "        \"data\": \"\"\n" +
//                    "    }\n" +
//                    "}" ;
            System.out.println("--------> jsonData : \n" + jsonData);
            System.out.println("---------------------------------\n");
            formparams.add(new BasicNameValuePair("jsonData",jsonData));
            String content = HttpclientUtils.post("http://192.168.0.25:8080/smscloud/app/api", formparams);
            entity = JsonUtils.json2pojo(content, RequestEntity.class) ;
            System.out.println(entity);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
//    	String url="http://localhost:8080/ezweb/app/api";
//    	String path="d:/images";
//    	Map<String,String> params=new HashMap<String, String>();
//		String jsonData = "{\n" +
//				"    \"header\": {\n" +
//				"        \"module\": \"api\",\n" +
//				"        \"action\": \"TestService\",\n" +
//				"        \"method\": \"testUploadImg\",\n" +
//				"        \"apiToken\": \"04dafcde80d65f1b90bf51d63a85bd\",\n" +
//				"        \"deviceId\": \"5d317e1717934e9926882f79fb19f6\",\n" +
//				"        \"deviceType\": \"1\"\n" +
//				"    },\n" +
//				"    \"body\": {\n" +
//				"        \"data\": {\n" +
//				"            \"mobile\": \"15311412612\"\n" +
//				"        }\n" +
//				"    }\n" +
//				"}";
//        System.out.println(jsonData);
//    	params.put("jsonData", jsonData);    	//
//
//    	ArrayList<File> files = new ArrayList<File>();
//    	File file = new File(path);
//    	File[] tempList = file.listFiles();
//    	for(File f:tempList){
//    		files.add(f);
//    	}
//    	HttpClient client=new DefaultHttpClient();// 开启一个客户端 HTTP 请求
//    	HttpPost post = new HttpPost(url);//创建 HTTP POST 请求
//    	MultipartEntityBuilder builder = MultipartEntityBuilder.create();
//    	builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);//设置浏览器兼容模式
//    	int count=0;
//    	for (File f:files) {
//    	    builder.addBinaryBody("file", f);
//    	    count++;
//    	}
//    	builder.addTextBody("jsonData", params.get("jsonData"));//设置请求参数
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

    public static void main(String[] args) throws Exception {
        new HttpclientUtilsTest().testPost() ;
    }
}
