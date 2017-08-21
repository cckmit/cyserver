package com.cy.core.weiXin.service;

import com.alibaba.fastjson.JSON;
import com.cy.base.entity.Message;
import com.cy.common.utils.StringUtils;
import com.cy.core.news.entity.News;
import com.cy.core.news.service.NewsService;
import com.cy.core.weiXin.dao.WeiXinAccountMapper;
import com.cy.core.weiXin.entity.WeiXinAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by niu on 2016/12/26.
 */
@Service("weiXinAccountService")
public class WeiXinAccountServiceImpl implements WeiXinAccountService {

    @Autowired
    private WeiXinAccountMapper weiXinAccountMapper;

    @Autowired
    private NewsService newsService;

    public WeiXinAccount selectById(String accountId){
        return weiXinAccountMapper.selectById(accountId);
    }

    public void save(WeiXinAccount weiXinAccount){
        if (StringUtils.isBlank(weiXinAccount.getId())){
            weiXinAccount.preInsert();
            weiXinAccountMapper.insert(weiXinAccount);
        }else {
            weiXinAccount.preUpdate();
            weiXinAccountMapper.update(weiXinAccount);
        }
    }

    /**
     * 通过map添加条件（accountType）获取公众号列表
     * @param map
     * @return
     */
    public List<WeiXinAccount> getList(Map<String,Object> map){
        List<WeiXinAccount> weiXinAccountList = weiXinAccountMapper.selectList(map);
        Map<String,Object> map1 = new HashMap<String,Object>();
        //关联新闻
        for (WeiXinAccount weiXinAccount:weiXinAccountList){
            if (weiXinAccount !=null && StringUtils.isNotBlank(weiXinAccount.getNewsId())){
                News news = newsService.selectById(Long.parseLong(weiXinAccount.getNewsId()));
                if (news != null ) {
                    weiXinAccount.setNews(news);
                }
            }
        }
        return weiXinAccountList;
    }


    /***********************************************************************
     * 【分享】相关API（以下区域）
     * <p>
     * 注意事项：
     * 1、方法名-格式要求
     * 创建方法：save[Name]
     * 撤销方法：remove[Name]
     * 查询分页列表方法：find[Name]ListPage
     * 查询列表方法：find[Name]List
     * 查询详细方法：find[Name]
     ***********************************************************************/

    /**
     * 通过accountType获取公众号配置详情接口
     * @param message
     * @param content
     * @return
     */
    public void findWeiXinAccountSetting(Message message, String content){

            if (StringUtils.isBlank(content)) {
                message.setMsg("未传入参数");
                message.setSuccess(false);
                return;
            }

            Map<String,Object> map = JSON.parseObject(content,Map.class);
            String accoutType = (String) map.get("accountType");
            String appId = (String) map.get("appId");
            if (StringUtils.isBlank(accoutType) && StringUtils.isBlank(appId)){
                message.init(false,"公众号类型和appId不能都为空",null);
                return;
            }
            List<WeiXinAccount> weiXinAccountList = getList(map);
            WeiXinAccount weiXinAccount = new WeiXinAccount();
            if (weiXinAccountList !=null && weiXinAccountList.size()>0){
                weiXinAccount = weiXinAccountList.get(0);
            }
            message.init(true,"查询成功",weiXinAccount);
    }
}
