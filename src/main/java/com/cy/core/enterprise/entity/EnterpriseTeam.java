package com.cy.core.enterprise.entity;

import com.cy.base.entity.DataEntity;
import com.cy.common.utils.StringUtils;
import com.cy.system.Global;

/**
 * Created by Administrator on 2017/5/15.
 */
public class EnterpriseTeam extends DataEntity<EnterpriseTeam> {
    private static final long serialVersionUID = 1L;

    private String fullName;            // 姓名
    private String enterpriseId;    // 所属企业编号
    private String pic;             // 头像
    private String picUrl;          //头像相对路径
    private String isAlumni;        // 是否为校友
    private String position;        // 职位
    private String classinfo;       // 班级信息
    private String enterpriseName;  // 企业名称
    private int sort;               // 排序编号
    private String description;    //个人简介
    private String isShow;         //是否在团队风采展示(0:是 ；1：否)
    private String cloudTeamId; //云平台成员编号
    private String userId;
    private String isCloud;      //条件查询（1：表示只查云平台同步过来的数据）


    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(String enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    public String getPic() {

        if(org.apache.commons.lang3.StringUtils.isBlank(pic) && org.apache.commons.lang3.StringUtils.isNotBlank(picUrl) && org.apache.commons.lang3.StringUtils.isNotBlank(Global.URL_DOMAIN)) {
            if(picUrl.indexOf("http") < 0) {
                if (!picUrl.trim().startsWith("/")){
                    picUrl="/"+picUrl;
                }
                pic = Global.URL_DOMAIN + picUrl ;
            }else{
                pic=picUrl;
            }
        }
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getPicUrl() {
        if(StringUtils.isBlank(picUrl) && StringUtils.isNotBlank(pic) &&StringUtils.isNotBlank(Global.URL_DOMAIN)) {
            if(pic.indexOf(Global.URL_DOMAIN) == 0) {
                if (!pic.trim().startsWith("/")){
                    pic="/"+pic;
                }
                picUrl = pic.substring(Global.URL_DOMAIN.length()) ;
            }else{
                picUrl=pic;
            }
        }
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getIsAlumni() {
        return isAlumni;
    }

    public void setIsAlumni(String isAlumni) {
        this.isAlumni = isAlumni;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getClassinfo() {
        return classinfo;
    }

    public void setClassinfo(String classinfo) {
        this.classinfo = classinfo;
    }

    public String getEnterpriseName() {
        return enterpriseName;
    }

    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIsShow() {
        return isShow;
    }

    public void setIsShow(String isShow) {
        this.isShow = isShow;
    }

    public String getCloudTeamId() {
        return cloudTeamId;
    }

    public void setCloudTeamId(String cloudTeamId) {
        this.cloudTeamId = cloudTeamId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getIsCloud() {
        return isCloud;
    }

    public void setIsCloud(String isCloud) {
        this.isCloud = isCloud;
    }
}
