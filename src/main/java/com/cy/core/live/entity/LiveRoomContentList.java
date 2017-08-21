package com.cy.core.live.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Mr.wu on 2017/4/7.
 */
public class LiveRoomContentList implements Serializable {
    private static final long serialVersionUID = 1L;
    /*
    * liveTopicAttentionFlag 是否参与过该话题
    */
    private String liveTopicAttentionFlag;

    /*
    * liveRoomContentList 直播内容列表
    */
    private List<LiveRoomContent> liveRoomContentList;

    /*
    * liveRoomContentListCount 直播内容列表
    */
    private long liveRoomContentListCount;

    public String getLiveTopicAttentionFlag() {
        return liveTopicAttentionFlag;
    }

    public void setLiveTopicAttentionFlag(String liveTopicAttentionFlag) {
        this.liveTopicAttentionFlag = liveTopicAttentionFlag;
    }

    public List<LiveRoomContent> getLiveRoomContentList() {
        return liveRoomContentList;
    }

    public void setLiveRoomContentList(List<LiveRoomContent> liveRoomContentList) {
        this.liveRoomContentList = liveRoomContentList;
    }

    public long getLiveRoomContentListCount() {
        return liveRoomContentListCount;
    }

    public void setLiveRoomContentListCount(long liveRoomContentListCount) {
        this.liveRoomContentListCount = liveRoomContentListCount;
    }
}
