package com.cy.core.serv.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cy.base.entity.DataGrid;
import com.cy.core.serv.dao.ServMapper;
import com.cy.core.serv.entity.ServComplaint;
import com.cy.core.serv.entity.Serv;
import com.cy.core.serv.entity.ServComment;

@Service("servService")
public class ServServiceImpl implements ServService {
	@Autowired
    private ServMapper servMapper;


    public DataGrid<Serv> dataGrid(Map<String, Object> map) {
        DataGrid<Serv> dataGrid = new DataGrid<Serv>();
        long total = servMapper.count(map);
        dataGrid.setTotal(total);
        int start = ((Integer) map.get("page") - 1) * (Integer) map.get("rows");
        map.put("start", start);
        List<Serv> list = servMapper.query(map);
        if(list != null && list.size() >0)
        {
			for(int i = 0; i < list.size(); i++)
			{
				boolean isJson;
				try
				{
					JSONObject jsonObject = JSONObject.fromObject(list.get(i).getContent());
					isJson = true;
				}
				catch(Exception e)
				{
					isJson = false;
				}
				if(isJson)
				{
					JSONObject reMap = JSONObject.fromObject(list.get(i).getContent());
					list.get(i).setRegion(reMap.get("address").toString());
					list.get(i).setContent(reMap.get("title").toString());
				}
			}
		}

        dataGrid.setRows(list);
        return dataGrid;
    }


    public Serv getById(long id) {
		Serv serv = servMapper.getById(id);

		/*if(serv !=null && StringUtils.isNotBlank(serv.getPic()) ){
			serv.setPic(Global.URL_DOMAIN + serv.getPic());
		}*/
		if(serv != null){
			boolean isJson;
			try
			{
				JSONObject jsonObject = JSONObject.fromObject(serv.getContent());
				isJson = true;
			}
			catch(Exception e)
			{
				isJson = false;
			}
			if(isJson)
			{
				JSONObject reMap = JSONObject.fromObject(serv.getContent());
				serv.setRegion(reMap.get("address").toString());
				String content = "标题："+ reMap.get("title").toString()+"\n\n类型："+ reMap.get("type").toString()+"\n\n要求："+reMap.get("requirement").toString()+"\n\n描述："+reMap.get("description").toString();
				serv.setContent(content);
			}
		}
        return serv;
    }


    public void save(Serv serv) {
        if (serv == null)
            throw new IllegalArgumentException("serv cannot be null!");

        servMapper.add(serv);
    }


    public void update(Serv serv) {
        if (serv == null)
            throw new IllegalArgumentException("serv cannot be null!");
        
        servMapper.update(serv);
    }


    public void delete(String ids) {
    	String[] array = ids.split(",");
		List<Long> list = new ArrayList<Long>();
		for (String id : array)
		{
			list.add(Long.parseLong(id));
		}
    	servMapper.delete(list);
    }
    
    public void deletex(String ids) {
    	String[] array = ids.split(",");
		List<Long> list = new ArrayList<Long>();
		for (String id : array)
		{
			list.add(Long.parseLong(id));
		}
    	servMapper.deletex(list);
	}


	public void audit(Serv serv) {
		if (serv == null)
            throw new IllegalArgumentException("serv cannot be null!");
        
        servMapper.audit(serv);
		
	}

	public DataGrid<ServComment> dataGridForServComment(Map<String, Object> map) {
		DataGrid<ServComment> dataGrid = new DataGrid<ServComment>();
        long total = servMapper.countServComment(map);
        dataGrid.setTotal(total);
        int start = ((Integer) map.get("page") - 1) * (Integer) map.get("rows");
        map.put("start", start);
        List<ServComment> list = servMapper.queryServComment(map);
        dataGrid.setRows(list);
        return dataGrid;
	}

	public void handleStatus(Map<String, Object> map) {
		servMapper.handleStatus(map);
	}


	public DataGrid<ServComplaint> dataGridForServComplaint(Map<String, Object> map) {
		DataGrid<ServComplaint> dataGrid = new DataGrid<ServComplaint>();
        long total = servMapper.countServComplaint(map);
        dataGrid.setTotal(total);
        int start = ((Integer) map.get("page") - 1) * (Integer) map.get("rows");
        map.put("start", start);
        List<ServComplaint> list = servMapper.queryServComplaint(map);
        dataGrid.setRows(list);
        return dataGrid;
	}

	public List<String> getPicByServId(long serviceId) {
		return servMapper.getPicByServId(serviceId);
	}

	public long getNewId() {
		return servMapper.getNewId();
	}

	public void savePic(Map<String, Object> map) {
		servMapper.addPic(map);
	}

	public void deletePic(long serviceId) {
		servMapper.deletePic(serviceId);
	}


	public void saveReply(Map<String, Object> map) {
		servMapper.addReply(map);
	}

	public String getRegionOfUser(long userId) {
		return servMapper.getRegionOfUser(userId);
	}

}
