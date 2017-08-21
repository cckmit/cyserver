package com.cy.core.major.dao;

import java.util.List;
import java.util.Map;

import com.cy.core.major.entity.Major;
import com.cy.core.major.entity.MajorDept;

public interface MajorMapper {
	
	List<Major> query(Map<String, Object> map);

    long count(Map<String, Object> map);

    Major getById(long id);
    
    long countByName(String majorName);
    
    long countByIdName(Major major);
    
    Major getByName(String majorName);

    void addMajor(Major major);
    
    void addMajorDept(Map<String, Object> map);

    void multiaddMajor( List<String> lmajor );        //Lixun 2017-5-12 优化导入效率

    void multiaddMajorDept( List< Map<String, Object> > lmd );    //Lixun 2017-5-12 优化导入效率

    void updateMajor(Major major);

    void deleteMajor(List<Long> list);
    
    void deleteMajorDept(List<Long> list);
    
    void deleteMajorByDeptId(List<String> list);
    
    List<Major> getMajor(String deptId);
    
    List<Major> selectAll();
    
    List<MajorDept> getMajorAndDept(Map<String, Object> map);
    
    List<MajorDept> selectMajorAndDeptAll();
}
