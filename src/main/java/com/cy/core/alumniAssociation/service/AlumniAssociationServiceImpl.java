package com.cy.core.alumniAssociation.service;

import com.alibaba.fastjson.JSON;
import com.cy.base.entity.DataGrid;
import com.cy.base.entity.Message;
import com.cy.common.utils.StringUtils;
import com.cy.core.alumniAssociation.dao.AlumniAssociationMapper;
import com.cy.core.alumniAssociation.entity.*;
import com.cy.core.enterprise.dao.EnterpriseMapper;
import com.cy.core.enterprise.dao.EnterpriseProductMapper;
import com.cy.core.enterprise.entity.Enterprise;
import com.cy.core.enterprise.entity.EnterpriseProduct;
import com.cy.core.event.entity.Event;
import com.cy.core.live.entity.LiveTopic;
import com.cy.core.mobevent.dao.MobEventMapper;
import com.cy.core.mobevent.entity.CyEventSign;
import com.cy.core.news.dao.MobNewsTypeMapper;
import com.cy.core.news.dao.NewsMapper;
import com.cy.core.news.entity.DataGridNews;
import com.cy.core.news.entity.News;
import com.cy.core.news.entity.NewsType;
import com.cy.core.schoolServ.entity.SchoolServ;
import com.cy.core.userProfile.entity.UserProfile;
import com.cy.system.Global;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Mr.wu on 2017/4/26.
 */
@Service("alumniAssociationService")
public class AlumniAssociationServiceImpl implements AlumniAssociationService {

    @Autowired
    private AlumniAssociationMapper alumniAssociationMapper;

    @Autowired
    private NewsMapper newsMapper;

    @Autowired
    private MobNewsTypeMapper mobNewsTypeMapper;

    @Autowired
    private EnterpriseMapper enterpriseMapper;

    @Autowired
    private EnterpriseProductMapper enterpriseProductMapper;

    @Autowired
    private MobEventMapper mobEventMapper;
    //校友会首页信息获取
    public void getTopAlumniInfo(Message message,String content) {
        if (StringUtils.isBlank(content)) {
            message.init(false, "请传入参数", null);
            return;
        }
        Map<String, Object> map = JSON.parseObject(content, Map.class);
        //用户编号
        String accountNum = (String) map.get("accountNum");

//        if (StringUtils.isBlank(accountNum)) {
//            message.init(false, "请传入用户编号", null);
//            return;
//        }

        //校友会首页
        AlumniAssociation alumniAssociation = new AlumniAssociation();

        //轮播新闻
        String page ="1";
        String rows = "5";
        String topnews = "100";
        String channelId = "10";
        map.put("topnews", topnews);
        map.put("channelId", channelId);
        if (StringUtils.isNotBlank(page) && StringUtils.isNotBlank(rows)) {
            int start = (Integer.valueOf(page) - 1) * Integer.valueOf(rows);
            map.put("start", start);
            map.put("rows", Integer.valueOf(rows));
        }
        List<News> cycleNewsList = newsMapper.listWebNews(map);
        alumniAssociation.setCycleNewsList(cycleNewsList);

        //校友会栏目
        List<SchoolServ> serverLists = alumniAssociationMapper.getServiceList();
        alumniAssociation.setSmallTitleList(serverLists);
        int total = alumniAssociationMapper.getServiceListCount();
        alumniAssociation.setSmallTitleCount(total);

        //校园生活栏目
        List<SchoolServ> campusLifeTitleList = alumniAssociationMapper.getCampusLifeTitleList();
        alumniAssociation.setCampusLifeTitleList(campusLifeTitleList);
        int campusLifeTitleCount = alumniAssociationMapper.getCampusLifeTitleCount();
        alumniAssociation.setCampusLifeTitleCount(campusLifeTitleCount);

        //新闻列表
        TopNews topNews = new TopNews();
        //新闻速递
        Map<String, Object> map1 = JSON.parseObject(content, Map.class);
        String page1 ="1";
        String rows1 = "3";
        String topnews1 = "0";
        String channelId1 = "10";
        map1.put("topnews", topnews1);
        map1.put("channelId", channelId1);
        if (StringUtils.isNotBlank(page1) && StringUtils.isNotBlank(rows1)) {
            int start1 = (Integer.valueOf(page1) - 1) * Integer.valueOf(rows1);
            map1.put("start", start1);
            map1.put("rows", Integer.valueOf(rows1));
        }
        List<News> TopNewsList = newsMapper.listWebNews(map1);
        topNews.setNewsList(TopNewsList);

        //爱心捐赠
        Map<String,Object> map2 = JSON.parseObject(content, Map.class);
        String page2 = "1";
        String rows2 = "4";
        if (StringUtils.isNotBlank(page2) && StringUtils.isNotBlank(rows2)) {
            int start2 = (Integer.valueOf(page2) - 1) * Integer.valueOf(rows2);
            map2.put("start", start2);
            map2.put("rows", Integer.valueOf(rows2));
        } else {
            map2.put("isNotLimni", "1");
        }
        map2.put("status", "1");
        List<Project> projectList = alumniAssociationMapper.selectDonateList(map2);
        if (projectList != null && projectList.size() > 0)  {
            for (Project project : projectList) {
                project.setDonateUrl(Global.cy_server_url +"/mobile/services/foundation/donate_detail.html?projectId=" + project.getProjectId()
                        + "&accountNum=" + accountNum);
                project.setDonateUrl_xd("/mobile/services/foundation/donate_detail.html?projectId=" + project.getProjectId()
                        + "&accountNum=" + accountNum);
            }
        }

        topNews.setDonationList(projectList);

        //校友风采
        Map<String,Object> map3 = JSON.parseObject(content, Map.class);
        String channel3 = "10";//手机
        String isNavigation = "1";//在首页显示
        map3.put("channel", channel3);
        map3.put("isNavigation", isNavigation);
        //新闻栏目获取
        List<NewsType> newsTypeList = mobNewsTypeMapper.findNewsType(map3);
        String xyfc = "校友风采";
        String category = "";//栏目ID
        //判断是否含有校友风采
        if (newsTypeList != null && newsTypeList.size() > 0) {
            for (NewsType list : newsTypeList) {
                if (list.getName().equals(xyfc)){
                    category = String.valueOf(list.getId());
                    break;
                }
            }
        }

        Map<String,Object> map4 = JSON.parseObject(content, Map.class);
        String page4 = "1";
        String rows4 = "4";
        String channel4 = "10";
        map4.put("channelId", channel4);
        map4.put("category", category);
        if (StringUtils.isNotBlank(page4) && StringUtils.isNotBlank(rows4)) {
            int start4 = (Integer.valueOf(page4) - 1) * Integer.valueOf(rows4);
            map4.put("start", start4);
            map4.put("rows", Integer.valueOf(rows4));
        } else {
            map4.put("isNotLimni", "1");
        }
        if (StringUtils.isNotBlank(category)) {
            List<News> styleList = newsMapper.listWebNews(map4);
            topNews.setStyleList(styleList);
        }

        //值年返校
        Map<String,Object> map5 = JSON.parseObject(content, Map.class);
        String page5 = "1";
        String rows5 = "4";
        String type = "0";//查询类型(0：查询全部，1：当前用户创建，2：当前用户参与的，3：当前用户收藏的)
        if (StringUtils.isNotBlank(page5) && StringUtils.isNotBlank(rows5)) {
            int start5 = (Integer.valueOf(page5) - 1) * Integer.valueOf(rows5);
            map5.put("start", start5);
            map5.put("rows", Integer.valueOf(rows5));
        } else {
            map5.put("isNotLimit", "1");
        }
        map5.put("type",type);
        map5.put("userId", "");
        map5.put("status", "20");
        List<Fxjh> fxjhlist = alumniAssociationMapper.findFxjhList(map5);
        if (fxjhlist != null && fxjhlist.size() > 0)  {
            for (Fxjh fxjh : fxjhlist) {
                fxjh.setFxjhUrl(Global.cy_server_url + "/mobile/services/backSchoolPlan/plan-detail.html?eventId=" + fxjh.getId()
                        + "&accountNum=" + accountNum);
                fxjh.setFxjhUrl_xd("/mobile/services/backSchoolPlan/plan-detail.html?eventId=" + fxjh.getId()
                        + "&accountNum=" + accountNum);
            }
        }
        topNews.setFxjhList(fxjhlist);

        alumniAssociation.setTopNews(topNews);

        message.setMsg("校友会首页查询成功");
        message.setObj(alumniAssociation);
        message.setSuccess(true);

    }

    //惠校友首页信息获取
    public void getTopFavourAlumniInfo(Message message,String content) {
        if (StringUtils.isBlank(content)) {
            message.init(false, "请传入参数", null);
            return;
        }
        Map<String, Object> map = JSON.parseObject(content, Map.class);
        //用户编号
        String accountNum = (String) map.get("accountNum");
        //当前页
        String page = (String)map.get("page");
        //一页几行
        String rows = (String)map.get("rows");
        //展示类型1：求职招聘 2：直播话题 3：校友企业 4：校友产品
        String favourAlumniType = (String)map.get("favourAlumniType");

//        if (StringUtils.isBlank(accountNum)) {
//            message.init(false, "请传入用户编号", null);
//            return;
//        }

        if (StringUtils.isBlank(favourAlumniType)) {
            message.init(false, "请传入展示类型", null);
            return;
        }

        //惠校友首页
        AlumniAssociation alumniAssociation = new AlumniAssociation();

        if (favourAlumniType.equals("1") || favourAlumniType.equals("2") || favourAlumniType.equals("3") || favourAlumniType.equals("4")) {
            if (favourAlumniType.equals("1")) {
                //求职招聘
                if (StringUtils.isNotBlank(page) && StringUtils.isNotBlank(rows)) {
                    int start = (Integer.valueOf(page) - 1) * Integer.valueOf(rows);
                    map.put("start", start);
                    map.put("rows", Integer.valueOf(rows));
                } else {
                    map.put("isNoLimit", "1");
                }

                List<Recruitment> recruitmentList = alumniAssociationMapper.listRecruitment(map);
                for(Recruitment recruitment : recruitmentList){
                    if(recruitment != null){
                        if(recruitment.getContent().startsWith("{")){
                            String contentStr[] = recruitment.getContent().split(",");
                            if(contentStr!=null && contentStr.length == 5){
                                recruitment.setRequirement(contentStr[0].substring(15).replaceAll("\"",""));
                                recruitment.setContent(contentStr[1].substring(8).replaceAll("\"", ""));
                                recruitment.setRegion(contentStr[2].substring(10).replaceAll("\"", ""));
                                recruitment.setDescription(contentStr[3].substring(14).replaceAll("\"", ""));
                                recruitment.setJobType(contentStr[4].substring(7).replaceAll("\"","").replaceAll("}",""));
                            }
                        }
                    }
                recruitment.setRecruitmentUrl(Global.cy_server_url + "/mobile/serv/favourDetailQzzp.jsp?isWhat=1&category=3&id="
                        + recruitment.getId() + "&accountNum=" + accountNum);
                recruitment.setRecruitmentUrl_xd("/mobile/serv/favourDetailQzzp.jsp?isWhat=1&category=3&id="
                        + recruitment.getId() + "&accountNum=" + accountNum);
                }
                long total = alumniAssociationMapper.listRecruitmentCount(map);
                alumniAssociation.setListRecruitment(recruitmentList);
                alumniAssociation.setListRecruitmentCount(total);
            } else if (favourAlumniType.equals("2")) {
                //直播话题列表
                if (StringUtils.isNotBlank(page) && StringUtils.isNotBlank(rows)) {
                    int start = (Integer.valueOf(page) - 1) * Integer.valueOf(rows);
                    map.put("start", start);
                    map.put("rows", Integer.valueOf(rows));
                }
                List<LiveTopic> liveTopicList = alumniAssociationMapper.getTopicList(map);
                if (liveTopicList != null && liveTopicList.size() > 0)  {
                    for (LiveTopic liveTopic : liveTopicList) {
                        if (liveTopic.getLiveTopicUserId().equals(accountNum)) {
                            liveTopic.setLiveRoomTopicUrl(Global.cy_server_url + "/mobile/live/my-talk-details.html?liveTopicId=" + liveTopic.getLiveTopicId() +
                                    "&liveTopicUserId=" + liveTopic.getLiveTopicUserId() +  "&liveRoomId=" + liveTopic.getLiveRoomId());
                            liveTopic.setLiveRoomTopicUrl_xd("/mobile/live/my-talk-details.html?liveTopicId=" + liveTopic.getLiveTopicId() +
                                    "&liveTopicUserId=" + liveTopic.getLiveTopicUserId() +  "&liveRoomId=" + liveTopic.getLiveRoomId());
                        } else {
                            liveTopic.setLiveRoomTopicUrl(Global.cy_server_url + "/mobile/live/talk-details.html?liveTopicId=" + liveTopic.getLiveTopicId() +
                                    "&liveTopicUserId=" + liveTopic.getLiveTopicUserId() +  "&liveRoomId=" + liveTopic.getLiveRoomId());
                            liveTopic.setLiveRoomTopicUrl_xd("/mobile/live/talk-details.html?liveTopicId=" + liveTopic.getLiveTopicId() +
                                    "&liveTopicUserId=" + liveTopic.getLiveTopicUserId() +  "&liveRoomId=" + liveTopic.getLiveRoomId());
                        }
                    }
                }
                long total = alumniAssociationMapper.getTopicListCount();
                alumniAssociation.setLiveTopicList(liveTopicList);
                alumniAssociation.setLiveTopicListCount(total);
            } else if (favourAlumniType.equals("3")) {
                //校友企业
                if (StringUtils.isNotBlank(page) && StringUtils.isNotBlank(rows)) {
                    int start = (Integer.valueOf(page) - 1) * Integer.valueOf(rows);
                    map.put("start", start);
                    map.put("rows", Integer.valueOf(rows));
                }
                List<Enterprise> enterpriseList = enterpriseMapper.selectEnterprise(map);
                long total = enterpriseMapper.count(map);
                if (enterpriseList != null && enterpriseList.size() > 0)  {
                    for (Enterprise enterprise : enterpriseList) {
                        enterprise.setEnterpriseUrl(Global.cy_server_url + "/mobile/services/product/company_detail.html?enterpriseId=" + enterprise.getId()
                                + "&accountNum=" + accountNum);
                        enterprise.setEnterpriseUrl_xd("/mobile/services/product/company_detail.html?enterpriseId=" + enterprise.getId()
                                + "&accountNum=" + accountNum);
                    }
                }
                alumniAssociation.setEnterpriseList(enterpriseList);
                alumniAssociation.setEnterpriseListCount(total);
            } else {
                //校友产品
                if (StringUtils.isNotBlank(page) && StringUtils.isNotBlank(rows)) {
                    int start = (Integer.valueOf(page) - 1) * Integer.valueOf(rows);
                    map.put("start", start);
                    map.put("rows", Integer.valueOf(rows));
                }
                List<EnterpriseProduct> enterpriseProductList = enterpriseProductMapper.findEnterPriseProductList(map);
                long total = enterpriseProductMapper.count(map);
                if (enterpriseProductList != null && enterpriseProductList.size() > 0)  {
                    for (EnterpriseProduct enterpriseProduct : enterpriseProductList) {
                        enterpriseProduct.setEnterpriseProductUrl(Global.cy_server_url + "/mobile/services/product/product_detail.html?productId=" + enterpriseProduct.getId()
                                + "&accountNum=" + accountNum);
                        enterpriseProduct.setEnterpriseProductUrl_xd("/mobile/services/product/product_detail.html?productId=" + enterpriseProduct.getId()
                                + "&accountNum=" + accountNum);
                    }
                }
                alumniAssociation.setEnterpriseProductList(enterpriseProductList);
                alumniAssociation.setEnterpriseProductListCount(total);
            }
        } else {
            message.setMsg("未知的展示类型");
            message.setSuccess(false);
            return;
        }

        //惠校友首页常用服务栏目获取
        UserService userService = alumniAssociationMapper.getUserServiceList(accountNum);
        if (userService == null) {
            //惠校友栏目
            List<SchoolServ> serverLists = alumniAssociationMapper.getFavourAlumniServiceList();
            alumniAssociation.setSmallTitleList(serverLists);
            int total = alumniAssociationMapper.getFavourAlumniServiceListCount();
            alumniAssociation.setSmallTitleCount(total);

            //设置常用服务
            Map<String, Object> userServiceMap = this.setUserService(serverLists,accountNum);
            alumniAssociationMapper.insertUserServiceList(userServiceMap);

        } else {
            //如果后台添加了新的服务，那么需要更新用户服务
            //用户自定义服务
            String[] normalServiceId = null;
            String[] unNormalServiceId = null;
            int normalServiceIdLength = 0;
            int unNormalServiceIdLength = 0;
            if (StringUtils.isNotBlank(userService.getNormalServiceId())) {
                normalServiceId = userService.getNormalServiceId().split(",");
                normalServiceIdLength = normalServiceId.length;
            }
            if (StringUtils.isNotBlank(userService.getUnNormalServiceId())) {
                unNormalServiceId = userService.getUnNormalServiceId().split(",");
                unNormalServiceIdLength = unNormalServiceId.length;
            }
            //系统服务
            int totalSchoolService = alumniAssociationMapper.getFavourAlumniServiceListCount();
            //如果用户自定义服务和系统服务个数不一样的话，更新之
            if (totalSchoolService != (normalServiceIdLength + unNormalServiceIdLength)) {
                List<SchoolServ> schoolServerLists = alumniAssociationMapper.getFavourAlumniServiceList();
                //设置常用服务
                Map<String, Object> userServiceMap = this.setUserService(schoolServerLists,accountNum);
                alumniAssociationMapper.updateUserServiceList(userServiceMap);
            }

            //获取用户常用服务
            UserService normalUserService = alumniAssociationMapper.getUserServiceList(accountNum);
            String[] serviceId = normalUserService.getNormalServiceId().split(",");
            map.put("serviceId", serviceId);
            List<SchoolServ> serverLists = alumniAssociationMapper.selectUserFavourAlumniServiceList(map);
            alumniAssociation.setSmallTitleList(serverLists);
            int total = serviceId.length;
            alumniAssociation.setSmallTitleCount(total);
        }

        message.setMsg("惠校友首页查询成功");
        message.setObj(alumniAssociation);
        message.setSuccess(true);

    }

    //惠校友动态栏目获取
    public void getServiceColumns(Message message,String content) {
        if (StringUtils.isBlank(content)) {
            message.init(false, "请传入参数", null);
            return;
        }
        Map<String, Object> map = JSON.parseObject(content, Map.class);
        String accountNum = (String) map.get("accountNum");

        if (StringUtils.isBlank(accountNum)) {
            message.init(false, "请传入用户编号", null);
            return;
        }
        //惠校友更多栏目获取
        UserService moreServiceColumns = alumniAssociationMapper.getMoreServiceColumns(accountNum);
        //用户服务
        String[] normalServiceId = null;
        String[] unNormalServiceId = null;
        if (moreServiceColumns != null) {
            if (StringUtils.isNotBlank(moreServiceColumns.getNormalServiceId())) {
                normalServiceId = moreServiceColumns.getNormalServiceId().split(",");
            }
            if (StringUtils.isNotBlank(moreServiceColumns.getUnNormalServiceId())) {
                unNormalServiceId = moreServiceColumns.getUnNormalServiceId().split(",");
            }
        }
        map.put("normalServiceId",normalServiceId);
        map.put("unNormalServiceId",unNormalServiceId);
        List<SchoolServ> normalService = null;
        if (normalServiceId != null) {
            normalService = alumniAssociationMapper.selectNormalServiceList(map);
        }
        List<SchoolServ> unNormalService = null;
        if(unNormalServiceId != null) {
            unNormalService = alumniAssociationMapper.selectUnNormalServiceList(map);
        }
        UserService userService= new UserService();
        userService.setNormalService(normalService);
        userService.setUnNormalService(unNormalService);
        message.setObj(userService);
        message.setMsg("会校友更多栏目获取成功");
        message.setSuccess(true);
    }

    //惠校友动态栏目变更
    public void updateServiceColumns(Message message,String content) {
        if (StringUtils.isBlank(content)) {
            message.init(false, "请传入参数", null);
            return;
        }
        Map<String, Object> map = JSON.parseObject(content, Map.class);
        //用户编号
        String accountNum = (String) map.get("accountNum");
        //常用的服务ID
        String normalServiceId = (String) map.get("normalServiceId");
        //不常用的服务ID
        String unNormalServiceId = (String) map.get("unNormalServiceId");

        if (StringUtils.isBlank(accountNum)) {
            message.init(false, "请传入用户编号", null);
            return;
        }

        if (StringUtils.isBlank(normalServiceId)) {
            message.init(false, "常用的服务ID不能为空", null);
            return;
        }

        //惠校友更多栏目变更
        map.put("accountNum",accountNum);
        map.put("normalServiceId",normalServiceId);
        map.put("unNormalServiceId",unNormalServiceId);
        alumniAssociationMapper.updateMoreServiceColumns(map);
        message.setMsg("会校友更多栏目修改成功");
        message.setSuccess(true);
    }

    //设置常用服务
    private Map setUserService(List<SchoolServ> serverLists ,String accountNum) {
        Map<String, Object> map = new HashMap();
        StringBuffer normalStr = new StringBuffer();
        StringBuffer unNormalStr = new StringBuffer();

        if (serverLists != null && serverLists.size() > 0 && serverLists.size() < 8) {
            for (SchoolServ schoolServ : serverLists) {
                normalStr.append(schoolServ.getId());
                normalStr.append(",");
            }
        }
        //超过7个的放到不常用服务里面
        if (serverLists != null && serverLists.size() > 7) {
            for (int i = 0; i < serverLists.size(); i++) {
                if (i < 7) {
                    normalStr.append(serverLists.get(i).getId());
                    normalStr.append(",");
                } else {
                    unNormalStr.append(serverLists.get(i).getId());
                    unNormalStr.append(",");
                }

            }
        }
        String  normalServiceId = "";
        String  unNormalServiceId = "";
        if (normalStr.length() > 0) {
            normalServiceId = normalStr.deleteCharAt(normalStr.length() - 1).toString();
        }
        if (unNormalStr.length() > 0) {
            unNormalServiceId = unNormalStr.deleteCharAt(unNormalStr.length() - 1).toString();
        }
        //插入用户服务
        map.put("normalServiceId",normalServiceId);
        map.put("unNormalServiceId",unNormalServiceId);
        map.put("accountNum",accountNum);
        return map;
    }

    /**
     * 根据分会组织ID获取新闻列表
     */
    public void getNewsByAlumniId(Message message, String content){
        if (org.apache.commons.lang3.StringUtils.isBlank(content)) {
            message.setMsg("未传入参数");
            message.setSuccess(false);
            return;
        }

        Map<String,Object> map = JSON.parseObject(content, Map.class);
        String page = (String) map.get("page");
        String rows = (String) map.get("rows");
        String alumniId = (String) map.get("alumniId");

        if (StringUtils.isBlank(alumniId)) {
            message.init(false, "分会组织ID不能为空", null);
            return;
        }

        Map<String, Object> map2 = new HashMap<String, Object>();

        if(StringUtils.isNotBlank(page) && StringUtils.isNotBlank(rows)){
            int start = (Integer.valueOf(page) - 1) * Integer.valueOf(rows);
            map2.put("start", start);
            map2.put("rows", Integer.valueOf(rows));
        }
        map2.put("channelId","10");
        map2.put("alumniId",alumniId);
        List<News> list = alumniAssociationMapper.getNewsByAlumniId(map2);
        DataGridNews<News> dataGrid = new DataGridNews<>();
        long total = alumniAssociationMapper.getNewsByAlumniIdCount(map2);
        dataGrid.setTotal(total);
        dataGrid.setRows(list);

        message.init(true ,"分会组织新闻查询成功",dataGrid,null);
    }

    /**
     * 根据分会组织ID获取活动列表
     */
    public void getEventsByAlumniId(Message message, String content) {
        if (org.apache.commons.lang3.StringUtils.isBlank(content)) {
            message.setMsg("未传入参数");
            message.setSuccess(false);
            return;
        }
        Map<String,Object> map = JSON.parseObject(content, Map.class);
        String accountNum = (String) map.get("accountNum");
        String page = (String) map.get("page");
        String rows = (String) map.get("rows");
        //分会组织ID
        String alumniId = (String) map.get("alumniId");

        if (StringUtils.isBlank(alumniId)) {
            message.init(false, "请传入分会组织ID", null);
            return;
        }
        if (org.apache.commons.lang3.StringUtils.isNotBlank(page) && org.apache.commons.lang3.StringUtils.isNotBlank(rows)) {
            int start = (Integer.valueOf(page) - 1) * Integer.valueOf(rows);
            map.put("start", start);
            map.put("rows", Integer.valueOf(rows));
        } else {
            map.put("isNotLimni", "1");
        }

        List<Event> list = new ArrayList<>();

        map.put("alumniId",alumniId);
        map.put("accountNum",accountNum);
        list.addAll(alumniAssociationMapper.getEventsByAlumniId(map));

        for(int i =0; i<list.size(); i++){
            if(org.apache.commons.lang3.StringUtils.isNotBlank(accountNum)){
                CyEventSign eventSign = new CyEventSign();
                eventSign.setEventId(list.get(i).getId());
                eventSign.setUserInfoId(accountNum);

                long signCount = mobEventMapper.countEventSign(eventSign);

                if(signCount > 0){
                    list.get(i).setIsJoined("1");
                }else{
                    list.get(i).setIsJoined("0");
                }
            }else{
                list.get(i).setIsJoined("0");
            }
        }

        DataGrid<Event> dataGrid = new DataGrid<Event>();
        long total = alumniAssociationMapper.getEventsByAlumniIdCount(map);
        dataGrid.setTotal(total);
        dataGrid.setRows(list);

        message.init(true ,"分会组织活动查询成功",dataGrid,null);
    }

    /**
     * 根据分会组织ID获取成员列表
     */
    public void getMemberListByAlumniId(Message message, String content) {
        if (org.apache.commons.lang3.StringUtils.isBlank(content)) {
            message.setMsg("未传入参数");
            message.setSuccess(false);
            return;
        }
        Map<String,Object> map = JSON.parseObject(content, Map.class);
        String accountNum = (String) map.get("accountNum");
        String page = (String) map.get("page");
        String rows = (String) map.get("rows");
        //分会组织ID
        String alumniId = (String) map.get("alumniId");

        if (StringUtils.isBlank(accountNum)) {
            message.init(false, "请传入用户编号", null);
            return;
        }

        if (StringUtils.isBlank(alumniId)) {
            message.init(false, "请传入分会组织ID", null);
            return;
        }
        if (org.apache.commons.lang3.StringUtils.isNotBlank(page) && org.apache.commons.lang3.StringUtils.isNotBlank(rows)) {
            int start = (Integer.valueOf(page) - 1) * Integer.valueOf(rows);
            map.put("start", start);
            map.put("rows", Integer.valueOf(rows));
        } else {
            map.put("isNotLimit", "1");
        }

        map.put("alumniId",alumniId);
        map.put("accountNum",accountNum);

        AlumniMember alumniMember = new AlumniMember();
        //分会所有成员
        List<UserProfile> memberList = alumniAssociationMapper.getMemberListByAlumniId(map);
        alumniMember.setMemberList(memberList);
        //获取当前用户在该会中的状态（5:邀请加入， 10：审核中，20：正式会员，30：未过审）
        String myStatus = alumniAssociationMapper.getMyStatus(map);
        alumniMember.setMsStatus(myStatus);
        //分会总人数
        long total = alumniAssociationMapper.getMemberCount(map);
        alumniMember.setMemberCount(total);
        message.setObj(alumniMember);
        message.setMsg("分会组织成员列表查询成功");
        message.setSuccess(true);
    }

    /**
     * 统计网站点击量
     */
    public void getWebClickNum(Message message, String content) {
        String clickNumCnt = alumniAssociationMapper.getClickNumCnt();
        long pageView = 0;
        if(StringUtils.isNotBlank(clickNumCnt)){
            pageView = Integer.parseInt(clickNumCnt);
        }
        pageView+=1;

        if (pageView == 1) {
            //第一次浏览插入
            alumniAssociationMapper.insertClickNumCnt(pageView);
        } else {
            //不是第一次浏览，浏览量加1
            alumniAssociationMapper.updateClickNumCnt(pageView);
        }


        message.setObj(pageView);
        message.setMsg("访问量查询成功");
        message.setSuccess(true);
    }
}
