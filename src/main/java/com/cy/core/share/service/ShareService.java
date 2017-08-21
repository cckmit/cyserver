package com.cy.core.share.service;

import com.cy.base.entity.DataGrid;
import com.cy.base.entity.Message;
import com.cy.core.share.entity.Share;

import java.util.List;
import java.util.Map;

/**
 * Created by niu on 2016/12/22.
 */
public interface ShareService {

    /**
     * 保存分享信息
     * @param share
     */
    void save(Share share);

    /**
     * 根据id查询分享信息
     * @param id
     * @return
     */
    Share selectById(String id);

    /**
     * 根据分享信息过滤，得到分享列表（不加条件查询所有）
     * @param share
     * @return
     */
    List<Share> findList(Share share);

    /**
     * 获取分享配置信息接口
     * @param message
     * @param content
     */
    void findShareSetting(Message message,String content);

    void updateAndroidDownloads();

}
