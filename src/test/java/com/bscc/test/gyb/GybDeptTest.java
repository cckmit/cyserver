package com.bscc.test.gyb;

import com.cy.core.dept.dao.DeptMapper;
import com.cy.core.dept.entity.Dept;
import com.cy.core.dept.service.DeptService;
import com.google.common.collect.Maps;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Map;

/**
 * <p>Title: DeptTest</p>
 * <p>Description: </p>
 *
 * @author LiuZhen
 * @Company 博视创诚
 * @data 2016-09-07 17:07
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath*:spring*.xml"})
public class GybDeptTest {

    @Autowired
    DeptService deptService ;

    @Autowired
    DeptMapper deptMapper ;

    @Test
    public void deptMapperTest(){
//        Map<String,String> map = Maps.newHashMap() ;
////        map.put("isCurrent","1") ;
//        List<Dept> list = deptMapper.selectList(map) ;
//        System.out.println("-----> list : " + list.size());
    }

    @Test
    public void deptMapperInsertTest() {
//        Dept dept = new Dept() ;
//        dept.setDeptId("11111");
//        dept.setParentId("0");
//        dept.setDeptName("单元测试学校");
//        dept.setStatus("20");
//        deptMapper.insert(dept);
    }

    @Test
    public void deptMapperFindByIdTest() {
//        Dept dept = deptMapper.checkDeptId("11111") ;
//        System.out.println("----------> dept:" + dept);
    }

    @Test
    public void deptServiceFindCurrDeptTreeTest() {
//        List<Dept> list = deptService.findCurrDeptTree() ;
//
//        System.out.println("-----> list : " + list.size());
    }
}
