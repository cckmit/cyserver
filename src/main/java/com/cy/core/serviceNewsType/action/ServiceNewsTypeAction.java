package com.cy.core.serviceNewsType.action;

import com.cy.base.action.AdminBaseAction;
import com.cy.base.entity.Message;
import com.cy.core.serviceNewsType.entity.ServiceNewsType;
import com.cy.core.serviceNewsType.service.ServiceNewsTypeService;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Namespace("/serviceNewsType")
@Action(value = "serviceNewsTypeAction")
public class ServiceNewsTypeAction extends AdminBaseAction {

    private ServiceNewsType type;

    private String serviceNewsTypeId;

    private String serviceId;

    @Autowired
    private ServiceNewsTypeService serviceNewsTypeService;


    //获取服务新闻栏目列表
    public void serviceNewsTypeTree(){

        Map<String, Object> map = new HashMap<String, Object>();

        if(type != null){
            map.put("name", type.getName());
            map.put("serviceId", type.getServiceId());
        }

        List<ServiceNewsType> list = serviceNewsTypeService.query(map);

        super.writeJson(list);
    }

    //获取服务新闻栏目详情
    public void getById(){
        ServiceNewsType st = serviceNewsTypeService.getById(serviceNewsTypeId);
        super.writeJson(st);
    }

    //新增新闻栏目
    public void save(){
        Message message = new Message();
        ServiceNewsType st = serviceNewsTypeService.getByName(type.getName());
        if(st != null){
            message.setMsg("栏目名已存在");
            message.setSuccess(false);
        }else{
            type.preInsert();
            type.setIsMain(0);
            type.setDeptId("1");
            type.setParentId("0");
            type.setOrigin(1);
            serviceNewsTypeService.save(type);
            message.setMsg("新增成功");
            message.setSuccess(true);
        }
        super.writeJson(message);
    }

    //修改新闻栏目
    public void update(){
        Message message = new Message();

        ServiceNewsType st = serviceNewsTypeService.getByName(type.getName());
        if(st == null || type.getId().equals(st.getId())){
            type.preUpdate();
            type.setDeptId("1");
            type.setIsMain(0);
            type.setDeptId("1");
            type.setParentId("0");
            type.setOrigin(1);
            serviceNewsTypeService.update(type);
            message.setMsg("修改成功");
            message.setSuccess(true);
        }else{
            message.setMsg("栏目名已存在");
            message.setSuccess(false);
        }
        super.writeJson(message);

    }

    //删除新闻栏目
    public void delete(){

        Message message = new Message();

        Map<String, Object> map = new HashMap<String, Object>();

        ServiceNewsType st = serviceNewsTypeService.getById(serviceNewsTypeId);
        st.preUpdate();
        st.setDelFlag("1");
        serviceNewsTypeService.update(st);

        map.put("parentId", serviceNewsTypeId);

        List<ServiceNewsType> list = serviceNewsTypeService.query(map);
        if(list != null){
            for(ServiceNewsType tmp: list){
                tmp.preUpdate();
                tmp.setDelFlag("1");
                serviceNewsTypeService.update(tmp);
            }
        }
        message.setMsg("删除成功");
        message.setSuccess(true);
        super.writeJson(message);
    }

    public void doNotNeedSessionAndSecurity_serviceNewsTypeTree(){

        Map<String, Object> map = new HashMap<String, Object>();

        map.put("serviceId",serviceId);

        List<ServiceNewsType> list = serviceNewsTypeService.query(map);

        super.writeJson(list);
    }

    public ServiceNewsType getType() {
        return type;
    }

    public void setType(ServiceNewsType type) {
        this.type = type;
    }

    public String getServiceNewsTypeId() {
        return serviceNewsTypeId;
    }

    public void setServiceNewsTypeId(String serviceNewsTypeId) {
        this.serviceNewsTypeId = serviceNewsTypeId;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }
}
