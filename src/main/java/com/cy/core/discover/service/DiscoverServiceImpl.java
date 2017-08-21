package com.cy.core.discover.service;

import com.alibaba.fastjson.JSON;
import com.cy.base.entity.DataGrid;
import com.cy.base.entity.Message;
import com.cy.common.utils.StringUtils;
import com.cy.core.discover.dao.DiscoverContentMapper;
import com.cy.core.discover.entity.*;
import com.cy.core.userProfile.dao.UserProfileMapper;
import com.cy.core.userProfile.entity.UserProfile;
import com.cy.mobileInterface.multipleFileUpload.entity.MultipleFileUpload;
import com.cy.system.Global;
import com.cy.util.DateUtils;
import com.cy.util.file.DefaultFileUpload;
import com.cy.util.file.FileResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.Comparator;

/**
 * Created by Mr.wu on 2017/3/7.
 */
@Service("discoverService")
class DiscoverServiceImpl implements DiscoverService{

    @Autowired
    private DiscoverContentMapper discoverContentMapper;

    @Autowired
    private UserProfileMapper userProfileMapper;

    public void save(Message message,String content, File[] upload, String[] uploadFileName) throws FileNotFoundException, IOException {
        if(StringUtils.isBlank(content)){
            message.init(false,"请传入参数", null);
            return;
        }
        Map<String,Object> map = JSON.parseObject(content, Map.class);
        String accountNum = (String) map.get("accountNum");
        String discoverContent = (String) map.get("discoverContent");
        String contentType = (String) map.get("contentType");
        String discoverType = (String) map.get("discoverType");
        //String picType = (String) map.get("picType");

        UserProfile userProfile = userProfileMapper.selectByAccountNum(accountNum);
        if(userProfile == null){
            message.init(false,"用户不存在", null);
            return;
        }

        //if (upload == null || upload.length == 0) {
        //message.setMsg("请上传文件!");
        //message.setSuccess(false);
        //return;
        //}

        //朋友圈上传的图片
        DefaultFileUpload defaultFileUpload = new DefaultFileUpload();
        //defaultFileUpload.setFileDir("discover_user_images/" + accountNum + "/" + picType);
        defaultFileUpload.setFileDir("discover_user_images/" + accountNum);
        defaultFileUpload.setMaxSize(1048576 * 5);
        defaultFileUpload.setCheckSuffix(true);
        defaultFileUpload.setSuffix("gif,jpg,jpeg,png,bmp");
        StringBuffer urlsb = new StringBuffer();
        StringBuffer msgsb = new StringBuffer();
        if(upload != null){
            for (int i = 0; i < upload.length; i++) {
                FileResult fileResult = defaultFileUpload.uploadFile(upload[i], uploadFileName[i]);
                urlsb.append(fileResult.getFileUrl());
                urlsb.append("|");
                msgsb.append(fileResult.getMsg());
                msgsb.append("|");
            }
        }
        if (urlsb.length() > 0) {
            urlsb.deleteCharAt(urlsb.length() - 1);
            message.setObj(urlsb.toString());
        }
        if (msgsb.length() > 0) {
            msgsb.deleteCharAt(msgsb.length() - 1);
            message.setMsg(msgsb.toString());
        }
        //朋友圈内容
        DiscoverContent discover = new DiscoverContent();
        discover.setDiscoverId(accountNum);
        discover.setDiscoverContent(discoverContent);
        discover.setContentType(contentType);
        discover.setCreateTime(DateUtils.getDateTime());
        if(message.getObj() != null){
            discover.setDiscoverPic(message.getObj().toString());
        }
        discover.setDiscoverType(discoverType);
        discoverContentMapper.save(discover);
        message.setMsg("发布成功!");
        message.setSuccess(true);
    }

    public void showAllList(Message message,String content){
        if(StringUtils.isBlank(content)){
            message.init(false,"请传入参数", null);
            return;
        }
        Map<String, Object> map = JSON.parseObject(content, Map.class);
        String currentAccountNum = (String)map.get("currentAccountNum");
        String accountNum = (String)map.get("accountNum");
        String page = (String)map.get("page");
        String rows = (String)map.get("rows");
        String showFlag = (String)map.get("showFlag");

        if(StringUtils.isBlank(accountNum)){
            message.init(false,"请传入被展示的用户编号", null);
            return;
        }

        if(!("1".equals(showFlag) || "2".equals(showFlag) || "3".equals(showFlag))){
            message.init(false,"请传入需要查看的范围1、2、3", null);
            return;
        }

        if("1".equals(showFlag)){
            if(StringUtils.isBlank(currentAccountNum)){
                message.init(false,"请传入当前用户编号", null);
                return;
            }
        }

        map.put("accountNum", accountNum );
        map.put("showFlag", showFlag );
        if (StringUtils.isNotBlank(page) && StringUtils.isNotBlank(rows)) {
            int start = (Integer.valueOf(page) - 1) * Integer.valueOf(rows);
            map.put("start", start);
            map.put("rows", Integer.valueOf(rows));
        } else {
            map.put("isNoLimit", "1");
        }

        DataGrid<DiscoverContent> dataGrid = new DataGrid<DiscoverContent>();
        List<DiscoverContent> list = discoverContentMapper.showAllList(map);
        List<DiscoverContent> list1 = new ArrayList<>();
        for(DiscoverContent discoverContent : list){
            if(discoverContent != null){
                String id = discoverContent.getId();
                //点赞人数
                discoverContent.setPraiseNum(discoverContentMapper.getPraise(id));
                //评论列表
                List<DiscoverComment> discoverCommentList = discoverContentMapper.getDiscoverCommentList(id);
                //discoverContent.setDiscoverCommentList(discoverCommentList);
                //评论回复列表
                List<DiscoverReplyComment> discoverReplyCommentList = new ArrayList<>();
                for (DiscoverComment discoverComment : discoverCommentList) {
                    if (discoverComment != null) {
                        //评论
                        DiscoverReplyComment comment = new DiscoverReplyComment();
                        comment.setCommentId(discoverComment.getCommentId());
                        comment.setCommentContent(discoverComment.getCommentContent());
                        comment.setCommentUserId(discoverComment.getCommentUserId());
                        comment.setCommentName(discoverComment.getCommentName());
                        comment.setCommentPicture(discoverComment.getCommentPicture());
                        comment.setCommentCreateTime(discoverComment.getCommentCreateTime());
                        discoverReplyCommentList.add(comment);

                        String commentId = discoverComment.getCommentId();
                        //discoverComment.setDiscoverReply(discoverContentMapper.getDiscoverReplyList(commentId));
                        //回复列表
                        //回复评论的列表
                        List<DiscoverReply> discoverReplyList = discoverContentMapper.getDiscoverReplyList(commentId);
                        if (discoverReplyList != null && discoverReplyList.size() > 0) {
                            for (DiscoverReply discoverReply : discoverReplyList) {
                                //回复
                                DiscoverReplyComment replyComment = new DiscoverReplyComment();
                                replyComment.setCommentId(discoverComment.getCommentId());
                                replyComment.setCommentContent(discoverComment.getCommentContent());
                                replyComment.setCommentUserId(discoverComment.getCommentUserId());
                                replyComment.setCommentName(discoverComment.getCommentName());
                                replyComment.setCommentPicture(discoverComment.getCommentPicture());
                                replyComment.setCommentCreateTime(discoverComment.getCommentCreateTime());
                                replyComment.setReplyId(discoverReply.getReplyId());
                                replyComment.setReplyContent(discoverReply.getReplyContent());
                                replyComment.setReplyUserId(discoverReply.getReplyUserId());
                                replyComment.setReplyName(discoverReply.getReplyName());
                                replyComment.setReplyPicture(discoverReply.getReplyPicture());
                                replyComment.setReplyCreateTime(discoverReply.getReplyCreateTime());
                                discoverReplyCommentList.add(replyComment);
                            }
                        }
                    }
                }

                //回复回复的列表
                List<DiscoverReplyComment> discoverReplyReplyList = new ArrayList<>();
                discoverReplyReplyList = discoverContentMapper.getDiscoverReplyReplyList(id);
                if (discoverReplyReplyList != null && discoverReplyReplyList.size() > 0) {
                    discoverReplyCommentList.addAll(discoverReplyReplyList);
                }

                //排序
                if (discoverReplyCommentList != null && discoverReplyCommentList.size() > 0) {
                    Collections.sort( discoverReplyCommentList, new Comparator<DiscoverReplyComment>() {
                        public int compare(DiscoverReplyComment o1, DiscoverReplyComment o2) {
                           if(StringUtils.isNotBlank(o1.getCommentCreateTime()) &&
                                   StringUtils.isNotBlank(o2.getCommentCreateTime()) &&
                                   !o1.getCommentCreateTime().equals(o2.getCommentCreateTime())) {
                               return o1.getCommentCreateTime().compareTo(o2.getCommentCreateTime());
                           }  else {if (StringUtils.isNotBlank(o1.getReplyCreateTime()) &&
                                   StringUtils.isNotBlank(o2.getReplyCreateTime())) {
                                    return o1.getReplyCreateTime().compareTo(o2.getReplyCreateTime());
                                    } else {
                                    return 1;
                                    }
                           }
                        }
                    });
                }

                //评论回复列表
                discoverContent.setDiscoverReplyCommentList(discoverReplyCommentList);

                //是否点赞
                map.put("discoverId", discoverContent.getDiscoverId());
                map.put("id", discoverContent.getId());
                if("1".equals(showFlag)){
                    map.put("currentAccountNum", currentAccountNum);
                } else {
                    map.put("currentAccountNum", accountNum);
                }
                Long count=discoverContentMapper.getPraiseExist(map);

                if(count>0){
                    discoverContent.setPraiseFlag(true);
                }else{
                    discoverContent.setPraiseFlag(false);
                }
                //图片列表
                List<DiscoverPicture> picList = new ArrayList<>();
                if (discoverContent.getDiscoverPic() != null && discoverContent.getDiscoverPic() != "") {
                    //图片分割
                    String[] pictures = discoverContent.getDiscoverPic().split("\\|");
                    for (int i=0; i<pictures.length; i++) {
                        DiscoverPicture pic = new DiscoverPicture();
//                        BufferedImage bufImg = null;
//                        try {
//                            bufImg = ImageIO.read(new URL(pictures[i]));
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                        if (bufImg != null){
//                            pic.setWidth(bufImg.getWidth());
//                            pic.setHeight(bufImg.getHeight());
//                        }
                        pic.setUrl(pictures[i]);
                        //获取图片的名字
                        String[] pictureName = pictures[i].substring(Global.URL_DOMAIN.length()).split("\\.");
                        //获取图片的宽高
                        if (pictureName != null && pictureName.length > 0){
                            String[] pictureStr = pictureName[0].split("_");
                            if (pictureStr != null && pictureStr.length >=2) {
                                pic.setWidth(Integer.valueOf(pictureStr[pictureStr.length - 2]));
                                pic.setHeight(Integer.valueOf(pictureStr[pictureStr.length - 1]));
                            }
                        }

                        if( StringUtils.isNotBlank(Global.URL_DOMAIN)) {
                            if(pictures[i].indexOf(Global.URL_DOMAIN) == 0) {
                                pic.setUrl_xd(pictures[i].substring(Global.URL_DOMAIN.length()));
                                if (!pic.getUrl_xd().trim().startsWith("/")){
                                    pic.setUrl_xd("/"+pic.getUrl_xd());
                                }
                            }else{
                                pic.setUrl_xd(pictures[i]);
                            }
                        }
                        picList.add(pic);
                    }
                    discoverContent.setDiscoverPicList(picList);
                }
                list1.add(discoverContent);
            }
        }
        long total = discoverContentMapper.showAllListCount(map);
        dataGrid.setTotal(total);
        dataGrid.setRows(list1);
        message.init(true ,"查询成功",dataGrid,null);
    }

    public void saveDiscoverCommentOrPraise(Message message, String content){
        if(StringUtils.isBlank(content)){
            message.setMsg("请求数据不能为空");
            message.setSuccess(false);
            return;
        }
        try{
            Map<String, String> map = JSON.parseObject(content, Map.class);

            String contentId = map.get("contentId");
            String userId = map.get("userId");
            String action = map.get("action");
            String comment = map.get("comment");
            String commentId = map.get("commentId");
            String discoverUserId = map.get("discoverUserId");

            if( StringUtils.isBlank(contentId) ){
                message.setMsg("当前被评论消息的id不能为空");
                message.setSuccess(false);
                return;
            }
            if(StringUtils.isBlank(userId)){
                message.setMsg("评论人账号不能为空");
                message.setSuccess(false);
                return;
            }

            if(action.equals("1")){
                if( StringUtils.isBlank(commentId) ){
                    message.setMsg("评论的内容的id不能为空");
                    message.setSuccess(false);
                    return;
                }
            }

            if(StringUtils.isBlank(discoverUserId)){
                message.setMsg("被评论人的id不能为空");
                message.setSuccess(false);
                return;
            }

            if(StringUtils.isBlank(action)){
                message.setMsg("操作类型不能为空");
                message.setSuccess(false);
                return;
            }

            if(action.equals("0") || action.equals("1")){

                DiscoverComment discoverComment = new DiscoverComment();
                discoverComment.setContentId(contentId);
                discoverComment.setCommentUserId(userId);
                discoverComment.setCommentContent(comment);
                discoverComment.setCommentId(commentId);
                discoverComment.setDiscoverUserId(discoverUserId);

                if(action.equals("0")){
                    if(StringUtils.isBlank(comment)){
                        message.setMsg("没有填写评论内容");
                        message.setSuccess(false);
                        return;
                    }

                    discoverContentMapper.insertDiscoverComment(discoverComment);
                    message.setMsg("评论成功");

                    String currentCommentId = discoverContentMapper.getDiscoverCurrentCommentId(discoverComment);
                    DiscoverComment discoverCurrentComment = discoverContentMapper.getDiscoverCurrentComment(currentCommentId);
                    message.setObj(discoverCurrentComment);
                    message.setSuccess(true);
                    //插入推送消息
                    DiscoverMsgPush discoverMsgPush = new DiscoverMsgPush();
                    discoverMsgPush.setContentId(contentId);
                    discoverMsgPush.setCommentId(currentCommentId);
                    discoverMsgPush.setPushUserId(discoverUserId);
                    discoverMsgPush.setBePushUserId(userId);
                    discoverMsgPush.setIsRead("0");
                    discoverMsgPush.setMessage(comment);
                    discoverMsgPush.setMessageType("2");
                    if (!discoverUserId.equals(userId)) {
                        discoverContentMapper.insertDiscoverMsgPush(discoverMsgPush);
                    }
                    return;
                }else{
                    discoverContentMapper.deleteDiscoverComment(discoverComment);
                    message.setMsg("删除评论成功");
                    message.setSuccess(true);
                    return;
                }

            }else if(action.equals("2") || action.equals("3")){
                DiscoverPraise discoverPraise = new DiscoverPraise();
                discoverPraise.setContentId(contentId);
                discoverPraise.setUserId(userId);
                discoverPraise.setDiscoverUserId(discoverUserId);
                discoverPraise.setCreateTime(DateUtils.getDateTime());

                if(action.equals("2")){
                    discoverContentMapper.insertDiscoverPraise(discoverPraise);
                    message.setMsg("点赞成功");
                    message.setSuccess(true);
                    //插入推送消息
                    DiscoverMsgPush discoverMsgPush = new DiscoverMsgPush();
                    discoverMsgPush.setContentId(contentId);
                    discoverMsgPush.setPushUserId(discoverUserId);
                    discoverMsgPush.setBePushUserId(userId);
                    discoverMsgPush.setIsRead("0");
                    discoverMsgPush.setMessage("点赞");
                    discoverMsgPush.setMessageType("1");
                    if (!discoverUserId.equals(userId)) {
                        //是否已经点过赞了
                        String pushId = discoverContentMapper.getPushId(discoverMsgPush);
                        if ( StringUtils.isBlank(pushId)) {
                            discoverContentMapper.insertDiscoverMsgPush(discoverMsgPush);
                        } else {
                            discoverContentMapper.updateDiscoverMsgPush(pushId);
                        }

                    }
                    return;
                }else {
                    discoverContentMapper.deleteDiscoverPraise(discoverPraise);
                    message.setMsg("取消点赞成功");
                    message.setSuccess(true);
                    return;
                }
            }else{
                message.setMsg("未知的操作类型");
                message.setSuccess(false);
                return;
            }
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteDiscoverContent(Message message, String content){
        if(StringUtils.isBlank(content)){
            message.init(false,"请传入参数", null);
            return;
        }
        Map<String, Object> map = JSON.parseObject(content, Map.class);


        String accountNum = (String)map.get("accountNum");
        String id = (String)map.get("id");
        if( StringUtils.isBlank(accountNum) ){
            message.setMsg("编号不能为空");
            message.setSuccess(false);
            return;
        }
        if( StringUtils.isBlank(id) ){
            message.setMsg("id不能为空");
            message.setSuccess(false);
            return;
        }
        map.put("accountNum", accountNum );
        map.put("id", id );

        discoverContentMapper.deleteDiscoverContent(map);
        message.setMsg("删除成功!");
        message.setSuccess(true);
    }

    public void uploadDiscoverWallPhoto(Message message, String content, File[] upload, String[] uploadFileName) {
        try {
            MultipleFileUpload multipleFileUpload = JSON.parseObject(content, MultipleFileUpload.class);
            if (multipleFileUpload == null) {
                message.setMsg("content is error");
                message.setSuccess(false);
                return;
            }
            DefaultFileUpload defaultFileUpload = new DefaultFileUpload();
            defaultFileUpload.setFileDir("wallPhoto/" + multipleFileUpload.getAccountNum());
            defaultFileUpload.setMaxSize(1048576 * 5);
            defaultFileUpload.setCheckSuffix(true);
            defaultFileUpload.setSuffix("gif,jpg,jpeg,png,bmp");
            StringBuffer urlsb = new StringBuffer();
            StringBuffer msgsb = new StringBuffer();
            if(upload != null) {
                for (int i = 0; i < upload.length; i++) {
                    FileResult fileResult = defaultFileUpload.uploadFile(upload[i], uploadFileName[i]);
                    urlsb.append("/" + fileResult.getFileUrl().substring(Global.URL_DOMAIN.length()));
                    urlsb.append("|");
                    msgsb.append(fileResult.getMsg());
                    msgsb.append("|");
                }
            }
            if (urlsb.length() > 0) {
                urlsb.deleteCharAt(urlsb.length() - 1);
                message.setObj(urlsb.toString());
            }

            if (msgsb.length() > 0) {
                msgsb.deleteCharAt(msgsb.length() - 1);
            }
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("wallPhoto", urlsb.toString());
            map.put("accountNum", multipleFileUpload.getAccountNum());

            int i = discoverContentMapper.selectDiscoverWallPhoto(map);
            if(i == 0){
                discoverContentMapper.insertDiscoverWallPhoto(map);
            } else {
                discoverContentMapper.updateDiscoverWallPhoto(map);
            }
            message.setMsg("背景图上传成功!");
            message.setSuccess(true);
            return;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void getDiscoverWallPhoto(Message message, String content){
        if(StringUtils.isBlank(content)){
            message.init(false,"请传入参数", null);
            return;
        }
        Map<String, Object> map = JSON.parseObject(content, Map.class);


        String accountNum = (String)map.get("accountNum");
        if( StringUtils.isBlank(accountNum) ){
            message.setMsg("编号不能为空");
            message.setSuccess(false);
            return;
        }
        map.put("accountNum", accountNum );
        DiscoverWallPhoto wallPhoto = new DiscoverWallPhoto();
        wallPhoto.setWallPhoto(discoverContentMapper.getDiscoverWallPhoto(map));
        message.setObj(wallPhoto);
        message.setMsg("背景图获取成功!");
        message.setSuccess(true);
    }

    public void getDiscoverNewsList(Message message, String content){
        if (org.apache.commons.lang3.StringUtils.isBlank(content)) {
            message.setMsg("未传入参数");
            message.setSuccess(false);
            return;
        }

        Map<String,Object> map = JSON.parseObject(content, Map.class);
        String page = (String) map.get("page");
        String rows = (String) map.get("rows");
        if (StringUtils.isNotBlank(page) && StringUtils.isNotBlank(rows)) {
            int start = (Integer.valueOf(page) - 1) * Integer.valueOf(rows);
            map.put("start", start);
            map.put("rows", Integer.valueOf(rows));
        } else {
            map.put("isNoLimit", "1");
        }

        List<DiscoverNews> list = discoverContentMapper.listDiscoverNews(map);
        DataGrid<DiscoverNews> dataGrid = new DataGrid<DiscoverNews>();
        long total = discoverContentMapper.listDiscoverNewsCount(map);
        dataGrid.setTotal(total);
        dataGrid.setRows(list);

        message.init(true ,"查询成功",dataGrid,null);
    }

    public void getDiscoverRecruitmentList(Message message, String content){
        if (org.apache.commons.lang3.StringUtils.isBlank(content)) {
            message.setMsg("未传入参数");
            message.setSuccess(false);
            return;
        }

        Map<String,Object> map = JSON.parseObject(content, Map.class);
        String page = (String) map.get("page");
        String rows = (String) map.get("rows");
        if (StringUtils.isNotBlank(page) && StringUtils.isNotBlank(rows)) {
            int start = (Integer.valueOf(page) - 1) * Integer.valueOf(rows);
            map.put("start", start);
            map.put("rows", Integer.valueOf(rows));
        } else {
            map.put("isNoLimit", "1");
        }

        List<DiscoverRecruitment> list = discoverContentMapper.listDiscoverRecruitment(map);
        for(DiscoverRecruitment discoverRecruitment : list){
            if(discoverRecruitment != null){
                if(discoverRecruitment.getContent().startsWith("{")){
                    String contentStr[] = discoverRecruitment.getContent().split(",");
                    if(contentStr!=null && contentStr.length == 5){
                        discoverRecruitment.setRequirement(contentStr[0].substring(15).replaceAll("\"",""));
                        discoverRecruitment.setContent(contentStr[1].substring(8).replaceAll("\"", ""));
                        discoverRecruitment.setRegion(contentStr[2].substring(10).replaceAll("\"", ""));
                        discoverRecruitment.setDescription(contentStr[3].substring(14).replaceAll("\"", ""));
                        discoverRecruitment.setJobType(contentStr[4].substring(7).replaceAll("\"","").replaceAll("}",""));
                    }
                }
            }
        }
        DataGrid<DiscoverRecruitment> dataGrid = new DataGrid<DiscoverRecruitment>();
        long total = discoverContentMapper.listDiscoverRecruitmentCount(map);
        dataGrid.setTotal(total);
        dataGrid.setRows(list);

        message.init(true ,"查询成功",dataGrid,null);
    }

    public void saveDiscoverReply(Message message, String content){
        if(StringUtils.isBlank(content)){
            message.setMsg("请求数据不能为空");
            message.setSuccess(false);
            return;
        }
        try{
            Map<String, String> map = JSON.parseObject(content, Map.class);

            String discoverUserId = map.get("discoverUserId");
            String replyCommentId = map.get("replyCommentId");
            String replyUserId = map.get("replyUserId");
            String replyContent = map.get("replyContent");
            String action = map.get("action");
            String replyId = map.get("replyId");
            String flag = map.get("flag");
            String messageId = map.get("messageId");

            if( StringUtils.isBlank(discoverUserId) ){
                message.setMsg("被回复人账号不能为空");
                message.setSuccess(false);
                return;
            }

            if(StringUtils.isBlank(replyUserId)){
                message.setMsg("回复人账号不能为空");
                message.setSuccess(false);
                return;
            }
            if(action.equals("0")) {
                if(StringUtils.isBlank(replyContent)){
                    message.setMsg("回复内容不能为空");
                    message.setSuccess(false);
                    return;
                }

                if( StringUtils.isBlank(flag) ){
                    message.setMsg("回复的标识不能为空");
                    message.setSuccess(false);
                    return;
                }

                if( StringUtils.isBlank(messageId) ){
                    message.setMsg("当前发布的这条朋友圈的ID不能为空");
                    message.setSuccess(false);
                    return;
                }
            }



//            if( StringUtils.isBlank(replyCommentId) ){
//                message.setMsg("被回复评论的ID不能为空");
//                message.setSuccess(false);
//                return;
//            }

            if(StringUtils.isBlank(action)){
                message.setMsg("操作类型不能为空");
                message.setSuccess(false);
                return;
            }

            if(action.equals("1")){
                if( StringUtils.isBlank(replyId) ){
                    message.setMsg("回复的内容的ID不能为空");
                    message.setSuccess(false);
                    return;
                }
            }


            if(action.equals("0") || action.equals("1")){

                DiscoverReply discoverReply = new DiscoverReply();
                discoverReply.setReplyId(replyId);
                discoverReply.setReplyUserId(replyUserId);
                discoverReply.setDiscoverUserId(discoverUserId);
                discoverReply.setReplyCommentId(replyCommentId);
                discoverReply.setReplyContent(replyContent);
                discoverReply.setFlag(flag);
                discoverReply.setMessageId(messageId);

                if(action.equals("0")){
                    discoverContentMapper.insertDiscoverReply(discoverReply);
                    message.setMsg("回复成功");

                    String currentReplyId = discoverContentMapper.getDiscoverCurrentReplyId(discoverReply);
                    DiscoverReply discoverCurrentReply = discoverContentMapper.getDiscoverCurrentReply(currentReplyId);
                    message.setObj(discoverCurrentReply);
                    message.setSuccess(true);
                    //插入推送消息
                    DiscoverMsgPush discoverMsgPush = new DiscoverMsgPush();
                    discoverMsgPush.setContentId(messageId);
                    discoverMsgPush.setPushUserId(discoverUserId);
                    discoverMsgPush.setBePushUserId(replyUserId);
                    discoverMsgPush.setReplyId(currentReplyId);
                    discoverMsgPush.setIsRead("0");
                    discoverMsgPush.setMessage(replyContent);
                    discoverMsgPush.setMessageType("3");
                    if (!discoverUserId.equals(replyUserId)) {
                        discoverContentMapper.insertDiscoverMsgPush(discoverMsgPush);
                    }

                    return;
                }else{
                    discoverContentMapper.deleteDiscoverReply(discoverReply);
                    message.setMsg("删除回复成功");
                    message.setSuccess(true);
                    return;
                }

            }else{
                message.setMsg("未知的操作类型");
                message.setSuccess(false);
                return;
            }
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void getDiscoverPersonalInfo(Message message,String content){
        if(StringUtils.isBlank(content)){
            message.init(false,"请传入参数", null);
            return;
        }
        Map<String, Object> map = JSON.parseObject(content, Map.class);

//        String currentAccountNum = (String)map.get("currentAccountNum");
//        String accountNum = (String)map.get("accountNum");
        String contentId = (String)map.get("contentId");

//        if(StringUtils.isBlank(currentAccountNum)){
//            message.init(false,"请传入当前用户编号", null);
//            return;
//        }
//
//        if(StringUtils.isBlank(accountNum)){
//            message.init(false,"请传入被查看的用户编号", null);
//            return;
//        }

        if(StringUtils.isBlank(contentId)){
            message.init(false,"请传入当条消息的ID", null);
            return;
        }

//        map.put("currentAccountNum", currentAccountNum );
//        map.put("accountNum", accountNum );
        map.put("contentId", contentId );

        DiscoverContent discoverContent = new DiscoverContent();
        discoverContent = discoverContentMapper.getDiscoverPersonalInfo(map);

            if(discoverContent != null){
                String id = discoverContent.getId();
                //点赞人数
                discoverContent.setPraiseNum(discoverContentMapper.getPraise(id));
                //评论列表
                List<DiscoverComment> discoverCommentList = discoverContentMapper.getDiscoverCommentList(id);

                List<DiscoverReplyComment> discoverReplyCommentList = new ArrayList<>();
                for (DiscoverComment discoverComment : discoverCommentList) {
                    if (discoverComment != null) {
                        //评论
                        DiscoverReplyComment comment = new DiscoverReplyComment();
                        comment.setCommentId(discoverComment.getCommentId());
                        comment.setCommentContent(discoverComment.getCommentContent());
                        comment.setCommentUserId(discoverComment.getCommentUserId());
                        comment.setCommentName(discoverComment.getCommentName());
                        comment.setCommentPicture(discoverComment.getCommentPicture());
                        comment.setCommentCreateTime(discoverComment.getCommentCreateTime());
                        discoverReplyCommentList.add(comment);

                        String commentId = discoverComment.getCommentId();

                        List<DiscoverReply> discoverReplyList = discoverContentMapper.getDiscoverReplyList(commentId);
                        if (discoverReplyList != null && discoverReplyList.size() > 0) {
                            for (DiscoverReply discoverReply : discoverReplyList) {
                                //回复
                                DiscoverReplyComment replyComment = new DiscoverReplyComment();
                                replyComment.setCommentId(discoverComment.getCommentId());
                                replyComment.setCommentContent(discoverComment.getCommentContent());
                                replyComment.setCommentUserId(discoverComment.getCommentUserId());
                                replyComment.setCommentName(discoverComment.getCommentName());
                                replyComment.setCommentPicture(discoverComment.getCommentPicture());
                                replyComment.setCommentCreateTime(discoverComment.getCommentCreateTime());
                                replyComment.setReplyId(discoverReply.getReplyId());
                                replyComment.setReplyContent(discoverReply.getReplyContent());
                                replyComment.setReplyUserId(discoverReply.getReplyUserId());
                                replyComment.setReplyName(discoverReply.getReplyName());
                                replyComment.setReplyPicture(discoverReply.getReplyPicture());
                                replyComment.setReplyCreateTime(discoverReply.getReplyCreateTime());
                                discoverReplyCommentList.add(replyComment);
                            }
                        }
                    }
                }

                //回复回复的列表
                List<DiscoverReplyComment> discoverReplyReplyList = new ArrayList<>();
                discoverReplyReplyList = discoverContentMapper.getDiscoverReplyReplyList(id);
                if (discoverReplyReplyList != null && discoverReplyReplyList.size() > 0) {
                    discoverReplyCommentList.addAll(discoverReplyReplyList);
                }

                //排序
                if (discoverReplyCommentList != null && discoverReplyCommentList.size() > 0) {
                    Collections.sort( discoverReplyCommentList, new Comparator<DiscoverReplyComment>() {
                        public int compare(DiscoverReplyComment o1, DiscoverReplyComment o2) {
                            if(StringUtils.isNotBlank(o1.getCommentCreateTime()) &&
                                    StringUtils.isNotBlank(o2.getCommentCreateTime()) &&
                                    !o1.getCommentCreateTime().equals(o2.getCommentCreateTime())) {
                                return o1.getCommentCreateTime().compareTo(o2.getCommentCreateTime());
                            }  else {if (StringUtils.isNotBlank(o1.getReplyCreateTime()) &&
                                    StringUtils.isNotBlank(o2.getReplyCreateTime())) {
                                return o1.getReplyCreateTime().compareTo(o2.getReplyCreateTime());
                            } else {
                                return 1;
                            }
                            }
                        }
                    });
                }

                //评论回复列表
                discoverContent.setDiscoverReplyCommentList(discoverReplyCommentList);
                //是否点赞
                map.put("discoverId", discoverContent.getDiscoverId() );
                map.put("id", discoverContent.getId() );
                Long count=discoverContentMapper.getPraiseExist(map);

                if(count>0){
                    discoverContent.setPraiseFlag(true);
                }else{
                    discoverContent.setPraiseFlag(false);
                }
                //图片列表
                List<DiscoverPicture> picList = new ArrayList<>();
                if (discoverContent.getDiscoverPic() != null && discoverContent.getDiscoverPic() != "") {
                    String[] pictures = discoverContent.getDiscoverPic().split("\\|");
                    for (int i=0; i<pictures.length; i++) {
                        DiscoverPicture pic = new DiscoverPicture();
                        pic.setUrl(pictures[i]);
                        //获取图片的名字
                        String[] pictureName = pictures[i].substring(Global.URL_DOMAIN.length()).split("\\.");
                        //获取图片的宽高
                        if (pictureName != null && pictureName.length > 0){
                            String[] pictureStr = pictureName[0].split("_");
                            if (pictureStr != null && pictureStr.length >=2) {
                                pic.setWidth(Integer.valueOf(pictureStr[pictureStr.length - 2]));
                                pic.setHeight(Integer.valueOf(pictureStr[pictureStr.length - 1]));
                            }
                        }
                        if( StringUtils.isNotBlank(Global.URL_DOMAIN)) {
                            if(pictures[i].indexOf(Global.URL_DOMAIN) == 0) {
                                pic.setUrl_xd(pictures[i].substring(Global.URL_DOMAIN.length()));
                                if (!pic.getUrl_xd().trim().startsWith("/")){
                                    pic.setUrl_xd("/"+pic.getUrl_xd());
                                }
                            }else{
                                pic.setUrl_xd(pictures[i]);
                            }
                        }
                        picList.add(pic);
                    }
                    discoverContent.setDiscoverPicList(picList);
                }
            }

        message.setObj(discoverContent);
        message.setMsg("个人详细信息获取成功!");
        message.setSuccess(true);
    }

    //获取朋友圈推送消息接口
    public void getDiscoverGetPushMsg(Message message, String content){
        if (org.apache.commons.lang3.StringUtils.isBlank(content)) {
            message.setMsg("未传入参数");
            message.setSuccess(false);
            return;
        }

        Map<String,Object> map = JSON.parseObject(content, Map.class);

        String accountNum = (String)map.get("accountNum");

        if(StringUtils.isBlank(accountNum)){
            message.init(false,"请传入当前用户编号", null);
            return;
        }
        map.put("accountNum", accountNum);

        String page = (String) map.get("page");
        String rows = (String) map.get("rows");
        //是否全部标为已读
        String flag = (String) map.get("flag");
        if (StringUtils.isNotBlank(page) && StringUtils.isNotBlank(rows)) {
            int start = (Integer.valueOf(page) - 1) * Integer.valueOf(rows);
            map.put("start", start);
            map.put("rows", Integer.valueOf(rows));
        } else {
            map.put("isNoLimit", "1");
        }

        //推送消息列表
        List<DiscoverMsgPush> list = discoverContentMapper.getDiscoverPushMsg(map);
        //总数
        long total = discoverContentMapper.getDiscoverPushMsgCount(map);
        //未读数量
        long notReadCount = discoverContentMapper.getDiscoverPushMsgNotReadCount(map);
        DiscoverMsgPush discoverMsgPush = new DiscoverMsgPush();
        discoverMsgPush.setDiscoverPushMsgList(list);
        discoverMsgPush.setTotal(String.valueOf(total));
        discoverMsgPush.setNotReadCount(String.valueOf(notReadCount));

        ////传1表示把所有消息标为已读
        if("1".equals(flag)){
            discoverContentMapper.setAllDiscoverPushMsgRead(map);
        }
        message.setObj(discoverMsgPush);
        message.setMsg("推送信息查询成功!");
        message.setSuccess(true);
    }

    //设置朋友圈消息推送已读接口
    public void discoverGetPushMsgIsRead(Message message, String content){
        if (org.apache.commons.lang3.StringUtils.isBlank(content)) {
            message.setMsg("未传入参数");
            message.setSuccess(false);
            return;
        }

        Map<String,Object> map = JSON.parseObject(content, Map.class);

        //用户编号
        String accountNum = (String)map.get("accountNum");
        //推送消息的ID
        String pushId = (String)map.get("pushId");

        if(StringUtils.isBlank(accountNum)){
            message.init(false,"请传入当前用户编号", null);
            return;
        }

        if(StringUtils.isBlank(pushId)){
            message.init(false,"请传入推送消息的ID", null);
            return;
        }
        map.put("accountNum", accountNum);
        map.put("pushId", pushId);

        //推送消息表已读字段更新
        discoverContentMapper.updateDiscoverPushMsgIsRead(map);
        message.setMsg("已读推送信息!");
        message.setSuccess(true);
    }

    //朋友圈消息推送清空接口
    public void discoverGetPushMsgDelete(Message message, String content){
        if (org.apache.commons.lang3.StringUtils.isBlank(content)) {
            message.setMsg("未传入参数");
            message.setSuccess(false);
            return;
        }

        Map<String,Object> map = JSON.parseObject(content, Map.class);

        //用户编号
        String accountNum = (String)map.get("accountNum");
        //推送消息的ID
        String pushId = (String)map.get("pushId");

        if(StringUtils.isBlank(accountNum)){
            message.init(false,"请传入当前用户编号", null);
            return;
        }

        map.put("accountNum", accountNum);
        map.put("pushId", pushId);

        //推送消息删除
        discoverContentMapper.deleteDiscoverPushMsg(map);
        message.setMsg("推送信息移除成功!");
        message.setSuccess(true);
    }

    //朋友圈举报
    public void discoverContentComplaint(Message message, String content){
        if (org.apache.commons.lang3.StringUtils.isBlank(content)) {
            message.setMsg("未传入参数");
            message.setSuccess(false);
            return;
        }

        Map<String,Object> map = JSON.parseObject(content, Map.class);

        //说说ID
        String contentId = (String)map.get("contentId");
        //举报人的ID
        String complaintUserId = (String)map.get("complaintUserId");
        //被举报人的ID
        String beComplaintUserId = (String)map.get("beComplaintUserId");
        //举报类型（1：色情低俗、2：政治敏感、3：违法、4：广告、5：病毒木马、6：其他）
        String complaintType = (String)map.get("complaintType");
        //举报原因
        String complaintReason = (String)map.get("complaintReason");

        if(StringUtils.isBlank(contentId)){
            message.init(false,"请传入说说ID", null);
            return;
        }
        if(StringUtils.isBlank(complaintUserId)){
            message.init(false,"请传入举报人的ID", null);
            return;
        }
        if(StringUtils.isBlank(beComplaintUserId)){
            message.init(false,"请传入被举报人的ID", null);
            return;
        }
        if(StringUtils.isBlank(complaintType)){
            message.init(false,"请传入举报类型", null);
            return;
        }
        if ("6".equals(complaintType)) {
            if(StringUtils.isBlank(complaintReason)){
                message.init(false,"请传入举报原因", null);
                return;
            }
        }

        map.put("contentId", contentId);
        map.put("complaintUserId", complaintUserId);
        map.put("beComplaintUserId", beComplaintUserId);
        map.put("complaintType", complaintType);
        map.put("complaintReason", complaintReason);

        //判断是否举报过当前说说
        int count = discoverContentMapper.getDiscoverComplaint(map);
        if (count > 0) {
            message.setMsg("您已经举报过，请勿重复提交!");
            message.setSuccess(false);
        } else {
            //举报信息插入
            discoverContentMapper.insertDiscoverComplaint(map);
            message.setMsg("举报成功!");
            message.setSuccess(true);

            //判断是否被举报三次
            int complaintCnt = discoverContentMapper.getTotalDiscoverComplaint(map);
            if (complaintCnt >= 3) {
                //举报超过三次，物理删除
                discoverContentMapper.updateDiscoverContentDelFlag(map);
            }
        }
    }

}
