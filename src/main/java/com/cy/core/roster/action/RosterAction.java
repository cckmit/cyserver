package com.cy.core.roster.action;

import com.cy.base.action.AdminBaseAction;
import com.cy.base.entity.Message;
import com.cy.core.dict.entity.Dict;
import com.cy.core.roster.entity.Roster;
import com.cy.core.roster.service.RosterService;
import com.cy.util.WebUtil;
import net.sf.json.*;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 黑白名单模块action
 * @author Administrator
 *
 */
@Namespace("/roster")
@Action(value = "rosterAction")
public class RosterAction extends AdminBaseAction {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(RosterAction.class);
	
	

	public Roster getRoster() {
		return roster;
	}

	public void setRoster(Roster roster) {
		this.roster = roster;
	}

	private Roster roster = new Roster();

	@Autowired
	private RosterService rosterService;

	/**--保存--**/
	public void save() {
		Message message = new Message();
		try {
			Timestamp stamp = new Timestamp(new Date().getTime());
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("id", roster.getId());
			map.put("ref_id", roster.getRef_id());
			map.put("dict_id", roster.getDict_id());
			map.put("create_time", stamp.toString());
			map.put("type", roster.getType());
			boolean flag = rosterService.save(map);
			if(flag){
				message.setMsg("新增成功");
				message.setSuccess(true);
			}else{
				message.setMsg("新增失败,该名单已存在,请不要重复添加");
				message.setSuccess(false);
			}
		}catch (Exception e) {
			logger.error(e, e);
			message.setMsg("新增失败");
			message.setSuccess(false);
		}
		super.writeJson(message);
	}

	/**--列表数据查询--**/
	public void dataGrid() {
		Map<String, Object> map = new HashMap<String, Object>();
		String ref_id = getRequest().getParameter("ref_id");
		int type = WebUtil.toInt(getRequest().getParameter("type")) ;
		map.put("page", page);
		map.put("rows", rows);
		map.put("ref_id", ref_id);
		map.put("type", type==0?1:type);
		super.writeJson(rosterService.dataGrid(map));
	}
	
	
	/**--批量删除--**/
	public void delete() {
		Message message = new Message();
		try {
			//名单类型(黑名单，白名单)
			int type = WebUtil.toInt(getRequest().getParameter("type"));
			rosterService.delete(ids,type);
			message.setMsg("删除成功");
			message.setSuccess(true);
		} catch (Exception e) {
			logger.error(e, e);
			message.setMsg("删除失败");
			message.setSuccess(false);
		}
		super.writeJson(message);
	}
	
	/**--对外接口,查询某个对象是否在黑白名单里
	 *  http://localhost/cy_v1/roster/rosterAction!doNotNeedSessionAndSecurity__isRoster.action?json={"ref_id":"0000500010200504049","type":"1","dict_name":"用户"}
	 *  返回打印true:在,flase:不在--**/
	public void doNotNeedSessionAndSecurity__isRoster(){	
		//{"ref_id":"0000500010200504049","type":"1","dict_name":"用户"}
		String json = this.getRequest().getParameter("json");
		Message message = new Message();
		
		if(WebUtil.isEmpty(json)){
			message.setMsg("json参数不能为空");
			message.setSuccess(false);
			message.setObj("params_error");
			super.writeJson(message);
			return;
		}
		long count = 0L;
		try{
			//解析json字符串
			Matcher mat =Pattern.compile("\"(.*?)\":\"(.*?)\"").matcher(json);
			String ref_id = "";
			int type = 0;
			String dict_name = "";
			for(int i=0;mat.find();i++){
	        	if(i==0){
	        		ref_id = mat.group(2);
	        	}else if(i==1){
	        		type = WebUtil.toInt(mat.group(2)) ;
	        	}else if(i==2){
	        		dict_name = mat.group(2) ;
	        	}
	        }
			Map dictMap = rosterService.getDictByName(dict_name);
			if(dictMap == null){
				message.setMsg("查询成功");
				message.setSuccess(true);
				if(count>0L){
					message.setObj("true");
				}else{
					message.setObj("false");
				}
			}else{
				Map map = new HashMap();
				map.put("ref_id", ref_id);
				map.put("type", type);
				map.put("dict_id", dictMap.get("dict_id"));
				count = this.rosterService.count(map);
				message.setMsg("查询成功");
				message.setSuccess(true);
				if(count>0L){
					message.setObj("true");
				}else{
					message.setObj("false");
				}
			}
		}catch(Exception ex){
			ex.printStackTrace();
			message.setMsg("发生异常");
			message.setSuccess(false);
			message.setObj("exception");
			
		}
		super.writeJson(message);
	}
	

	public static void main(String[] args) throws Exception {

    }
}
