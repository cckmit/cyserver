package com.cy.core.live.service;

import com.alibaba.fastjson.JSON;
import com.cy.base.entity.DataGrid;
import com.cy.base.entity.Message;
import com.cy.common.utils.StringUtils;
import com.cy.core.live.dao.LiveMapper;
import com.cy.core.live.entity.*;
import com.cy.core.schoolServ.entity.SchoolServ;
import com.cy.core.userProfile.dao.UserProfileMapper;
import com.cy.core.userProfile.entity.UserProfile;
import com.cy.system.Global;
import com.cy.util.file.DefaultFileUpload;
import com.cy.util.file.FileResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

/**
 * Created by Mr.wu on 2017/4/7.
 */
@Service("liveService")
public class LiveServiceImpl implements LiveService {

    @Autowired
    private LiveMapper liveMapper;

    @Autowired
    private UserProfileMapper userProfileMapper;

    //是否创建直播间
    public void liveRoomCreateOrNot(Message message,String content) {
        if (StringUtils.isBlank(content)) {
            message.init(false, "请传入参数", null);
            return;
        }
        Map<String, Object> map = JSON.parseObject(content, Map.class);
        //用户编号
        String accountNum = (String) map.get("accountNum");

        if (StringUtils.isBlank(accountNum)) {
            message.init(false, "请传入用户编号", null);
            return;
        }

        String liveRoomId = liveMapper.getLiveRoom(accountNum);
        if (StringUtils.isNotBlank(liveRoomId)) {
            message.setObj(liveRoomId);
            message.setMsg("您已经创建过直播间!");
        } else {
            message.setObj(false);
            message.setMsg("您还未创建过直播间!");
        }
        message.setSuccess(true);
    }

    //创建直播间
    public void liveRoomCreate(Message message,String content, File[] upload, String[] uploadFileName) throws FileNotFoundException, IOException {
        if (StringUtils.isBlank(content)) {
            message.init(false, "请传入参数", null);
            return;
        }
        Map<String, Object> map = JSON.parseObject(content, Map.class);
        //用户编号
        String accountNum = (String) map.get("accountNum");
        //直播间名称
        String liveRoomName = (String) map.get("liveRoomName");
        //直播间描述
        String liveRoomDescription = (String) map.get("liveRoomDescription");
        //主播头像
        String liveRoomPic = "";
        //直播间背景
        String liveRoomWallPhoto = "";

        if (StringUtils.isBlank(accountNum)) {
            message.init(false, "请传入用户编号", null);
            return;
        }

        if (StringUtils.isBlank(liveRoomName)) {
            message.init(false, "请传入直播间的名称", null);
            return;
        }

        if (StringUtils.isBlank(liveRoomDescription)) {
            message.init(false, "请传入直播间描述", null);
            return;
        }

        DefaultFileUpload defaultFileUpload = new DefaultFileUpload();
        defaultFileUpload.setFileDir("liveRoom/" + accountNum);
        defaultFileUpload.setMaxSize(1048576 * 5);
        defaultFileUpload.setCheckSuffix(true);
        defaultFileUpload.setSuffix("gif,jpg,jpeg,png,bmp");
        StringBuffer urlsb = new StringBuffer();
        if(upload != null) {
            for (int i = 0; i < upload.length; i++) {
                FileResult fileResult = defaultFileUpload.uploadFile(upload[i], uploadFileName[i]);
                urlsb.append("/" + fileResult.getFileUrl().substring(Global.URL_DOMAIN.length()));
                urlsb.append("|");
            }
        }
        if (urlsb.length() > 0) {
            urlsb.deleteCharAt(urlsb.length() - 1);
        }
        if (urlsb.length() > 0) {
            String[] pictures = urlsb.toString().split("\\|");
            if (pictures != null && pictures.length > 1) {
                liveRoomPic = pictures[0];
                liveRoomWallPhoto = pictures[1];
            }
        }else {
            message.init(false, "请传入直播间头像或背景", null);
            return;
        }

        LiveRoom liveRoom = new LiveRoom();
        liveRoom.setLiveRoomUserId(accountNum);
        liveRoom.setLiveRoomName(liveRoomName);
        liveRoom.setLiveRoomPic(liveRoomPic);
        liveRoom.setLiveRoomWallPhoto(liveRoomWallPhoto);
        liveRoom.setLiveRoomDescription(liveRoomDescription);

        liveMapper.liveRoomCreate(liveRoom);
        message.setMsg("直播间创建成功!");
        String currentLiveRoomId = liveMapper.getCurrentLiveRoomId(liveRoom);
        LiveRoom liveRoomInfo = liveMapper.getCurrentLiveRoomInfo(currentLiveRoomId);
        message.setObj(liveRoomInfo);
        message.setSuccess(true);
    }

    //修改直播间
    public void liveRoomUpdate(Message message,String content, File[] upload, String[] uploadFileName) throws FileNotFoundException, IOException {
        if (StringUtils.isBlank(content)) {
            message.init(false, "请传入参数", null);
            return;
        }
        Map<String, Object> map = JSON.parseObject(content, Map.class);
        //直播间ID
        String liveRoomId = (String) map.get("liveRoomId");
        //直播间名称
        String liveRoomName = (String) map.get("liveRoomName");
        //直播间描述
        String liveRoomDescription = (String) map.get("liveRoomDescription");
        //主播头像
        String liveRoomPic = "";
        //直播间背景
        String liveRoomWallPhoto = "";
        //主播头像是否更新
        Boolean liveRoomPicUpdateFlag = true;
        //直播间背景是否更新
        Boolean liveRoomWallPhotoUpdateFlag = true;

        if (StringUtils.isBlank(liveRoomId)) {
            message.init(false, "请传入直播间ID", null);
            return;
        }

//        if (StringUtils.isBlank(liveRoomName)) {
//            message.init(false, "请传入直播间的名称", null);
//            return;
//        }
//
//        if (StringUtils.isBlank(liveRoomDescription)) {
//            message.init(false, "请传入直播间描述", null);
//            return;
//        }


        DefaultFileUpload defaultFileUpload = new DefaultFileUpload();
        defaultFileUpload.setFileDir("liveRoom/" + liveRoomId);
        defaultFileUpload.setMaxSize(1048576 * 5);
        defaultFileUpload.setCheckSuffix(true);
        defaultFileUpload.setSuffix("gif,jpg,jpeg,png,bmp");
        StringBuffer urlsb = new StringBuffer();
        if(upload != null) {
            for (int i = 0; i < upload.length; i++) {
                FileResult fileResult = defaultFileUpload.uploadFile(upload[i], uploadFileName[i]);
                if (StringUtils.isBlank(fileResult.getFileUrl())) {
                    if (i == 0) {
                        liveRoomPicUpdateFlag = false;
                    } else {
                        liveRoomWallPhotoUpdateFlag = false;
                    }
                    continue;
                }
                urlsb.append("/" + fileResult.getFileUrl().substring(Global.URL_DOMAIN.length()));
                urlsb.append("|");
            }
        }
        if (urlsb.length() > 0) {
            urlsb.deleteCharAt(urlsb.length() - 1);
        }
        if (urlsb.length() > 0) {
            String[] pictures = urlsb.toString().split("\\|");
            if (pictures != null && pictures.length > 0) {
                if (liveRoomPicUpdateFlag && liveRoomWallPhotoUpdateFlag) {
                    liveRoomPic = pictures[0];
                    liveRoomWallPhoto = pictures[1];
                } else if (liveRoomPicUpdateFlag && !liveRoomWallPhotoUpdateFlag) {
                    liveRoomPic = pictures[0];
                } else if (!liveRoomPicUpdateFlag && liveRoomWallPhotoUpdateFlag) {
                    liveRoomWallPhoto = pictures[0];
                }

            }
        }
//        else {
//            message.init(false, "请传入直播间头像或背景", null);
//            return;
//        }

        LiveRoom liveRoom = new LiveRoom();
        liveRoom.setLiveRoomId(liveRoomId);
        liveRoom.setLiveRoomName(liveRoomName);
        liveRoom.setLiveRoomPic(liveRoomPic);
        liveRoom.setLiveRoomWallPhoto(liveRoomWallPhoto);
        liveRoom.setLiveRoomDescription(liveRoomDescription);

        liveMapper.liveRoomUpdate(liveRoom);
        message.setMsg("直播间修改成功!");
        message.setSuccess(true);
    }

    //语音上传
    public void liveRoomUploadVoice(Message message,String content, File[] upload, String[] uploadFileName) throws FileNotFoundException, IOException {
//        if (StringUtils.isBlank(content)) {
//            message.init(false, "请传入参数", null);
//            return;
//        }
//        Map<String, Object> map = JSON.parseObject(content, Map.class);
//        String accountNum = (String) map.get("accountNum");
//
//        if (StringUtils.isBlank(accountNum)) {
//            message.init(false, "请传入用户编号", null);
//            return;
//        }

        DefaultFileUpload defaultFileUpload = new DefaultFileUpload();
        defaultFileUpload.setFileDir("liveRoomVoice");
        defaultFileUpload.setMaxSize(1048576 * 5);
        defaultFileUpload.setCheckSuffix(true);
        defaultFileUpload.setSuffix("mp3");
        StringBuffer urlsb = new StringBuffer();
        if(upload != null) {
            for (int i = 0; i < upload.length; i++) {
                FileResult fileResult = defaultFileUpload.uploadFile(upload[i], uploadFileName[i]);
                urlsb.append("/" + fileResult.getFileUrl().substring(Global.URL_DOMAIN.length()));
                urlsb.append("|");
            }
        }
        if (urlsb.length() > 0) {
            urlsb.deleteCharAt(urlsb.length() - 1);
            message.setObj(urlsb.toString());
        }
        message.setMsg("语音上传成功!");
        message.setSuccess(true);
    }

    //话题创建
    public void liveTopicCreate(Message message,String content, File[] upload, String[] uploadFileName) throws FileNotFoundException, IOException {
        if (StringUtils.isBlank(content)) {
            message.init(false, "请传入参数", null);
            return;
        }
        Map<String, Object> map = JSON.parseObject(content, Map.class);
        //用户编号
        String accountNum = (String) map.get("accountNum");
        //直播间ID
        String liveRoomId = (String) map.get("liveRoomId");
        //话题名称
        String liveTopicName = (String) map.get("liveTopicName");
        //话题主讲人姓名
        String liveTopicPersonName = (String) map.get("liveTopicPersonName");
        //话题描述
        String liveTopicDescription = (String) map.get("liveTopicDescription");
        //话题背景
        String liveTopicWallPhoto = "";
        //相关话题图片
        String liveTopicPhoto = "";

        if (StringUtils.isBlank(accountNum)) {
            message.init(false, "请传入用户编号", null);
            return;
        }

        if (StringUtils.isBlank(liveRoomId)) {
            message.init(false, "请传入直播间ID", null);
            return;
        }

        if (StringUtils.isBlank(liveTopicName)) {
            message.init(false, "请传入话题名称", null);
            return;
        }

        if (StringUtils.isBlank(liveTopicPersonName)) {
            message.init(false, "请传入话题主讲人姓名", null);
            return;
        }

        if (StringUtils.isBlank(liveTopicDescription)) {
            message.init(false, "请传入话题描述", null);
            return;
        }

        DefaultFileUpload defaultFileUpload = new DefaultFileUpload();
        defaultFileUpload.setFileDir("liveTopic/" + accountNum);
        defaultFileUpload.setMaxSize(1048576 * 5);
        defaultFileUpload.setCheckSuffix(true);
        defaultFileUpload.setSuffix("gif,jpg,jpeg,png,bmp");
        StringBuffer urlsb = new StringBuffer();
        if(upload != null) {
            for (int i = 0; i < upload.length; i++) {
                FileResult fileResult = defaultFileUpload.uploadFile(upload[i], uploadFileName[i]);
                urlsb.append("/" + fileResult.getFileUrl().substring(Global.URL_DOMAIN.length()));
                urlsb.append("|");
            }
        }
        if (urlsb.length() > 0) {
            urlsb.deleteCharAt(urlsb.length() - 1);
            message.setObj(urlsb.toString());
        }
        if (urlsb.length() > 0) {
            String[] pictures = urlsb.toString().split("\\|");
            if (pictures != null && pictures.length > 1) {
                liveTopicWallPhoto = pictures[0];
                liveTopicPhoto = urlsb.substring(pictures[0].length() + 1);
            }
        } else {
            message.init(false, "请传入话题背景或相关图片", null);
            return;
        }

        LiveTopic liveTopic = new LiveTopic();
        liveTopic.setLiveRoomId(liveRoomId);
        liveTopic.setLiveTopicUserId(accountNum);
        liveTopic.setLiveTopicName(liveTopicName);
        liveTopic.setLiveTopicPersonName(liveTopicPersonName);
        liveTopic.setLiveTopicDescription(liveTopicDescription);
        liveTopic.setLiveTopicWallPhoto(liveTopicWallPhoto);
        liveTopic.setLiveTopicPhoto(liveTopicPhoto);

        liveMapper.liveTopicCreate(liveTopic);
        message.setMsg("直播间话题创建成功!");
        message.setSuccess(true);
    }

    //话题修改
    public void liveTopicUpdate(Message message,String content, File[] upload, String[] uploadFileName) throws FileNotFoundException, IOException {
        if (StringUtils.isBlank(content)) {
            message.init(false, "请传入参数", null);
            return;
        }
        Map<String, Object> map = JSON.parseObject(content, Map.class);
        //直播间话题ID
        String liveTopicId = (String) map.get("liveTopicId");
        //话题名称
        String liveTopicName = (String) map.get("liveTopicName");
        //话题主讲人姓名
        String liveTopicPersonName = (String) map.get("liveTopicPersonName");
        //话题描述
        String liveTopicDescription = (String) map.get("liveTopicDescription");
        //话题背景
        String liveTopicWallPhoto = "";
        //相关话题图片
        String liveTopicPhoto = "";

        if (StringUtils.isBlank(liveTopicId)) {
            message.init(false, "请传入直播间话题ID", null);
            return;
        }

//        if (StringUtils.isBlank(liveTopicName)) {
//            message.init(false, "请传入话题名称", null);
//            return;
//        }
//
//        if (StringUtils.isBlank(liveTopicPersonName)) {
//            message.init(false, "请传入话题主讲人姓名", null);
//            return;
//        }
//
//        if (StringUtils.isBlank(liveTopicDescription)) {
//            message.init(false, "请传入话题描述", null);
//            return;
//        }

        DefaultFileUpload defaultFileUpload = new DefaultFileUpload();
        defaultFileUpload.setFileDir("liveTopic/" + liveTopicId);
        defaultFileUpload.setMaxSize(1048576 * 5);
        defaultFileUpload.setCheckSuffix(true);
        defaultFileUpload.setSuffix("gif,jpg,jpeg,png,bmp");
        StringBuffer urlsb = new StringBuffer();
        if(upload != null) {
            for (int i = 0; i < upload.length; i++) {
                FileResult fileResult = defaultFileUpload.uploadFile(upload[i], uploadFileName[i]);
                if (StringUtils.isBlank(fileResult.getFileUrl())) {
                    continue;
                }
                urlsb.append("/" + fileResult.getFileUrl().substring(Global.URL_DOMAIN.length()));
                urlsb.append("|");
            }
        }
        if (urlsb.length() > 0) {
            urlsb.deleteCharAt(urlsb.length() - 1);
        }
        if (urlsb.length() > 0) {
            String[] pictures = urlsb.toString().split("\\|");
            if (pictures != null && pictures.length > 1) {
                liveTopicWallPhoto = pictures[0];
                liveTopicPhoto = urlsb.substring(pictures[0].length() + 1);
            }
        }
//        else {
//            message.init(false, "请传入话题背景或相关图片", null);
//            return;
//        }

        LiveTopic liveTopic = new LiveTopic();
        liveTopic.setLiveTopicId(liveTopicId);
        liveTopic.setLiveTopicName(liveTopicName);
        liveTopic.setLiveTopicPersonName(liveTopicPersonName);
        liveTopic.setLiveTopicDescription(liveTopicDescription);
        liveTopic.setLiveTopicWallPhoto(liveTopicWallPhoto);
        liveTopic.setLiveTopicPhoto(liveTopicPhoto);

        liveMapper.liveTopicUpdate(liveTopic);
        message.setMsg("直播间话题修改成功!");
        message.setSuccess(true);
    }

    //直播间话题展示
    public void liveRoomListShow(Message message,String content) {
        if (StringUtils.isBlank(content)) {
            message.init(false, "请传入参数", null);
            return;
        }
        Map<String, Object> map = JSON.parseObject(content, Map.class);
        //用户编号
        String accountNum = (String) map.get("accountNum");
        //当前页
        String page = (String)map.get("page");
        //一页几行
        String rows = (String)map.get("rows");
        //展示标识1:人气推荐2：展示更多
        String flag = (String)map.get("flag");

        if (StringUtils.isBlank(accountNum)) {
            message.init(false, "请传入用户编号", null);
            return;
        }

        if (StringUtils.isBlank(flag)) {
            message.init(false, "请传入展示标识1:人气推荐2：展示更多", null);
            return;
        }

        map.put("accountNum", accountNum );
        map.put("flag", flag );

        if (StringUtils.isNotBlank(page) && StringUtils.isNotBlank(rows)) {
            int start = (Integer.valueOf(page) - 1) * Integer.valueOf(rows);
            map.put("start", start);
            map.put("rows", Integer.valueOf(rows));
        } else {
            map.put("isNoLimit", "1");
        }
        DataGrid<LiveRoomTopic> dataGrid = new DataGrid<LiveRoomTopic>();
        List LiveRoomList = new ArrayList<LiveRoomTopic>();
        LiveRoomList = liveMapper.showLiveRoomList(map);
        long total = liveMapper.showLiveRoomListCount();
        dataGrid.setTotal(total);
        dataGrid.setRows(LiveRoomList);
        message.init(true ,"直播列表话题查询成功",dataGrid,null);
    }

    //个人直播间展示
    public void liveRoomShow(Message message,String content) {
        if (StringUtils.isBlank(content)) {
            message.init(false, "请传入参数", null);
            return;
        }
        Map<String, Object> map = JSON.parseObject(content, Map.class);
        //用户编号
        String accountNum = (String) map.get("accountNum");
        //需要展示直播间的人的编号
        String userId = (String) map.get("userId");
        //直播间ID
        String liveRoomId = (String) map.get("liveRoomId");
        //当前页
        String page = (String)map.get("page");
        //一页几行
        String rows = (String)map.get("rows");

        if (StringUtils.isBlank(accountNum)) {
            message.init(false, "请传入用户编号", null);
            return;
        }

        if (StringUtils.isBlank(userId)) {
            message.init(false, "请传入需要展示直播间的人的编号", null);
            return;
        }

        if (StringUtils.isBlank(liveRoomId)) {
            message.init(false, "请传入直播间ID", null);
            return;
        }

        map.put("accountNum", accountNum );
        map.put("userId", userId );
        map.put("liveRoomId", liveRoomId );

        if (StringUtils.isNotBlank(page) && StringUtils.isNotBlank(rows)) {
            int start = (Integer.valueOf(page) - 1) * Integer.valueOf(rows);
            map.put("start", start);
            map.put("rows", Integer.valueOf(rows));
        } else {
            map.put("isNoLimit", "1");
        }

        LiveRoomTopic liveRoomTopic = new LiveRoomTopic();
        //当前直播间信息
        liveRoomTopic = liveMapper.showLiveRoom(map);
        if (liveRoomTopic != null) {
            //当前话题信息
            liveRoomTopic.setLiveTopic(liveMapper.showLiveRoomTopic(map));
            //话题总数
            liveRoomTopic.setLiveTopicCnt( liveMapper.showLiveTopicCount(map));
            //关注人数
            liveRoomTopic.setLiveRoomAttentionNum(liveMapper.getLiveRoomAttentionNum(map));
            //是否关注
            if (liveMapper.getAttentionExist(map) > 0) {
                liveRoomTopic.setAttentionFlag("1");
            } else {
                liveRoomTopic.setAttentionFlag("0");
            }

            message.setMsg("直播间信息查询成功");
            message.setObj(liveRoomTopic);
            message.setSuccess(true);
        } else {
            message.setMsg("当前直播间不存在");
            message.setSuccess(false);
        }

    }

    //直播间关注
    public void liveRoomAttention(Message message,String content) {
        if (StringUtils.isBlank(content)) {
            message.init(false, "请传入参数", null);
            return;
        }
        Map<String, Object> map = JSON.parseObject(content, Map.class);
        //关注的人的编号
        String accountNum = (String) map.get("accountNum");
        //被关注的人的编号
        String userId = (String) map.get("userId");
        //关注的直播间ID
        String liveRoomId = (String) map.get("liveRoomId");
        //关注标识1:关注2：取消关注
        String flag = (String)map.get("flag");

        if (StringUtils.isBlank(accountNum)) {
            message.init(false, "请传入用户编号", null);
            return;
        }

        if (StringUtils.isBlank(userId)) {
            message.init(false, "请传入被关注的人的编号", null);
            return;
        }

        if (StringUtils.isBlank(liveRoomId)) {
            message.init(false, "请传入直播间ID", null);
            return;
        }

        map.put("accountNum", accountNum );
        map.put("userId", userId );
        map.put("liveRoomId", liveRoomId );

        if ("1".equals(flag)) {
            liveMapper.liveRoomAttention(map);
            message.setMsg("关注成功!");
            message.setSuccess(true);
        } else if ("2".equals(flag)) {
            liveMapper.liveRoomAttentionCancel(map);
            message.setMsg("取消关注成功!");
            message.setSuccess(true);
        }else{
            message.setMsg("未知的操作类型");
            message.setSuccess(false);
            return;
        }
    }

    //直播间话题详情
    public void liveTopicInfo(Message message,String content) {
        if (StringUtils.isBlank(content)) {
            message.init(false, "请传入参数", null);
            return;
        }
        Map<String, Object> map = JSON.parseObject(content, Map.class);
        //用户编号
        String accountNum = (String) map.get("accountNum");
        //需要展示直播间的人的编号
        String userId = (String) map.get("userId");
        //话题ID
        String liveTopicId = (String) map.get("liveTopicId");

        //直播间ID
        String liveRoomId = (String) map.get("liveRoomId");

        if (StringUtils.isBlank(accountNum)) {
            message.init(false, "请传入用户编号", null);
            return;
        }

        if (StringUtils.isBlank(userId)) {
            message.init(false, "请传入需要展示话题的人的编号", null);
            return;
        }

        if (StringUtils.isBlank(liveTopicId)) {
            message.init(false, "请传入话题ID", null);
            return;
        }

        if (StringUtils.isBlank(liveRoomId)) {
            message.init(false, "请传入直播间ID", null);
            return;
        }

        map.put("accountNum", accountNum );
        map.put("userId", userId );
        map.put("liveTopicId", liveTopicId );
        map.put("liveRoomId", liveRoomId );

        LiveRoomTopic liveRoomTopic = new LiveRoomTopic();

        //当前直播间信息
        String currentLiveRoomId = liveRoomId;
        LiveRoom liveRoom = liveMapper.getCurrentLiveRoomInfo(currentLiveRoomId);
        if (liveRoom != null) {
            liveRoomTopic.setLiveRoomId(liveRoom.getLiveRoomId());
            liveRoomTopic.setLiveRoomName(liveRoom.getLiveRoomName());
            liveRoomTopic.setLiveRoomPic(liveRoom.getLiveRoomPic());
        }
        //当前话题信息
        liveRoomTopic.setLiveTopicInfo(liveMapper.showLiveTopicInfo(map));
        //话题总数
        liveRoomTopic.setLiveTopicCnt( liveMapper.showLiveTopicCount(map));
        //关注人数
        liveRoomTopic.setLiveRoomAttentionNum(liveMapper.getLiveRoomAttentionNum(map));
        //是否关注
        if (liveMapper.getAttentionExist(map) > 0) {
            liveRoomTopic.setAttentionFlag("1");
        } else {
            liveRoomTopic.setAttentionFlag("0");
        }

        message.setMsg("话题信息查询成功");
        message.setObj(liveRoomTopic);
        message.setSuccess(true);
    }

    //直播间留言板
    public void liveRoomComment(Message message, String content){
        if(StringUtils.isBlank(content)){
            message.setMsg("请求数据不能为空");
            message.setSuccess(false);
            return;
        }
        try{
            Map<String, String> map = JSON.parseObject(content, Map.class);

            //用户编号
            String accountNum = (String)map.get("accountNum");
            //被留言人的ID
            String liveUserId = (String)map.get("liveUserId");
            //操作类型1：留言 2：删除留言
            String action = (String)map.get("action");
            //留言内容
            String liveComment = (String)map.get("liveComment");
            //直播间ID
            String liveRoomId = (String) map.get("liveRoomId");
            //当条留言ID
            String liveCommentId = (String)map.get("liveCommentId");


            if( StringUtils.isBlank(accountNum) ){
                message.setMsg("用户编号不能为空");
                message.setSuccess(false);
                return;
            }
            if(StringUtils.isBlank(liveUserId)){
                message.setMsg("被留言人的ID不能为空");
                message.setSuccess(false);
                return;
            }

            if(StringUtils.isBlank(liveRoomId)){
                message.setMsg("直播间ID不能为空");
                message.setSuccess(false);
                return;
            }

            if(action.equals("1")) {
                if (StringUtils.isBlank(liveComment)) {
                    message.setMsg("留言内容不能为空");
                    message.setSuccess(false);
                    return;
                }
            }

            if(StringUtils.isBlank(action)){
                message.setMsg("操作类型不能为空");
                message.setSuccess(false);
                return;
            }

            if(action.equals("2")){
                if( StringUtils.isBlank(liveCommentId) ){
                    message.setMsg("当条留言ID不能为空");
                    message.setSuccess(false);
                    return;
                }
            }

            if(action.equals("1") || action.equals("2")){
                map.put("accountNum", accountNum );
                map.put("liveUserId", liveUserId );
                map.put("liveComment", liveComment );
                map.put("liveRoomId", liveRoomId );
                map.put("liveCommentId", liveCommentId );
                if(action.equals("1")){

                    liveMapper.insertLiveRoomComment(map);
                    message.setMsg("留言成功");
                    String currentLiveCommentId = liveMapper.getCurrentLiveCommentId(map);
                    LiveComment currentLiveComment = liveMapper.getCurrentLiveComment(currentLiveCommentId);
                    message.setObj(currentLiveComment);
                    message.setSuccess(true);
                    return;
                }else{
                    liveMapper.deleteLiveRoomComment(map);
                    message.setMsg("删除留言成功");
                    message.setSuccess(true);
                    return;
                }

            }else {
                message.setMsg("未知的操作类型");
                message.setSuccess(false);
                return;
            }
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //直播间留言板列表
    public void liveRoomCommentList(Message message, String content){
        if(StringUtils.isBlank(content)){
            message.setMsg("请求数据不能为空");
            message.setSuccess(false);
            return;
        }

        Map<String, Object> map = JSON.parseObject(content, Map.class);

        //用户编号
        String accountNum = (String)map.get("accountNum");
        //直播间ID
        String liveRoomId = (String) map.get("liveRoomId");
        //当前页
        String page = (String)map.get("page");
        //一页几行
        String rows = (String)map.get("rows");


        if( StringUtils.isBlank(accountNum) ){
            message.setMsg("用户编号不能为空");
            message.setSuccess(false);
            return;
        }

        if(StringUtils.isBlank(liveRoomId)){
            message.setMsg("直播间ID不能为空");
            message.setSuccess(false);
            return;
        }

        map.put("accountNum", accountNum );
        map.put("liveRoomId", liveRoomId );

        if (StringUtils.isNotBlank(page) && StringUtils.isNotBlank(rows)) {
            int start = (Integer.valueOf(page) - 1) * Integer.valueOf(rows);
            map.put("start", start);
            map.put("rows", Integer.valueOf(rows));
        } else {
            map.put("isNoLimit", "1");
        }

        DataGrid<LiveComment> dataGrid = new DataGrid<LiveComment>();
        List<LiveComment> liveCommentList = liveMapper.showLiveCommentList(map);
        long total = liveMapper.showLiveCommentListCount(map);
        dataGrid.setTotal(total);
        dataGrid.setRows(liveCommentList);
        message.init(true ,"直播间留言板列表查询成功",dataGrid,null);

    }

    //直播间回复留言
    public void liveRoomReply(Message message, String content){
        if(StringUtils.isBlank(content)){
            message.setMsg("请求数据不能为空");
            message.setSuccess(false);
            return;
        }
        try{
            Map<String, String> map = JSON.parseObject(content, Map.class);

            //用户编号
            String accountNum = (String)map.get("accountNum");
            //被回复人的ID
            // String liveUserId = (String)map.get("liveUserId");
            //操作类型1：回复 2：删除回复
            String action = (String)map.get("action");
            //回复内容
            String liveReply = (String)map.get("liveReply");
            //直播间ID
            String liveRoomId = (String) map.get("liveRoomId");
            //留言ID
            String liveCommentId = (String)map.get("liveCommentId");
            //回复ID
            String liveReplyId = (String)map.get("liveReplyId");


            if( StringUtils.isBlank(accountNum) ){
                message.setMsg("用户编号不能为空");
                message.setSuccess(false);
                return;
            }

            if(StringUtils.isBlank(liveRoomId)){
                message.setMsg("直播间ID不能为空");
                message.setSuccess(false);
                return;
            }

            if(action.equals("1")) {
                if (StringUtils.isBlank(liveReply)) {
                    message.setMsg("回复内容不能为空");
                    message.setSuccess(false);
                    return;
                }

                if (StringUtils.isBlank(liveCommentId)) {
                    message.setMsg("留言ID不能为空");
                    message.setSuccess(false);
                    return;
                }
            }

            if(StringUtils.isBlank(action)){
                message.setMsg("操作类型不能为空");
                message.setSuccess(false);
                return;
            }

            if(action.equals("2")){
                if( StringUtils.isBlank(liveReplyId) ){
                    message.setMsg("当条回复ID不能为空");
                    message.setSuccess(false);
                    return;
                }
            }

            if(action.equals("1") || action.equals("2")){
                map.put("accountNum", accountNum );
                map.put("liveReply", liveReply );
                map.put("liveRoomId", liveRoomId );
                map.put("liveReplyId", liveReplyId );
                map.put("liveCommentId", liveCommentId );
                if(action.equals("1")){

                    liveMapper.insertLiveRoomReply(map);
                    message.setMsg("回复成功");
                    String currentLiveReplyId = liveMapper.getCurrentLiveReplyId(map);
                    LiveReply currentLiveReply = liveMapper.getCurrentLiveReply(currentLiveReplyId);
                    message.setObj(currentLiveReply);
                    message.setSuccess(true);
                    return;
                }else{
                    liveMapper.deleteLiveRoomReply(map);
                    message.setMsg("删除回复成功");
                    message.setSuccess(true);
                    return;
                }

            }else {
                message.setMsg("未知的操作类型");
                message.setSuccess(false);
                return;
            }
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //直播间留言详情
    public void liveRoomCommentInfo(Message message,String content) {
        if (StringUtils.isBlank(content)) {
            message.init(false, "请传入参数", null);
            return;
        }
        Map<String, Object> map = JSON.parseObject(content, Map.class);
        //当前留言ID
        String liveCommentId = (String) map.get("liveCommentId");

        if (StringUtils.isBlank(liveCommentId)) {
            message.init(false, "请传入当前留言ID", null);
            return;
        }

        map.put("liveCommentId", liveCommentId );

        LiveComment liveComment = new LiveComment();
        //当前留言信息
        liveComment = liveMapper.showLiveComment(map);
        //当前回复列表
        liveComment.setLiveReply(liveMapper.getLiveReplyList(map));

        message.setMsg("留言详情查询成功");
        message.setObj(liveComment);
        message.setSuccess(true);
    }

    //我关注的直播间列表接口
    public void liveRoomAttentionList(Message message,String content) {
        if (StringUtils.isBlank(content)) {
            message.init(false, "请传入参数", null);
            return;
        }
        Map<String, Object> map = JSON.parseObject(content, Map.class);
        //用户编号
        String accountNum = (String) map.get("accountNum");
        //当前页
        String page = (String)map.get("page");
        //一页几行
        String rows = (String)map.get("rows");

        if (StringUtils.isBlank(accountNum)) {
            message.init(false, "请传入用户编号", null);
            return;
        }

        if (StringUtils.isNotBlank(page) && StringUtils.isNotBlank(rows)) {
            int start = (Integer.valueOf(page) - 1) * Integer.valueOf(rows);
            map.put("start", start);
            map.put("rows", Integer.valueOf(rows));
        } else {
            map.put("isNoLimit", "1");
        }

        map.put("accountNum", accountNum);
        DataGrid<LiveRoomTopic> dataGrid = new DataGrid<LiveRoomTopic>();
        List<LiveRoomTopic> liveRoomTopicList = new ArrayList<LiveRoomTopic>();
        List<LiveRoomAttention> liveRoomAttentionList = new ArrayList<LiveRoomAttention>();
        //我关注的直播间列表
        liveRoomAttentionList = liveMapper.getLiveRoomAttentionList(map);
        if (liveRoomAttentionList != null && liveRoomAttentionList.size() > 0) {
            for (LiveRoomAttention liveRoomAttention : liveRoomAttentionList) {
                map.put("liveRoomId",liveRoomAttention.getLiveRoomId());
                LiveRoomTopic liveRoomTopic = new LiveRoomTopic();
                //直播间信息
                LiveRoomTopic liveRoom = liveMapper.showLiveRoom(map);
                liveRoomTopic.setLiveRoomId(liveRoom.getLiveRoomId());
                liveRoomTopic.setLiveRoomUserId(liveRoom.getLiveRoomUserId());
                liveRoomTopic.setLiveRoomName(liveRoom.getLiveRoomName());
                liveRoomTopic.setLiveRoomPic(liveRoom.getLiveRoomPic());
                liveRoomTopic.setLiveRoomWallPhoto(liveRoom.getLiveRoomWallPhoto());
                liveRoomTopic.setLiveRoomDescription(liveRoom.getLiveRoomDescription());
                liveRoomTopic.setLiveRoomCreateTime(liveRoom.getLiveRoomCreateTime());
                liveRoomTopic.setName(liveRoom.getName());

                //话题总数
                liveRoomTopic.setLiveTopicCnt( liveMapper.showLiveTopicCount(map));
                //关注人数
                liveRoomTopic.setLiveRoomAttentionNum(liveMapper.getLiveRoomAttentionNum(map));

                liveRoomTopicList.add(liveRoomTopic);

            }
        }

        long total = liveMapper.getLiveRoomAttentionCount(map);
        dataGrid.setTotal(total);
        dataGrid.setRows(liveRoomTopicList);
        message.init(true ,"关注的直播间列表查询成功",dataGrid,null);
    }

    //参与话题
    public void liveTopicAttention(Message message,String content) {
        if (StringUtils.isBlank(content)) {
            message.init(false, "请传入参数", null);
            return;
        }
        Map<String, Object> map = JSON.parseObject(content, Map.class);
        //参与话题人的编号
        String accountNum = (String) map.get("accountNum");
        //发表话题的人的编号
        String liveUserId = (String) map.get("liveUserId");
        //当前话题ID
        String liveTopicId = (String) map.get("liveTopicId");
        //当前话题所属直播间ID
        String liveRoomId = (String) map.get("liveRoomId");


        if (StringUtils.isBlank(accountNum)) {
            message.init(false, "请传入参与话题人的编号", null);
            return;
        }

        if (StringUtils.isBlank(liveUserId)) {
            message.init(false, "请传入发表话题的人的编号", null);
            return;
        }

        if (StringUtils.isBlank(liveTopicId)) {
            message.init(false, "请传入当前话题ID", null);
            return;
        }

        if (StringUtils.isBlank(liveRoomId)) {
            message.init(false, "请传入当前话题所属直播间ID", null);
            return;
        }

        map.put("accountNum", accountNum );
        map.put("liveUserId", liveUserId );
        map.put("liveTopicId", liveTopicId );
        map.put("liveRoomId", liveRoomId );

        liveMapper.liveTopicAttention(map);
        //参与人数更新
        liveMapper.updateLiveTopicNumOfPeople(map);
        message.setMsg("参与话题成功!");
        message.setSuccess(true);

    }

    //我参与的话题列表接口
    public void liveTopicAttentionList(Message message,String content) {
        if (StringUtils.isBlank(content)) {
            message.init(false, "请传入参数", null);
            return;
        }
        Map<String, Object> map = JSON.parseObject(content, Map.class);
        //用户编号
        String accountNum = (String) map.get("accountNum");
        //当前页
        String page = (String)map.get("page");
        //一页几行
        String rows = (String)map.get("rows");

        if (StringUtils.isBlank(accountNum)) {
            message.init(false, "请传入用户编号", null);
            return;
        }

        if (StringUtils.isNotBlank(page) && StringUtils.isNotBlank(rows)) {
            int start = (Integer.valueOf(page) - 1) * Integer.valueOf(rows);
            map.put("start", start);
            map.put("rows", Integer.valueOf(rows));
        } else {
            map.put("isNoLimit", "1");
        }

        map.put("accountNum", accountNum);
        DataGrid<LiveTopic> dataGrid = new DataGrid<LiveTopic>();
        List<LiveTopic> liveTopicList = new ArrayList<LiveTopic>();
        List<LiveTopicAttention> liveTopicAttentionList = new ArrayList<LiveTopicAttention>();
        //我参与的话题列表
        liveTopicAttentionList = liveMapper.getLiveTopicAttentionList(map);
        if (liveTopicAttentionList != null && liveTopicAttentionList.size() > 0) {
            for (LiveTopicAttention liveTopicAttention : liveTopicAttentionList) {
                map.put("liveTopicId",liveTopicAttention.getLiveTopicId());
                LiveTopic liveTopic = new LiveTopic();
                //话题信息
                liveTopic = liveMapper.showLiveTopicInfo(map);

                liveTopicList.add(liveTopic);

            }
        }

        long total = liveMapper.getLiveTopicAttentionCount(map);
        dataGrid.setTotal(total);
        dataGrid.setRows(liveTopicList);
        message.init(true ,"我参与的话题列表查询成功",dataGrid,null);
    }

    //结束直播
    public void liveTopicEnd(Message message,String content) {
        if (StringUtils.isBlank(content)) {
            message.init(false, "请传入参数", null);
            return;
        }
        Map<String, Object> map = JSON.parseObject(content, Map.class);
        //当前话题ID
        String liveTopicId = (String) map.get("liveTopicId");

        if (StringUtils.isBlank(liveTopicId)) {
            message.init(false, "请传入当前话题ID", null);
            return;
        }

        map.put("liveTopicId", liveTopicId );

        //直播状态位变更
        liveMapper.updateLiveIn(map);
        message.setMsg("结束直播成功!");
        message.setSuccess(true);

    }

    //直播间内容保存接口
    public void saveLiveRoomContent(Message message,String content, File[] upload, String[] uploadFileName) throws FileNotFoundException, IOException {
        if (StringUtils.isBlank(content)) {
            message.init(false, "请传入参数", null);
            return;
        }
        Map<String, Object> map = JSON.parseObject(content, Map.class);
        //发言人编号
        String accountNum = (String) map.get("accountNum");
        //直播间ID
        String liveRoomId = (String) map.get("liveRoomId");
        //话题ID
        String liveTopicId = (String) map.get("liveTopicId");
        //发表内容
        String liveContent = (String) map.get("liveContent");
        //发表类型（1：文字 2：图片 3：语音）
        String liveContentType = (String) map.get("liveContentType");


        if (StringUtils.isBlank(accountNum)) {
            message.init(false, "请传入发言人编号", null);
            return;
        }

        if (StringUtils.isBlank(liveRoomId)) {
            message.init(false, "请传入当前直播间ID", null);
            return;
        }

        if (StringUtils.isBlank(liveTopicId)) {
            message.init(false, "请传入当前话题ID", null);
            return;
        }

        if (StringUtils.isBlank(liveContentType)) {
            message.init(false, "请传入发表类型", null);
            return;
        }


        map.put("accountNum", accountNum );
        map.put("liveRoomId", liveRoomId );
        map.put("liveTopicId", liveTopicId );
        map.put("liveContentType", liveContentType );

        if(liveContentType.equals("1") || liveContentType.equals("2") || liveContentType.equals("3")){
            //文字
            if(liveContentType.equals("1")){
                map.put("liveContent", liveContent );
                liveMapper.insertLiveRoomContent(map);
                message.setObj(liveContent);
                message.setMsg("文字发表成功");
                message.setSuccess(true);
                return;
            //图片
            }else if (liveContentType.equals("2")) {
                DefaultFileUpload defaultFileUpload = new DefaultFileUpload();
                defaultFileUpload.setFileDir("liveRoomContentPic/" + liveRoomId);
                defaultFileUpload.setMaxSize(1048576 * 5);
                defaultFileUpload.setCheckSuffix(true);
                defaultFileUpload.setSuffix("gif,jpg,jpeg,png,bmp");
                StringBuffer urlsb = new StringBuffer();
                if(upload != null) {
                    for (int i = 0; i < upload.length; i++) {
                        FileResult fileResult = defaultFileUpload.uploadFile(upload[i], uploadFileName[i]);
                        urlsb.append("/" + fileResult.getFileUrl().substring(Global.URL_DOMAIN.length()));
                        urlsb.append("|");
                    }
                }
                if (urlsb.length() > 0) {
                    urlsb.deleteCharAt(urlsb.length() - 1);
                    message.setObj(urlsb.toString());
                }
                map.put("liveContent", urlsb.toString());
                liveMapper.insertLiveRoomContent(map);
                message.setMsg("图片发表成功");
                message.setSuccess(true);
                return;
            //语音
            } else {
                DefaultFileUpload defaultFileUpload = new DefaultFileUpload();
                defaultFileUpload.setFileDir("liveRoomContentVoice/" + liveRoomId);
                defaultFileUpload.setMaxSize(1048576 * 5);
                defaultFileUpload.setCheckSuffix(true);
                defaultFileUpload.setSuffix("mp3");
                StringBuffer urlsb = new StringBuffer();
                if(upload != null) {
                    for (int i = 0; i < upload.length; i++) {
                        FileResult fileResult = defaultFileUpload.uploadFile(upload[i], uploadFileName[i]);
                        urlsb.append("/" + fileResult.getFileUrl().substring(Global.URL_DOMAIN.length()));
                        urlsb.append("|");
                    }
                }
                if (urlsb.length() > 0) {
                    urlsb.deleteCharAt(urlsb.length() - 1);
                    message.setObj(urlsb.toString());
                }
                map.put("liveContent", urlsb.toString());
                liveMapper.insertLiveRoomContent(map);
                message.setMsg("语音发表成功");
                message.setSuccess(true);
                return;
            }
        }else {
            message.setMsg("未知的操作类型");
            message.setSuccess(false);
            return;
        }

    }

    //直播间内容获取接口
    public void getLiveRoomContent(Message message,String content) {
        if (StringUtils.isBlank(content)) {
            message.init(false, "请传入参数", null);
            return;
        }
        Map<String, Object> map = JSON.parseObject(content, Map.class);
        //话题ID
        String liveTopicId = (String) map.get("liveTopicId");
        //进入直播间人的ID
        String accountNum = (String) map.get("accountNum");
        //当前页
        String page = (String)map.get("page");
        //一页几行
        String rows = (String)map.get("rows");

        if (StringUtils.isBlank(liveTopicId)) {
            message.init(false, "请传入话题ID", null);
            return;
        }

        if (StringUtils.isNotBlank(page) && StringUtils.isNotBlank(rows)) {
            int start = (Integer.valueOf(page) - 1) * Integer.valueOf(rows);
            map.put("start", start);
            map.put("rows", Integer.valueOf(rows));
        } else {
            map.put("isNoLimit", "1");
        }

        map.put("accountNum", accountNum);
        map.put("liveTopicId", liveTopicId);
        List<LiveRoomContent> contentList =liveMapper.getLiveRoomContent(map);
        LiveRoomContentList list = new LiveRoomContentList();
        list.setLiveRoomContentList(contentList);
        long total = liveMapper.getLiveRoomContentCount(map);
        list.setLiveRoomContentListCount(total);
        //是否参与过话题
        Long count = liveMapper.getLiveTopicAttentionExist(map);
        if(count > 0){
            list.setLiveTopicAttentionFlag("1");
        }else{
            list.setLiveTopicAttentionFlag("0");
        }

        message.setMsg("直播间内容获取成功");
        message.setObj(list);
        message.setSuccess(true);
    }
}
