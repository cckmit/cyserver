package com.cy.core.association.service;

/**
 * Created by cha0res on 12/13/16.
 */

import com.alibaba.fastjson.JSON;
import com.cy.base.entity.DataGrid;
import com.cy.base.entity.Message;
import com.cy.common.utils.StringUtils;
import com.cy.core.association.dao.AssociationHistoryMapper;
import com.cy.core.association.dao.AssociationMapper;
import com.cy.core.association.entity.Association;
import com.cy.core.association.entity.AssociationHistory;
import com.cy.core.dict.entity.Dict;
import com.cy.core.event.dao.EventMapper;
import com.cy.core.event.entity.Event;
import com.cy.core.user.dao.UserMapper;
import com.cy.core.user.entity.User;
import com.cy.core.user.entity.UserRole;
import com.cy.core.user.service.UserService;
import com.cy.core.userProfile.dao.UserProfileMapper;
import com.cy.core.userProfile.entity.UserProfile;
import com.cy.system.SecretUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("associationService")
public class AssociationServiceImpl implements AssociationService {

    @Autowired
    private AssociationMapper associationMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private AssociationHistoryMapper associationHistoryMapper;

    @Autowired
    private EventMapper eventMapper;

    @Autowired
    private UserProfileMapper userProfileMapper;


    @Override
    public List<Association> findList() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("isNoLimit", "1");
        List<Association> list = associationMapper.selectAssociation(map);
        return list;
    }

    @Override
    public Long findCount(Map<String, Object> map) {
        return null;
    }

    @Override
    public DataGrid<Association> dataGrid(Map<String, Object> map) {
        DataGrid<Association> dataGrid = new DataGrid<Association>();
        Long total = associationMapper.countAssociation(map);
        dataGrid.setTotal(total);
        int start = ((Integer) map.get("page") - 1) * (Integer) map.get("rows");
        map.put("start", start);
        List<Association> list = associationMapper.selectAssociation(map);
        dataGrid.setRows(list);
        return dataGrid;
    }

    @Override
    public int saveAssociation(Association association) {
        association.preInsert();
        User user = new User();
        if (StringUtils.isNotBlank(association.getUserAccount()) && StringUtils.isNotBlank(association.getUserPassword()) && StringUtils.isNotBlank(association.getTel())) {
            User userTmp = userMapper.selectByUserAccount(association.getUserAccount());
            if(userTmp != null){
                return 1;
            }
            user.setUserName(association.getConcatName());
            user.setUserAccount(association.getUserAccount());
            user.setUserPassword(SecretUtil.encryptToSHA(association.getUserPassword()));
            user.setTelephone(association.getTel());
            user.setFlag(99);
            user.setAssociationId(association.getId());
            user.setDeptId(Long.parseLong(association.getAlumniId()));
            userMapper.save(user);
            //用户关联角色
            UserRole userRole = new UserRole();
            userRole.setUserId(user.getUserId());
            userRole.setRoleId(22);
            userMapper.insertUserAndRole(userRole);
            association.setUserId(String.valueOf(user.getUserId()));
        }
        associationMapper.insert(association);
        return 0;
    }

    @Override
    public int deleteAssociation(String ids) {
        String[] array = ids.split(",");
        List<String> list = new ArrayList<>();
        for (String id : array) {
            list.add(id);
            Association association = associationMapper.getAssociationById(id);
            if (StringUtils.isNotBlank(association.getUserId())) {
                userService.delete(Long.parseLong(association.getUserId()));
            }
        }
        associationMapper.delete(list);
        return 0;
    }

    @Override
    public int updateAssociation(Association association) {
        association.preUpdate();
        if(StringUtils.isNotBlank(association.getUserAccount()) && StringUtils.isNotBlank(association.getUserPassword()) && StringUtils.isNotBlank(association.getTel())) {
            User user = userMapper.selectByUserId(association.getUserId());
            if (user != null) {
                if (!association.getUserPassword().equals(user.getUserPassword())) {
                    association.setUserPassword(SecretUtil.encryptToSHA(association.getUserPassword()));
                }
                user.setTelephone(association.getTel());
                userMapper.update(user);
            }
        }
        associationMapper.update(association);
        return 1;
    }


    @Override
    public Association getAssociationById(String id) {
        Association association = associationMapper.getAssociationById(id);
        if(association != null){
            Map<String, Object> map = new HashMap<>();
            map.put("associationOldId", association.getId());
            map.put("status", "20");
            List<AssociationHistory> list = associationHistoryMapper.selectAssociationHistory(map);
            if(list != null && list.size()>0){
                association.setHistoryList(list);
            }
            map.put("status", "10");
            list = associationHistoryMapper.selectAssociationHistory(map);
            if(list != null && list.size() > 0){
                association.setCurrentChange(list.get(0));
            }
        }
        return association;
    }


    //获取社团类型标签
    public List<Dict> getAssociationType() {
        return associationMapper.getAssociationType("23");
    }


    /***********************************************************************
     * 【社团列表】相关API（以下区域）
     * <p>
     * 注意事项：
     * 1、方法名-格式要求
     * 创建方法：save[Name]
     * 撤销方法：remove[Name]
     * 查询分页列表方法：find[Name]ListPage
     * 查询列表方法：find[Name]List
     * 查询详细方法：find[Name]
     ***********************************************************************/


    public void findAssociationList(Message message, String content) {
        if (StringUtils.isBlank(content)) {
            message.setMsg("未传入参数");
            message.setSuccess(false);
            return;
        }

        Map<String, Object> map = JSON.parseObject(content, Map.class);
        String page = (String) map.get("page");
        String rows = (String) map.get("rows");
        String userAccount = (String) map.get("userAccount");
        String type = (String) map.get("type");
        String top = (String)map.get("top");

        Map<String, Object> map2 = new HashMap<>();

        if("1".equals(type) || "2".equals(type) || "3".equals(type)){
            if(StringUtils.isBlank(userAccount)){
                message.init(false,"请传入用户ID", null);
                return;
            }else{
                UserProfile userProfile = userProfileMapper.selectByAccountNum(userAccount);
                if(userProfile == null){
                    message.init(false,"用户不存在", null);
                    return;
                }
            }
        }
        if(StringUtils.isNotBlank(userAccount)){
            map2.put("userAccount", userAccount);
        }

        if(StringUtils.isBlank(type) || "0".equals(type)){
            //查詢全部
        }else if("1".equals(type)){
            //查詢加入的
            map2.put("inType","20");
        }else if("2".equals(type)){
            //查詢申請中
            map2.put("inType","10");
        }else if("3".equals(type)){
            //查詢未加入或者退出的
            map2.put("inType", "30");
        }else{
            message.init(false,"非法的查询类型",null);
            return;
        }
        if(StringUtils.isNotBlank(top)){
            map2.put("top", top);
        }

        if (StringUtils.isNotBlank(page) && StringUtils.isNotBlank(rows)) {
            int start = (Integer.valueOf(page) - 1) * Integer.valueOf(rows);
            map2.put("start", start);
            map2.put("rows", Integer.valueOf(rows));
        }else{
            map2.put("isNoLimit", "1");
        }

        List<Association> list = associationMapper.selectAssociation(map2);
        DataGrid<Association> dataGrid = new DataGrid<>();
        long total = associationMapper.countAssociation(map2);

        dataGrid.setTotal(total);
        dataGrid.setRows(list);

        message.init(true, "查询成功", dataGrid, null);

    }

    /**
     *　获取社团详情
     * @param message
     * @param content
     */
    public void findAssociationInfo(Message message, String content){
        if (StringUtils.isBlank(content)) {
            message.setMsg("未传入参数");
            message.setSuccess(false);
            return;
        }
        Map<String, String> map = JSON.parseObject(content, Map.class);
        String associationId = map.get("associationId");
        String userAccount = map.get("userAccount");
        if(StringUtils.isBlank(associationId)){
            message.setMsg("请传入社团ID");
            message.setSuccess(false);
            return;
        }

        Association association = associationMapper.getAssociationInfoByAssociationAndUserId(map);
        if(association == null){
            message.init(false,"社团不存在", null);
        }else{
            Map<String, Object> tmp = new HashMap<>();
            tmp.put("start", 0);
            tmp.put("rows", 5);
            tmp.put("currUserId", userAccount);
            tmp.put("associationId", associationId );
            tmp.put("type", "99");
            List<Event> eventList = eventMapper.query(tmp) ;
            association.setEventList(eventList);
            message.init(true, "获取社团详情成功", association);
        }
    }
}
