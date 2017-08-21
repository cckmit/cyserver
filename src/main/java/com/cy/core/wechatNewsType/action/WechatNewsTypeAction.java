package com.cy.core.wechatNewsType.action;

import com.cy.base.action.AdminBaseAction;
import com.cy.base.entity.DataGrid;
import com.cy.base.entity.Message;
import com.cy.core.user.entity.User;
import com.cy.core.wechatNewsType.entity.WechatNewsType;
import com.cy.core.wechatNewsType.service.WechatNewsTypeService;
import com.cy.util.UserUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>Title: WechatNewsTypeAction</p>
 * <p>Description: </p>
 * USER jiangling
 * DATE 8/9/16
 * To change this template use File | Settings |File Templates.
 */
@Namespace("/wechatNewsType")
@Action(value="wechatNewsTypeAction")
public class WechatNewsTypeAction extends AdminBaseAction {

    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(WechatNewsType.class);

    @Autowired
    private WechatNewsTypeService wechatNewsTypeService;

    private WechatNewsType wechatNewsType;

    private String weId;

    public String getWeId() {
        return weId;
    }

    public void setWeId(String weId) {
        this.weId = weId;
    }

    public WechatNewsType getWechatNewsType() {
        return wechatNewsType;
    }

    public void setWechatNewsType(WechatNewsType wechatNewsType) {
        this.wechatNewsType = wechatNewsType;
    }

    /*public void dataGrid() {
        //没有条件查询
        //在cy_wechat_news_type查询所有的微信新闻,并且分页展示
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("rows",rows);
        map.put("page",page);
        //添加查询条件
        //微信新闻标题
        if (wechatNewsType != null) {
            map.put("title", wechatNewsType.getName());
        }
        //查询时间

        DataGrid<WechatNewsType> data = wechatNewsTypeService.dataGrid(map);

        //按条件查询

        super.writeJson(data);

    }*/
    /**--WeChat新闻类型管理--**/
    public void treeGrid() {
        if(wechatNewsType == null){
            wechatNewsType = new WechatNewsType();
        }
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("name", wechatNewsType.getName());
        map.put("type", wechatNewsType.getType());
        map.put("origin", wechatNewsType.getOrigin());

        User user = UserUtils.getUser();
        if(user != null && user.getDeptId() > 0) {
            map.put("deptId",user.getDeptId());
        }

        List<WechatNewsType> type = new ArrayList<WechatNewsType>();
        type = wechatNewsTypeService.wechatNewsTypes(map);

        super.writeJson(type);
    }

    /**--WeChat新闻类型添加--**/
    public void saveWeChatNewsType(){
        Message message = new Message();
        if(getUser().getDeptId() > 0){
            wechatNewsType.setDeptId(Long.toString(getUser().getDeptId()));
        }else{
            wechatNewsType.setDeptId("");
        }
        wechatNewsTypeService.save(wechatNewsType);
        message.setMsg("添加成功");
        message.setSuccess(true);
        super.writeJson(message);
    }

    /**--WeChat新闻栏目删除--**/
    public void deleteWeChatNewsType(){
        Message message = new Message();
        wechatNewsTypeService.delete(weId);
        message.setSuccess(true);
        message.setMsg("删除成功");
        super.writeJson(message);
    }

    /**--WeChat新闻栏目查看--**/
    public void getWeChatNewsType(){
        super.writeJson(wechatNewsTypeService.getById(Long.toString(wechatNewsType.getId())));
    }

    /**--WeChat新闻栏目编辑--**/
    public void updateWeChatNewsType(){
        Message message = new Message();
        if(getUser().getDeptId()>0){
            wechatNewsType.setDeptId(Long.toString(getUser().getDeptId()));
        }else{
            wechatNewsType.setDeptId("");
        }
        if(wechatNewsType.getOrigin() == null){
            wechatNewsType.setOrigin("1");
        }
        wechatNewsTypeService.update(wechatNewsType);
        message.setSuccess(true);
        message.setMsg("更新成功");
        super.writeJson(message);
    }
}
