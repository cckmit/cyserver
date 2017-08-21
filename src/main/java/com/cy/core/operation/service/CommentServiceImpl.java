package com.cy.core.operation.service;

import com.alibaba.fastjson.JSON;
import com.cy.base.entity.Message;
import com.cy.common.utils.StringUtils;
import com.cy.core.enterprise.entity.Enterprise;
import com.cy.core.enterprise.entity.EnterpriseProduct;
import com.cy.core.enterprise.service.EnterpriseProductService;
import com.cy.core.enterprise.service.EnterpriseService;
import com.cy.core.operation.dao.CommentMapper;
import com.cy.core.operation.entity.Comment;
import com.cy.core.schoolServ.dao.SchoolServMapper;
import com.cy.core.schoolServ.entity.Fxjh;
import com.cy.core.userProfile.dao.UserProfileMapper;
import com.cy.core.userProfile.entity.UserProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by cha0res on 12/27/16.
 */
@Service("commentService")
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private SchoolServMapper schoolServMapper;
    @Autowired
    private EnterpriseService enterpriseService;
    @Autowired
    private EnterpriseProductService enterpriseProductService;
    @Autowired
    private UserProfileMapper userProfileMapper;

    public void findCommentList(Message message, String content){
        if(StringUtils.isBlank(content)){
            message.init(false,"请传入参数", null);
            return;
        }
        Map<String, Object> map = JSON.parseObject(content, Map.class);

        String bussId = (String)map.get("bussId");
        String userId = (String)map.get("userId");
        String page = (String)map.get("page");
        String rows = (String)map.get("rows");

        if(StringUtils.isBlank(bussId)){
            message.init(false,"请传入业务ID", null);
            return;
        }
        if(StringUtils.isBlank(userId)){
            message.init(false,"请传入用户ID", null);
            return;
        }

        map.put("currentUserId", userId );
        map.put("userId","");
        map.put("type","10");

        if (StringUtils.isNotBlank(page) && StringUtils.isNotBlank(rows)) {
            int start = (Integer.valueOf(page) - 1) * Integer.valueOf(rows);
            map.put("start", start);
            map.put("rows", Integer.valueOf(rows));
        } else {
            map.put("isNoLimit", "1");
        }

        List<Comment> list = commentMapper.getCommentList(map);
        if( list==null || list.size()<=0 ){
            message.init(false,"暂无评论",null);
            return;
        }

        for(int i=0;i<list.size();i++){
            Map<String, Object> mapTmp = new HashMap<>();
            mapTmp.put("currentUserId", userId );
            mapTmp.put("bussId",list.get(i).getId());
            mapTmp.put("type","20");
            mapTmp.put("orderType", "1");
            mapTmp.put("isNoLimit","1");
            List<Comment> listChild = commentMapper.getCommentList(mapTmp);
            long commentNumber = commentMapper.count(mapTmp);
            mapTmp.put("type","40");
            long praiseNumber = commentMapper.count(mapTmp);
            list.get(i).setCommentNumber(String.valueOf(commentNumber));
            list.get(i).setPraiseNumber(String.valueOf(praiseNumber));
            if(listChild != null && listChild.size()>0){
                list.get(i).setChild(listChild);
            }
        }
        message.init(true, "查询评论列表成功", list);
    }


    public void saveComment(Message message, String content){
        if(StringUtils.isBlank(content)){
            message.init(false,"请传入参数", null);
            return;
        }
        Comment comment = JSON.parseObject(content, Comment.class);
        if(StringUtils.isBlank(comment.getType())){
            message.init(false,"请传入业务类型", null);
            return;
        }
        if(StringUtils.isBlank(comment.getUserId())){
            message.init(false,"请传入用户ID", null);
            return;
        }
        if(StringUtils.isBlank(comment.getBussId())){
            message.init(false,"请传入评论业务ID", null);
            return;
        }

        UserProfile userProfile = userProfileMapper.selectByAccountNum(comment.getUserId());
        if(userProfile == null){
            message.init(false,"用户不存在", null);
            return;
        }
        if(StringUtils.isBlank(comment.getBussType())){
            comment.setBussType("30");
        }
        if("10".equals(comment.getType())){
            if("30".equals(comment.getBussType())){
                Fxjh fxjh = schoolServMapper.selectByFxjhId(comment.getBussId());
                if(fxjh == null){
                    message.init(false,"不存在的返校计划", null);
                    return;
                }
            }else{
                message.init(false, "这个业务类型", null);
                return;
            }

            if(StringUtils.isBlank(comment.getContent())){
                message.init(false,"请填写评论内容", null);
                return;
            }
            comment.preInsert();
            commentMapper.save(comment);
            message.init(true,"评论成功", null);
        }else if("20".equals(comment.getType())){
            Comment commentTmp = commentMapper.getById(comment.getBussId());
            if(commentTmp == null){
                message.init(false,"不存在该评论", null);
                return;
            }
            if(StringUtils.isBlank(comment.getContent())){
                message.init(false,"请填写评论内容", null);
                return;
            }
            comment.preInsert();
            commentMapper.save(comment);
            message.init(true,"评论成功", null);
        }else if("30".equals(comment.getType()) || "40".equals(comment.getType())){
            if("30".equals(comment.getType())){
                if("30".equals(comment.getBussType())){
                    Fxjh fxjh = schoolServMapper.selectByFxjhId(comment.getBussId());
                    if(fxjh == null){
                        message.init(false,"不存在的返校计划", null);
                        return;
                    }
                }else if("40".equals(comment.getBussType())){
                    Enterprise enterprise = enterpriseService.getById(comment.getBussId());
                    if(enterprise == null){
                        message.init(false, "不存在的企业ID", null);
                        return;
                    }
                }else if("50".equals(comment.getBussType())){
                    EnterpriseProduct enterpriseProduct = enterpriseProductService.getById(comment.getBussId());
                    if(enterpriseProduct == null){
                        message.init(false, "不存在的企业产品ID", null);
                        return;
                    }
                }
            }
            if("40".equals(comment.getType())){
                Comment commentTmp = commentMapper.getById(comment.getBussId());
                if(commentTmp == null){
                    message.init(false,"不存在的评论", null);
                    return;
                }
            }
            Map<String,Object> map = new HashMap<>();
            map.put("type", comment.getType());
            map.put("bussId", comment.getBussId());
            map.put("userId", comment.getUserId());
            map.put("isNoLimit","1");
            List<Comment> list = commentMapper.getCommentList(map);
            if(list != null && list.size() >0){
                Comment commentTmp = list.get(0);
                commentTmp.setDelFlag("1");
                commentTmp.preUpdate();
                commentMapper.delete(commentTmp);
                message.init(true,"取消点赞成功", null);
            }else{
                comment.setPraise("1");
                comment.preInsert();
                commentMapper.save(comment);
                message.init(true,"点赞成功", null);
            }
        }else if("50".equals(comment.getType()) || "60".equals(comment.getType())){
            if("50".equals(comment.getType())){
                Fxjh fxjh = schoolServMapper.selectByFxjhId(comment.getBussId());
                if(fxjh == null){
                    message.init(false,"不存在业务", null);
                    return;
                }
            }
            if("60".equals(comment.getType())){
                Comment commentTmp = commentMapper.getById(comment.getBussId());
                if(commentTmp == null){
                    message.init(false,"不存在的评论", null);
                    return;
                }
            }
            Map<String,Object> map = new HashMap<>();
            map.put("type", comment.getType());
            map.put("bussId", comment.getBussId());
            map.put("userId", comment.getUserId());
            map.put("isNoLimit","1");
            List<Comment> list = commentMapper.getCommentList(map);
            if(list != null && list.size() >0){
                Comment commentTmp = list.get(0);
                commentTmp.setDelFlag("1");
                commentTmp.preUpdate();
                commentMapper.delete(commentTmp);
                message.init(true,"取消打星成功", null);
            }else{
                comment.setStar("1");
                comment.preInsert();
                commentMapper.save(comment);
                message.init(true,"打星成功", null);
            }
        }else if("25".equals(comment.getType())){
            Comment commentTmp = commentMapper.getById(comment.getBussId());
            if(commentTmp == null){
                message.init(false,"不存在的评论", null);
                return;
            }
            if(!comment.getUserId().equals(commentTmp.getUserId())){
                message.init(false,"该评论不是由您发出", null);
                return;
            }
            commentTmp.setDelFlag("1");
            commentTmp.preUpdate();
            commentMapper.delete(commentTmp);
            message.init(false,"删除评论成功", null);
        }
    }
}
