package com.cy.core.event.action;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cy.base.action.AdminBaseAction;
import com.cy.base.entity.DataEntity;
import com.cy.base.entity.DataGrid;
import com.cy.base.entity.Message;
import com.cy.core.chatGroup.entity.ChatGroup;
import com.cy.core.chatGroup.service.ChatGroupService;
import com.cy.core.event.entity.Event;
import com.cy.core.event.entity.SignUserProfile;
import com.cy.core.event.service.EventService;
import com.cy.core.notify.utils.PushUtils;
import com.cy.core.weiXin.entity.WeiXinAccount;
import com.cy.core.weiXin.service.WeiXinAccountService;
import com.cy.system.Global;
import com.cy.util.Base64Utils;
import com.cy.util.WebUtil;
import com.google.code.kaptcha.Producer;
import com.jfinal.weixin.sdk.api.SnsAccessTokenApi;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import sun.misc.BASE64Decoder;


@Namespace("/event")
@Action(value = "eventAction")
public class EventAction extends AdminBaseAction {

	private static final Logger logger = Logger.getLogger(EventAction.class);

    private Event event;
    @Autowired
    private Producer captchaProducer;

    @Autowired
    private EventService eventService;

    @Autowired
    private WeiXinAccountService weiXinAccountService;

    @Autowired
    private ChatGroupService chatGroupService;
    
    private int handleStatus;  //花絮处理结果 (0=正常，1=投诉处理-花絮正常，2=投诉处理-花絮违规，3=用户自己删除，4=管理员删除)

    private int checkPage;

    private String ei;   // 活动编号
    private String an;   // 个人活动账号
    private String t ;   // 活动类型（0：官方活动，9：个人活动）
    private String ed ;  // 活动创建所属部门编号
    private String qr;   // 签到字符串

    private String name; //姓名
    private String isSignIn; //是否签到

    public String getEi() {
        return ei;
    }

    public void setEi(String ei) {
        this.ei = ei;
    }

    public String getAn() {
        return an;
    }

    public void setAn(String an) {
        this.an = an;
    }

    public String getT() {
        return t;
    }

    public void setT(String t) {
        this.t = t;
    }

    public String getEd() {
        return ed;
    }

    public void setEd(String ed) {
        this.ed = ed;
    }

    public int getCheckPage() {
        return checkPage;
    }

    public void setCheckPage(int checkPage) {
        this.checkPage = checkPage;
    }

    public String getQr() {
        return qr;
    }

    public void setQr(String qr) {
        this.qr = qr;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getIsSignIn() {
        return isSignIn;
    }

    public void setIsSignIn(String isSignIn) {
        this.isSignIn = isSignIn;
    }

    private List<Map<String, String>> boardPicList;  //花絮图片
    
    public void save() {
    	save0();    	
    }
    public void savex() {
    	save0();    	
    }
    public void savea(){
        save0();
    }
    private void save0() {
        //富文本是否转换 Lixun 2017.5.5
        if( Global.IS_RICH_TEXT_CONVERT == 1 ) {
            String content = Base64Utils.getFromBase64(event.getContent().replaceAll("</?[^>]+>", ""));
            event.setContent(content);
        }

        Message message = new Message();
        try {
        	com.cy.core.user.entity.User user = (com.cy.core.user.entity.User)getSession().get("user");
        	if(event.getType() == 0) { //官方活动     		
        		event.setUserId(user.getUserId());
        		event.setAuditStatus(1);
                String region = eventService.getRegionOfUser(user.getUserId()); //lixun
                event.setRegion(region);    //lixun
        	}
        	
        	if(event.getType() == 5) { //校友会活动  //lixun 这一块其实就作废了
        		event.setUserId(user.getUserId());
        		event.setAuditStatus(0);
        		String region = eventService.getRegionOfUser(user.getUserId());
        		event.setRegion(region);
        	}

        	if(event.getType() == 99){
        	    event.setUserId(user.getUserId());
                event.setAuditStatus(1);

                if(StringUtils.isNotBlank(user.getAssociationId())){
                    event.setDept_id(user.getAssociationId());
                }
                String region = eventService.getRegionOfUser(user.getUserId());
                event.setRegion(region);
            }
        	
        	//生成签到码
            if(event.isNeedSignIn()) {
            	event.setSignInCode(generateSignInCode());
            }

            event.preInsert();

            try {
                eventService.save(event);
                String eventId = event.getId();
                //String eventid=event.getId();
                String title=event.getTitle();
                String alumniId= event.getDept_id();
                String accountNum=String.valueOf(event.getUserId());
                HttpServletRequest request = ServletActionContext.getRequest();;
                String path = request.getContextPath();
                String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
                String eventUrl = basePath +"mobile/services/activity/"+ "detail.html?eventId=" + eventId+"&accountNum="+accountNum + "&sourceFlag=0";
                //System.out.println(eventUrl+"====================================");
                PushUtils.pushOfficalInsertEvent(alumniId,title,eventUrl,eventId,accountNum);
                // 创建群组
                ChatGroup group = new ChatGroup();
                group.setName(title);
                group.setIntroduction("后台创建返校计划群");
                chatGroupService.insert(group);
                String groupId = group.getId() ;
                //更新返校计划群字段
                Map<String, String> map = new HashMap<>();
                map.put("id",eventId);
                map.put("groupId",groupId);
                eventService.updateEventGroupId(map);
            } catch (Exception e ) {
                e.printStackTrace();
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

    public void getListOfficial() {
    	getList("0","", "");
    }
    public void getListAlumni() {
    	getList("5","", "");
    }
    public void getListPersonal() {
    	getList("9","", "");
    }
    public void getListAssociationEvent() {
        getList("99","", "");
    }
    public void getListAssociation(){
        getList("99", "", getUser().getAssociationId());
    }
    public void getListx() {
    	com.cy.core.user.entity.User user = (com.cy.core.user.entity.User)getSession().get("user");
		getList("5",Long.toString(user.getUserId()), "");
    }
    
    private void getList(String type, String userId, String associationId) {
        Map<String, Object> map = new HashMap<String, Object>();
        String title = getRequest().getParameter("title");
        String category = getRequest().getParameter("category");
        String place = getRequest().getParameter("place");
        String organizer = getRequest().getParameter("organizer");
        String startFrom = getRequest().getParameter("startFrom");
        String startTo = getRequest().getParameter("startTo");
        String endFrom = getRequest().getParameter("endFrom");
        String endTo = getRequest().getParameter("endTo");
        String userInfoId = getRequest().getParameter("userInfoId");
        String auditStatus = getRequest().getParameter("auditStatus");
        String status = getRequest().getParameter("status");
        String alumniId = getRequest().getParameter("alumniId");
        String region = getRequest().getParameter("region");
        if(StringUtils.isBlank(associationId)){
            associationId = getRequest().getParameter("associationId");
        }
        map.put("page", page);
        map.put("rows", rows);
        map.put("title", title);
        map.put("category", category);
        map.put("place", place);
        map.put("organizer", organizer);
        map.put("startFrom", startFrom);
        map.put("startTo", startTo);
        map.put("endFrom", endFrom);
        map.put("endTo", endTo);
        map.put("userInfoId", userInfoId);
        map.put("auditStatus", auditStatus);
        map.put("status", status);
        map.put("alumniId", alumniId);
        map.put("region", region);
        map.put("associationId",associationId);
        if(checkPage == 1){
            map.put("auditStatus", "0");
        }

        if( type.equals("0") )
            map.put("userAlumniId", getUser().getDeptId() );  //lixun
        else if (type.equals("9")) {
            map.put("authorityAlumniId",getUser().getDeptId()) ;
        }
        map.put("type", type);
        map.put("userId", userId);
        map.put("associationId", associationId);
        
//        if (getUser().getRole().getSystemAdmin() != 1 && type.equals("0")) {
        /* lixun
        if (RoleUtil.isNotSystemAdmin(getUser()) && type.equals("0")) {
        	map.put("deptList", getUser().getDepts());
        }*/
        super.writeJson(eventService.dataGrid(map));
    }
    
    public void getSignupPeople() {
    	getSignupPeople0();
    }
    public void getSignupPeoplex() {
    	getSignupPeople0();
    }
    private void getSignupPeople0() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("page", page);
        map.put("rows", rows);
        map.put("eventId", event.getId());
        map.put("isSignIn", isSignIn);
        map.put("name", name);
        DataGrid<SignUserProfile> dataGrid = eventService.dataGridForSignUser(map);
        super.writeJson(dataGrid);
    }
    
    public void getEventBoard() {
    	getEventBoard0();
    }
    public void getEventBoardx() {
    	getEventBoard0();
    }
    private void getEventBoard0() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("page", page);
        map.put("rows", rows);
        map.put("eventId", event.getId());
        super.writeJson(eventService.dataGridForEventBoard(map));
    }
    
    public void getEventBoardComment() {
    	getEventBoardComment0();
    }
    public void getEventBoardCommentx() {
    	getEventBoardComment0();
    }
    private void getEventBoardComment0() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("page", page);
        map.put("rows", rows);
        map.put("boardId", id);
        super.writeJson(eventService.dataGridForEventBoardComment(map));
    }
    
    public void getEventBoardComplaint() {
    	getEventBoardComplaint0();
    }
    public void getEventBoardComplaintx() {
    	getEventBoardComplaint0();
    }
    private void getEventBoardComplaint0() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("page", page);
        map.put("rows", rows);
        map.put("boardId", id);
        super.writeJson(eventService.dataGridForEventBoardComplaint(map));
    }
    
    public void getComplaintList() {
    	getComplaintList0(false);
    }
    public void getComplaintListx() {
    	getComplaintList0(true);
    }
    private void getComplaintList0(boolean isAlumni) {
        Map<String, Object> map = new HashMap<String, Object>();
        String status = getRequest().getParameter("status");
        if(status == null) {
        	status = "0";
        }
        map.put("page", page);
        map.put("rows", rows);
        map.put("status", status);
        if(isAlumni) {
        	com.cy.core.user.entity.User user = (com.cy.core.user.entity.User)getSession().get("user");
        	map.put("userId", user.getUserId());
        }
        super.writeJson(eventService.dataGridForComplaint(map));
    }

    public void getByIdOfficial() {
    	getById();
    }
    public void getByIdAlumni() {
    	getById();
    }
    public void getByIdPersonal() {
    	getById();
    }
    public void getByIdAssociation() {
        getById();
    }
    public void getByIdx() {
    	getById();
    }
    private void getById() {
    	super.writeJson(eventService.getById( event.getId() ));
    }
    
    public void update() {
    	update0();
    }
    public void updatex() {
    	update0();
    }
    public void updatea() {
        update0();
    }
    private void update0() {

        //富文本是否转换 Lixun 2017.5.5
        if( Global.IS_RICH_TEXT_CONVERT == 1 ) {
            String content = Base64Utils.getFromBase64(event.getContent().replaceAll("</?[^>]+>", ""));
            event.setContent(content);
        }

        Message message = new Message();
        try {
            //原先无签到码，现在需要，则生成
            if(event.isNeedSignIn() && event.getSignInCode().isEmpty()) {
            	event.setSignInCode(generateSignInCode());
            }
            //原先有签到码，现在不需要，则去掉
            if(!event.isNeedSignIn() && !event.getSignInCode().isEmpty()) {
            	event.setSignInCode("");
            }
            event.preUpdate();
        	eventService.update(event);
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
    public void auditAlumni() {
    	audit0();
    }
    public void auditx() {
    	audit0();
    }
    public void audit0() {
        Message message = new Message();
        try {
        	com.cy.core.user.entity.User user = (com.cy.core.user.entity.User)getSession().get("user");
    		event.setAuditUserId(user.getUserId());
            
        	eventService.audit(event);
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
    	delete0();
    }
    public void deletex() {
    	delete0();
    }
    public void deletea() {
        delete0();
    }
    private void delete0() {
        Message message = new Message();
        try {
        	eventService.delete(ids);
            message.setMsg("删除成功");
            message.setSuccess(true);
        } catch (Exception e) {
            logger.error(e, e);
            message.setMsg("删除失败");
            message.setSuccess(false);
        }
        super.writeJson(message);
    }
    
    public void undoDelete() {
    	undoDelete0();
    }
    public void undoDeletex() {
    	undoDelete0();
    }
    private void undoDelete0() {
        Message message = new Message();
        try {
        	eventService.undoDelete(event.getId());
            message.setMsg("恢复成功");
            message.setSuccess(true);
        } catch (Exception e) {
            logger.error(e, e);
            message.setMsg("恢复失败");
            message.setSuccess(false);
        }
        super.writeJson(message);
    }
    
    public void handleEventBoard() {
    	handleEventBoard0();
    }
    public void handleEventBoardx() {
    	handleEventBoard0();
    }
    private void handleEventBoard0() {
        Message message = new Message();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("boardId", id);
        map.put("handleStatus", handleStatus);
        try {
        	eventService.handleBoardStatus(map);
            message.setMsg("处理成功");
            message.setSuccess(true);
        } catch (Exception e) {
            logger.error(e, e);
            message.setMsg("处理失败");
            message.setSuccess(false);
        }
        super.writeJson(message);
    }
    
    public void handleComplaint() {
    	handleComplaint0();
    }
    public void handleComplaintx() {
    	handleComplaint0();
    }
    private void handleComplaint0() {
        Message message = new Message();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("boardId", id);
        map.put("handleStatus", handleStatus);

        try {
        	eventService.handleBoardStatus(map);
            message.setMsg("处理成功");
            message.setSuccess(true);
        } catch (Exception e) {
            logger.error(e, e);
            message.setMsg("处理失败");
            message.setSuccess(false);
        }
        super.writeJson(message);
    }
    
    public void viewEventBoardPic() {
    	viewEventBoardPic0();
    }
    public void viewEventBoardPicx() {
    	viewEventBoardPic0();
    }
    private void viewEventBoardPic0() {
    	boardPicList = new ArrayList<Map<String,String>>();
		List<String> pics = eventService.getPicByBoardId(id);
		for(String pic : pics) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("n", pic);  								 //normal 原图地址
			map.put("s", WebUtil.getPictureByType(pic, "MIN"));  //small  小缩略图地址
			map.put("b", WebUtil.getPictureByType(pic, "MAX"));  //big    大缩略图地址
			boardPicList.add(map);
		}
		super.writeJson(boardPicList);
	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}


	private String generateSignInCode() {
    	Random r = new Random(); 
    	int x = r.nextInt(9999); 
    	String code = String.format("%04d", x);
    	return code;
    }

	public void setHandleStatus(int handleStatus) {
		this.handleStatus = handleStatus;
	}

	public int getHandleStatus() {
		return handleStatus;
	}

	public List<Map<String, String>> getBoardPicList() {
		return boardPicList;
	}

	public void setBoardPicList(List<Map<String, String>> boardPicList) {
		this.boardPicList = boardPicList;
	}




    private static final int BLACK = 0xFF000000;
    private static final int WHITE = 0xFFFFFFFF;

    public void doNotNeedSessionAndSecurity_getErWeiMa(){
//        String qrUrl = "http://www.cymobi.com" ;
        String qrUrl = Global.cy_server_url + "/event/eventAction!doNotNeedSessionAndSecurity_wechatSign.action";
        getResponse().setDateHeader("Expires", 0);
        getResponse().setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        getResponse().addHeader("Cache-Control", "post-check=0, pre-check=0");
        getResponse().setHeader("Pragma", "no-cache");
        getResponse().setContentType("image/gif");
        Message message = new Message();

        if (StringUtils.isBlank(ei)){
//            message.setMsg("活动编号为空");
//            message.setSuccess(false);
//            return;

            qrUrl += "?err=活动编号为空" ;
        } else if (StringUtils.isBlank(an) && StringUtils.isBlank(ed)){
//            message.setMsg("账号id为空");
//            message.setSuccess(false);
//            return;
            qrUrl += "?err=活动所属者不能为空" ;
        } else {
            Event event = eventService.getById(ei);
            if (event == null) {
//                message.setMsg("该活动所有者不属于他");
//                message.setSuccess(false);
//                return;
                qrUrl += "?err=活动不存在";
            } else if ((an != null && !an.equals(String.valueOf(event.getUserInfoId()))) && (ed != null && !ed.equals(event.getDept_id()))) {
//                message.setMsg("该活动所有者不属于他");
//                message.setSuccess(false);
//                return;
                qrUrl += "?err=该活动所有者不属于他";
            } else {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("c", "409");
                jsonObject.put("ei", event.getId());
                String jsonStr = jsonObject.toJSONString();
//            String value = "http://www.cymobi.com?qr=" + jsonStr;
                qrUrl += "?qr=" + jsonStr;
            }
        }
        System.out.println("--------------> qrUrl : " + qrUrl);
        try{

            int width = 300;
            int height = 300;
            //二维码的图片格式
            String format = "gif";
            Hashtable hints = new Hashtable();
            //内容所使用编码
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
            BitMatrix bitMatrix = new MultiFormatWriter().encode(qrUrl,
                    BarcodeFormat.QR_CODE, width, height, hints);
            //生成二维码
            BufferedImage image = new BufferedImage(width, height,
                    BufferedImage.TYPE_INT_RGB);
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                   image.setRGB(x, y, bitMatrix.get(x, y) ? BLACK : WHITE);
                }
            }
            ServletOutputStream out = getResponse().getOutputStream();
            ImageIO.write(image,format, out);
            out.flush();
            out.close();
           /* Map<String,Object> map = new HashMap<>();
            map.put("QRimage",out);
            message.setMsg("二维码生成成功");
            message.setObj(map);
            message.setSuccess(true);*/
        }catch (Exception e){
//            message.setMsg("该活动编号不存在");
//            message.setSuccess(false);
            logger.error(e, e);
        }
//        super.writeJson(message);

    }


    public void doNotNeedSessionAndSecurity_wechatSign(){
        try {
            if(StringUtils.isBlank(qr)){
                getResponse().sendRedirect("/error/500.jsp") ;
                return;
            }
            Map<String, String> map = JSON.parseObject(qr,Map.class);
            String eventId = map.get("ei");
            Event event = eventService.getById(eventId);
            if(event == null){
                getResponse().sendRedirect("/error/500.jsp") ;
                return;
            }
            Map<String,Object> searchMap = new HashMap<String,Object>();
            searchMap.put("accountType", "10");
            List<WeiXinAccount> weiXinAccountList = weiXinAccountService.getList(searchMap);
            if(weiXinAccountList == null || weiXinAccountList.size() <= 0){
                //公众号不存在
                System.out.println("公众号不存在");
                getResponse().sendRedirect("/error/500.jsp") ;
                return;
            }
            String url = Global.cy_server_url + "/mobile/services/activity/detail.html?eventId="+eventId+",forWeChatSign=1";
            System.out.println("------------------------->>>>>>>>>>>>>>"+url);
            String appId = weiXinAccountList.get(0).getAccountAppId();
            String redirectUrl = Global.wechat_api_callback_path + appId;
            String newUrl = SnsAccessTokenApi.getAuthorizeURL(appId, redirectUrl, url, true);
            getResponse().sendRedirect(newUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
