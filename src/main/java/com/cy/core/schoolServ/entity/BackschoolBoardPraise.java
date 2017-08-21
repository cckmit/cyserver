package com.cy.core.schoolServ.entity;

import com.cy.common.utils.StringUtils;
import com.cy.system.Global;
import java.io.Serializable;
import java.util.Date;
/**
 * Created by Yang on 2017/7/10.
 */
public class BackschoolBoardPraise implements Serializable{
    private static final long serialVersionUID = 1L;

    private long id;//花絮图片编号

    private String boardId;//花絮编号

    private String userId;  //创建花絮的用户编号

    private String userName;//前台用户名称

    private String userAvatar;//前台用户头像

    private String userAvatarUrl;

    private Date createTime; //花絮创建时间

    private String createTimeStr;//格式化创建时间

    public long getId() {
        return id;
    }

    public String getBoardId() {
        return boardId;
    }

    public String getUserInfoId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserAvatar() {
        if(StringUtils.isBlank(userAvatar) && StringUtils.isNotBlank(userAvatarUrl) && StringUtils.isNotBlank(Global.URL_DOMAIN)) {
            if(userAvatarUrl.indexOf("http") < 0) {
                userAvatar = Global.URL_DOMAIN + userAvatarUrl ;
            }else{
                userAvatar=userAvatarUrl;
            }
        }
        return userAvatar;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public String getCreateTimeStr() {
        return createTimeStr;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setBoardId(String boardId) {
        this.boardId=boardId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public void setCreateTimeStr(String createTimeStr) {
        this.createTimeStr = createTimeStr;
    }

    public String getUserAvatarUrl() {
        if(StringUtils.isBlank(userAvatarUrl) && StringUtils.isNotBlank(userAvatar) && StringUtils.isNotBlank(Global.URL_DOMAIN)) {
            if(userAvatar.indexOf(Global.URL_DOMAIN) == 0) {
                userAvatarUrl = userAvatar.substring(Global.URL_DOMAIN.length()) ;
            }else{
                userAvatarUrl=userAvatar;
            }
        }
        return userAvatarUrl;
    }

    public void setUserAvatarUrl(String userAvatarUrl) {
        this.userAvatarUrl = userAvatarUrl;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("BackschoolBoardPraise [id=<");
        builder.append(id);
        builder.append(">, boardId=<");
        builder.append(boardId);
        builder.append(">, userInfoId=<");
        builder.append(userId);
        builder.append(">, userName=<");
        builder.append(userName);
        builder.append(">, userAvatar=<");
        builder.append(userAvatar);
        builder.append(">, userAvatarUrl=<");
        builder.append(userAvatarUrl);
        builder.append(">, createTime=<");
        builder.append(createTime);
        builder.append(">, createTimeStr=<");
        builder.append(createTimeStr);
        builder.append(">]");
        return builder.toString();
    }
}
