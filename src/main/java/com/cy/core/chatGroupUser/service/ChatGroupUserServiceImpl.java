package com.cy.core.chatGroupUser.service;

import com.alibaba.fastjson.JSON;
import com.cy.base.entity.DataGrid;
import com.cy.base.entity.Message;
import com.cy.core.chatGroup.dao.ChatGroupMapper;
import com.cy.core.chatGroup.entity.ChatGroup;
import com.cy.core.chatGroupUser.dao.ChatGroupUserMapper;
import com.cy.core.chatGroupUser.entity.ChatGroupUser;
import com.cy.common.utils.StringUtils;
import com.cy.core.notify.utils.PushUtils;
import com.cy.util.easemob.comm.utils.EasemobUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service("chatGroupUserService")
public class ChatGroupUserServiceImpl implements ChatGroupUserService {
	@Autowired
    private ChatGroupUserMapper chatGroupUserMapper;

    @Autowired
    private ChatGroupMapper chatGroupMapper;

    public List<ChatGroupUser> findList(ChatGroupUser chatGroupUser) {
        List<ChatGroupUser> list = chatGroupUserMapper.query(chatGroupUser);
        return list ;
    }

    public DataGrid<ChatGroupUser> dataGrid(ChatGroupUser chatGroupUser) {
        DataGrid<ChatGroupUser> dataGrid = new DataGrid<ChatGroupUser>();
        long total = chatGroupUserMapper.count(chatGroupUser);
        dataGrid.setTotal(total);
        int start = (Integer.valueOf(chatGroupUser.getPage()) - 1) * Integer.valueOf(chatGroupUser.getRows());
        chatGroupUser.setStart(String.valueOf(start));
        List<ChatGroupUser> list = chatGroupUserMapper.query(chatGroupUser);
        dataGrid.setRows(list);
        return dataGrid;
    }


    public ChatGroupUser getById(String id) {
        return chatGroupUserMapper.getById(id);
    }

    public void update(ChatGroupUser chatGroupUser) {
        if (chatGroupUser == null)
            throw new IllegalArgumentException("chatGroupUser cannot be null!");

        chatGroupUserMapper.update(chatGroupUser);
    }

    /**
     * 根据ID 集合删除 联系人信息
     */
    public void deleteByIdList(String ids) {
    	String[] array = ids.split(",");
		List<String> list = Arrays.asList(array);
		chatGroupUserMapper.deleteByIdList(list);
    }
    
    /**
     * 根据ID 删除联系人信息
     */
    public void delete(String id) {
    	ChatGroupUser chatGroupUser = new ChatGroupUser() ;
    	chatGroupUser.setId(id) ;
        chatGroupUser.preUpdate();
    	chatGroupUserMapper.delete(chatGroupUser);
    }

    /***********************************************************************
     *
     * 【联系人】相关API（以下区域）
     *
     * 注意事项：
     * 1、方法名-格式要求
     * 创建方法：save[Name]
     * 撤销方法：remove[Name]
     * 查询分页列表方法：find[Name]ListPage
     * 查询列表方法：find[Name]List
     * 查询详细方法：find[Name]
     *
     ***********************************************************************/


    /**
     * add jiangling
     * 删除群组用户
     * @param message
     * @param content
     */
    public void removeChatGroupUser(Message message, String content) {
        if(StringUtils.isBlank(content)) {
            message.setMsg("未传入参数");
            message.setSuccess(false);
            return;
        }
        ChatGroupUser chatGroupUser = JSON.parseObject(content,ChatGroupUser.class);

        if(chatGroupUser == null || StringUtils.isBlank(chatGroupUser.getMemberId())) {
            message.setMsg("未传入群组成员用户编号");
            message.setSuccess(false);
            return;
        }
        if(chatGroupUser == null || StringUtils.isBlank(chatGroupUser.getUserId())) {
            message.setMsg("未传入用户编号");
            message.setSuccess(false);
            return;
        }
        if(chatGroupUser == null || StringUtils.isBlank(chatGroupUser.getGroupId())) {
            message.setMsg("未传入群组编号");
            message.setSuccess(false);
            return;
        }

        ChatGroup group =  chatGroupMapper.queryChatGroupById(chatGroupUser.getGroupId());
        if(group == null){
            message.setMsg("查无此群");
            message.setSuccess(false);
            return;
        }

        if(!chatGroupUser.getUserId().equals(group.getUserId())) {
            message.init(false ,"当前用户不是群主,不能进行删除群成员操作",null);
            return;
        }



        List<ChatGroupUser> list = new ArrayList<ChatGroupUser>();
        String[] memberIds = chatGroupUser.getMemberId().split(",");
        for(String mb:memberIds){
            if(mb.equals(group.getUserId())) {
                message.setMsg("群主不能删除自己");
                message.setSuccess(false);
                return;
            }
            ChatGroupUser groupUser = new ChatGroupUser();
            groupUser.setUserId(mb);
            groupUser.setGroupId(chatGroupUser.getGroupId());
            ChatGroupUser queriedGroupUser = chatGroupUserMapper.queryGroupUser(groupUser);
            if(queriedGroupUser == null){
                message.setMsg("您选择了不存在于该组的成员");
                message.setSuccess(false);
                return;
            }
            list.add(queriedGroupUser);
        }

        for(ChatGroupUser cg:list){
            chatGroupUserMapper.updateDelFlag(cg);
        }

        ChatGroupUser groupUser = new ChatGroupUser();
        groupUser.setGroupId(chatGroupUser.getGroupId());
        long groupMemberNum = chatGroupUserMapper.count(groupUser) ;
       
        //将用户从环信群组中移除 by sky 2017-7-7
        if (chatGroupUser.getMemberId().indexOf(",")>0)  //多用户删除
        	EasemobUtils.deleteUsersFromGroup(group.getEasemobGroupId(),chatGroupUser.getMemberId()) ;
        else     //单用户删除
            EasemobUtils.deleteUserFromGroup(group.getEasemobGroupId(),chatGroupUser.getMemberId()) ;
        
        group.setTotal(String.valueOf(groupMemberNum));
        group.preUpdate();
        chatGroupMapper.update(group);
        
        //剔除群成员推送
        PushUtils.pushExitGroupAll(chatGroupUser.getUserId(),chatGroupUser.getGroupId(),chatGroupUser.getMemberId());
        message.setMsg("删除成功!");
        message.setSuccess(true);

    }

}
