package com.cy.smscloud.entity;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import com.cy.common.utils.MapUtils;

/**
 * Created by Kentun on 2015-08-28.
 */
public class Header {
    private String module;//模块名
    private String action;//控制器名
    private String method;//方法名
    private String apiToken;//接口令牌
    private String userId;//用户ID
    private String userToken;//用户令牌
    private String deviceId;//设备ID
    private String deviceType;//设备类型，1Android，2IOS

    private String appAccount;  // 短信应用在平台中的账号
    private String appKey;      // 应用在平台的密钥（密码）

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getApiToken() {
        return apiToken;
    }

    public void setApiToken(String apiToken) {
        this.apiToken = apiToken;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    
    public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getAppAccount() {
        return appAccount;
    }

    public void setAppAccount(String appAccount) {
        this.appAccount = appAccount;
    }


	public static Header getHeaderByMap(Object map) throws InvocationTargetException, IntrospectionException, InstantiationException, IllegalAccessException {
        return (Header) MapUtils.convertMap(Header.class,  (Map<String,Object>)map);
    }

	@Override
	public String toString() {
		return "Header [module=" + module + ", action=" + action + ", method="
				+ method + ", apiToken=" + apiToken + ", userId=" + userId
				+ ", userToken=" + userToken + ", deviceId=" + deviceId
				+ ", deviceType=" + deviceType + ", appAccount=" + appAccount
				+ ", appKey=" + appKey + "]";
	}
}
