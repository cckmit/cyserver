package com.bscc.test.lz;

import com.cy.core.chatGroup.service.ChatGroupService;
import com.cy.core.dept.dao.DeptMapper;
import com.cy.core.dept.entity.Dept;
import com.cy.core.dept.service.DeptService;
import com.cy.mobileInterface.alumni.service.AlumniService;
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
public class JpushTest {

    @Autowired
    DeptService deptService ;

    @Autowired
    DeptMapper deptMapper ;

    @Autowired
    ChatGroupService chatGroupService ;

    @Autowired
    AlumniService alumniService ;

    @Test
    public void checkAndDeleteEmptyGroup() {
//        String groupId = "" ;
//        chatGroupService.checkAndDeleteEmptyGroup(groupId) ;
    }

    @Test
    public void createDeptGroupAndAddMemberToGroup(){
//        Map<String,String> map = Maps.newHashMap() ;
////        map.put("isCurrent","1") ;
//        map.put("deptLength","16") ;
//        List<Dept> list = deptMapper.selectList(map) ;
//        if(list != null) {
//            int size = list.size() ;
//            System.out.println("********************************************共需创建班级群组个数:" + size + "********************************************");
//            int index = 0 ;
//            for(Dept dept : list) {
//                System.out.println("------> 初始化第" +(++index)+ "个班级群组("+dept.getDeptName()+"):");
//                chatGroupService.initInsertClassGroup(dept.getDeptId());
//            }
//        }
    }
    @Test
    public void deptMapperInsertChatGroupTest(){
//        Map<String,String> map = Maps.newHashMap() ;
////        map.put("isCurrent","1") ;
//        map.put("deptLength","16") ;
//        List<Dept> list = deptMapper.selectList(map) ;
//        if(list != null) {
//            int size = list.size() ;
//            System.out.println("********************************************共需创建班级群组个数:" + size + "********************************************");
//            int index = 0 ;
//            for(Dept dept : list) {
//                System.out.println("------> 初始化第" +(++index)+ "个班级群组("+dept.getDeptName()+"):");
//            }
//        }
//        chatGroupService.initInsertClassGroup("0001800080201506");
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

    @Test
    public void selectAlumniList() {
//        Map<String,String> map = Maps.newHashMap() ;
//        map.put("type","1") ;
//        map.put("userId","446") ;
//        map.put("currUserId","446") ;
//        map.put("isJoin","1");
//        List<Map<String,String>> list = alumniService.selectAlumni(map) ;
//        System.out.println(list);
    }
}
