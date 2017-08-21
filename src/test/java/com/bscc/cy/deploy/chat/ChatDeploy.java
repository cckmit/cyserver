package com.bscc.cy.deploy.chat;

import com.cy.core.chatContacts.entity.ChatContacts;
import com.cy.core.chatContacts.service.ChatContactsService;
import com.cy.core.chatGroup.entity.ChatGroup;
import com.cy.core.chatGroup.service.ChatGroupService;
import com.cy.core.chatGroupUser.entity.ChatGroupUser;
import com.cy.core.chatGroupUser.service.ChatGroupUserService;
import com.cy.core.dept.dao.DeptMapper;
import com.cy.core.dept.entity.Dept;
import com.cy.core.dept.service.DeptService;
import com.google.common.collect.Maps;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Map;

/**
 * <p>Title: ChatDeploy</p>
 * <p>Description: </p>
 *
 * @author LiuZhen
 * @Company 博视创诚
 * @data 2016-09-07 17:07
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath*:spring*.xml"})
public class ChatDeploy {

    @Autowired
    ChatGroupService chatGroupService;

    @Autowired
    ChatGroupUserService chatGroupUserService ;

    @Autowired
    ChatContactsService chatContactsService ;

    @Autowired
    DeptService deptService ;

    @Autowired
    DeptMapper deptMapper ;

//    @Test
//    public void test1(){
//        //用户名
//        String regex="[a-zA-Z]\\w{3,15}";
//        String str="user12323324";
//        System.out.println("\n\n-------------> "+str.matches(regex));
//    }
//
//    @Test
//    public void createDeptGroupAndAddMemberToGroup(){
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
//    }
//
//    @Test
//    public void deptMapperInsertChatGroupTest(){
//        chatGroupService.initInsertClassGroup("0001800080201506");
//    }
}
