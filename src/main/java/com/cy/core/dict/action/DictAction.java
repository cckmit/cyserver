package com.cy.core.dict.action;

import com.cy.common.utils.StringUtils;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.cy.base.action.AdminBaseAction;
import com.cy.base.entity.Message;
import com.cy.core.dict.entity.Dict;
import com.cy.core.dict.service.DictService;

@Namespace("/dict")
@Action(value = "dictAction", results = { @Result(name = "showUpdate", location = "/page/admin/dict/editDict.jsp") })
public class DictAction extends AdminBaseAction {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(DictAction.class);

	@Autowired
	private DictService dictService;
	private Dict dictObj;
	private String dictId;  //字典id

	public Dict getDictObj() {
		return dictObj;
	}

	public void setDictObj(Dict dictObj) {
		this.dictObj = dictObj;
	}

	public void getDict() {
		super.writeJson(dictService.selectByDictTypeId(id));
	}

	public String getDictId() {
		return dictId;
	}

	public void setDictId(String dictId) {
		this.dictId = dictId;
	}

	public void addDict() {
		Message j = new Message();
		//判断是否有重复 1表示有
		String flag = dictService.isRepetition(dictObj);
		if (StringUtils.isNotBlank(flag)&& !flag.equals("1")){
			try {
				dictService.addDict(dictObj);
				j.setSuccess(true);
				j.setMsg("新增成功");
			} catch (Exception e) {
				logger.error(e, e);
				j.setSuccess(false);
				j.setMsg("新增失败");
			}
		}else {
			j.setSuccess(false);
			j.setMsg("字典值有重复，请重新输入！");
		}
		super.writeJson(j);
	}

	public void deleteDict() {
		Message j = new Message();
		try {
			dictService.deleteDict(id);
			j.setSuccess(true);
			j.setMsg("删除成功");
		} catch (Exception e) {
			logger.error(e, e);
			j.setSuccess(false);
			j.setMsg("删除失败");
		}
		super.writeJson(j);
	}

	public String doNotNeedSessionAndSecurity_showUpdate() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String id = request.getParameter("id");
		dictObj = dictService.selectDictById(id);
		return "showUpdate";
	}

	public void updateDict() {
		Message j = new Message();
		//判断是否有重复 1表示有
		String flag = dictService.isRepetition(dictObj);
		if (StringUtils.isNotBlank(flag)&& !flag.equals("1")){
			try {
				dictService.updateDict(dictObj);
				j.setSuccess(true);
				j.setMsg("修改成功");
			} catch (Exception e) {
				logger.error(e, e);
				j.setSuccess(false);
				j.setMsg("修改失败");
			}
		}else {
			j.setSuccess(false);
			j.setMsg("字典值有重复，请重新输入！");
		}
		super.writeJson(j);
	}
	/**
	 * 方法getByDictId 的功能描述：通过字典id获取字典详情
	 * @createAuthor niu
	 * @createDate 2017-04-17 16:05:52
	 * @param
	 * @return void
	 * @throw
	 *
	 */
	public void getByDictId() {
		Dict dict = dictService.selectDictById(dictId);
		super.writeJson(dict);
	}

}
