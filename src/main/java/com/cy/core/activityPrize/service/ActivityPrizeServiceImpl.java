package com.cy.core.activityPrize.service;

import com.cy.base.entity.DataGrid;
import com.cy.common.utils.StringUtils;
import com.cy.core.activityPrize.dao.ActivityPrizeMapper;
import com.cy.core.activityPrize.entity.ActivityPrize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 奖项业务实现类
 *
 * @author niu
 * @create 2017-06-06 上午 10:46
 **/
@Service("activityPrizeService")
public class ActivityPrizeServiceImpl implements ActivityPrizeService {

    @Autowired
    private ActivityPrizeMapper activityPrizeMapper;

    public DataGrid<ActivityPrize> dataGrid(Map<String, Object> map) {
        DataGrid<ActivityPrize> dataGrid = new DataGrid<ActivityPrize>();
        long total = activityPrizeMapper.count(map);
        dataGrid.setTotal(total);
        int start = ((Integer) map.get("page") - 1) * (Integer) map.get("rows");
        map.put("start", start);
        map.put("isNotLimit",0);
        List<ActivityPrize> list = activityPrizeMapper.selectList(map);
        dataGrid.setRows(list);
        return dataGrid;
    }

    @Override
    public void save(ActivityPrize activityPrize) {
        if (StringUtils.isNotBlank(activityPrize.getId())){
            activityPrize.preUpdate();
            activityPrizeMapper.update(activityPrize);
        }else {
            activityPrize.preInsert();
            activityPrizeMapper.insert(activityPrize);
        }
    }

    @Override
    public ActivityPrize getById(String id) {
       return activityPrizeMapper.selectById(id);
    }

    public void delete(String ids) {
        String[] array = ids.split(",");
        List<String> list = new ArrayList<>();
        for (String id : array){
            list.add(id);
        }
        activityPrizeMapper.delete(list);
    }

}
