package com.cy.core.express.service;

import com.alibaba.fastjson.JSON;
import com.cy.base.entity.DataGrid;
import com.cy.base.entity.Message;
import com.cy.common.utils.StringUtils;
import com.cy.core.express.dao.ExpressMapper;
import com.cy.core.express.entity.ExpressComment;
import com.cy.core.express.entity.ExpressContent;
import com.cy.core.express.entity.ExpressUpvote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Mr.wu on 2017/6/7.
 */
@Service("expressService")
public class ExpressServiceImpl implements ExpressService {

    @Autowired
    private ExpressMapper expressMapper;

    //表白墙发布接口
    public void sendExpressContent(Message message, String content) {
        if (StringUtils.isBlank(content)) {
            message.setMsg("请求数据不能为空");
            message.setSuccess(false);
            return;
        }

        Map<String, Object> map = JSON.parseObject(content, Map.class);

        //被表白的人
        String expressTo = (String) map.get("expressTo");
        //表白的人
        String expressFrom = (String) map.get("expressFrom");
        //表白内容
        String expressContent = (String) map.get("expressContent");
        //用户ID
        String accountNum = (String) map.get("accountNum");
        //图片URL（多个图片用|分割）
        List<Map> picList = (List) map.get("cyExpressPicList");
        StringBuffer stringBuffer = new StringBuffer();
        if (!picList.isEmpty()) {
            for (int i = 0; i < picList.size(); i++) {
                stringBuffer.append(picList.get(i).get("pic")).append("|");
            }
            stringBuffer.replace(stringBuffer.length() - 1, stringBuffer.length(), "");
        }

        if (StringUtils.isBlank(expressTo)) {
            message.setMsg("请输入你想表白的人");
            message.setSuccess(false);
            return;
        }

        if (StringUtils.isBlank(expressFrom)) {
            expressFrom = "匿名";
        }

        if (StringUtils.isBlank(expressContent)) {
            message.setMsg("请输入表白内容");
            message.setSuccess(false);
            return;
        }
        if (StringUtils.isBlank(accountNum)) {
            message.setMsg("请输入用户ID");
            message.setSuccess(false);
            return;
        }

        map.put("expressTo", expressTo);
        map.put("expressFrom", expressFrom);
        map.put("expressContent", expressContent);
        map.put("accountNum", accountNum);
        map.put("pictureUrl", stringBuffer.toString());
        expressMapper.insertExpressContent(map);
        message.setMsg("表白成功");
        message.setSuccess(true);
    }

    //表白墙内容获取接口
    public void getExpressContentList(Message message, String content) {
        if (StringUtils.isBlank(content)) {
            message.init(false, "请传入参数", null);
            return;
        }
        Map<String, Object> map = JSON.parseObject(content, Map.class);
        //当前页
        String page = (String) map.get("page");
        //一页几行
        String rows = (String) map.get("rows");
        //用户ID
        String accountNum = (String) map.get("accountNum");
        //获取全部表白墙列表 action：0  获取点赞最多100条表白墙列表 action:1
        String action = (String) map.get("action");
        if (StringUtils.isBlank(accountNum)) {
            message.setMsg("请输入用户ID");
            message.setSuccess(false);
            return;
        }
        if (StringUtils.isBlank(action)) {
            message.setMsg("操作类型不能为空");
            message.setSuccess(false);
            return;
        }
        if (StringUtils.isNotBlank(page) && StringUtils.isNotBlank(rows)) {
            int start = (Integer.valueOf(page) - 1) * Integer.valueOf(rows);
            map.put("start", start);
            map.put("rows", Integer.valueOf(rows));
        } else {
            map.put("isNoLimit", "1");
        }

        DataGrid<ExpressContent> dataGrid = new DataGrid<ExpressContent>();
        List<ExpressContent> contentList = new ArrayList<ExpressContent>();
        if (("0").equals(action)) {
            //表白墙内容获取
            contentList = expressMapper.getExpressContentList(map);
        }
        if (("1").equals(action)) {
            //表白墙内容点赞前100获取
            map.put("totalLimit","1");
            contentList = expressMapper.getExpressContentList(map);
        }
            List<ExpressContent> list = new ArrayList<ExpressContent>();
            for (ExpressContent expressContent : contentList) {
                map.put("expressId", expressContent.getExpressId());
                map.put("accountNum", accountNum);
                map.put("upvoteState", 1);
                //表白墙获取该用户是否点赞
                ExpressUpvote expressUpvote = expressMapper.getExpressUpvote(map);
                //表白墙获取点赞次数
                Long upvoteCount = expressMapper.getExpressUpvoteCount(map);
                //表白墙获取评论次数
                Long commentCount = expressMapper.getExpressCommentCount(map);
                expressContent.setUpvoteCount(upvoteCount);
                expressContent.setCommentCount(commentCount);
                if (expressUpvote == null) {
                    expressContent.setUpvoteState("0");
                } else {
                    expressContent.setUpvoteState("1");
                }
                list.add(expressContent);
            }
            //表白墙个数
            long total = expressMapper.getExpressContentCount();
            dataGrid.setTotal(total);
            dataGrid.setRows(list);
            message.init(true, "表白墙内容获取成功", dataGrid, null);
    }

    //表白墙评论与点赞接口
    public void saveExpressCommentOrPraise(Message message, String content) {
        if (StringUtils.isBlank(content)) {
            message.setMsg("请求数据不能为空");
            message.setSuccess(false);
            return;
        }
        Map<String, Object> map = JSON.parseObject(content, Map.class);
        //操作类型
        String action = (String) map.get("action");
        if (StringUtils.isBlank(action)) {
            message.setMsg("操作类型不能为空");
            message.setSuccess(false);
            return;
        }
        //发布评论
        if (action.equals("0")) {
            //表白内容ID
            String expressId = (String) map.get("expressId");
            //表白的评论内容
            String commentContent = (String) map.get("commentContent");
            //评论用户ID
            String accountNum = (String) map.get("accountNum");
            if (StringUtils.isBlank(expressId)) {
                message.setMsg("请输入表白内容ID");
                message.setSuccess(false);
                return;
            }
            if (StringUtils.isBlank(accountNum)) {
                message.setMsg("请输入用户ID");
                message.setSuccess(false);
                return;
            }
            if (StringUtils.isBlank(commentContent)) {
                message.setMsg("请输入表白的评论内容");
                message.setSuccess(false);
                return;
            }
            map.put("expressId", expressId);
            map.put("commentContent", commentContent);
            map.put("accountNum", accountNum);
            expressMapper.insertExpressComment(map);
            message.setMsg("评论表白成功");
            //获取当前评论内容
            String currentCommentId = expressMapper.getCurrentExpressCommentId(expressId);
            ExpressComment expressComment = expressMapper.getCurrentExpressComment(currentCommentId);
            message.setObj(expressComment);
            message.setSuccess(true);
        }
        //表白墙点赞
        else if (action.equals("1")) {
            //表白内容ID
            String expressId = (String) map.get("expressId");
            //评论用户ID
            String accountNum = (String) map.get("accountNum");
            if (StringUtils.isBlank(expressId)) {
                message.setMsg("请输入表白墙ID");
                message.setSuccess(false);
                return;
            }
            if (StringUtils.isBlank(accountNum)) {
                message.setMsg("请输入用户ID");
                message.setSuccess(false);
                return;
            }
            map.put("accountNum", accountNum);
            map.put("expressId", expressId);
            map.put("upvoteState", -1);
            if (expressMapper.getExpressUpvote(map) == null) {
                expressMapper.insertExpressUpvote(map);
            } else {
                map.put("upvoteState", 1);
                expressMapper.cancelExpressUpvote(map);
            }
            message.setMsg("点赞成功");
            message.setSuccess(true);
        }
        //取消点赞
       else if (action.equals("2")) {
            //表白内容ID
            String expressId = (String) map.get("expressId");
            //评论用户ID
            String accountNum = (String) map.get("accountNum");
            if (StringUtils.isBlank(expressId)) {
                message.setMsg("请输入表白墙ID");
                message.setSuccess(false);
                return;
            }
            if (StringUtils.isBlank(accountNum)) {
                message.setMsg("请输入用户ID");
                message.setSuccess(false);
                return;
            }
            map.put("accountNum", accountNum);
            map.put("expressId", expressId);
            map.put("upvoteState", 0);
            expressMapper.cancelExpressUpvote(map);
            message.setMsg("取消点赞成功");
            message.setSuccess(true);
        }
    }
    //表白墙详情获取
    public void getExpressInfo(Message message, String content) {
        if (StringUtils.isBlank(content)) {
            message.init(false, "请传入参数", null);
            return;
        }
        Map<String, Object> map = JSON.parseObject(content, Map.class);
        //表白内容ID
        String expressId = (String) map.get("expressId");

        if (StringUtils.isBlank(expressId)) {
            message.setMsg("请输入表白内容ID");
            message.setSuccess(false);
            return;
        }
        map.put("expressId", expressId);
        ExpressContent expressContent = new ExpressContent();
        //当前表白内容
        expressContent = expressMapper.getExpressContentById(map);
        //表白墙评论列表
        List<ExpressComment> expressCommentList = expressMapper.getExpressCommentList(map);
        expressContent.setExpressCommentList(expressCommentList);
        message.setMsg("表白墙详情查询成功");
        message.setObj(expressContent);
        message.setSuccess(true);
    }


}
