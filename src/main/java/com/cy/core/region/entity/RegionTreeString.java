package com.cy.core.region.entity;

import com.cy.base.entity.TreeString;
import com.google.common.collect.Lists;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author jiangling
 * @date 2016.07.21
 *
 */
public class RegionTreeString implements Serializable {
    private static final long serialVersionUID = 1L;

    private String level;       //地区层级: 1-国家;2-省;3-城市;4-地区
    private String areaCode;    //地区代码
    private String postCode;    //邮政编码

    private String id;          //区域编码,参见RegionMapper.xml 国家id=3.+地区id; 省id=2.+地区id; 市id=1.+地区id
    private String text;        //区域名称
    private String state = "open";// open,closed
    private boolean checked = false;
    private Object attributes;

    private String iconCls;
    private String pid;

    private boolean checkable;

    private List<RegionTreeString> children;

    // 构造函数
    public RegionTreeString( TreeString ts )
    {
        this.setId( ts.getId() );
        this.setText( ts.getText() );
        this.setAttributes( ts.getAttributes() );
        this.setState( ts.getState() );
        this.setChecked( ts.isChecked() );
        this.setIconCls( ts.getIconCls() );
        this.setPid( ts.getPid() );
        this.setCheckable( ts.isCheckable() );
        //children
        List<TreeString> lt = ts.getChildren();
        this.children = Lists.newArrayList();
        if( lt == null ) return;
        for( TreeString t : lt )
        {
            //将TreeString类型中节点node中的attributes中的level,areaCode,postCode放入到RegionTreeString类型中的节点node中
            RegionTreeString rtemp = new RegionTreeString( t );
            rtemp.setLevel( ((Map<String, Object>)t.getAttributes()).get("level").toString() );
            rtemp.setAreaCode( ((Map<String, Object>)t.getAttributes()).get("areaCode").toString() );
            rtemp.setPostCode( ((Map<String, Object>)t.getAttributes()).get("postCode").toString() );
            this.children.add( rtemp );
        }
    }


    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public List<RegionTreeString> getChildren() {
        return children;
    }

    public void setChildren(List<RegionTreeString> children) {
        this.children = children;
    }

    public boolean isCheckable() {return checkable; }

    public void setCheckable(boolean checkable) {this.checkable = checkable; }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public Object getAttributes() {
        return attributes;
    }

    public void setAttributes(Object attributes) {
        this.attributes = attributes;
    }

    public String getIconCls() {
        return iconCls;
    }

    public void setIconCls(String iconCls) {
        this.iconCls = iconCls;
    }

    @Override
    public String toString() {
        return "Tree [id=" + id + ", text=" + text + ", state=" + state + ", checked=" + checked + ", attributes="
                + attributes + ", children=" + children + ", iconCls=" + iconCls + ", pid=" + pid + "]";
    }

}
