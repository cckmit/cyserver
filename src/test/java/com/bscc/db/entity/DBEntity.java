package com.bscc.db.entity;

import java.io.Serializable;

/**
 * <p>Title: DBEntity</p>
 * <p>Description: </p>
 *
 * @author LiuZhen
 * @Company 博视创诚
 * @data 2016-08-31 10:18
 */
public class DBEntity implements Serializable {

    private String url ;        // 数据库连接路径(不带数据库名称)
    private String dbName ;     // 数据库名称
    private String driveName ;  // 数据库驱动
    private String username ;   // 数据库访问用户名
    private String password ;   // 数据库访问密码

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = (url.lastIndexOf("/") < url.length() - 1 && url.lastIndexOf("\\") < url.length() - 1 ) ? url+"/" : url;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getDriveName() {
        return driveName;
    }

    public void setDriveName(String driveName) {
        this.driveName = driveName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
