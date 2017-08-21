package com.cy.core.industry.service;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.alibaba.fastjson.JSON;
import com.cy.base.entity.Message;
import com.cy.common.utils.CacheUtils;
import com.cy.common.utils.easyui.TreeStringUtil;
import com.cy.core.news.entity.TreeForNewsType;
import com.cy.core.user.entity.User;
import com.cy.system.ExcelUtil;
import com.cy.system.GetDictionaryInfo;
import com.cy.system.Global;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cy.base.entity.TreeString;
import com.cy.core.industry.dao.IndustryMapper;
import com.cy.core.industry.entity.Industry;

@Service("industryService")
public class IndustryServiceImpl implements IndustryService{

	@Autowired
	private IndustryMapper industryMapper;
	private Industry industry;

	@Override
	public List<Industry> selectByParentCode(String parentCode) {
		return industryMapper.selectByParentCode(parentCode);
	}

	/*@Override
	public DataGrid<Industry> selectIndustryList(Map<String, Object> map) {
		DataGrid<Industry> dataGrid = new DataGrid<Industry>();
		long count = industryMapper.countIndustry(map);
		dataGrid.setTotal(count);
		int start = ((Integer) map.get("page") - 1) * (Integer) map.get("rows");
		map.put("start", start);
		List<Industry> list = industryMapper.selectIndustryList(map);
		dataGrid.setRows(list);
		return dataGrid;
	}*/


	@Override
	public List<Industry> selectIndustryList(Map<String, Object> map) {
		return industryMapper.selectIndustryList(map);
	}
	@Override
	public void save(Industry industry) {
		try {
			if(industry.getId()==0) {
				industryMapper.save(industry);
			}else{
				industryMapper.update(industry);
			}
			GetDictionaryInfo.getInstance().loadIndustryDict();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void delete(String code) {
		try {
			industryMapper.delete(code);
			GetDictionaryInfo.getInstance().loadIndustryDict();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public int countByIndustryCode(Industry industry) {
		return industryMapper.countByIndustryCode(industry);
	}

	@Override
	public int countByIndustryValue(Industry industry) {
		return industryMapper.countByIndustryName(industry);
	}

	@Override
	public void parseTree(List<TreeString> trees, List<TreeString> allList) {
		for (TreeString tree : allList) {
			if (tree.getPid().equals("-1")) {
				trees.add(tree);
			}
		}
		for (TreeString tree : trees) {
			getChildren(tree, allList);
		}
		// 当查询的记录是树中的一个子节点
		if(trees.size()==0){
			for (TreeString tree : allList) {
				trees.add(tree);
			}
		}
	}

	private void getChildren(TreeString tree, List<TreeString> allList) {
		List<TreeString> children = getChild(tree.getId(), allList);
		if (children != null && children.size() > 0) {
			tree.setChildren(children);
			for (TreeString tree2 : children) {
				getChildren(tree2, allList);
			}
		}
	}

	private List<TreeString> getChild(String id, List<TreeString> allList) {
		List<TreeString> children = new ArrayList<TreeString>();
		if (allList != null && allList.size() > 0) {
			for (TreeString tree : allList) {
				if (tree.getPid().equals(id)) {
					children.add(tree);
				}
			}
		}
		return children;
	}

	@Override
	public Industry selectIndustryByCode(String code) {
		return industryMapper.selectIndustryByCode(code);
	}

	@Override
	public Industry selectIndustryByParentCode(String code) {
		return industryMapper.selectIndustryByParentCode(code);
	}

	@Override
	public List<Industry> selectIndustryTrees(Map<String, Object> map) {
		return industryMapper.selectIndustryTrees(map);
	}

	/**
	 *
	 * @param level	初始为1
	 * @param count	初始为1
	 * @param treeString
     * @return
     */
	public List<TreeString> findIndustryTree(int level ,int count ,TreeString treeString) {
		List<TreeString> list = Lists.newArrayList() ;

		Map<String,Object> map = Maps.newHashMap() ;
		if(treeString == null || StringUtils.isBlank(treeString.getId())){
			map.put("parentCode", -1);
		}else{
			map.put("parentCode", treeString.getId());
		}
		List<Industry> industryList = industryMapper.findIndustryList(map);
		if (industryList != null && !industryList.isEmpty()) {
			for (Industry industry : industryList) {
				TreeString node = new TreeString() ;
				node.setPid(industry.getParentCode());
				node.setId(industry.getCode());
				node.setText(industry.getValue());
				//递归查询传入的所有子集
				if(level != 0 && level <= count) {
					list.add(node);
				}else {
					node.setChildren(findIndustryTree(level,(count+1),node));
					list.add(node);
				}
			}
		}
		return list ;
	}

	/**
	 * 获取行业列表接口
	 * @param message
	 * @param content
	 */
	public void findIndustryList(Message message, String content){

		Map<String, Object>  map = JSON.parseObject(content, Map.class);

		String level = (String) map.get("level");
		String parentCode = (String) map.get("parentCode");

		//级数为空时默认为1查询父级列表
		if(StringUtils.isBlank(level)) level = "1" ;

		TreeString node = new TreeString() ;
		node.setId(parentCode);

		List<TreeString> tree = findIndustryTree(Integer.valueOf(level),1,node) ;

/*
		List<TreeString> treeList = new ArrayList<>();
		List<TreeString> rootTrees = new ArrayList<>();
		List<TreeString> tree = new ArrayList<TreeString>();

		if(StringUtils.isBlank(level)){
			map.put("level", 1);
		}else{
			map.put("level", Integer.parseInt(level));
		}
		if(StringUtils.isBlank(parentCode)){
			map.put("parentCode", -1);
		}else{
			map.put("parentCode", parentCode);
		}

		List<Industry> list = industryMapper.findIndustryList(map);

		*//*treeList =(List<TreeString>) CacheUtils.get("industryDicts");
		List<TreeString> treeTemp = TreeStringUtil.getChild(parentCode,treeList);
		for(TreeString t : treeTemp){
			List<TreeString> treeChild = TreeStringUtil.getChild(t.getId(),treeList);
			if(treeChild.size()>0){
				t.setState("closed");
			}
			tree.add(t);
		}*//*

		for( Industry alu : list ) {
			TreeForNewsType node = new TreeForNewsType();
			String pid = alu.getParentCode();
			node.setId( String.valueOf( alu.getCode() ) );
			node.setPid( String.valueOf( pid ) );
			node.setText(alu.getValue());

			if( "-1".equals(pid) ) {
				tree.add(node);
			}
			else
				treeList.add( node );
		}

		TreeStringUtil.parseTreeString( tree, treeList );

		if( StringUtils.isNotBlank(parentCode) && !parentCode.equals("-1") )
			message.setObj(treeList);
		else
			message.setObj(tree);*/
		message.setObj(tree);
		message.setMsg("获取列表成功");

		message.setSuccess(true);
	}

	@Override
	public String importDataBeforeThread( String url, User user )
	{
		final String sUrl = url;
		final User sUser = user;

		/*Lixun 2017-5-11 正在导入的状态*/
		if ( (Integer)CacheUtils.get( "isImport") > 0  )
			return "正在导入...";

		/*Lixun*/

		new Thread() {
			@Override
			public void run() {

				String re = importDataNew( sUrl, sUser );
			}
		}.start();

		return "";
	}


	public String importDataNew(String url, User user ) {

		if ( (Integer)CacheUtils.get( "isImport") > 0 )
			return "正在导入...";

		CacheUtils.put( "isImport", 1 );

		int rownumber = 0;
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
			// 文件保存目录路径
			String savePath = Global.DISK_PATH;

			// 文件保存目录URL
			String saveUrl = Global.URL_DOMAIN;
			url = savePath + url.replace(saveUrl, "");
			File file = new File(url);
			List<Object[]> list = ExcelUtil.parseExcel(file);
			CacheUtils.put( "nImportTotal", list.size() ) ;
			if (list.size() > 50000) {
				throw new RuntimeException("一次只能导入5万条数据");
			}
			List<Object[]> errorList = new ArrayList<Object[]>();
			// 整理数据开始


			CacheUtils.put( "isImport", 2 );	//导入状态
			if (list != null && list.size() > 0) {
				// 第一行为excel表头
				Object[] head = new Object[list.get(0).length + 1];
				for (int j = 0; j < head.length; j++) {
					if (j != head.length - 1) {
						head[j] = list.get(0)[j];
					} else {
						head[j] = "失败原因";
					}
				}
				errorList.add(head);

				/*Lixun 2017-5-12优化批量导入效率*/
				List<Industry> lub = new ArrayList<Industry>();		//行业缓存list
				Map<String,Object> map = new HashMap<>();
				List<Industry> all = industryMapper.selectIndustryList(map);
				int nCur = 0;	//Industry总数

				/*Lixun*/

				for (int i = 1; i < list.size(); i++) {
					rownumber = i;
					CacheUtils.put( "nImportNow", i );	//Lixun 导入状态 2017-5-11
					String code = ((String) list.get(i)[0]).trim();
					String value = ((String) list.get(i)[1]).trim();
					String parentCode = ((String) list.get(i)[2]).trim();
					String orderNo = ((String) list.get(i)[3]).trim();

					//chensheng 2017-5-12 添加 级别和所有父级
					String level = "";
					String sequence = "";

					if (code == null || "".equals(code.trim())) {
						// 行业代码为空
						Object[] content = new Object[list.get(i).length + 1];
						for (int j = 0; j < content.length; j++) {
							if (j != content.length - 1) {
								content[j] = list.get(i)[j];
							} else {
								content[j] = "姓名为空";
							}
						}
						errorList.add(content);
						continue;
					}
					if (value == null || "".equals(value.trim())) {
						// 行业名称为空
						Object[] content = new Object[list.get(i).length + 1];
						for (int j = 0; j < content.length; j++) {
							if (j != content.length - 1) {
								content[j] = list.get(i)[j];
							} else {
								content[j] = "姓名为空";
							}
						}
						errorList.add(content);
						continue;
					}
					if (parentCode == null || "".equals(parentCode.trim())) {
						// 父级code为空
						Object[] content = new Object[list.get(i).length + 1];
						for (int j = 0; j < content.length; j++) {
							if (j != content.length - 1) {
								content[j] = list.get(i)[j];
							} else {
								content[j] = "姓名为空";
							}
						}
						errorList.add(content);
						continue;
					}
					if (orderNo == null || "".equals(orderNo.trim())) {
						// 排序为空
						Object[] content = new Object[list.get(i).length + 1];
						for (int j = 0; j < content.length; j++) {
							if (j != content.length - 1) {
								content[j] = list.get(i)[j];
							} else {
								content[j] = "姓名为空";
							}
						}
						errorList.add(content);
						continue;
					}
					//正则判段是否为数字
					Pattern pattern = Pattern.compile("[0-9]*");
					Matcher isNum = pattern.matcher(orderNo);
					if(!isNum.matches()){
						// 排序不是数字
						Object[] content = new Object[list.get(i).length + 1];
						for (int j = 0; j < content.length; j++) {
							if (j != content.length - 1) {
								content[j] = list.get(i)[j];
							} else {
								content[j] = "排序不是数字";
							}
						}
						errorList.add(content);
						continue;
					}
					//父级行业代码是否存在，不存在返回错误
					boolean flag = false;
					boolean isHave = false;
					if(!parentCode.equals("-1")) {
						for (Industry in : all) {
							if (in.getCode().equals(parentCode)) {
								flag = true;
								level = Integer.parseInt(in.getLevel()) + 1 + "";
								sequence = in.getSequence() + "." + code;
								break;
							}
						}
					}
					for(Industry in:all){
						if(in.getCode().equals(code)){
							isHave = true;
							break;
						}
					}
					if(isHave){
						// 判断行业代码是否存在
						Object[] content = new Object[list.get(i).length + 1];
						for (int j = 0; j < content.length; j++) {
							if (j != content.length - 1) {
								content[j] = list.get(i)[j];
							} else {
								content[j] = "行业代码已存在";
							}
						}
						errorList.add(content);
						continue;
					}
					if(!flag){
						// 父级行业代码不存在
						Object[] content = new Object[list.get(i).length + 1];
						for (int j = 0; j < content.length; j++) {
							if (j != content.length - 1) {
								content[j] = list.get(i)[j];
							} else {
								content[j] = "父级行业代码不存在";
							}
						}
						errorList.add(content);
						continue;
					}
					Industry industry = new Industry();
					industry.setCode(code);
					industry.setValue(value);
					industry.setParentCode(parentCode);
					industry.setOrderNo(Integer.parseInt(orderNo));
					industry.setLevel(level);
					industry.setSequence(sequence);
					industry.setCreateuser(user.getUserId()+"");
					industry.setCreatetime(new Date());
					lub.add(industry);
					++nCur;
					if(nCur == 1000 || i == list.size()-1){
						if(lub.size()>0) {
							industryMapper.multisave(lub);
							lub = new ArrayList<Industry>();
							nCur = 0;
						}
					}
				}
				//Lixun 2017-5-12 处理最后一批保存
				if( nCur > 0 )
				{
					industryMapper.multisave( lub );
				}
			}
			System.gc();

			CacheUtils.put( "sImportResult", ExcelUtil.exportData(errorList) );
			CacheUtils.put( "isImport", 0 );
			return "";
		} catch (Exception e) {
			String str = "";
			if (rownumber > 0) {
				str = "第" + rownumber + "行数据导致数据导入失败(" + e.getMessage() + ")";
			} else {
				str = e.getMessage();
			}
			CacheUtils.put( "isImport", 0 );
			CacheUtils.put( "sImportResult", "1" + e.getMessage() );
			throw new RuntimeException(str, e);
		}
	}
}