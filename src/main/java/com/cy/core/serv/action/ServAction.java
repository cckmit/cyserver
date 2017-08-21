package com.cy.core.serv.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cy.system.Global;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import com.cy.base.action.AdminBaseAction;
import com.cy.base.entity.Message;
import com.cy.core.serv.entity.Serv;
import com.cy.core.serv.service.ServService;
import com.cy.util.RoleUtil;
import com.cy.util.WebUtil;

@Namespace("/serv")
@Action(value = "servAction")
public class ServAction extends AdminBaseAction {

	private static final Logger logger = Logger.getLogger(ServAction.class);

    private Serv serv;
    
    private String province;
    private String city;
    private String[] pics;
    private int handleStatus;  //处理结果 (0=正常，1=投诉处理-花絮正常，2=投诉处理-花絮违规，3=用户自己删除，4=管理员删除)

    @Autowired
    private ServService servService;
    
    public void save() {
    	save0();
    }
    public void savex() {
    	save0();
    }
    public void save0() {
        Message message = new Message();
        try {
        	com.cy.core.user.entity.User user = (com.cy.core.user.entity.User)getSession().get("user");
        	if(serv.getType() == 0) { //官方
        		serv.setUserId(user.getUserId());
        		serv.setAuditStatus(1);
        		serv.setRegion(province + " " + city);
        	}        	        	
        	
        	if(serv.getType() == 5) { //校友会
        		serv.setUserId(user.getUserId());
        		serv.setAuditStatus(0);
        		String region = servService.getRegionOfUser(user.getUserId());
        		serv.setRegion(region);
        	}
        	servService.save(serv);
        	
        	if(pics != null && pics.length > 0) {
        		long newId = servService.getNewId();
            	Map<String, Object> map = new HashMap<String, Object>();
            	map.put("serviceId", newId);
            	
            	for(String pic : pics) {
                    if(pic.indexOf(Global.URL_DOMAIN) == 0) {
                        pic = pic.substring(Global.URL_DOMAIN.length()) ;
                    }
            		map.put("pic", pic);
            		servService.savePic(map);            		
            	}
        	}
        	
            message.setMsg("保存成功");
            message.setSuccess(true);
        } catch (Exception e) {
            logger.error(e, e);
            message.setMsg("保存失败");
            message.setSuccess(false);
        }
        super.writeJson(message);
    }
    
    public void getList() {
    	getList0("");
    }
    public void getListx() {
    	com.cy.core.user.entity.User user = (com.cy.core.user.entity.User)getSession().get("user");
		getList0(Long.toString(user.getUserId()));
    }
    public void getList0(String userId) {
        Map<String, Object> map = new HashMap<String, Object>();
        String title = getRequest().getParameter("title");
        String type = getRequest().getParameter("type");
        String category = getRequest().getParameter("category");
        String region = getRequest().getParameter("region");
        String createFrom = getRequest().getParameter("createFrom");
        String createTo = getRequest().getParameter("createTo");
        String auditStatus = getRequest().getParameter("auditStatus");
        String status = getRequest().getParameter("status");
        
        map.put("page", page);
        map.put("rows", rows);
        map.put("title", title);
        map.put("type", type);
        map.put("category", category);
        map.put("region", region);
        map.put("createFrom", createFrom);
        map.put("createTo", createTo);
        map.put("auditStatus", auditStatus);
        map.put("status", status);
        
        map.put("userId", userId);
//        if (getUser().getRole().getSystemAdmin() != 1) {
        if (RoleUtil.isNotSystemAdmin(getUser())) {
        	map.put("deptList", getUser().getDepts());
        }
        super.writeJson(servService.dataGrid(map));
    }

    public void getServReply() {
    	getServReply0();
    }
    public void getServReplyx() {
    	getServReply0();
    }
    public void getServReply0() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("page", page);
        map.put("rows", rows);
        map.put("serviceId", id);
        super.writeJson(servService.dataGridForServComment(map));
    }
    
    public void getServComplaint() {
    	getServComplaint0();
    }
    public void getServComplaintx() {
    	getServComplaint0();
    }
    public void getServComplaint0() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("page", page);
        map.put("rows", rows);
        map.put("serviceId", id);
        super.writeJson(servService.dataGridForServComplaint(map));
    }


    public void getById() {
    	getById0();
    }
    public void getByIdx() {
    	getById0();
    }
    public void getById0() {
    	Serv s = servService.getById(serv.getId());
        super.writeJson(s);
    }
    
    public void update() {
    	update0();
    }
    public void updatex() {
    	update0();
    }
    public void update0() {
        Message message = new Message();
        try {
        	if(serv.getType() == 0) { //官方
	        	String region = province + " " + city;
	        	serv.setRegion(region);
        	}
        	servService.update(serv);
        	
        	servService.deletePic(serv.getId());
        	if(pics != null && pics.length > 0) {
            	Map<String, Object> map = new HashMap<String, Object>();
            	map.put("serviceId", serv.getId());
            	
            	for(String pic : pics) {
                    if(pic.indexOf(Global.URL_DOMAIN) == 0) {
                        pic = pic.substring(Global.URL_DOMAIN.length()) ;
                    }
            		map.put("pic", pic);
            		servService.savePic(map);            		
            	}
        	}            
            
            message.setMsg("保存成功");
            message.setSuccess(true);
        } catch (Exception e) {
            logger.error(e, e);
            message.setMsg("保存失败");
            message.setSuccess(false);
        }
        super.writeJson(message);
    }
    
    public void audit() {
    	audit0();
    }
    public void auditx() {
    	audit0();
    }
    public void audit0() {
        Message message = new Message();
        try {
        	com.cy.core.user.entity.User user = (com.cy.core.user.entity.User)getSession().get("user");
    		serv.setAuditUserId(user.getUserId());
            
    		servService.audit(serv);
            message.setMsg("审核成功");
            message.setSuccess(true);
        } catch (Exception e) {
            logger.error(e, e);
            message.setMsg("审核失败");
            message.setSuccess(false);
        }
        super.writeJson(message);
    }

    public void delete() {
    	Message message = new Message();
        try {
        	servService.delete(ids);
            message.setMsg("删除成功");
            message.setSuccess(true);
        } catch (Exception e) {
            logger.error(e, e);
            message.setMsg("删除失败");
            message.setSuccess(false);
        }
        super.writeJson(message);
    }
    public void deletex() {
    	Message message = new Message();
        try {
        	servService.deletex(ids);
            message.setMsg("删除成功");
            message.setSuccess(true);
        } catch (Exception e) {
            logger.error(e, e);
            message.setMsg("删除失败");
            message.setSuccess(false);
        }
        super.writeJson(message);
    }

    public void handleStatus() {
    	handleStatus0();
    }
    public void handleStatusx() {
    	handleStatus0();
    }
    public void handleStatus0() {
        Message message = new Message();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", id);
        map.put("handleStatus", handleStatus);
        try {
        	servService.handleStatus(map);
            message.setMsg("处理成功");
            message.setSuccess(true);
        } catch (Exception e) {
            logger.error(e, e);
            message.setMsg("处理失败");
            message.setSuccess(false);
        }
        super.writeJson(message);
    }
 
    public void viewServPic() {
    	viewServPic0();
    }
    public void viewServPicx() {
    	viewServPic0();
    }
    public void viewServPic0() {
    	List<Map<String, String>> servPicList = new ArrayList<Map<String,String>>();
		List<String> pics = servService.getPicByServId(id);
		for(String pic : pics) {
            if(pic.indexOf("http") < 0) {
                pic = Global.cy_server_url + pic ;
            }
			Map<String, String> map = new HashMap<String, String>();
			map.put("n", pic);  								 //normal 原图地址
			map.put("s", WebUtil.getPictureByType(pic, "MIN"));  //small  小缩略图地址
			map.put("b", WebUtil.getPictureByType(pic, "MAX"));  //big    大缩略图地址
			servPicList.add(map);
		}
		super.writeJson(servPicList);
	}

    public void saveReply() {
    	saveReply0(0);
    }
    public void saveReplyx() {
    	saveReply0(5);
    }
    public void saveReply0(int type) {
        Message message = new Message();
        try {
        	com.cy.core.user.entity.User user = (com.cy.core.user.entity.User)getSession().get("user");
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("serviceId", serv.getId());
            map.put("content", serv.getContent());
            map.put("type", type);
            map.put("userId", user.getUserId());

        	servService.saveReply(map);
            message.setMsg("保存成功");
            message.setSuccess(true);
        } catch (Exception e) {
            logger.error(e, e);
            message.setMsg("保存失败");
            message.setSuccess(false);
        }
        super.writeJson(message);
    }
	

	public Serv getServ() {
		return serv;
	}

	public void setServ(Serv serv) {
		this.serv = serv;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getProvince() {
		return province;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCity() {
		return city;
	}

	public void setPics(String[] pics) {
		this.pics = pics;
	}

	public String[] getPics() {
		return pics;
	}

	public void setHandleStatus(int handleStatus) {
		this.handleStatus = handleStatus;
	}

	public int getHandleStatus() {
		return handleStatus;
	}
    
}
