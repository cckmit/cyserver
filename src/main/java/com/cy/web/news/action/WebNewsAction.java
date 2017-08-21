package com.cy.web.news.action;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.cy.base.action.WebBaseAction;

import com.cy.web.news.entity.WebNews;
import com.cy.web.news.entity.WebNewsType;
import com.cy.web.news.service.WebNewsService;


@Namespace("/web")
@Action(value = "webNewsAction", results = {
//		@Result(name = "webNavigation", location = "/web/webNavigation.jsp"),
//		@Result(name = "webOfChooseCity", location = "/web/chooseCity.jsp"),
//		@Result(name = "webSubAlumniPage", location = "/web/indexForRegion.jsp")
})
public class WebNewsAction extends WebBaseAction{

	
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(WebNewsAction.class);
	
	@Autowired
	private WebNewsService webNewsService;
	
	private WebNewsType webNewsType;
	
	private WebNews webNews;
	
	
//	public String getWebNavigation(){
//
//		List<WebNewsType> listNewsType = webNewsService.getWebNewsTypeList(webNewsType);
//
//		this.getRequest().setAttribute("listNewsType", listNewsType);
//
//		return "webNavigation";
//	}
//
//	public String getWebOfChooseCity(){
//
//
//		List<WebNewsType> listNewsType = webNewsService.getAlumniLocList(webNewsType);
//
//		this.getRequest().setAttribute("realListNewsType", getGroupListForRegion(listNewsType));
//
//		return "webOfChooseCity";
//	}
//
//
//	public String getWebSubAlumniPage(){
//
//		this.getRequest().setAttribute("cityName", this.getRequest().getParameter("cityName"));
//
//		return "webSubAlumniPage";
//	}
	
	public List<WebNewsType> getGroupListForRegion(List<WebNewsType> listNewsType){
		
		String rexStr = " ";
		String usedProStr = "";
		
		WebNewsType usedWebNewsType = new WebNewsType();
		WebNewsType usedSubWebNewsType = new WebNewsType();
		
		
		List<WebNewsType> realListNewsType = new ArrayList<WebNewsType>();
		List<WebNewsType> realSubListNewsType = new ArrayList<WebNewsType>();
		
		for (WebNewsType tmpWebNewsType : listNewsType) { 
			
			if(tmpWebNewsType.getCityName()!=null && tmpWebNewsType.getCityName().indexOf(rexStr)!=-1 
					&& tmpWebNewsType.getCityName().split(rexStr).length==2){
				
				String[] splitStrArr = tmpWebNewsType.getCityName().split(rexStr);
				
				if(splitStrArr[0].equals(usedProStr)){
					
					usedSubWebNewsType = new WebNewsType();
					usedSubWebNewsType.setCityName(splitStrArr[1]);
					usedSubWebNewsType.setDispCityName(tmpWebNewsType.getCityName());
					realSubListNewsType.add(usedSubWebNewsType);
					
				}else{
					if(realSubListNewsType!=null && realSubListNewsType.size()>0){
						usedWebNewsType.setWebNewsType(realSubListNewsType);
						realListNewsType.add(usedWebNewsType);
						realSubListNewsType = new ArrayList<WebNewsType>();
					}
					
					
					usedWebNewsType = new WebNewsType();
					usedWebNewsType.setCityName(splitStrArr[0]);
					
					usedSubWebNewsType = new WebNewsType();
					usedSubWebNewsType.setCityName(splitStrArr[1]);
					usedSubWebNewsType.setDispCityName(tmpWebNewsType.getCityName());
					realSubListNewsType.add(usedSubWebNewsType);
				}
				
				usedProStr = splitStrArr[0];
				
			}else{
				logger.info("Failed for region >>" + tmpWebNewsType.toString()+"<<");
			}
		}
		
		if(realSubListNewsType!=null && realSubListNewsType.size()>0){
			usedWebNewsType.setWebNewsType(realSubListNewsType);
			realListNewsType.add(usedWebNewsType);
		}
		
		return realListNewsType;
	}

	public WebNewsType getWebNewsType() {
		return webNewsType;
	}


	public void setWebNewsType(WebNewsType webNewsType) {
		this.webNewsType = webNewsType;
	}

	public WebNews getWebNews() {
		return webNews;
	}

	public void setWebNews(WebNews webNews) {
		this.webNews = webNews;
	}

}
