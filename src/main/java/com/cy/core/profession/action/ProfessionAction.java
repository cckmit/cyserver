package com.cy.core.profession.action;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;

import com.alibaba.fastjson.JSON;
import com.cy.base.action.AdminBaseAction;
import com.cy.core.profession.entity.Profession;

@Namespace("/profession")
@Action(value = "professionAction", results = {})
public class ProfessionAction extends AdminBaseAction {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(ProfessionAction.class);

	private Profession profession;

	/**
	 * 得到所有的行业
	 * 
	 */
	public void doNotNeedSecurity_getALLProfessionList() {
		List<Profession> p = new ArrayList<Profession>();
		Profession p1 = new Profession();
		p1.setProfessioName("互联网通信");
		p1.setProfessioRemark("互联网通信");
		Profession p2 = new Profession();
		p2.setProfessioName("会计金融");
		p2.setProfessioRemark("会计金融");
		Profession p3 = new Profession();
		p3.setProfessioName("贸易消费");
		p3.setProfessioRemark("贸易消费");
		Profession p4 = new Profession();
		p4.setProfessioName("制药医疗");
		p4.setProfessioRemark("制药医疗");
		Profession p5 = new Profession();
		p5.setProfessioName("广告媒体");
		p5.setProfessioRemark("广告媒体");
		Profession p6 = new Profession();
		p6.setProfessioName("房地产建筑");
		p6.setProfessioRemark("房地产建筑");
		Profession p7 = new Profession();
		p7.setProfessioName("教育培训");
		p7.setProfessioRemark("教育培训");
		Profession p8 = new Profession();
		p8.setProfessioName("物流运输");
		p8.setProfessioRemark("物流运输");
		Profession p9 = new Profession();
		p9.setProfessioName("能源原材料");
		p9.setProfessioRemark("能源原材料");
		Profession p10 = new Profession();
		p10.setProfessioName("服务餐饮");
		p10.setProfessioRemark("服务餐饮");
		Profession p11 = new Profession();
		p11.setProfessioName("政府");
		p11.setProfessioRemark("政府");
		Profession p12 = new Profession();
		p12.setProfessioName("其他");
		p12.setProfessioRemark("其他");
		
		p.add(p1);
		p.add(p2);
		p.add(p3);
		p.add(p4);
		p.add(p5);
		p.add(p6);
		p.add(p7);
		p.add(p8);
		p.add(p9);
		p.add(p10);
		p.add(p11);
		p.add(p12);
		
		logger.info(JSON.toJSONString(p));
		super.writeJson(p);
	}

}
