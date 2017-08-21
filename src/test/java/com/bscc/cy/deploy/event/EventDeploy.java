package com.bscc.cy.deploy.event;

import com.cy.common.utils.SpringContextHolder;
import com.cy.core.event.dao.EventMapper;
import com.cy.core.event.entity.Event;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>Title: EventDeploy</p>
 * <p>Description: 活动数据迁移处理</p>
 *
 * @author LiuZhen
 * @Company 博视创诚
 * @data 2016-09-06 16:41
 */
@Component("EventDeploy")
public class EventDeploy {

    private static EventMapper eventMapper = SpringContextHolder.getBean("EventMapper"); // 活动Mapper

    public static void main(String[] args) {
        List<Event> list = eventMapper.query(null) ;
        System.out.println("----> "+ list.size());
    }

}
