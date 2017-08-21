package com.cy.core.news.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cy.base.entity.DataGrid;
import com.cy.core.news.dao.WebNewsTypeMapper;
import com.cy.core.news.entity.News;
import com.cy.core.news.entity.NewsType;

@Service("webNewsTypeService")
public class WebNewsTypeServiceImpl implements WebNewsTypeService
{
	@Autowired
	private WebNewsTypeMapper webNewsTypeMapper;


	public DataGrid<NewsType> dataGrid(Map<String, Object> map){
		DataGrid<NewsType> dataGrid = new DataGrid<NewsType>();
		int total = webNewsTypeMapper.count(map);
		dataGrid.setTotal(total);
		int start = ((Integer) map.get("page") - 1) * (Integer) map.get("rows");
		map.put("start", start);
		List<NewsType> list = webNewsTypeMapper.query(map);
		long parent_id = (Long) map.get("parent_id");
		if(parent_id>0){
			NewsType webNewsType = this.getById(parent_id+"");
			for(NewsType type:list){
				type.setParent_name(webNewsType.getName());
			}
		}
		dataGrid.setRows(list);
		return dataGrid;
	}

	public List<NewsType> treeGird(Map<String, Object> map){
		return webNewsTypeMapper.query(map);
	}
	

	public NewsType getById(String id){
		return this.webNewsTypeMapper.getById(id);
	}


	public String save(NewsType type){
		long parent_id = type.getParent_id();
		if(parent_id>0){
			//添加子栏目
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("parent_id", parent_id);
			int count  = webNewsTypeMapper.count(map);
			if(count > 5){
				return "2";
			}
		}
		this.webNewsTypeMapper.save(type);
		return "1";
	}

	public void delete(List<Long> list,long parent_id){
		if(parent_id==0){								//该栏目为父栏目
			//父栏目删除
			this.webNewsTypeMapper.deleteById(list);
			//对应子栏目删除
			this.webNewsTypeMapper.deleteByPid(list);
		}else if(parent_id > 0){
			//子栏目删除									//该栏目为子栏目
			this.webNewsTypeMapper.deleteById(list);
		}
	}
	

	public void update(NewsType type){
		this.webNewsTypeMapper.update(type);
	}
	
	public void saveNews(News news) {
		this.webNewsTypeMapper.saveNews(news);
	}

	public long getNewId() {
		return this.webNewsTypeMapper.getNewId();
	}

	public void updateNews(News news) {
		this.webNewsTypeMapper.updateNews(news);
	}
	
	public void deleteNews(String typeId) {
		this.webNewsTypeMapper.deleteNews(typeId);
	}


	@Override
	public NewsType getByName(String name) {
		return webNewsTypeMapper.getByName(name);
	}

}
