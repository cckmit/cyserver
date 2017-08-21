package com.cy.core.activityMusic.service;

import com.cy.base.entity.DataGrid;
import com.cy.core.activityMusic.dao.ActivityMusicMapper;
import com.cy.core.activityMusic.entity.ActivityMusic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 活动音乐业务实现类
 *
 * @author niu
 * @create 2017-06-06 上午 10:38
 **/
@Service("activityMusicService")
public class ActivityMusicServiceImpl implements ActivityMusicService {

    @Autowired
    private ActivityMusicMapper activityMusicMapper;

    @Override
    public List<ActivityMusic> findList(Map<String, Object> map) {
        List<ActivityMusic> list = activityMusicMapper.selectList(map);
        return list;
    }

    @Override
    public Long findCount(Map<String, Object> map) {
        Long l = activityMusicMapper.selectCount(map);
        return l;
    }

    @Override
    public DataGrid<ActivityMusic> dataGrid(Map<String, Object> map) {
        DataGrid<ActivityMusic> dataGrid = new DataGrid<ActivityMusic>();
        long total = activityMusicMapper.selectCount(map);
        dataGrid.setTotal(total);
        int start = ((Integer) map.get("page") - 1) * (Integer) map.get("rows");
        map.put("start", start);
        List<ActivityMusic> list = activityMusicMapper.selectList(map);
        dataGrid.setRows(list);
        return dataGrid;
    }

    @Override
    public List<ActivityMusic> activityMusicList() {
        List<ActivityMusic> list = activityMusicMapper.selectAll();
        return list;
    }

    @Override
    public ActivityMusic getById(String id) {
        ActivityMusic activityMusic = activityMusicMapper.selectById(id);
        return activityMusic;
    }

    @Override
    @Transactional(readOnly = false)
    public ActivityMusic save(ActivityMusic activityMusic) {
        activityMusic.preInsert();
        activityMusicMapper.insert(activityMusic);
        return activityMusic;
    }

    @Override
    public ActivityMusic update(ActivityMusic activityMusic) {
        activityMusic.preUpdate();
        activityMusicMapper.update(activityMusic);
        return activityMusic;
    }

    @Override
    public void delete(String ids) {
        String[] array = ids.split(",");
        List<String> list = new ArrayList<>();
        for (String id : array){
            list.add(id);
        }
        activityMusicMapper.delete(list);
    }

    @Override
    public void deleteByActivityId(String activityId) {
        activityMusicMapper.deleteByActivityId(activityId);
    }
}
