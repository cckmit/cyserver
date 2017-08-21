package com.cy.core.association.service;
import com.cy.base.entity.DataGrid;
import com.cy.core.association.dao.AssociationHistoryMapper;
import com.cy.core.association.entity.AssociationHistory;
import com.cy.core.classHandle.entity.ClassHandle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by cha0res on 12/13/16.
 */
@Service("associationHistoryService")
public class AssociationHistoryServiceImpl implements AssociationHistoryService {

    @Autowired
    AssociationHistoryMapper associationHistoryMapper;

    public DataGrid<AssociationHistory> selectAssociationHistory(Map<String, Object> map){
        DataGrid<AssociationHistory> dataGrid = new DataGrid<AssociationHistory>();
        long total = associationHistoryMapper.count(map);
        dataGrid.setTotal(total);
        int start = ((Integer) map.get("page") - 1) * (Integer) map.get("rows");
        map.put("start", start);
        List<AssociationHistory> list = associationHistoryMapper.selectAssociationHistory(map);
        dataGrid.setRows(list);
        return dataGrid;
    }

    public List<AssociationHistory> selectAssociationHistoryList(Map<String, Object> map){
        List<AssociationHistory> list = associationHistoryMapper.selectAssociationHistory(map);
        return list;
    }

    public AssociationHistory getById(String id){
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        AssociationHistory associationHistory = new AssociationHistory();
        List<AssociationHistory> list = associationHistoryMapper.selectAssociationHistory(map);
        if(list != null && list.size() > 0){
            associationHistory = list.get(0);
        }
        return associationHistory;
    }

    public void save(AssociationHistory associationHistory){
        associationHistoryMapper.save(associationHistory);
    }

    public void update(AssociationHistory associationHistory){
        associationHistoryMapper.update(associationHistory);
    }

    public void delete(String ids){
        String[] array = ids.split(",");
        List<String> list = new ArrayList<>();
		for (String id : array){
            list.add(id);
        }
        associationHistoryMapper.delete(list);
    }
}
