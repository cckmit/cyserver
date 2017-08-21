package com.cy.core.association.entity;

import com.cy.base.entity.DataEntity;
import com.cy.common.utils.StringUtils;
import com.cy.system.Global;

/**
 * Created by cha0res on 12/13/16.
 */
public class AssociationMember extends DataEntity<AssociationMember> {
    private static final long serialVersionUID = 1L;

    private String associationId;       //所属社团编号
    private String status;              //审核状态（10：加入申请中；15：邀请中；20：正式社員；25：申请退出中；30：拒绝加入；35：拒绝邀请；40：已退出；）
    private String accountNum;          //手机用户编号（userprofile表中的accountNum）
    private String joinTime;            //加入时间
    private String quitTime;            //退出时间
    private String telephone;           //联系电话
    private String position;            //职位（空为普通成员）
    private String inviteAccountNum;    //邀请人编号

    private String name;                //用户姓名
    private String positionName;        //职位名称
    private String picture;             //用户头像
    private String pictureUrl;          //相对
    private String picture_xd;             //新加用户头像
    private String address;             //地址
    private String sign;                //个性签名
    private String sex;                 //性别
    private String birthday;            //生日
    private String hobby;               //兴趣爱好
    private String groupName;           //学习经历
    private String allPath;            //完整的社团和职位信息
    private String isCurrent;           //是否当前用户

    public String getAssociationId() {
        return associationId;
    }

    public void setAssociationId(String associationId) {
        this.associationId = associationId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAccountNum() {
        return accountNum;
    }

    public void setAccountNum(String accountNum) {
        this.accountNum = accountNum;
    }

    public String getJoinTime() {
        return joinTime;
    }

    public void setJoinTime(String joinTime) {
        this.joinTime = joinTime;
    }

    public String getQuitTime() {
        return quitTime;
    }

    public void setQuitTime(String quitTime) {
        this.quitTime = quitTime;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getInviteAccountNum() {
        return inviteAccountNum;
    }

    public void setInviteAccountNum(String inviteAccountNum) {
        this.inviteAccountNum = inviteAccountNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public String getPicture() {
        if(StringUtils.isBlank(picture) && StringUtils.isNotBlank(pictureUrl) && StringUtils.isNotBlank(Global.URL_DOMAIN)) {
            if(pictureUrl.indexOf("http") < 0) {
                picture = Global.URL_DOMAIN + pictureUrl ;
            }else{
                picture=pictureUrl;
            }
        }
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getAllPath() {
        return allPath;
    }

    public void setAllPath(String allPath) {
        this.allPath = allPath;
    }

    public String getIsCurrent() {
        return isCurrent;
    }

    public void setIsCurrent(String isCurrent) {
        this.isCurrent = isCurrent;
    }

    public String getPictureUrl() {
        if(StringUtils.isBlank(pictureUrl) && StringUtils.isNotBlank(picture) && org.apache.commons.lang3.StringUtils.isNotBlank(Global.URL_DOMAIN)) {
            if(picture.indexOf(Global.URL_DOMAIN) == 0) {
                pictureUrl = picture.substring(Global.URL_DOMAIN.length()) ;
            }else{
                pictureUrl = picture;
            }
        }
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getPicture_xd() {
        if(StringUtils.isBlank(picture_xd) && StringUtils.isNotBlank(picture) &&StringUtils.isNotBlank(Global.URL_DOMAIN)) {
            if(picture.indexOf(Global.URL_DOMAIN) == 0) {
                picture_xd = picture.substring(Global.URL_DOMAIN.length()) ;
            }else{
                picture_xd=picture;
            }
        }
        return picture_xd;
    }

    public void setPicture_xd(String picture_xd) {
        this.picture_xd = picture_xd;
    }
}
