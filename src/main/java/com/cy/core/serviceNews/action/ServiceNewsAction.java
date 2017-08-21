package com.cy.core.serviceNews.action;

import com.cy.base.action.AdminBaseAction;
import com.cy.base.entity.DataGrid;
import com.cy.base.entity.Message;
import com.cy.common.utils.StringUtils;
import com.cy.core.news.entity.News;
import com.cy.core.serviceNews.entity.ServiceNews;
import com.cy.core.serviceNews.service.ServiceNewsService;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/9/12 0012.
 */
@Namespace("/serviceNews")
@Action(value = "serviceNewsAction",results = {
        @Result(name = "getMobNew", location = "/page/admin/serviceNews/show.jsp")
})
public class ServiceNewsAction extends AdminBaseAction {

    private ServiceNews serviceNews;
    private String serviceNewsId;
    private String isRmv;
    private String newsId ;

    @Autowired
    private ServiceNewsService serviceNewsService;
    //得到新闻列表并且分页
    public void dataGraidServiceNews(){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("rows", rows);
        map.put("page", page);
        if(serviceNews != null && !serviceNews.equals("")){
            map.put("title",serviceNews.getTitle());
            map.put("serviceId", serviceNews.getServiceId());
            map.put("channel", serviceNews.getChannel());
        }
        DataGrid<ServiceNews> data = serviceNewsService.dataGrid(map);
        super.writeJson(data);
    }
    //批量删除新闻
    public void deleteServiceNews(){
        Message message=new Message();
        try {
            serviceNewsService.deleteServiceNews(ids);
            message.setMsg("删除成功");
            message.setSuccess(true);

        } catch (Exception e) {
            message.setMsg("删除失败");
            message.setSuccess(false);
        }
        super.writeJson(message);
    }
    //新增新闻
    public void saveServiceNews(){
        Message message=new Message();
        try {
            serviceNews.setStatus("20");
            serviceNewsService.saveServiceNews(serviceNews);
            message.setMsg("新增成功");
            message.setSuccess(true);

        } catch (Exception e) {
            message.setMsg("新增失败");
            message.setSuccess(false);
        }
        super.writeJson(message);
    }
    //修改新闻
    public void updateServiceNews(){
        Message message=new Message();
        try {
            serviceNews.setStatus("20");

            if(StringUtils.isBlank(serviceNews.getNewsUrl())) {
                // 自动生成URL
                HttpServletRequest request = this.getRequest();
                String path = request.getContextPath();
                String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
                String url = basePath + "serviceNews/serviceNewsAction!doNotNeedSessionAndSecurity_getMobNew.action?newsId=" + serviceNews.getId();
                serviceNews.setNewsUrl(url);
            }

            serviceNewsService.updateServiceNews(serviceNews);
            message.setMsg("修改成功");
            message.setSuccess(true);

        } catch (Exception e) {
            message.setMsg("修改失败");
            message.setSuccess(false);
        }
        super.writeJson(message);
    }
    //查看某一天新闻的详情
    public void getServiceNewsById(){

        ServiceNews serviceNews=serviceNewsService.getServiceNewsById(serviceNewsId);
        super.writeJson(serviceNews);
    }
    public void setMobTypeList() {
        Message message = new Message();
        String controlStr = "";
        if (isRmv.equals("true")) {
            // 100 is for topnews of mobile
            controlStr = "100";
        } else {
            controlStr = null;
        }
        try {
            serviceNewsService.setMobTypeList(ids, controlStr);
            message.setMsg("设置成功");
            message.setSuccess(true);

        } catch (Exception e) {
            message.setMsg("设置失败");
            message.setSuccess(false);
        }
        super.writeJson(message);
    }



    public String doNotNeedSessionAndSecurity_getMobNew() {

        serviceNews = serviceNewsService.getServiceNewsById(newsId);

        if (serviceNews.getContent() != null) {

            String tmpOneStr = "";

            StringBuffer bufConStr = new StringBuffer(serviceNews.getContent());

            int findImg = bufConStr.indexOf("<img");

            while (findImg != -1) {

                tmpOneStr = bufConStr.substring(findImg, bufConStr.indexOf(">", findImg) + 1);

                bufConStr.delete(findImg, bufConStr.indexOf(">", findImg) + 1);

                tmpOneStr = tmpOneStr.replaceAll("(?i)style[\\s]*=[\\s]*[\'\"][\\S]*[\\s]*[\'\"]", "");

                tmpOneStr = tmpOneStr.replaceAll("(?i)width[\\s]*=[\\s]*[\'\"][\\S]*[\\s]*[\'\"]", "");

                tmpOneStr = tmpOneStr.replaceAll("(?i)height[\\s]*=[\\s]*[\'\"][\\S]*[\\s]*[\'\"]", "");

                bufConStr.insert(findImg, tmpOneStr);

                findImg = bufConStr.indexOf("<img", findImg + 1);

            }

            serviceNews.setContent(bufConStr.toString());
        }

        return "getMobNew";
    }

    //get set 方法------------------------------------------------------
    public ServiceNews getServiceNews() {
        return serviceNews;
    }

    public void setServiceNews(ServiceNews serviceNews) {
        this.serviceNews = serviceNews;
    }

    public String getServiceNewsId() {
        return serviceNewsId;
    }

    public void setServiceNewsId(String serviceNewsId) {
        this.serviceNewsId = serviceNewsId;
    }

    public String getIsRmv() {
        return isRmv;
    }

    public void setIsRmv(String isRmv) {
        this.isRmv = isRmv;
    }

    public String getNewsId() {
        return newsId;
    }

    public void setNewsId(String newsId) {
        this.newsId = newsId;
    }
}
