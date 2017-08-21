package com.cy.core.dicttype.entity;

import java.io.Serializable;
import java.util.List;

import com.cy.common.utils.StringUtils;
import com.cy.core.dict.entity.Dict;

public class DictType implements Serializable {
	private static final long serialVersionUID = 1L;
	private long dictTypeId;
	private String dictTypeName;
	private String dictTypeValue;
	private List<Dict> list;

	public long getDictTypeId() {
		return dictTypeId;
	}

	public void setDictTypeId(long dictTypeId) {
		this.dictTypeId = dictTypeId;
	}

	public void setDictTypeId(int dictTypeId) {
		this.dictTypeId = dictTypeId;
	}

	public String getDictTypeName() {
		if (StringUtils.isNotBlank(dictTypeName)) {
			dictTypeName = dictTypeName.trim();
		}
		return dictTypeName;
	}

	public void setDictTypeName(String dictTypeName) {
		this.dictTypeName = dictTypeName;
	}

	public String getDictTypeValue() {
		if (StringUtils.isNotBlank(dictTypeValue)) {
			dictTypeValue = dictTypeValue.trim();
		}
		return dictTypeValue;
	}

	public void setDictTypeValue(String dictTypeValue) {
		this.dictTypeValue = dictTypeValue;
	}

	public List<Dict> getList() {
		return list;
	}

	public void setList(List<Dict> list) {
		this.list = list;
	}

}
