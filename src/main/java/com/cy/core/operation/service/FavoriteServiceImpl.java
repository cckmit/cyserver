package com.cy.core.operation.service;

import com.alibaba.fastjson.JSON;
import com.cy.base.entity.Message;
import com.cy.common.utils.StringUtils;
import com.cy.core.association.dao.AssociationMapper;
import com.cy.core.association.entity.Association;
import com.cy.core.event.dao.EventMapper;
import com.cy.core.operation.dao.FavoriteMapper;
import com.cy.core.operation.entity.Favorite;
import com.cy.core.schoolServ.dao.SchoolServMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by niu on 2016/12/27.
 */
@Service("favoriteService")
public class FavoriteServiceImpl implements FavoriteService {

    @Autowired
    private FavoriteMapper favoriteMapper;

    @Autowired
    private EventMapper eventMapper;

    @Autowired
    private AssociationMapper associationMapper;

    @Autowired
    private SchoolServMapper schoolServMapper;


    /***********************************************************************
     * 【收藏】相关API（以下区域）
     * <p>
     * 注意事项：
     * 1、方法名-格式要求
     * 创建方法：save[Name]
     * 撤销方法：remove[Name]
     * 查询分页列表方法：find[Name]ListPage
     * 查询列表方法：find[Name]List
     * 查询详细方法：find[Name]
     ***********************************************************************/

    @Override
    public void findMyFavoriteList(Message message, String content) {
        if (StringUtils.isBlank(content)) {
            message.init(false, "未传入参数", null);
            return;
        }
        Favorite favorite = JSON.parseObject(content, Favorite.class);
        if (StringUtils.isBlank(favorite.getUserId())) {
            message.init(false, "用户id为空", null);
            return;
        }
        List<Favorite> favoriteList = favoriteMapper.selectList(favorite);
        if (favoriteList != null && favoriteList.size() > 0) {
            for (Favorite favorite1 : favoriteList) {
                if (favorite1 != null && StringUtils.isNotBlank(favorite1.getBussType())) {
                    switch (favorite1.getBussType()) {
                        case "10":
                            favorite1.setBuss(eventMapper.getById(favorite1.getBussId()));
                            break;
                        case "20":
                            favorite1.setBuss(associationMapper.getAssociationById(favorite1.getBussId()));
                            break;
                        case "30":
                            favorite.setBuss(schoolServMapper.selectByFxjhId(favorite1.getBussId()));
                            break;
                        case "40":
                            //校友企业暂无
                            break;
                        case "50":
                            //校友产品暂无
                            break;
                        default:
                            favorite1.setBuss(null);
                    }
                }
            }
        }
        message.init(true, "查询成功", favoriteList);
    }

    @Override
    public void saveOrCancelFavorite(Message message, String content) {
        if (StringUtils.isBlank(content)) {
            message.init(false, "未传入参数", null);
            return;
        }
        Favorite favorite = JSON.parseObject(content, Favorite.class);
        if (StringUtils.isBlank(favorite.getUserId())) {
            message.init(false, "用户id为空", null);
            return;
        }
        if (StringUtils.isBlank(favorite.getBussId())) {
            message.init(false, "业务id为空", null);
            return;
        }
        if (StringUtils.isBlank(favorite.getBussType())) {
            message.init(false, "业务类型为空", null);
            return;
        }
        Map<String, String> map = new HashMap<String, String>();
        List<Favorite> favoriteList = favoriteMapper.selectList(favorite);
        //判断是否收藏
        if (favoriteList != null && favoriteList.size() > 0 && favoriteList.get(0) != null) {
            favoriteMapper.delete(favorite);
            map.put("code", "0");
            message.init(true, "取消收藏成功", map);
            return;
        } else {
            //返回状态码 1 表示收藏
            favorite.preInsert();
            favoriteMapper.insert(favorite);
            map.put("code", "1");
            message.init(true, "收藏成功", map);
            return;

        }
    }
}
