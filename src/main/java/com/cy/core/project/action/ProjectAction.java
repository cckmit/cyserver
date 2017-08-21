package com.cy.core.project.action;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cy.common.utils.StringUtils;
import com.cy.core.project.entity.ProjectCost;
import com.cy.core.weiXin.entity.WeiXinAccount;
import com.cy.core.weiXin.service.WeiXinAccountService;
import com.cy.system.Global;
import com.cy.util.Base64Utils;
import com.cy.util.PairUtil;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.jfinal.weixin.sdk.api.SnsAccessTokenApi;
import org.apache.log4j.Logger;

import java.awt.image.BufferedImage;
import java.util.*;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.cy.base.action.AdminBaseAction;
import com.cy.base.entity.Message;
import com.cy.core.project.entity.Project;
import com.cy.core.project.service.ProjectService;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;

@Namespace("/project")
@Action(value = "projectAction", results = { @Result(name = "donateDetail", location = "/mobile/donate/donateDetail.jsp"),
		@Result(name = "donateForm", location = "/mobile/donate/donateForm.jsp") })
public class ProjectAction extends AdminBaseAction {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(ProjectAction.class);

	@Autowired
	private ProjectService projectService;
	@Autowired
	private WeiXinAccountService weiXinAccountService;

	private Project project;

	private String projectName;

	private String accountNum;

	private String checkFlag;

	private ProjectCost projectCost;

	private String projectId;

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public void dataGrid() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", rows);
		map.put("page", page);
		if (project !=null) {
			map.put("projectName", project.getProjectName());
			map.put("projectType", project.getProjectType());
			map.put("timeStatus", project.getTimeStatus());
			map.put("isCommand", project.getIsCommand());
		}
		super.writeJson(projectService.dataGrid(map));
	}

	public void save() {
		Message message = new Message();
		try {
			//富文本是否转换 Lixun 2017.5.5
			if( Global.IS_RICH_TEXT_CONVERT == 1 ) {
				String content = Base64Utils.getFromBase64(project.getContent().replaceAll("</?[^>]+>", ""));
				project.setContent(content);
			}
			// 检查捐赠项目是否重复
			Project checkProject = projectService.selectByProjectName(project.getProjectName());
			if (checkProject == null) {
				project.setCreateTime(new Date());
				if (getUser().getUserId() != 0) {
					project.setCreateId(getUser().getUserId());
				}
				projectService.save(project);
				message.setMsg("新增成功");
				message.setSuccess(true);
			} else {
				message.setMsg("新增失败,捐赠项目重复");
				message.setSuccess(false);
			}
		} catch (Exception e) {
			logger.error(e, e);
			message.setMsg("新增失败");
			message.setSuccess(false);
		}
		super.writeJson(message);
	}

	public void getById() {
		super.writeJson(projectService.selectById(id));
	}

	public void doNotNeedSecurity_getById() {
		super.writeJson(projectService.selectById(id));
	}

	public void delete() {
		Message message = new Message();
		try {
			projectService.delete(ids);
			message.setMsg("删除成功");
			message.setSuccess(true);
		} catch (Exception e) {
			logger.error(e, e);
			message.setMsg("删除失败");
			message.setSuccess(false);
		}
		super.writeJson(message);
	}

	public void update() {
		Message message = new Message();
		try {
			//富文本是否转换 Lixun 2017.5.5
			if( Global.IS_RICH_TEXT_CONVERT == 1 ) {
				if (project !=null && StringUtils.isNotBlank(project.getContent())) {
					String content = Base64Utils.getFromBase64(project.getContent().replaceAll("</?[^>]+>", ""));
					project.setContent(content);
				}
			}
			//下线与发布
			if(StringUtils.isNotBlank(checkFlag) && !"0".equals(checkFlag) && StringUtils.isNotBlank(ids)&& !"3".equals(checkFlag) && !"4".equals(checkFlag)){
				String[] idArray = ids.split(",");
				if (idArray != null) {
					for (String id : idArray) {
						Project item = projectService.selectById(Long.parseLong(id));
						item.setStatus(checkFlag);
						projectService.update(item);
					}
				}
				message.setMsg("操作成功");
				message.setSuccess(true);
			}else if(StringUtils.isNotBlank(checkFlag) && !"0".equals(checkFlag) && StringUtils.isNotBlank(ids)&& "3".equals(checkFlag)){
						try {
							projectService.updateCommand(ids);
							message.setMsg("设置成功");
							message.setSuccess(true);

						} catch (Exception e) {
							logger.error(e, e);
							message.setMsg("设置失败");
							message.setSuccess(false);
						}
						super.writeJson(message);
//				message.setMsg("操作成功");
//				message.setSuccess(true);
			}else if(StringUtils.isNotBlank(checkFlag) && !"0".equals(checkFlag) && StringUtils.isNotBlank(ids)&& "4".equals(checkFlag)){
				try {
					projectService.updateNotCommand(ids);
					message.setMsg("设置成功");
					message.setSuccess(true);

				} catch (Exception e) {
					logger.error(e, e);
					message.setMsg("设置失败");
					message.setSuccess(false);
				}
				super.writeJson(message);
//				message.setMsg("操作成功");
//				message.setSuccess(true);
			}
			else{
				// 检查捐赠项目是否重复
				Project checkProject = projectService.selectByProjectNameAndProjectId(project);
				if (checkProject == null) {
					projectService.update(project);
					message.setMsg("修改成功");
					message.setSuccess(true);
				} else {
					message.setMsg("修改失败,捐赠项目重复");
					message.setSuccess(false);
				}
			}
		} catch (Exception e) {
			logger.error(e, e);
			message.setMsg("修改失败");
			message.setSuccess(false);
		}
		super.writeJson(message);
	}

	public void costDataGrid(){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", rows);
		map.put("page", page);
		map.put("projectId", projectCost.getProjectId());
		super.writeJson(projectService.dataGridCost(map));
	}

	public void selectCostById(){
		super.writeJson(projectService.selectCostById(projectCost.getId()));
	}

	public void saveCost(){
		Message message = new Message();
		try {
			Project project = projectService.selectById(Long.parseLong(projectCost.getProjectId()));
			if(project == null){
				message.setMsg("项目不存在");
				message.setSuccess(false);
			}else{
				if(Double.parseDouble(project.getRestMoney()) < Double.parseDouble(projectCost.getCostMoney())){
					message.setMsg("已超出剩余金额");
					message.setSuccess(false);
				}else{
					projectService.saveCost(projectCost);
					message.setSuccess(true);
					message.setMsg("保存成功");
				}
			}
		} catch (Exception e) {
			logger.error(e, e);
			message.setMsg("保存失败");
			message.setSuccess(false);
		}
		super.writeJson(message);
	}

	public void updateCost(){
		Message message = new Message();
		try {
			Project project = projectService.selectById(Long.parseLong(projectCost.getProjectId()));
			ProjectCost oldProjectCost = projectService.selectCostById(projectCost.getId());
			if(project == null){
				message.setMsg("项目不存在");
				message.setSuccess(false);
			}else{
				if(oldProjectCost == null){
					message.setMsg("该支出记录不存在");
					message.setSuccess(false);
				}else if((Double.parseDouble(project.getRestMoney())+Double.parseDouble(oldProjectCost.getCostMoney())) < Double.parseDouble(projectCost.getCostMoney())){
					message.setMsg("已超出剩余金额");
					message.setSuccess(false);
				}else{
					projectService.updateCost(projectCost);
					message.setSuccess(true);
					message.setMsg("修改成功");
				}
			}
		} catch (Exception e) {
			logger.error(e, e);
			message.setMsg("修改失败");
			message.setSuccess(false);
		}
		super.writeJson(message);
	}

	public void deleteCost() {
		Message message = new Message();
		try {
			projectService.deleteCost(ids);
			message.setMsg("删除成功");
			message.setSuccess(true);
		} catch (Exception e) {
			logger.error(e, e);
			message.setMsg("删除失败");
			message.setSuccess(false);
		}
		super.writeJson(message);
	}

	/*public void typeDataGrid(){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", rows);
		map.put("page", page);
		if (projectType !=null) {
			map.put("name", projectType.getName());
		}
		super.writeJson(projectService.dataGridType(map));
	}*/

	public void doNotNeedSecurity_getAll() {
		super.writeJson(projectService.selectAll());
	}

	public void doNotNeedSessionAndSecurity_getAll() {
		super.writeJson(projectService.selectAll());
	}

	public String doNotNeedSessionAndSecurity_getById() {
		project = projectService.selectById(id);
		return "donateDetail";
	}

	public String doNotNeedSessionAndSecurity_getByIdForm() {
		project = projectService.selectById(id);
		return "donateForm";
	}

	public void doNotNeedSessionAndSecurity_listAll() {
		super.writeJson(projectService.listAll(page, rows,accountNum));
	}

	public String getAccountNum() {
		return accountNum;
	}

	public void setAccountNum(String accountNum) {
		this.accountNum = accountNum;
	}

	public String getCheckFlag() {
		return checkFlag;
	}

	public void setCheckFlag(String checkFlag) {
		this.checkFlag = checkFlag;
	}

	public ProjectCost getProjectCost() {
		return projectCost;
	}

	public void setProjectCost(ProjectCost projectCost) {
		this.projectCost = projectCost;
	}

	/*public ProjectType getProjectType() {
		return projectType;
	}

	public void setProjectType(ProjectType projectType) {
		this.projectType = projectType;
	}
*/
	/**
	 * 方法doNotNeedSecurity_getALLLabelList 的功能描述：获取所有的捐赠项目类型
	 * @createAuthor niu
	 * @createDate 2017-04-01 11:19:03
	 * @param
	 * @return void
	 * @throw
	 *
	 */
	public void doNotNeedSecurity_getALLDonateType() {

		List<PairUtil<String,String>>donateType = projectService.getALLDonateType();
		logger.info(JSON.toJSONString(donateType));
		super.writeJson(donateType);
	}


	/**
	 * 生成二维码
	 */
	private static final int BLACK = 0xFF000000;
	private static final int WHITE = 0xFFFFFFFF;
	public void doNotNeedSessionAndSecurity_getErWeiMa(){
		// 查询基金会公众号
		Map<String,Object> map = new HashMap<>();
		map.put("accountType","20");
		List<WeiXinAccount> weiXinAccountList = weiXinAccountService.getList(map);
		String appId = "";
		if(weiXinAccountList != null && weiXinAccountList.size()>0){
			appId = weiXinAccountList.get(0).getAccountAppId();
		}
		String qrUrl = Global.cy_server_url;
		if(!"/".equals(qrUrl.substring(qrUrl.length()-1, qrUrl.length()))){
			qrUrl += "/";
		}
		qrUrl += "foundation/donate_detail.html?projectId="+projectId;

		if(StringUtils.isNotBlank(appId)){
			String redirectUri = Global.wechat_api_callback_path + appId;
			qrUrl = SnsAccessTokenApi.getAuthorizeURL(appId, redirectUri, qrUrl, false);
		}
		getResponse().setDateHeader("Expires", 0);
		getResponse().setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
		getResponse().addHeader("Cache-Control", "post-check=0, pre-check=0");
		getResponse().setHeader("Pragma", "no-cache");
		getResponse().setContentType("image/jpg");
		try{
			int width = 300;
			int height = 300;
			//二维码的图片格式
			String format = "jpg";
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
		}catch (Exception e){
			logger.error(e, e);
		}

	}

}
