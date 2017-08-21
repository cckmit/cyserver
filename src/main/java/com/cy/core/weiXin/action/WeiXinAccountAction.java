package com.cy.core.weiXin.action;

import com.cy.base.action.AdminBaseAction;
import com.cy.base.action.BaseAction;
import com.cy.base.entity.Message;
import com.cy.common.utils.StringUtils;
import com.cy.core.weiXin.entity.WeiXinAccount;
import com.cy.core.weiXin.service.WeiXinAccountService;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by niu on 2016/12/26.
 */

@Namespace("/weiXin")
@Action("weiXinAccountAction")
public class WeiXinAccountAction extends AdminBaseAction {

    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(WeiXinAccount.class);

    private WeiXinAccount weiXinAccount;

    public WeiXinAccount getWeiXinAccount() {
        return weiXinAccount;
    }

    public void setWeiXinAccount(WeiXinAccount weiXinAccount) {
        this.weiXinAccount = weiXinAccount;
    }

    @Autowired
    private WeiXinAccountService weiXinAccountService;


    //得到公众号列表
    public void doNotNeedSessionAndSecurity_getList() {
        Map<String, Object> map = new HashMap<String, Object>();
        List<WeiXinAccount> weiXinAccountList = weiXinAccountService.getList(map);
        super.writeJson(weiXinAccountList);
    }

    /**
     * 公众号配置页面数据初始化
     */
    public void initSetting(){
        String accountType = getRequest().getParameter("accountType");
        Map<String,Object> map = new HashMap<String,Object>();
        //添加查询条件公众号类型
        map.put("accountType",accountType);
        List<WeiXinAccount> weiXinAccountList = weiXinAccountService.getList(map);
        if (weiXinAccountList !=null && weiXinAccountList.size()>0){
           //只有一条公众号配置信息，默认第一条
            weiXinAccount = weiXinAccountList.get(0);
        }else {
            weiXinAccount = new WeiXinAccount();
        }
        super.writeJson(weiXinAccount);
    }

    /**
     * 保存公众号信息
     */
    public void save(){
        Message message = new Message();
        try{
            if (StringUtils.isNotBlank(weiXinAccount.getAccountType())){
                Map<String,Object> map = new HashMap<String,Object>();
                map.put("accountType",weiXinAccount.getAccountType());
                List<WeiXinAccount> weiXinAccountList = weiXinAccountService.getList(map);
                if (weiXinAccountList !=null && weiXinAccountList.size()>0 && weiXinAccountList.get(0) != null){
                    weiXinAccount.setId( weiXinAccountList.get(0).getId());
                }
                weiXinAccountService.save(weiXinAccount);
                message.init(true,"保存成功",null);
            }else {
                message.init(false,"保存失败:公众号类型为空",null);
            }
        }catch (Exception e){
            logger.error(e);
            message.init(false,"保存失败",null);
        }
        super.writeJson(message);
    }
}

