package com.cy.core.weiXin.service;

import com.alibaba.fastjson.JSON;
import com.cy.base.entity.Message;
import com.cy.common.utils.StringUtils;
import com.cy.common.utils.wechat.entity.*;
import com.cy.core.weiXin.dao.WeiXinMenuMapper;
import com.cy.core.weiXin.entity.WeiXinAccount;
import com.cy.core.weiXin.entity.WeiXinMenu;
import com.cy.core.weiXin.dao.WeiXinAccountMapper;
import com.cy.system.Global;
import com.cy.util.PairUtil;
import com.jfinal.weixin.sdk.api.SnsAccessTokenApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/12/24.
 */

@Service("weiXinMenuService")
public class WeiXinMenuServiceImpl implements WeiXinMenuService {

    @Autowired
    private WeiXinMenuMapper weiXinMenuMapper;

    @Autowired
    private WeiXinAccountMapper weiXinAccountMapper;

    private WeiXinMenu weiXinMenu;

    public WeiXinMenu getWeiXinMenu() {
        return weiXinMenu;
    }

    public void setWeiXinMenu(WeiXinMenu weiXinMenu) {
        this.weiXinMenu = weiXinMenu;
    }

    //查询服务新闻栏目
    public List<WeiXinMenu> query(Map<String, Object> map){
        return weiXinMenuMapper.query(map);
    }

    //查询服务新闻栏目详情
    public WeiXinMenu getById(String id){
        return weiXinMenuMapper.getById(id);
    }

    //通过栏目名查询新闻栏目
    public WeiXinMenu getByName(String name){
        return weiXinMenuMapper.getByName(name);
    }

    //新增新闻栏目
    public void save(WeiXinMenu weiXinMenu){
        weiXinMenu.preInsert();
        weiXinMenuMapper.save(weiXinMenu);
    }

    //更新新闻栏目
    public void update(WeiXinMenu weiXinMenu){
        weiXinMenu.preUpdate();
        weiXinMenuMapper.update(weiXinMenu);
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
    public void findWeiXinMenu(Message message, String content){

        if (StringUtils.isBlank(content)) {
            message.setMsg("未传入参数");
            message.setSuccess(false);
            return;
        }
        Map<String,Object> map = JSON.parseObject(content,Map.class);
        String appId = (String) map.get("accountAppId");
//        String menuKey = (String) map.get("menuKey");
        if (StringUtils.isBlank(appId)){
            message.init(false,"公众号appId为空",null);
            return;
        }
        List<WeiXinMenu> weiXinMenuList = weiXinMenuMapper.query(map);


        message.init(true,"查询成功",weiXinMenuList);
    }

    public void findWeiXinMenuButton(Message message,String content){
        if (StringUtils.isBlank(content)) {
            message.setMsg("未传入参数");
            message.setSuccess(false);
            return;
        }
        Map<String,Object> map = JSON.parseObject(content,Map.class);
        String appId = (String) map.get("accountAppId");
        if (StringUtils.isBlank(appId)){
            message.init(false,"公众号accountAppId为空",null);
            return;
        }
        map.put("parentId","0");
        Menu menu = new Menu();

        PairUtil<String,Button[]> buttons = getButton(map);
        if ("0".equals(buttons.getOne()) && buttons.getTwo()!=null) {
            message.init(false, "该公众号的菜单为空", null);
        }else if ("1".equals(buttons.getOne())){
            message.init(false, "该公众号的菜单信息不全，请补全后获取", null);
        }else if("2".equals(buttons.getOne())){
            menu.setButton(buttons.getTwo());
            message.init(true, "菜单获取成功", menu);
        }else {
            message.init(false, "未知错误", null);
        }
    }

    public void getAuthUrl(Message message,String content){
        if (StringUtils.isBlank(content)) {
            message.setMsg("未传入参数");
            message.setSuccess(false);
            return;
        }
        Map<String,String> map = JSON.parseObject(content,Map.class);

        String appId = map.get("appId");
        String accountType = map.get("accountType");
        String url = map.get("url");

        if(StringUtils.isBlank(url)){
            message.init(false, "请提供要转换的地址",null);
            return;
        }

        Map<String,Object> searchMap = new HashMap<String,Object>();
        //添加查询条件公众号类型
        if(StringUtils.isNotBlank(appId)){
            searchMap.put("appId",appId);
        }else if(StringUtils.isNotBlank(accountType)){
            searchMap.put("accountType", accountType);
        }else{
            message.init(false, "请提供AppID或公众号类型",null);
            return;
        }

        List<WeiXinAccount> weiXinAccountList = weiXinAccountMapper.selectList(searchMap);
        if(weiXinAccountList == null || weiXinAccountList.size() <= 0){
            message.init(false, "不存在的公众号", null);
            return;
        }
        appId = weiXinAccountList.get(0).getAccountAppId();
        accountType = weiXinAccountList.get(0).getAccountType();

        String redirectUri = Global.wechat_api_callback_path + appId;
        String newUrl = SnsAccessTokenApi.getAuthorizeURL(appId, redirectUri, url, false);
        Map<String, String> result = new HashMap<>();
        result.put("url", newUrl);
        if("20".equals(accountType)){
            result.put("logo", Global.FOUNDATION_LOGO);
            result.put("sign", Global.FOUNDATION_NAME);
        }else{
            result.put("logo", Global.schoolLogo);
            result.put("sign", Global.schoolSign);
        }

        message.init(true, "获取成功", result);

    }


    /**
     * @param map
     * @return
     * @author niu
     * @description 根据map中添加的条件，递归查询微信菜单，得到菜单数组
     */
    public PairUtil<String, Button[]> getButton(Map<String, Object> map) {
        PairUtil<String, Button[]> pairUtil = new PairUtil<>();
        List<WeiXinMenu> weiXinMenuList = weiXinMenuMapper.query(map);
        if (weiXinMenuList != null && weiXinMenuList.size() > 0 && weiXinMenuList.get(0) != null) {

            Button[] buttons = new Button[weiXinMenuList.size()];
            for (int i = 0; i < weiXinMenuList.size(); i++) {
                CommonButton commonButton = new CommonButton();
                ComplexButton complexButton = new ComplexButton();
                ViewButton viewButton = new ViewButton();
                switch (weiXinMenuList.get(i).getType()) {
                    case "10":
                        if (StringUtils.isNotBlank(weiXinMenuList.get(i).getName()) && StringUtils.isNotBlank(weiXinMenuList.get(i).getMenuKey())) {
                            //添加到消息触发按钮
                            commonButton.setType("click");
                            commonButton.setName(weiXinMenuList.get(i).getName());
                            commonButton.setKey(weiXinMenuList.get(i).getMenuKey());
                            buttons[i] = commonButton;
                        } else {
                            pairUtil.setOne("1");
                            pairUtil.setTwo(null);
                            return pairUtil;
                        }
                        break;
                    case "20":
                        if (StringUtils.isNotBlank(weiXinMenuList.get(i).getName()) && StringUtils.isNotBlank(weiXinMenuList.get(i).getUrl())) {

                            //添加到图文按钮
                            viewButton.setType("view");
                            viewButton.setName(weiXinMenuList.get(i).getName());

                            String url = weiXinMenuList.get(i).getUrl();
                            // 新菜单链接地址
                            String newUrl;
                            if("1".equals(weiXinMenuList.get(i).getIsOutSide())){
                                newUrl = url;
                            }else{
                                // 回调地址
                                String redirectUri = Global.wechat_api_callback_path+ map.get("accountAppId");
                                newUrl = SnsAccessTokenApi.getAuthorizeURL((String) map.get("accountAppId"),redirectUri,url,true);
                            }
                            viewButton.setUrl(newUrl);
                            buttons[i] = viewButton;
                        } else {
                            pairUtil.setOne("1");
                            pairUtil.setTwo(null);
                            return pairUtil;
                        }
                        break;
                    case "30":
                        if (StringUtils.isNotBlank(weiXinMenuList.get(i).getId()) && StringUtils.isNotBlank(weiXinMenuList.get(i).getName())) {
                            //如果是一级菜单继续遍历子菜单
                            map.put("parentId", weiXinMenuList.get(i).getId());
                            PairUtil<String,Button[]> buttons1= getButton(map);
                            if ("0".equals(buttons1.getOne()) && buttons1.getTwo()!=null){
                                //添加到父级菜单
                                complexButton.setName(weiXinMenuList.get(i).getName());
                                complexButton.setSub_button(buttons1.getTwo());
                                buttons[i] = complexButton;
                            }else {
                                pairUtil.setOne(buttons1.getOne());
                                pairUtil.setTwo(buttons1.getTwo());
                                return pairUtil;
                            }
                        } else {
                            pairUtil.setOne("1");
                            pairUtil.setTwo(null);
                            return pairUtil;
                        }
                        break;
                }

            }
            pairUtil.setOne("0");
            pairUtil.setTwo(buttons);
            return pairUtil;
        }else {
            pairUtil.setOne("2");
            pairUtil.setTwo(null);
            return pairUtil;
        }
    }

}
