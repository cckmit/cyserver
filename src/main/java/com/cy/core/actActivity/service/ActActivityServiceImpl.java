package com.cy.core.actActivity.service;

import com.alibaba.fastjson.JSON;
import com.cy.base.entity.DataGrid;
import com.cy.base.entity.Message;
import com.cy.common.utils.StringUtils;
import com.cy.core.actActivity.dao.ActActivityMapper;
import com.cy.core.actActivity.entity.ActActivity;
import com.cy.core.activityApplicant.dao.ActivityApplicantMapper;
import com.cy.core.activityMusic.dao.ActivityMusicMapper;
import com.cy.core.activityMusic.entity.ActivityMusic;
import com.cy.core.activityPrize.dao.ActivityPrizeMapper;
import com.cy.core.activityPrize.entity.ActivityPrize;
import com.cy.core.activityPrize.service.ActivityPrizeService;
import com.cy.core.activityWinning.dao.ActivityWinningMapper;
import com.cy.core.share.dao.FileMapper;
import com.cy.core.share.entity.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 抽奖活动业务实现类
 *
 * @author niu
 * @create 2017-06-06 上午 10:24
 **/
@Service("actActivityService")
public class ActActivityServiceImpl implements ActActivityService {

    @Autowired
    private ActActivityMapper actActivityMapper;

    @Autowired
    private FileMapper fileMapper;

    @Autowired
    private ActivityMusicMapper activityMusicMapper;

    @Autowired
    private ActivityPrizeMapper activityPrizeMapper;

    @Autowired
    private ActivityApplicantMapper activityApplicantMapper;

    @Autowired
    private ActivityWinningMapper activityWinningMapper;

    @Override
    public List<ActActivity> findList(Map<String, Object> map) {
        List<ActActivity> list = actActivityMapper.selectList(map);
        return list;
    }

    @Override
    public Long findCount(Map<String, Object> map) {
        Long l = actActivityMapper.selectCount(map);
        return l;
    }

    @Override
    public DataGrid<ActActivity> dataGrid(Map<String, Object> map) {
        DataGrid<ActActivity> dataGrid = new DataGrid<ActActivity>();
        long total = actActivityMapper.selectCount(map);
        dataGrid.setTotal(total);
        int start = ((Integer) map.get("page") - 1) * (Integer) map.get("rows");
        map.put("start", start);
        List<ActActivity> list = actActivityMapper.selectList(map);
        long l = new Date().getTime();
        /*for(ActActivity act:list){
            long startTime = act.getStartTime().getTime();
            long endTime = act.getEndTime().getTime();
            if(l<startTime){
                act.setStatus("未开始");
            }else if(l>startTime && l<endTime){
                act.setStatus("进行中");
            }else if(l>endTime){
                act.setStatus("已结束");
            }
        }*/
        dataGrid.setRows(list);
        return dataGrid;
    }

    @Override
    public List<ActActivity> activityList() {
        List<ActActivity> list = actActivityMapper.selectAll();
        return list;
    }

    @Override
    public ActActivity getById(String id) {
        ActActivity activity = actActivityMapper.selectById(id);
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("fileGroup",activity.getId());
        map.put("bussType","100");
        List<File> fileList = fileMapper.selectList(map);
        activity.setFileList(fileList);
        map.put("activityId",activity.getId());
        List<ActivityMusic> activityMusicList = activityMusicMapper.selectList(map);
        List<ActivityPrize> activityPrizeList = activityPrizeMapper.selectList(map);
        activity.setActivityMusicList(activityMusicList);
        activity.setActivityPrizeList(activityPrizeList);
        return activity;
    }

    @Override
    @Transactional(readOnly = false)
    public ActActivity save(ActActivity activity,String musics) {
        activity.preInsert();
        actActivityMapper.insert(activity);
        saveFile(activity);
        saveMusics(musics,activity);
        return activity;
    }

    @Override
    public ActActivity update(ActActivity activity,String musics) {
        activity.preUpdate();
        actActivityMapper.update(activity);
        saveFile(activity);
        activityMusicMapper.deleteByActivityId(activity.getId());
        saveMusics(musics,activity);
        return activity;
    }

    @Override
    public void delete(String ids) {
        String[] array = ids.split(",");
        List<String> list = new ArrayList<>();
        for (String id : array){
            list.add(id);
        }
        actActivityMapper.delete(list);
    }



    public void saveFile(ActActivity activity){
        if (StringUtils.isNotBlank(activity.getPictureUrls())) {
            //删除该分享关联的图片
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("fileGroup",activity.getId());
            map.put("bussType","100");
            fileMapper.delete(map);
            //保存改分享所关联的图片
            String[] urls = activity.getPictureUrls().split(",");
            for (String url : urls) {
                File file = new File();
                file.setFileGroup(activity.getId());
                file.setBussType("100");
                file.setPicUrl(url);
                file.preInsert();
                fileMapper.insert(file);
            }
        }
    }

    /**
     * 方法activityDetail 的功能描述：抽奖活动详情接口
     * @createAuthor niu
     * @createDate 2017-06-07 09:00:39
     * @param message
     * @param content
     * @return void
     * @throw
     *
     */
    @Override
    public void activityDetail(Message message, String content) {

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

        ActActivity actActivity = actActivityMapper.selectById(activityId);

        Map<String,Object> fileMap = new HashMap<String,Object>();
        fileMap.put("fileGroup",activityId);
        fileMap.put("bussType","100");
        List<File> fileList = fileMapper.selectList(fileMap);
        actActivity.setFileList(fileList);

        List<ActivityMusic> activityMusicList = activityMusicMapper.selectList(map);

        actActivity.setActivityMusicList(activityMusicList);

        List<ActivityPrize> activityPrizeList = activityPrizeMapper.selectList(map);

        actActivity.setActivityPrizeList(activityPrizeList);


        message.init(true,"获取成功",actActivity);

    }

    public void saveMusics(String musics,ActActivity activity){
        if(musics!=null && !musics.trim().equals("")){
            String[] list = musics.split("%,&");
            for(String mu:list){
                String[] mul = mu.split("%&:");
                ActivityMusic activityMusic = new ActivityMusic();
                activityMusic.setActivityId(activity.getId());
                activityMusic.setType(mul[0]);
                activityMusic.setIsRepeatPlay(mul[1]);
                activityMusic.setFilePath(mul[2]);
                activityMusic.preInsert();
                activityMusicMapper.insert(activityMusic);
            }
        }
    }


}
