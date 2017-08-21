package com.cy.schedule;

import com.cy.core.cloudEnterprise.entity.CloudEnterprise;
import com.cy.core.cloudEnterprise.service.CloudEnterpriseService;
import com.cy.core.enterprise.service.EnterpriseService;
import com.cy.core.weiXin.service.WeiXinUserService;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;


/**
 * 校友企业定时器
 *
 * @author niu
 * @create 2017-07-27 下午 14:52
 **/

public class EnterpriseJob extends QuartzJobBean {

    private CloudEnterpriseService cloudEnterpriseService;
    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        cloudEnterpriseService.syncTeamToSchoolByQuartz();
//        cloudEnterpriseService.pushAuditFailByQuartz();
//        cloudEnterpriseService.findApplyEnterpriseByQuartz();
//        cloudEnterpriseService.findAuditEnterpriseByQuartz();
    }

    public CloudEnterpriseService getCloudEnterpriseService() {
        return cloudEnterpriseService;
    }

    public void setCloudEnterpriseService(CloudEnterpriseService cloudEnterpriseService) {
        this.cloudEnterpriseService = cloudEnterpriseService;
    }
}
