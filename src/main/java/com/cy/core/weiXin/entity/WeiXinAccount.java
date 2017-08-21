package com.cy.core.weiXin.entity;

import com.cy.base.entity.DataEntity;
import com.cy.core.news.entity.News;
import com.cy.system.Global;
import org.apache.commons.lang3.StringUtils;

/**
 * @description 微信公众号实体类
 * @author niu
 * @date 2016/12/26.
 */
public class WeiXinAccount extends DataEntity<WeiXinAccount> {
    private static final long serialVersionUID = 1L;

    private String accountName;          //公众帐号名称
    private String accountToken;         //公众帐号TOKEN
    private String accountNumber;        //公众微信号
    private String accountType;          //公众号类型（10：校友会公共号；20：基金会公共号）
    private String accountEmail;         //电子邮箱
    private String accountDesc;          //公众帐号描述
    private String accountAccessToken;   //ACCESS_TOKEN

    private String partner;              //微信支付商户号
    private String partnerKey;          //微信支付商户API密钥
    private String codeImage;            //二维码图片路径

    private String accountAppId;         //公众帐号APPID
    private String accountAppSecret;     //公众帐号APPSECRET
    private String encryptMessage;       //消息加解密方式(10:不加密;20:加密)
    private String encodingAesKey;       //消息加密密钥由43位字符组成，可随机修改，字符范围为A-Z，a-z，0-9。
    private String addTokenTime	;        //TOKEN获取时间

    private String subscribeType;        //关注类型
    private String welcomes;              //关注欢迎语
    private String newsId;                //关注欢迎新闻id
    private News news;                   //欢迎新闻

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountToken() {
        return accountToken;
    }

    public void setAccountToken(String accountToken) {
        this.accountToken = accountToken;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getAccountEmail() {
        return accountEmail;
    }

    public void setAccountEmail(String accountEmail) {
        this.accountEmail = accountEmail;
    }

    public String getAccountDesc() {
        return accountDesc;
    }

    public void setAccountDesc(String accountDesc) {
        this.accountDesc = accountDesc;
    }

    public String getAccountAccessToken() {
        return accountAccessToken;
    }

    public void setAccountAccessToken(String accountAccessToken) {
        this.accountAccessToken = accountAccessToken;
    }

    public String getAccountAppId() {
        return accountAppId;
    }

    public void setAccountAppId(String accountAppId) {
        this.accountAppId = accountAppId;
    }

    public String getAccountAppSecret() {
        return accountAppSecret;
    }

    public void setAccountAppSecret(String accountAppSecret) {
        this.accountAppSecret = accountAppSecret;
    }

    public String getEncryptMessage() {
        return encryptMessage;
    }

    public void setEncryptMessage(String encryptMessage) {
        this.encryptMessage = encryptMessage;
    }

    public String getEncodingAesKey() {
        return encodingAesKey;
    }

    public void setEncodingAesKey(String encodingAesKey) {
        this.encodingAesKey = encodingAesKey;
    }

    public String getAddTokenTime() {
        return addTokenTime;
    }

    public void setAddTokenTime(String addTokenTime) {
        this.addTokenTime = addTokenTime;
    }

    public String getSubscribeType() {
        return subscribeType;
    }

    public void setSubscribeType(String subscribeType) {
        this.subscribeType = subscribeType;
    }

    public String getWelcomes() {
        return welcomes;
    }

    public void setWelcomes(String welcomes) {
        this.welcomes = welcomes;
    }

    public String getNewsId() {
        return newsId;
    }

    public void setNewsId(String newsId) {
        this.newsId = newsId;
    }

    public News getNews() {
        return news;
    }

    public void setNews(News news) {
        this.news = news;
    }

    public String getPartner() {
        return partner;
    }

    public void setPartner(String partner) {
        this.partner = partner;
    }

    public String getCodeImage() {
        if(StringUtils.isNotBlank(codeImage) && StringUtils.isNotBlank(Global.URL_DOMAIN) && codeImage.indexOf(Global.URL_DOMAIN) == 0) {
            codeImage = codeImage.substring(Global.URL_DOMAIN.length());
        }
        return codeImage;
    }

    public void setCodeImage(String codeImage) {
        this.codeImage = codeImage;
    }

    public String getPartnerKey() {
        return partnerKey;
    }

    public void setPartnerKey(String partnerKey) {
        this.partnerKey = partnerKey;
    }
}
