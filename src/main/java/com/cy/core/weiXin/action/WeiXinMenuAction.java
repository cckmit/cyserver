package com.cy.core.weiXin.action;

import com.alibaba.fastjson.JSON;
import com.cy.base.action.AdminBaseAction;
import com.cy.base.entity.Message;
import com.cy.common.utils.HttpclientUtils;
import com.cy.common.utils.wechat.entity.Button;
import com.cy.common.utils.wechat.entity.Menu;
import com.cy.core.weiXin.entity.WeiXinMenu;
import com.cy.core.weiXin.service.WeiXinMenuService;
import com.cy.core.weiXin.entity.WeiXinAccount;
import com.cy.core.weiXin.service.WeiXinAccountService;
import com.cy.smscloud.exception.NetServiceException;
import com.cy.system.Global;
import com.cy.util.PairUtil;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.NameValuePair;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * Created by cha0res on 12/13/16.
 */
@Namespace("/weiXin")
@Action(value = "weiXinMenuAction")
public class WeiXinMenuAction extends AdminBaseAction {
    private static final Logger logger = Logger.getLogger(WeiXinMenuAction.class);

    @Autowired
    private WeiXinMenuService weiXinMenuService;

    @Autowired
    private WeiXinAccountService weiXinAccountService;

    private WeiXinMenu weiXinMenu;
    private String weiXinMenuId;
    private String accountType;


    //获取菜单列表
    public void weiXinMenuTree() {

        Map<String, Object> map = new HashMap<String, Object>();

        if (weiXinMenu != null) {
            map.put("name", weiXinMenu.getName());
            map.put("accountId", weiXinMenu.getAccountId());
        }

        List<WeiXinMenu> list = weiXinMenuService.query(map);

        super.writeJson(list);
    }

    //获取菜单详情
    public void getById() {
        WeiXinMenu st = weiXinMenuService.getById(weiXinMenuId);

        super.writeJson(st);
    }

    //新增菜单

    /**
     * @update_author niu
     * @update_date 2017-1-6
     * @description 修改保存时判断每个公众号菜单名称和菜单唯一标识是否重复
     */
    public void save() {
        Message message = new Message();
        try {
            if (StringUtils.isNotBlank(weiXinMenu.getAccountId())) {
                Map<String, Object> map = new HashMap();
                map.put("accountId", weiXinMenu.getAccountId());
                List<WeiXinMenu> weiXinMenuList = weiXinMenuService.query(map);
                if (weiXinMenuList != null && weiXinMenuList.size() > 0 && weiXinMenuList.get(0) != null) {
                    for (WeiXinMenu weiXinMenu1:weiXinMenuList){

                            if (weiXinMenu.getMenuKey().equals(weiXinMenu1.getMenuKey())){
                                message.init(false,"菜单唯一标识重复",null);
                                super.writeJson(message);
                                return;
                            }

                    }
                }

                if ("0".equals(weiXinMenu.getParentId())) {
                    weiXinMenu.setParentIds("0");
                } else {
                    weiXinMenu.setParentIds("0," + weiXinMenu.getParentId());
                }
                weiXinMenuService.save(weiXinMenu);
                message.setMsg("新增成功");
                message.setSuccess(true);

            } else {
                message.setMsg("公众号为空");
                message.setSuccess(false);
            }
        } catch (Exception e) {
            logger.error(e, e);
            message.setMsg("操作失败");
            message.setSuccess(false);

        }
        super.writeJson(message);

    }

    //修改菜单
    /**
     * @update_author niu
     * @update_date 2017-1-6
     * @description 修改保存时判断每个公众号菜单名称和菜单唯一标识是否重复
     */
    public void update() {
        Message message = new Message();

        if (StringUtils.isNotBlank(weiXinMenu.getAccountId())) {
            Map<String, Object> map = new HashMap();
            map.put("accountId", weiXinMenu.getAccountId());
            List<WeiXinMenu> weiXinMenuList = weiXinMenuService.query(map);
            if (weiXinMenuList != null && weiXinMenuList.size() > 0 && weiXinMenuList.get(0) != null) {
                for (WeiXinMenu weiXinMenu1 : weiXinMenuList) {
                    if (!weiXinMenu.getId().equals(weiXinMenu1.getId())){

                        if (weiXinMenu.getMenuKey().equals(weiXinMenu1.getMenuKey())) {
                            message.init(false, "菜单唯一标识重复", null);
                            super.writeJson(message);
                            return;
                        }
                    }
                }
            }
            weiXinMenu.setIsNull("1");
            weiXinMenuService.update(weiXinMenu);
            message.setMsg("修改成功");
            message.setSuccess(true);

        }else {
            message.setMsg("公众号为空");
            message.setSuccess(false);
        }

        super.writeJson(message);

    }

    //删除菜单

    /**
     * @update_author niu
     * @update_date 2017-1-7
     * @description 修改删除时如果是一级菜单
     */
    public void delete() {

        Message message = new Message();
        if (StringUtils.isNotBlank(weiXinMenuId)) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("parentId", weiXinMenuId);
            WeiXinMenu st = weiXinMenuService.getById(weiXinMenuId);

            List<WeiXinMenu> weiXinMenuList = weiXinMenuService.query(map);
            if (weiXinMenuList !=null && weiXinMenuList.size()>0 && weiXinMenuList.get(0)!= null){
                for (WeiXinMenu weiXinMenu1: weiXinMenuList){
                    weiXinMenu1.setDelFlag("1");
                    weiXinMenuService.update(weiXinMenu1);
                }
            }
            st.setDelFlag("1");
            weiXinMenuService.update(st);
            message.setMsg("删除成功");
            message.setSuccess(true);
        }else {
            message.setMsg("删除菜单编号为空");
            message.setSuccess(false);
        }
        super.writeJson(message);
    }

    /**
     * @author niu
     * @description 同步微信菜单到微信
     */
    public void toWeiXinMenu() {
        Message message = new Message();
        if (StringUtils.isNotBlank(accountType)) {
            Map<String, Object> map = new HashMap<>();
            map.put("accountType", accountType);
            //通过公众号类型获取公众号
            List<WeiXinAccount> weiXinAccountList = weiXinAccountService.getList(map);
            if (weiXinAccountList != null && weiXinAccountList.size() > 0 && weiXinAccountList.get(0) != null) {
                if (StringUtils.isBlank(weiXinAccountList.get(0).getAccountAppId())) {
                    message.init(false, "该公众号的appId为空", null);
                    super.writeJson(message);
                    return;
                }
                String paramsJson = null;

                Map<String, Object> map1 = new HashMap<>();
                map1.put("accountAppId", weiXinAccountList.get(0).getAccountAppId());
                map1.put("parentId", "0");
                //通过公众号appId和菜单父级编号得到菜单数组
                PairUtil<String, Button[]> buttons = weiXinMenuService.getButton(map1);
                if ("2".equals(buttons.getOne())) {
                    message.init(false, "该公众号的菜单或子菜单为空", null);
                    super.writeJson(message);
                    return;
                } else if ("1".equals(buttons.getOne())) {
                    message.init(false, "该公众号的菜单信息不全，请补全后同步", null);
                    super.writeJson(message);
                    return;
                } else if ("0".equals(buttons.getOne()) && buttons.getTwo() != null) {
                    //将菜单数组赋给菜单对象
                    Menu menu = new Menu();
                    menu.setButton(buttons.getTwo());
                    paramsJson = JSON.toJSONString(menu);
                } else {
                    message.init(false, "未知错误", null);
                    super.writeJson(message);
                }

                try {
                    //调用微信创建菜单接口地址
                    String url = Global.wechat_api_url + "createMenu/" + weiXinAccountList.get(0).getAccountAppId();
                    //请求入参
                    List<NameValuePair> formparams = new ArrayList<NameValuePair>();
                    formparams.add(new BasicNameValuePair("menu", paramsJson));
                    String result = HttpclientUtils.post(url, formparams);
                    Map<String, Object> resultMap = JSON.parseObject(result, Map.class);
                    Integer errcode = (Integer) resultMap.get("errcode");
                    if (errcode == 0) {
                        message.init(true, "同步成功", null);
                    } else {
                        message.init(false, "同步失败" + resultMap.get("errmsg"), null);
                    }
                } catch (NetServiceException e) {
                    e.printStackTrace();
//                    logger.error(e);
                    message.init(false, "同步异常", null);
                }
            } else {
                message.init(false, "该类型不存在", null);
            }
        } else {
            message.init(false, "公众号类型为空", null);
        }
        super.writeJson(message);
    }
   /* public void doNotNeedSessionAndSecurity_serviceNewsTypeTree(){

        Map<String, Object> map = new HashMap<String, Object>();

        map.put("serviceId",serviceId);

        List<ServiceNewsType> list = serviceNewsTypeService.query(map);

        super.writeJson(list);
    }*/

    public WeiXinMenu getWeiXinMenu() {
        return weiXinMenu;
    }

    public void setWeiXinMenu(WeiXinMenu weiXinMenu) {
        this.weiXinMenu = weiXinMenu;
    }

    public String getWeiXinMenuId() {
        return weiXinMenuId;
    }

    public void setWeiXinMenuId(String weiXinMenuId) {
        this.weiXinMenuId = weiXinMenuId;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }
}
