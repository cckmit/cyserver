package com.cy.core.activityWinning.service;

import com.alibaba.fastjson.JSON;
import com.cy.base.entity.DataGrid;
import com.cy.base.entity.Message;
import com.cy.common.utils.StringUtils;
import com.cy.core.activityApplicant.dao.ActivityApplicantMapper;
import com.cy.core.activityMusic.dao.ActivityMusicMapper;
import com.cy.core.activityMusic.entity.ActivityMusic;
import com.cy.core.activityWinning.dao.ActivityWinningMapper;
import com.cy.core.activityWinning.entity.ActivityWinning;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by hp on 2017/6/7.
 */
@Service("activityWinningService")
public class ActivityWinningServiceImpl implements ActivityWinningService {

    @Autowired
    private ActivityWinningMapper activityWinningMapper;

    @Autowired
    private ActivityApplicantMapper activityApplicantMapper;

    @Override
    public List<ActivityWinning> findList(Map<String, Object> map) {
        List<ActivityWinning> list = activityWinningMapper.selectList(map);
        return list;
    }

    @Override
    public Long findCount(Map<String, Object> map) {
        Long l = activityWinningMapper.selectCount(map);
        return l;
    }

    @Override
    public DataGrid<ActivityWinning> dataGrid(Map<String, Object> map) {
        DataGrid<ActivityWinning> dataGrid = new DataGrid<ActivityWinning>();
        long total = activityWinningMapper.selectCount(map);
        dataGrid.setTotal(total);
        int start = ((Integer) map.get("page") - 1) * (Integer) map.get("rows");
        map.put("start", start);
        List<ActivityWinning> list = activityWinningMapper.selectList(map);
        dataGrid.setRows(list);
        return dataGrid;
    }

    @Override
    public List<ActivityWinning> activityWinningList() {
        List<ActivityWinning> list = activityWinningMapper.selectAll();
        return list;
    }

    @Override
    public ActivityWinning getById(String id) {
        ActivityWinning activityMusic = activityWinningMapper.selectById(id);
        return activityMusic;
    }

    @Override
    @Transactional(readOnly = false)
    public ActivityWinning save(ActivityWinning activityWinning) {
        activityWinning.preInsert();
        activityWinningMapper.insert(activityWinning);
        return activityWinning;
    }

    @Override
    public ActivityWinning update(ActivityWinning activityWinning) {
        activityWinning.preUpdate();
        activityWinningMapper.update(activityWinning);
        return activityWinning;
    }

    @Override
    public void delete(String ids) {
        String[] array = ids.split(",");
        List<String> list = new ArrayList<>();
        for (String id : array){
            list.add(id);
        }
        activityWinningMapper.delete(list);
    }

    @Override
    public void winning(Message message, String content) {
        if (StringUtils.isBlank(content)) {
            message.setMsg("未传入参数");
            message.setSuccess(false);
            return;
        }
        ActivityWinning activityWinning = JSON.parseObject(content,ActivityWinning.class);

        if (StringUtils.isBlank(activityWinning.getActivityId())){
            message.init(false,"活动编号不能为空",null);
        }
        if (StringUtils.isBlank(activityWinning.getApplicantId())){
            message.init(false,"中奖人编号不能为空",null);
        }
        if (StringUtils.isBlank(activityWinning.getAwardsId())){
            message.init(false,"奖项编号不能为空",null);
        }

        activityWinning.preInsert();
        activityWinningMapper.insert(activityWinning);
        //更新报名人中奖状态为已中奖
        activityApplicantMapper.updateIsWinning(activityWinning.getApplicantId());

        message.init(true,"成功",null);
    }

    @Override
    public void winningList(Message message, String content) {
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
        List<ActivityWinning> activityWinningList = activityWinningMapper.selectList(map);

        message.init(true,"成功",activityWinningList);
    }
}
