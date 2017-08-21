package com.cy.common.utils;

import java.io.IOException;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import com.cy.smscloud.exception.NetServiceException;

/**
 * Created by Kentun on 2015-08-25.
 */
public class HttpclientUtils {

    /**
     * get方式请求，默认编码格式UTF-8
     * @param url
     * @return
     */
    public static String  get(String url) throws NetServiceException {
        return get(url, "UTF-8");
    }
    /**
     * get方式请求，
     * @param url URL地址
     * @param charset 编码
     * @return
     */
    public static String get(String url,String charset) throws NetServiceException {

        //创建HttpClientBuilder
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        //HttpClient
        CloseableHttpClient closeableHttpClient = httpClientBuilder.build();

        HttpGet httpGet = new HttpGet(url);

        System.out.println(httpGet.getRequestLine());

        //执行get请求
        try {
            HttpResponse httpResponse = closeableHttpClient.execute(httpGet);
            HttpEntity httpEntity = httpResponse.getEntity();
            //响应状态
            System.out.println("status:" + httpResponse.getStatusLine());
            //判断响应实体是否为空
            if (httpEntity != null) {
                System.out.println("contentEncoding:" + httpEntity.getContentEncoding());
//                System.out.println("response content:" + EntityUtils.toString(httpEntity,"UTF-8"));
                return EntityUtils.toString(httpEntity,charset);
            }else{
                return "";
            }
        } catch (IOException e) {
            throw new NetServiceException(e);
        } finally {
            try {
                //关闭流并释放内存
                closeableHttpClient.close();
            } catch (IOException e) {
                throw new NetServiceException(e);
            }
        }
    }

    /**
     * post方式请求，
     * List<NameValuePair> formparams= new ArrayList<NameValuePair>();
     * formparams.add(new BasicNameValuePair("username","NTY000000"));
     * @param url URL地址
     * @param formparams 参数
     * @return
     */
    public static String post(String url,List<NameValuePair> formparams)throws NetServiceException{
        return post(url,formparams,"UTF-8");
    }
    /**
     * post方式请求，
     * List<NameValuePair> formparams= new ArrayList<NameValuePair>();
     * formparams.add(new BasicNameValuePair("username","NTY000000"));
     * @param url URL地址
     * @param formparams 参数
     * @param charset 编码
     * @return
     */
    public static String post(String url, List<NameValuePair> formparams,String charset) throws NetServiceException {

        //创建HttpClientBuilder  
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        //HttpClient  
        CloseableHttpClient closeableHttpClient = httpClientBuilder.build();

        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(RequestConfig.DEFAULT);
        // 创建参数队列
        //formparams.add(new BasicNameValuePair("searchText", "英语"));

        UrlEncodedFormEntity entity;
        try {
            entity = new UrlEncodedFormEntity(formparams, charset);
            httpPost.setEntity(entity);

            HttpResponse httpResponse;
            //post请求  
            httpResponse = closeableHttpClient.execute(httpPost);

            //getEntity()  
            HttpEntity httpEntity = httpResponse.getEntity();
            if (httpEntity != null) {
                //打印响应内容
               return EntityUtils.toString(httpEntity, charset);
            }else{
                return "";
            }
        } catch (IOException e) {
            throw new NetServiceException(e);
        }finally {
            try {
                //关闭流并释放内存
                closeableHttpClient.close();
            } catch (IOException e) {
                throw new NetServiceException(e);
            }
        }
    }


}
