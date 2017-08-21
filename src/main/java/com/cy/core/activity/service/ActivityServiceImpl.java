package com.cy.core.activity.service;

import java.util.*;

import com.alibaba.fastjson.JSON;
import com.cy.base.entity.Message;
import com.cy.common.utils.TimeZoneUtils;
import com.cy.core.backschoolOnlineSign.dao.BackschoolOnlineSignMapper;
import com.cy.core.operation.entity.Comment;
import com.cy.core.userProfile.dao.UserProfileMapper;
import com.cy.core.userProfile.entity.UserProfile;
import com.cy.util.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cy.base.entity.DataGrid;
import com.cy.core.activity.dao.ActivityMapper;
import com.cy.core.activity.entity.Activity;
import com.cy.core.userinfo.dao.UserInfoMapper;
import com.cy.core.userinfo.entity.UserInfo;

@Service("activityService")
public class ActivityServiceImpl implements ActivityService {
    @Autowired
    private ActivityMapper activityMapper;

    @Autowired
    private UserProfileMapper userProfileMapper;

    @Autowired
    private BackschoolOnlineSignMapper backschoolOnlineSignMapper;


    public DataGrid<Activity> dataGrid(Map<String, Object> map) {
        DataGrid<Activity> dataGrid = new DataGrid<Activity>();
        long total = activityMapper.count(map);
        dataGrid.setTotal(total);
        int start = ((Integer) map.get("page") - 1) * (Integer) map.get("rows");
        map.put("start", start);
        List<Activity> list = activityMapper.query(map);
        dataGrid.setRows(list);
        return dataGrid;
    }


    public Activity getById(String id) {
        return activityMapper.getById(id);
    }


    public void save(Activity activity) {
        activityMapper.add(activity);
    }


    public void update(Activity activity) {
        if (activity == null)
            throw new IllegalArgumentException("activity cannot be null!");

        activityMapper.update(activity);
    }


    public void delete(String ids) {
        String[] array = ids.split(",");
        List<Long> list = new ArrayList<Long>();
        for (String id : array)
        {
            list.add(Long.parseLong(id));
        }
        activityMapper.delete(list);
    }


    public List<Activity> selectAll() {
        return activityMapper.queryAll();
    }



    /***********************************************************************
     *
     * 【返校计划】相关API（以下区域）
     *
     * 注意事项：
     * 1、方法名-格式要求
     * 创建方法：save[Name]
     * 撤销方法：remove[Name]
     * 查询分页列表方法：find[Name]ListPage
     * 查询列表方法：find[Name]List
     * 查询详细方法：find[Name]
     *
     ***********************************************************************/
    /**
     * 创建返校聚会接口
     */

    public void saveActivity(Message message, String content) {
        try {
            if (StringUtils.isBlank(content)) {
                message.setMsg("未传入参数");
                message.setSuccess(false);
                return;
            }
            Activity activity = JSON.parseObject(content, Activity.class);
            if (StringUtils.isBlank(activity.getDepartment())) {
                message.setMsg("所属院系不能为空");
                message.setSuccess(false);
                return;
            }
            if (StringUtils.isBlank(activity.getGrade())) {
                message.setMsg("年级不能为空");
                message.setSuccess(false);
                return;
            }
            if (StringUtils.isBlank(activity.getMajor())) {
                message.setMsg("专业不能为空");
                message.setSuccess(false);
                return;
            }
            if (activity.getBackStartime() == null) {
                message.setMsg("返校开始时间不能为空");
                message.setSuccess(false);
                return;
            }
            if (activity.getBackEndtime() == null) {
                message.setMsg("返校结束时间不能为空");
                message.setSuccess(false);
                return;
            }
            Date startTime = activity.getBackStartime();
            Date endTime = activity.getBackEndtime();


            if (startTime.getTime() < TimeZoneUtils.getDate().getTime()) {
                message.setMsg("返校时间不能早于当前时间");
                message.setSuccess(false);
                return;
            }

            if (startTime.getTime() > endTime.getTime()) {
                message.setMsg("开始时间不能晚于结束时间");
                message.setSuccess(false);
                return;
            }

            if (StringUtils.isBlank(activity.getContactPerson())) {
                message.setMsg("联系人不能为空");
                message.setSuccess(false);
                return;
            }
            if (StringUtils.isBlank(activity.getContactPhone())) {
                message.setMsg("联系电话不能为空");
                message.setSuccess(false);
                return;
            }

            if(activity.isNeedMeeting()){
                if (activity.getMeetingTime() ==null) {
                    message.setMsg("场地借用时间不能为空");
                    message.setSuccess(false);
                    return;
                }
                if (activity.getMeetingEndTime() ==null) {
                    message.setMsg("归还场地时间不能为空");
                    message.setSuccess(false);
                    return;
                }

                Date start = activity.getMeetingTime();
                if (start.getTime() < TimeZoneUtils.getDate().getTime()) {
                    message.setMsg("场地借用时间不能早于当前时间");
                    message.setSuccess(false);
                    return;
                }
                Date end = activity.getMeetingEndTime();
                if (start.getTime() > end.getTime()) {
                    message.setMsg("场地借用时间不能晚于归还时间");
                    message.setSuccess(false);
                    return;
                }
            }

            if (StringUtils.isBlank(activity.getId())) {
                activity.preInsert();
                activityMapper.add(activity);
            } else {

                Activity tmp = activityMapper.getById(activity.getId());
                if (tmp != null) {
                    activity.preUpdate();
                    activityMapper.update(activity);
                } else {
                    message.init(false, "该聚会不存在", null);
                }
            }

            message.setMsg("已提返交校聚会申请");
            message.setSuccess(true);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 删除返校聚会接口
     *
     * @param message
     * @param content
     */
    public void deleteActivity(Message message, String content) {
        try {
            if (StringUtils.isBlank(content)) {
                message.setMsg("未传入参数");
                message.setSuccess(false);
                return;
            }
            Map<String, String> map = JSON.parseObject(content, Map.class);
            String id = map.get("id");
            String userId = map.get("userId");

            if (StringUtils.isBlank(id)) {
                message.setMsg("ID不能为空");
                message.setSuccess(false);
                return;
            }

            if (StringUtils.isBlank(userId)) {
                message.setMsg("用户ID不能为空");
                message.setSuccess(false);
                return;
            }
            Activity activity = getById(id);
            if(activity !=null){
                UserProfile userProfile = userProfileMapper.selectByAccountNum(activity.getUserId());
                if (userProfile == null) {
                    message.init(false, "用户不存在", null);
                    return;
                }

            }
            if (activity == null) {
                message.init(false, "不存在的返校聚会", null);
                return;
            }

            if (!activity.getUserId().equals(activity.getUserId())) {
                message.init(false, "该聚会不是您创建的", null);
                return;
            }

            List<Long> list = new ArrayList<Long>();

            list.add(Long.parseLong(id));

            activityMapper.delete(list);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取返校计划列表接口
     *
     * @param message
     * @param content
     */
    public void findActivityList(Message message, String content) {
        if (StringUtils.isBlank(content)) {
            message.setMsg("未传入参数");
            message.setSuccess(false);
            return;
        }
        Map<String, Object> map = JSON.parseObject(content, Map.class);
        String page = (String) map.get("page");
        String rows = (String) map.get("rows");
        String accountNum = (String) map.get("accountNum");
        if (StringUtils.isNotBlank(page) && StringUtils.isNotBlank(rows)) {
            int start = (Integer.valueOf(page) - 1) * Integer.valueOf(rows);
            map.put("start", start);
            map.put("rows", Integer.valueOf(rows));
        } else {
            map.put("isNotLimit", "1");
        }

        if(StringUtils.isBlank(accountNum)){
            map.put("status", 20);
        }

        long total = activityMapper.count(map);
        List<Activity> list = activityMapper.query(map);
        DataGrid<Activity> dataGrid = new DataGrid<>();
        dataGrid.setRows(list);
        dataGrid.setTotal(total);

        message.init(true, "查询成功", dataGrid);

    }

    /**
     * 获取返校聚会详情接口
     *
     * @param message
     * @param content
     */
    public void getActivityById(Message message, String content){

        if(StringUtils.isBlank(content)){
            message.setMsg("未传入参数");
            message.setSuccess(false);
            return;
        }
        Map<String, String> map = JSON.parseObject(content, Map.class);
        String id = map.get("id");
        String accountNum = map.get("accountNum");
        if(StringUtils.isBlank(map.get("id"))){
            message.setMsg("聚会Id为空");
            message.setSuccess(false);
            return;
        }

        Activity activity = activityMapper.getById(id);

        if(activity == null){
            message.setMsg("聚会不存在");
            message.setSuccess(false);
            return;
        }else{
            if(StringUtils.isNotBlank(accountNum))
            {
                if(accountNum.equals(activity.getUserId()))
                {
                    activity.setSignStatus("2");
                }
                else
                {
                    Map<String, Object> searchMap = new HashMap<>();
                    searchMap.put("isNoLimit", "1");
                    searchMap.put("accountNum", accountNum);
                    searchMap.put("activityId", id);
                    long total = backschoolOnlineSignMapper.count(searchMap);
                    if(total > 0){
                        activity.setSignStatus("1");
                    }else{
                        activity.setSignStatus("0");
                    }
                }
            }
            else
            {
                activity.setSignStatus("0");
            }
            message.init(true, "获取聚会详情成功", activity);

        }


    }
}
