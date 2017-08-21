package com.cy.core.association.entity;

import com.cy.base.entity.DataEntity;
import com.cy.core.event.entity.Event;
import com.cy.system.Global;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * Created by cha0res on 12/13/16.
 */
public class Association extends DataEntity<Association> {
    private static final long serialVersionUID = 1L;

    private String name;            //社团名称
    private String alumniId;        //所属院系编号
    private String isused;          //是否开通（0：未开通，1：审核中，2：已开通）
    private String typeId;          //社团类型ID
    private String introduction;    //分会简介
    private String memberCount;     //成员人数
    private String concatName;     //会长姓名
    private String telephone;       //联系电话
    private String address;         //总部地址
    private String email;           //电子邮箱
    private String userId;          //后台管理员编号
    private String changeTime;     //变更时间
    private String logo;            //社团logo
    private String logoUrl;         //logo存储地址
    private String eventNum;        //活动数量
    private String poster;          //社团海报图片
    private String posterUrl;       //海报存储地址

    private String userAccount;
    private String userPassword;
    private String tel;

    private String alumniName;

    private String typeName;
    private String isMember;
    private String position;
    private String memberStatus;
    private String top;

    private List<AssociationHistory> historyList; //变更历史

    private AssociationHistory currentChange;     //当前变更

    private List<Event> eventList;                //活动列表

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlumniId() {
        return alumniId;
    }

    public void setAlumniId(String alumniId) {
        this.alumniId = alumniId;
    }

    public String getIsused() {
        return isused;
    }

    public void setIsused(String isused) {
        this.isused = isused;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getMemberCount() {
        if(StringUtils.isBlank(memberCount)){
            memberCount = "0";
        }
        return memberCount;
    }

    public void setMemberCount(String memberCount) {
        this.memberCount = memberCount;
    }

    public String getConcatName() {
        return concatName;
    }

    public void setConcatName(String concatName) {
        this.concatName = concatName;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getChangeTime() {
        return changeTime;
    }

    public void setChangeTime(String changeTime) {
        this.changeTime = changeTime;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getAlumniName() {
        return alumniName;
    }

    public void setAlumniName(String alumniName) {
        this.alumniName = alumniName;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public List<AssociationHistory> getHistoryList() {
        return historyList;
    }

    public void setHistoryList(List<AssociationHistory> historyList) {
        this.historyList = historyList;
    }

    public AssociationHistory getCurrentChange() {
        return currentChange;
    }

    public void setCurrentChange(AssociationHistory currentChange) {
        this.currentChange = currentChange;
    }

    public String getLogo() {
        if(StringUtils.isBlank(logo) && StringUtils.isNotBlank(logoUrl) && StringUtils.isNotBlank(Global.URL_DOMAIN)) {
            if(logoUrl.indexOf("http") < 0) {
                if (!logoUrl.trim().startsWith("/")){
                    logoUrl="/"+logoUrl;
                }
                logo = Global.URL_DOMAIN + logoUrl ;
            }else{
                logo=logoUrl;
            }
        }
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getEventNum() {
        return eventNum;
    }

    public void setEventNum(String eventNum) {
        this.eventNum = eventNum;
    }

    public String getIsMember() {
        return isMember;
    }

    public void setIsMember(String isMember) {
        this.isMember = isMember;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public List<Event> getEventList() {
        return eventList;
    }

    public void setEventList(List<Event> eventList) {
        this.eventList = eventList;
    }

    public String getPoster() {
        if(StringUtils.isBlank(poster) && StringUtils.isNotBlank(posterUrl) && StringUtils.isNotBlank(Global.URL_DOMAIN)) {
            if(posterUrl.indexOf("http") < 0) {
                if (!posterUrl.trim().startsWith("/")){
                    posterUrl="/"+posterUrl;
                }
                poster = Global.URL_DOMAIN + posterUrl ;
            }else{
                poster=posterUrl;
            }
        }
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getLogoUrl() {
        if(StringUtils.isBlank(logoUrl) && StringUtils.isNotBlank(logo) && StringUtils.isNotBlank(Global.URL_DOMAIN)) {
            if(logo.indexOf(Global.URL_DOMAIN) == 0) {
                if (!logo.trim().startsWith("/")){
                    logo="/"+logo;
                }
                logoUrl = logo.substring(Global.URL_DOMAIN.length()) ;
            }else{
                logoUrl=logo;
            }
        }
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getPosterUrl() {
        if(StringUtils.isBlank(posterUrl) && StringUtils.isNotBlank(poster) && StringUtils.isNotBlank(Global.URL_DOMAIN)) {
            if(poster.indexOf(Global.URL_DOMAIN) == 0) {
                if (!poster.trim().startsWith("/")){
                    poster="/"+poster;
                }
                posterUrl = poster.substring(Global.URL_DOMAIN.length()) ;
            }else{
                posterUrl=poster;
            }
        }
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public String getMemberStatus() {
        return memberStatus;
    }

    public void setMemberStatus(String memberStatus) {
        this.memberStatus = memberStatus;
    }

    public String getTop() {
        return top;
    }

    public void setTop(String top) {
        this.top = top;
    }
}
