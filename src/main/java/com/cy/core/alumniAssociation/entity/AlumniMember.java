package com.cy.core.alumniAssociation.entity;

import com.cy.core.userProfile.entity.UserProfile;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Mr.wu on 2017/6/15.
 */
public class AlumniMember implements Serializable {
    private static final long serialVersionUID = 1L;
    private String alumniId;//分会组织ID
    private long memberCount;//分会总人数
    private String msStatus;//当前用户在该会中的状态（5:邀请加入， 10：审核中，20：正式会员，30：未过审）
    private List<UserProfile> memberList;//成员信息

    public String getAlumniId() {
        return alumniId;
    }

    public void setAlumniId(String alumniId) {
        this.alumniId = alumniId;
    }

    public String getMsStatus() {
        return msStatus;
    }

    public void setMsStatus(String msStatus) {
        this.msStatus = msStatus;
    }

    public List<UserProfile> getMemberList() {
        return memberList;
    }

    public void setMemberList(List<UserProfile> memberList) {
        this.memberList = memberList;
    }

    public long getMemberCount() {
        return memberCount;
    }

    public void setMemberCount(long memberCount) {
        this.memberCount = memberCount;
    }
}
