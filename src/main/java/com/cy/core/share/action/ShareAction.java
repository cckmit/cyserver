package com.cy.core.share.action;

import com.cy.base.action.AdminBaseAction;

import com.cy.base.entity.Message;
import com.cy.common.utils.StringUtils;
import com.cy.core.share.entity.Share;
import com.cy.core.share.service.ShareService;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by niu on 2016/12/22.
 */
@Namespace("/share")
@Action("shareAction")
public class ShareAction extends AdminBaseAction {

    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(ShareAction.class);



    private Share share;

    public Share getShare() {
        return share;
    }

    public void setShare(Share share) {
        this.share = share;
    }

    @Resource
    private ShareService shareService;

    public void initShare() {
        List<Share> list = shareService.findList(new Share());
        if (list != null && list.size() > 0 ) {
            share = list.get(0);
        } else {
            share = new Share();
        }
        super.writeJson(share);
    }

    public void save() {
        Message message = new Message();
        try {
            //查询分享配置，是否存在
            List<Share> list = shareService.findList(new Share());
            if (list != null && list.size() > 0 && list.get(0) != null) {
                share.setId(list.get(0).getId());
            }
            shareService.save(share);

            message.setMsg("保存成功");
            message.setSuccess(true);
        } catch (Exception e) {
            logger.error(e, e);
            message.setMsg("保存失败");
            message.setSuccess(false);
        }
        super.writeJson(message);
    }

}
