package com.cy.core.industry.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cy.common.utils.CacheUtils;
import com.cy.common.utils.easyui.TreeStringUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import com.cy.base.action.AdminBaseAction;
import com.cy.base.entity.Message;
import com.cy.base.entity.TreeString;
import com.cy.core.industry.entity.Industry;
import com.cy.core.industry.service.IndustryService;

@Namespace("/industry")
@Action(value = "industryAction")
public class IndustryAction extends AdminBaseAction {

	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(IndustryAction.class);

	private Industry industry;

	private String parentCode;

	private String url;

	@Autowired
	private IndustryService industryService;

	/**
	 * 需优化
	 */
	public void doNotNeedSecurity_getIndustry2ComboBox() {
		super.writeJson(industryService.selectByParentCode(parentCode));
	}

	public void dataGrid() {
		Map<String, Object> map = new HashMap<String, Object>();
		List<TreeString> treeList = new ArrayList<TreeString>();
		List<TreeString> tree = new ArrayList<TreeString>();
		List<Industry> industryList = new ArrayList<Industry>();

		if(industry==null){
			industry =new Industry();
		}

		//没有查询条件输入,则展示全部信息
		if(StringUtils.isBlank(industry.getCode())&&StringUtils.isBlank(industry.getValue())) {
			treeList =(List<TreeString>) CacheUtils.get("industryDicts");
			List<TreeString> treeTemp = TreeStringUtil.getChild(parentCode,treeList);
			for(TreeString t : treeTemp){
				List<TreeString> treeChild = TreeStringUtil.getChild(t.getId(),treeList);
				if(treeChild.size()>0){
					t.setState("closed");
				}
				tree.add(t);
			}
		}else{
			map.put("code", industry.getCode());
			map.put("value", industry.getValue());
			industryList = industryService.selectIndustryTrees(map);

			for (Industry industry : industryList) {
				TreeString node = new TreeString();
				node.setId(industry.getCode());
				node.setPid(industry.getParentCode());
				node.setText(industry.getValue());
				node.setState( industry.getState() );	//lixun
				Map<String, Object> attributes = new HashMap<String, Object>();
				attributes.put("level", industry.getLevel());
				attributes.put("sequence", industry.getSequence());
				node.setAttributes(attributes);
				treeList.add(node);
			}

			industryService.parseTree(tree, treeList);
		}
		super.writeJson(tree);
	}

	public void save() {
		Message message = new Message();
		try {
			int codeCount = industryService.countByIndustryCode(industry);
			int valueCount = industryService.countByIndustryValue(industry);
			if (codeCount > 0) {
				message.setMsg("行业代码已被占用");
				message.setSuccess(false);
			} else if (valueCount > 0) {
				message.setMsg("行业名称已被占用");
				message.setSuccess(false);
			} else {
				if(industry.getId()==0) {
					industry.setCreateuser(getUser().getUserId() + "");
					industry.setCreatetime(new Date());
				}
				industry.setUpdatetime(new Date());
				industry.setUpdateuser(getUser().getUserId()+"");

				// add by jiangling
				//sequence = 上级机构的Sequence + 本级机构的getCode
                //根据父节点来查询上级机构的Sequence
				if(StringUtils.isBlank(industry.getParentCode())){
					industry.setParentCode("-1");
				}
                Industry aIndustry = industryService.selectIndustryByParentCode(industry.getParentCode());
				String sequenceCode="";
				if(aIndustry!=null) {
					sequenceCode = aIndustry.getSequence()+"."+industry.getCode();;
				}else{
					sequenceCode+=industry.getCode();
				}
				industry.setSequence(sequenceCode+"");
                String levelArray[] = sequenceCode.split("\\.");
                industry.setLevel(levelArray.length+"");
				industryService.save(industry);
				message.setMsg("保存成功");
				message.setSuccess(true);
			}
		} catch (Exception e) {
			logger.error(e, e);
			message.setMsg("保存失败");
			message.setSuccess(false);
		}
		super.writeJson(message);
	}

	public void delete() {
		Message message = new Message();
		try {
			List<TreeString> treeList = (List<TreeString>) CacheUtils.get("industryDicts");
			if(TreeStringUtil.getChild(industry.getCode(),treeList).size()>0){
				message.setMsg("该节点有子节点，请先删除子节点，再删除该节点！");
				message.setSuccess(false);
			}else{
				industryService.delete(industry.getCode());
				message.setMsg("删除成功");
				message.setSuccess(true);
			}
		} catch (Exception e) {
			logger.error(e, e);
			message.setMsg("删除失败");
			message.setSuccess(false);
		}
		super.writeJson(message);
	}

	/*Lixun 2017-5-11 正在导入的状态*/
	public void importState()
	{
		Map<String, Object> map = new HashMap<String, Object>();

		if ( (Integer)CacheUtils.get( "isImport") == 2 )
		{	//正在导入数据
			map.put( "state", 2 );
			map.put( "total", (Integer)CacheUtils.get( "nImportTotal") -1);
			map.put( "now", (Integer)CacheUtils.get( "nImportNow") );
		}
		else if ( (Integer)CacheUtils.get( "isImport") == 1 )
		{	//正在整理数据
			map.put( "state", 1 );
		}
		else
		{	//未导入数据
			map.put( "state", 0 );
			map.put( "url", (String)CacheUtils.get( "sImportResult") );
			if( CacheUtils.get( "sImportResult" ) != null )
				CacheUtils.remove( "sImportResult" );
			if( CacheUtils.get( "isImport" ) != null )
				CacheUtils.remove( "isImport" );
			if( CacheUtils.get( "nImportTotal" ) != null )
				CacheUtils.remove( "nImportTotal" );
			if( CacheUtils.get( "nImportNow" ) != null )
				CacheUtils.remove( "nImportNow" );
		}

		super.writeJson(map);
	}
	/*Lixun*/

	public void importData() {
		Message message = new Message();
		if( CacheUtils.get( "isImport" ) == null )
			CacheUtils.put( "isImport", 0 );
		if( CacheUtils.get( "nImportTotal" ) == null )
			CacheUtils.put( "nImportTotal", 0 ) ;
		if( CacheUtils.get( "nImportNow" ) == null )
			CacheUtils.put( "nImportNow", 0 );
		if( CacheUtils.get( "sImportResult" ) == null )
			CacheUtils.put( "sImportResult", "" );
		try {
			String result = "";
			/*Lixun 2017-5-11 正在导入的状态*/
			if ( (Integer)CacheUtils.get( "isImport") > 0 )
				result = "importing";
			else
				result = industryService.importDataBeforeThread( url, getUser() );
			/*Lixun*/

			message.setMsg(result);
			message.setSuccess(true);
		} catch (Exception e) {
			logger.error(e, e);
			message.setMsg("导入失败," + e.getMessage());
			message.setSuccess(false);
		}
		super.writeJson(message);
	}
	
	public void view(){
		super.writeJson(industryService.selectIndustryByCode(industry.getCode()));
	}

	public String getParentCode() {
		return parentCode;
	}

	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}

	public Industry getIndustry() {
		return industry;
	}

	public void setIndustry(Industry industry) {
		this.industry = industry;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}