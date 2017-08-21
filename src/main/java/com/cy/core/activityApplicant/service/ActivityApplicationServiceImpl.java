package com.cy.core.activityApplicant.service;

import com.alibaba.fastjson.JSON;
import com.cy.base.entity.DataGrid;
import com.cy.base.entity.Message;
import com.cy.common.utils.StringUtils;
import com.cy.core.activityApplicant.dao.ActivityApplicantMapper;
import com.cy.core.activityApplicant.entity.ActivityApplicant;
import com.cy.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 报名人业务实现类
 *
 * @author niu
 * @create 2017-06-06 上午 10:33
 **/
@Service("activityApplicantService")
public class ActivityApplicationServiceImpl implements ActivityApplicantService {

    @Autowired
    private ActivityApplicantMapper activityApplicantMapper;

    @Override
    public DataGrid<ActivityApplicant> dataGrid(Map<String, Object> map) {
        DataGrid<ActivityApplicant> dataGrid = new DataGrid<ActivityApplicant>();
        long total = activityApplicantMapper.count(map);
        dataGrid.setTotal(total);
        int start = ((Integer) map.get("page") - 1) * (Integer) map.get("rows");
        map.put("start", start);
        map.put("isNotLimit",0);
        List<ActivityApplicant> list = activityApplicantMapper.selectList(map);
        dataGrid.setRows(list);
        return dataGrid;
    }

    @Override
    public void save(ActivityApplicant activityApplicant) {
        activityApplicant.preInsert();
        activityApplicantMapper.insert(activityApplicant);
    }

    /**
     * 方法applicant 的功能描述：抽奖活动报名接口
     * @createAuthor niu
     * @createDate 2017-06-06 19:41:37
     * @param message
     * @param content
     * @return void
     * @throw
     *
     */
    @Override
    public void applicant(Message message, String content) {

        if (StringUtils.isBlank(content)) {
            message.setMsg("未传入参数");
            message.setSuccess(false);
            return;
        }
        ActivityApplicant activityApplicant = JSON.parseObject(content,ActivityApplicant.class);
        if (activityApplicant !=null){
            if (StringUtils.isBlank(activityApplicant.getName())){
                message.init(false,"姓名不能为空", null);
            }
            if(StringUtils.isBlank(activityApplicant.getTelephone())){
                message.init(false,"手机号不能为空", null);
            }
            if(StringUtils.isBlank(activityApplicant.getActivityId())){
                message.init(false,"活动编号为空", null);
            }
            if (StringUtils.isBlank(activityApplicant.getOpenId())){
                message.init(false,"openId不能为空", null);
            }
        }else {
            message.init(false,"报名信息为空", null);
        }

        //判断是否已报过名
        Map<String,Object> map = new HashMap<>();
        map.put("activityId",activityApplicant.getActivityId());
        map.put("openId",activityApplicant.getOpenId());
        List<ActivityApplicant> activityApplicantList = activityApplicantMapper.selectList(map);
        if (activityApplicantList != null && !activityApplicantList.isEmpty()){
            message.init(false,"您已报过名啦",null);
        }else {
            activityApplicant.preInsert();
            activityApplicantMapper.insert(activityApplicant);
            message.init(true,"报名成功",null);
        }

    }



    /**
     * 方法applicantList 的功能描述：获取报名人列表
     * @createAuthor niu
     * @createDate 2017-06-07 09:52:45
     * @param message
     * @param content
     * @return void
     * @throw
     *
     */
    @Override
    public void applicantList(Message message, String content) {

        if (StringUtils.isBlank(content)) {
            message.setMsg("未传入参数");
            message.setSuccess(false);
            return;
        }

        Map<String,Object> map = JSON.parseObject(content,Map.class);
        String activityId = (String) map.get("activityId");

        if (StringUtils.isBlank(activityId)){
            message.init(false,"活动编号不能为空",null);
        }


        List<ActivityApplicant> activityApplicantList = activityApplicantMapper.selectList(map);
       /* if (activityApplicantList !=null && !activityApplicantList.isEmpty()){
            ActivityApplicant activityApplicant = activityApplicantList.get(0);

            DateFormat dd=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = activityApplicant.getCreateDate();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            int ss =calendar.get(Calendar.SECOND);
            calendar.set(Calendar.SECOND,ss+1);
            activityApplicant.setCreateDate(calendar.getTime());
        }*/
        message.init(true,"获取报名人列表成功",activityApplicantList);
    }

    /**
     * 方法applicantDetail 的功能描述：获取报名人信息
     * @createAuthor niu
     * @createDate 2017-06-08 14:40:21
     * @param message
     * @param content
     * @return void
     * @throw
     *
     */

    @Override
    public void applicantDetail(Message message, String content) {

        if (StringUtils.isBlank(content)) {
            message.setMsg("未传入参数");
            message.setSuccess(false);
            return;
        }
        Map<String,Object> map = JSON.parseObject(content,Map.class);
        String activityId = (String) map.get("activityId");
        String telephone = (String) map.get("telephone");
        String openId = (String) map.get("openId");
        if(StringUtils.isBlank(activityId)){
            message.init(false,"活动编号为空", null);
        }

        if(StringUtils.isBlank(telephone) && StringUtils.isBlank(openId)){
            message.init(false,"手机号和openId有一个必不为空", null);
        }
        ActivityApplicant activityApplicant = activityApplicantMapper.selectApplicantDetail(map);

        message.init(true,"成功", activityApplicant);
    }


}
