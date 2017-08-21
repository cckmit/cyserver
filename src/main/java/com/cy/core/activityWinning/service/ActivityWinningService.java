package com.cy.core.activityWinning.service;

import com.cy.base.entity.DataGrid;
import com.cy.base.entity.Message;
import com.cy.core.activityWinning.entity.ActivityWinning;

import java.util.List;
import java.util.Map;

/**
 * Created by hp on 2017/6/7.
 */
public interface ActivityWinningService {

    /**
     * 获取列表
     * @param map
     * @return
     */
    List<ActivityWinning> findList(Map<String, Object> map) ;
    /**
     * 获取总数
     * @param map
     * @return
     */
    Long findCount(Map<String, Object> map) ;

    DataGrid<ActivityWinning> dataGrid(Map<String, Object> map);

    List<ActivityWinning> activityWinningList();

    ActivityWinning getById(String id);
    ActivityWinning save(ActivityWinning activityWinning);
    ActivityWinning update(ActivityWinning activityWinning);
    void delete(String ids);

    //中奖接口
    void winning(Message message,String content);

    //中奖人列表
    void winningList(Message message,String content);
}
