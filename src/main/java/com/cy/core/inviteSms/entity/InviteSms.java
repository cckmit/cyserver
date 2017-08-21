package com.cy.core.inviteSms.entity;

import com.cy.base.entity.DataEntity;


public class InviteSms extends DataEntity<InviteSms> {
    private static final long serialVersionUID = 1L;

    private String inviterId;         //邀请者user_id
    private String inviterName;       //邀请者姓名
    private String inviteeId;         //被邀请者user_id
    private String inviteeName;       //被邀请者姓名
    private String inviteeTelephone;  //被邀请者手机号
    private String className;         //邀请认证班级
    private String inviteCode;        //邀请码

    public String getInviterId() {
        return inviterId;
    }

    public void setInviterId(String inviterId) {
        this.inviterId = inviterId;
    }

    public String getInviterName() {
        return inviterName;
    }

    public void setInviterName(String inviterName) {
        this.inviterName = inviterName;
    }

    public String getInviteeId() {
        return inviteeId;
    }

    public void setInviteeId(String inviteeId) {
        this.inviteeId = inviteeId;
    }

    public String getInviteeName() {
        return inviteeName;
    }

    public void setInviteeName(String inviteeName) {
        this.inviteeName = inviteeName;
    }

    public String getInviteeTelephone() {
        return inviteeTelephone;
    }

    public void setInviteeTelephone(String inviteeTelephone) {
        this.inviteeTelephone = inviteeTelephone;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }
}
