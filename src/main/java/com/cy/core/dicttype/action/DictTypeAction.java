package com.cy.core.dicttype.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cy.common.utils.CacheUtils;

import com.cy.common.utils.StringUtils;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.cy.base.action.AdminBaseAction;
import com.cy.base.entity.Message;
import com.cy.base.entity.Tree;
import com.cy.core.dict.entity.Dict;
import com.cy.core.dicttype.entity.DictType;
import com.cy.core.dicttype.service.DictTypeService;

@Namespace("/dicttype")
@Action(value = "dictTypeAction", results = {@Result(name = "showUpdate", location = "/page/admin/dicttype/editDictType.jsp")})
public class DictTypeAction extends AdminBaseAction {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(DictTypeAction.class);

    @Autowired
    private DictTypeService dictTypeService;

    private DictType dictTypeObj;

    private String dictTypeId;

    public DictType getDictTypeObj() {
        return dictTypeObj;
    }

    public void setDictTypeObj(DictType dictTypeObj) {
        this.dictTypeObj = dictTypeObj;
    }

    public String getDictTypeId() {
        return dictTypeId;
    }

    public void setDictTypeId(String dictTypeId) {
        this.dictTypeId = dictTypeId;
    }

    public void initDictKey() {
        //@SuppressWarnings("unchecked")
        //Map<String, Object> map = (Map<String, Object>) ServletActionContext.getServletContext().getAttribute(
        //        "dictionaryInfoMap");
        @SuppressWarnings("unchecked")
        List<DictType> allList = (List<DictType>) CacheUtils.get("dicts");
        List<Tree> tree = new ArrayList<Tree>();
        for (DictType dict : allList) {
            Tree node = new Tree();
            node.setText(dict.getDictTypeName());
            node.setId(dict.getDictTypeId());
            tree.add(node);
        }
        super.writeJson(tree);
    }

    public void getDictType() {
        HttpServletRequest request = ServletActionContext.getRequest();
        String dictTypeName = request.getParameter("dictTypeName");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("page", page);
        map.put("rows", rows);
        map.put("dictTypeName", dictTypeName);
        super.writeJson(dictTypeService.dataGridDictType(map));
    }

    @SuppressWarnings("unchecked")
    public void doNotNeedSecurity_getDict() {
        HttpServletRequest request = ServletActionContext.getRequest();
        String dictTypeName = request.getParameter("dictTypeName");
        //Map<String, Object> map = (Map<String, Object>) ServletActionContext.getServletContext().getAttribute(
        //        "dictionaryInfoMap");
        List<DictType> list = (List<DictType>) CacheUtils.get("dicts");
        List<Dict> dictList = new ArrayList<Dict>();
        if (list != null && list.size() > 0) {
            for (DictType dictType : list) {
                if (dictType.getDictTypeName().equals(dictTypeName)) {
                    dictList = dictType.getList();
                }
            }
        }
        super.writeJson(dictList);
    }

    public void addDictType() {
        Message j = new Message();
        String flag = dictTypeService.isRepetition(dictTypeObj);
        if (StringUtils.isNotBlank(flag) && !flag.equals("1")) {
            try {
                dictTypeService.addDictType(dictTypeObj);
                j.setSuccess(true);
                j.setMsg("新增成功");
            } catch (Exception e) {
                logger.error(e, e);
                j.setSuccess(false);
                j.setMsg("新增失败");
            }
        } else {
            j.setSuccess(false);
            j.setMsg("字典类别名称或值有重复，请重新输入！");
        }
        super.writeJson(j);
    }

    public void deleteDictType() {
        Message j = new Message();
        try {
            dictTypeService.deleteDictType(id);
            j.setSuccess(true);
            j.setMsg("删除成功");
        } catch (Exception e) {
            logger.error(e, e);
            j.setSuccess(false);
            j.setMsg("删除失败");
        }
        super.writeJson(j);
    }

    public String doNotNeedSessionAndSecurity_showUpdate() {
        HttpServletRequest request = ServletActionContext.getRequest();
        String id = request.getParameter("id");
        dictTypeObj = dictTypeService.selectById(id);
        return "showUpdate";
    }

    public void updateDictType() {
        Message j = new Message();
        String flag = dictTypeService.isRepetition(dictTypeObj);
        if (StringUtils.isNotBlank(flag) && !flag.equals("1")) {
            try {
                dictTypeService.updateDictType(dictTypeObj);
                j.setSuccess(true);
                j.setMsg("修改成功");
            } catch (Exception e) {
                logger.error(e, e);
                j.setSuccess(false);
                j.setMsg("修改失败");
            }
        } else {
            j.setSuccess(false);
            j.setMsg("字典类别名称或值有重复，请重新输入！");
        }
        super.writeJson(j);
    }

    public void getByDictTypeId() {

        DictType dictType = dictTypeService.selectById(dictTypeId);

        super.writeJson(dictType);
    }

}
