package com.cy.smscloud.http;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;
import org.apache.commons.lang.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.cy.smscloud.entity.Body;
import com.cy.smscloud.entity.Header;
import com.cy.smscloud.entity.RequestEntity;
import com.cy.common.utils.HttpclientUtils;
import com.cy.common.utils.JsonUtils;
import org.apache.log4j.Logger;

/**
 * 访问请求
 * @author 刘振
 *
 */
public class HttpClientBase {
	private static final Logger logger = Logger.getLogger(HttpClientBase.class);
	public final static String SMS_CLOUD_URL = "http://api.cycloud.net";
	public final static String SMS_API_URL = SMS_CLOUD_URL + "/smscloud/app/api" ;



	
	/**
	 * post 请求
	 * @param header
	 * @param url
	 * @return
	 */
    public RequestEntity post(Header header ,Object data ,String url){
    	url = StringUtils.isNotBlank(url) ? url : SMS_API_URL ;
        try {
            List<NameValuePair> formparams= new ArrayList<NameValuePair>();
            RequestEntity entity = new RequestEntity() ;
            entity.setHeader(header);
            Body body = new Body() ;
            body.setData(data) ;
            entity.setBody(body);
            String jsonData = JsonUtils.obj2json(entity) ;
            logger.info("--------> jsonData : \n" + jsonData);
			logger.info("---------------------------------\n");
            formparams.add(new BasicNameValuePair("jsonData",jsonData));
            String content = HttpclientUtils.post(url, formparams);
            entity = JsonUtils.json2pojo(content, RequestEntity.class) ;
            logger.info(entity);
            return entity ;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null ;
    }
    
    public void post(){
    	try {
    		List<NameValuePair> formparams= new ArrayList<NameValuePair>();
			formparams.add(new BasicNameValuePair("client_id","123123123"));
			formparams.add(new BasicNameValuePair("client_secret","123123123"));
			formparams.add(new BasicNameValuePair("grant_type","client_credentials"));
			formparams.add(new BasicNameValuePair("scope","read"));
    		String content = HttpclientUtils.post("http://localhost:8080/oauth/token", formparams);
    		System.out.println("--------> 出参：\n" + content);
//    		entity = JsonUtils.json2pojo(content, RequestEntity.class) ;
//    		logger.info(entity);
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    }

    public static void main(String[] args) throws Exception {
        new HttpClientBase().post() ;
    }
}
