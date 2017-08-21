package com.cy.schedule;

import com.cy.core.weiXin.entity.WeiXinUser;
import com.cy.core.weiXin.service.WeiXinUserService;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * 微信公众号关注用户更新定时器
 *
 * @author niu
 * @create 2017-05-15 下午 17:19
 **/

public class WeChatUserJob extends QuartzJobBean {

    private WeiXinUserService weiXinUserService;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        weiXinUserService.updateLocalWeChatUser();
    }

    public WeiXinUserService getWeiXinUserService() {
        return weiXinUserService;
    }

    public void setWeiXinUserService(WeiXinUserService weiXinUserService) {
        this.weiXinUserService = weiXinUserService;
    }
}
