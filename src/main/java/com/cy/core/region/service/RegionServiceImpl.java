package com.cy.core.region.service;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.cy.base.entity.Message;
import com.cy.base.entity.TreeString;
import com.cy.core.region.dao.*;
import com.cy.core.region.entity.*;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("regionService")
public class RegionServiceImpl implements RegionService {

	@Autowired
	private RegionMapper regionMapper;


	/**
	 * add by jiangling
	 * @param region
	 * @return
     */
	@Override
	public List<Region> selectRegionList( String region ) {
			return regionMapper.selectRegion( region );
	}

	@Override
	public List<Region> selectCountryList( )
	{
		return regionMapper.selectCountryList();
	}

	@Override
	public Region selectRegionById(String id) {
		return regionMapper.selectRegionById(id);
	}

	@Override
	public void update(Region region) {

		if("1".equals(region.getLevel())) {
			regionMapper.update1(region);
		}

		if("2".equals(region.getLevel())) {
			regionMapper.update2(region);
		}
		if("3".equals(region.getLevel())) {
			regionMapper.update3(region);
		}
		if("4".equals(region.getLevel())) {
			regionMapper.update4(region);
		}
	}


	/**
	 *
	 * @param level	初始为1
	 * @param count	初始为1
	 * @param treeString
	 * @return
	 */
	public List<TreeString> findRegionTree(int level , int count , TreeString treeString) {
		List<TreeString> list = Lists.newArrayList() ;

		Map<String,Object> map = Maps.newHashMap() ;
		if(treeString == null || StringUtils.isBlank(treeString.getId())){
			map.put("pid", -1);
		}else{
			map.put("pid", treeString.getId());
		}
		List<Region> regionList = regionMapper.selectRegionList(map);
		if (regionList != null && !regionList.isEmpty()) {
			for (Region region : regionList) {
				TreeString node = new TreeString() ;
				node.setPid(region.getPid());
				node.setId(region.getId());
				node.setText(region.getName());
				//递归查询传入的所有子集
				if(level != 0 && level <= count) {
					list.add(node);
				}else {
					node.setChildren(findRegionTree(level,(count+1),node));
					list.add(node);
				}
			}
		}
		return list ;
	}

	/**
	 * 获取地区列表接口
	 * @param message
	 * @param content
     */
	public void findRegionList(Message message, String content){
		Map<String, Object>  map = JSON.parseObject(content, Map.class);

		String level = (String) map.get("level");
		String pid = (String) map.get("pid");

		//级数为空时默认为1查询父级列表
		if(StringUtils.isBlank(level)) level = "1" ;

		TreeString node = new TreeString() ;
		node.setId(pid);

		List<TreeString> tree = findRegionTree(Integer.valueOf(level),1,node) ;
		message.setObj(tree);
		message.setMsg("获取列表成功");

		message.setSuccess(true);
	}
}