package com.cy.util.shortmsg;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;

import org.apache.ibatis.annotations.Results;

import com.cy.core.sms.entity.MsgSend;

/**
 *
 * @author Administrator
 */
public class Send {
	private static String SMS_ACCOUNT = "9SDK-EMY-0999-JBRPM" ; 
	private static String SMS_PASSWORD = "123123" ; 

    public static String SMS(String postData, String postUrl) {
        try {
            //发送POST请求
            URL url = new URL(postUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setUseCaches(false);
            conn.setDoOutput(true);

            conn.setRequestProperty("Content-Length", "" + postData.length());
            OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
            out.write(postData);
            out.flush();
            out.close();

            //获取响应状态
            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                System.out.println("connect failed!");
                return "";
            }
            //获取响应内容体
            String line, result = "";
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
            while ((line = in.readLine()) != null) {
                result += line + "\n";
            }
            in.close();
            return result;
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
        return "";
    }
    
    /**
     * 发送即时短信
     * @param phone
     * @param message
     * @param account
     * @param password
     */
    public static String sendSms(String phone ,String message ,String account ,String password) {
    	account = (account != null && "".equals(account.trim()) ) ? account : SMS_ACCOUNT ;
    	password = (password != null && "".equals(password.trim()) ) ? account : SMS_PASSWORD ;
        String postData = "cdkey="+account+"&password="+password+"&phone="+phone+"&message="+message ;
        System.out.println(postData);
        String ret = SMS(postData, "http://sdk999in.eucp.b2m.cn:8080/sdkproxy/sendsms.action");
        System.out.println("------> ret : " + ret );

    	String[] s0= ret.split("<error>") ;
    	String[] s1 = s0[1].split("</error>") ;
		System.out.println(s1[0].trim());
		ret = s1[0].trim() ;
        return ret ;
    }
    
    /**
     * 发送定时短信
     * @param phone
     * @param message
     * @param account
     * @param password
     * @param sendtime
     */
    public static String sendTimeSms(String phone ,String message ,String account ,String password ,String sendtime) {
    	account = (account != null && "".equals(account.trim()) ) ? account : SMS_ACCOUNT ;
    	password = (password != null && "".equals(password.trim()) ) ? account : SMS_PASSWORD ;
    	
    	String postData = "cdkey="+account+"&password="+password+"&phone="+phone+"&sendtime="+sendtime+"&message="+message ;
    	//out.println(PostData);
    	String ret = SMS(postData, "http://sdk999in.eucp.b2m.cn:8080/sdkproxy/sendtimesms.action");
    	System.out.println(ret);

    	String[] s0= ret.split("<error>") ;
    	String[] s1 = s0[1].split("</error>") ;
		System.out.println(s1[0].trim());
		ret = s1[0].trim() ;
    	return ret ;
    }

    public static void main(String[] args) throws Exception{
    	String str = "【窗友】您的验证码是12314，在5分钟内有效，如非本人操作请忽略本短信。(武汉科技大学)" ;
//        PostData = "cdkey=9SDK-EMY-0999-JBRPM&password=123123&phone=18612241910,13011012564,13520095321&seqid=&smspriority=&message="+ URLEncoder.encode("验证码为2141612【口腔圈】\n", "utf-8");
        
//        postData = "http://sdk999in.eucp.b2m.cn:8080/sdkproxy/regist.action?cdkey=2SDK-EMY-6688-AAAAA&password=123456" ;
//        String PostData = "cdkey=9SDK-EMY-0999-JBRPM&password=123123&phone=18612241910,13011012564&message="+str ;
        //out.println(PostData);
//        String ret = SMS(PostData, "http://sdk999in.eucp.b2m.cn:8080/sdkproxy/sendsms.action");
//        System.out.println(ret);
    	String result = sendSms("13011012564", str, "9SDK-EMY-0999-JBRPM", "123123");
//    	String result = sendTimeSms("13011012564", str, "9SDK-EMY-0999-JBRPM", "123123","20160531184500");
    	
    	String[] s0= result.split("<error>") ;
    	String[] s1 = s0[1].split("</error>") ;
		System.out.println(s1[0].trim());
    }
}