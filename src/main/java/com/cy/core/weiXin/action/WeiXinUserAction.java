package com.cy.core.weiXin.action;

import com.cy.base.action.AdminBaseAction;
import com.cy.base.entity.DataGrid;
import com.cy.base.entity.Message;
import com.cy.common.utils.StringUtils;
import com.cy.core.weiXin.entity.WeiXinUser;
import com.cy.core.weiXin.service.WeiXinUserService;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by WangAoHui on 2017/1/3.
 */
@Namespace("/weiXin")
@Action(value = "weiXinUserAction")
public class WeiXinUserAction extends AdminBaseAction {
    private static final Logger logger = Logger.getLogger(WeiXinUserAction.class);

    private WeiXinUser weiXinUser;
    @Autowired
    private WeiXinUserService weiXinUserService;

    private String weiXinUserId;


    //得到列表并且分页
    public void dataGraid() {
        try{
            Map<String, Object> map = new HashMap<String, Object>();
            if(weiXinUser !=null){
                String a = weiXinUser.getNickname();
                map.put("nickname",a);
            }
            map.put("rows", rows);
            map.put("page", page);
            DataGrid<WeiXinUser> data = weiXinUserService.dataGrid(map);
            super.writeJson(data);
        }catch (Exception e){
            logger.error(e, e);

        }


    }
    //新增用户信息
    public void saveAssociation(){
        Message message=new Message();
        try {
            weiXinUserService.save(weiXinUser);


        } catch (Exception e) {
            logger.error(e, e);
            message.setMsg("新增失败");
            message.setSuccess(false);
        }
        super.writeJson(message);
    }

    //修改用户信息
    public void updateAssociation(){
        Message message=new Message();
        try {
            weiXinUserService.update(weiXinUser);
            message.setMsg("修改成功");
            message.setSuccess(true);

        } catch (Exception e) {
            logger.error(e, e);
            message.setMsg("修改失败");
            message.setSuccess(false);
        }
        super.writeJson(message);
    }
    //查看详情
    public void getById(){
        Message message=new Message();
        try{
            WeiXinUser weiXinUser=weiXinUserService.getById(weiXinUserId);
            super.writeJson(weiXinUser);
        }catch (Exception e){
            logger.error(e,e);
        }

    }

    /**
     * @author niu
     * @date 2017-1-14
     * @description 解除微信用户和后台用户绑定
     */
    public void removeBinding(){
        Message message = new Message();
        if (StringUtils.isNotBlank(weiXinUserId)){
            WeiXinUser weiXinUser = weiXinUserService.getById(weiXinUserId);
            if (weiXinUser != null){
                //设置要接触绑定标志
                weiXinUser.setAccountNum(null);
                weiXinUser.setIsRemoveBinding("1");
                weiXinUserService.update(weiXinUser);
                message.init(true,"已解除绑定",null);
            }else {
                message.init(false,"解除绑定失败:该用户不存在",null);
            }
        }else {
            message.init(false,"解除绑定失败:用户编号为空",null);
        }
        super.writeJson(message);
    }


    public WeiXinUser getWeiXinUser() {
        return weiXinUser;
    }

    public void setWeiXinUser(WeiXinUser weiXinUser) {
        this.weiXinUser = weiXinUser;
    }

    public WeiXinUserService getWeiXinUserService() {
        return weiXinUserService;
    }

    public void setWeiXinUserService(WeiXinUserService weiXinUserService) {
        this.weiXinUserService = weiXinUserService;
    }

    public String getWeiXinUserId() {
        return weiXinUserId;
    }

    public void setWeiXinUserId(String weiXinUserId) {
        this.weiXinUserId = weiXinUserId;
    }
}
