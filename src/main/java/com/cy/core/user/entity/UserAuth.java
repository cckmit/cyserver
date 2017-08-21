package com.cy.core.user.entity;


public class UserAuth {

    private Integer id;

    private String userid;

 
    private String username;

    
    private String clientSys;

  
    private String clientMeid;

  
    private String clientMacid;

  
    private String token;

  
    private String tokenExp;

   
    private String tokenStatus;


    private String tokenFh;

  
    private String tokenFhExp;

  
    private String tokenFhStatus;

   
    private String stype;  //token 针对不同客户端产生不同值,

 
    private String createTime;

    private String updateTime;

    
    private String tempStr1;
    private String tempStr2;
    private String tempStr3;    
    private String tempStr4;    
    private String tempStr5;
    
    public Integer getId() {
        return id;
    }

 
    public void setId(Integer id) {
        this.id = id;
    }

  
    public String getUserid() {
        return userid;
    }

  
    public void setUserid(String userid) {
        this.userid = userid;
    }

   
    public String getUsername() {
        return username;
    }

   
    public void setUsername(String username) {
        this.username = username;
    }

   
    public String getClientSys() {
        return clientSys;
    }

   
    public void setClientSys(String clientSys) {
        this.clientSys = clientSys;
    }

    public String getClientMeid() {
        return clientMeid;
    }

   
    public void setClientMeid(String clientMeid) {
        this.clientMeid = clientMeid;
    }

    
    public String getClientMacid() {
        return clientMacid;
    }

    public void setClientMacid(String clientMacid) {
        this.clientMacid = clientMacid;
    }

    
    public String getToken() {
        return token;
    }

    
    public void setToken(String token) {
        this.token = token;
    }


	public String getUpdateTime() {
		return updateTime;
	}


	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}


	public String getCreateTime() {
		return createTime;
	}


	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}


	public String getStype() {
		return stype;
	}


	public void setStype(String stype) {
		this.stype = stype;
	}


	public String getTokenFhStatus() {
		return tokenFhStatus;
	}


	public void setTokenFhStatus(String tokenFhStatus) {
		this.tokenFhStatus = tokenFhStatus;
	}


	public String getTokenFhExp() {
		return tokenFhExp;
	}


	public void setTokenFhExp(String tokenFhExp) {
		this.tokenFhExp = tokenFhExp;
	}


	public String getTokenFh() {
		return tokenFh;
	}


	public void setTokenFh(String tokenFh) {
		this.tokenFh = tokenFh;
	}


	public String getTokenStatus() {
		return tokenStatus;
	}


	public void setTokenStatus(String tokenStatus) {
		this.tokenStatus = tokenStatus;
	}


	public String getTokenExp() {
		return tokenExp;
	}


	public void setTokenExp(String tokenExp) {
		this.tokenExp = tokenExp;
	}


	public String getTempStr1() {
		return tempStr1;
	}


	public void setTempStr1(String tempStr1) {
		this.tempStr1 = tempStr1;
	}


	public String getTempStr2() {
		return tempStr2;
	}


	public void setTempStr2(String tempStr2) {
		this.tempStr2 = tempStr2;
	}


	public String getTempStr3() {
		return tempStr3;
	}


	public void setTempStr3(String tempStr3) {
		this.tempStr3 = tempStr3;
	}


	public String getTempStr4() {
		return tempStr4;
	}


	public void setTempStr4(String tempStr4) {
		this.tempStr4 = tempStr4;
	}


	public String getTempStr5() {
		return tempStr5;
	}


	public void setTempStr5(String tempStr5) {
		this.tempStr5 = tempStr5;
	}

    
}