package com.cy.core.dict.entity;

import com.cy.system.Global;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

public class Dict implements Serializable {
	private static final long serialVersionUID = 1L;
	private long dictId;
	private long dictTypeId;
	private String dictName;
	private String dictValue;
	private String dictUrl;
	private String dictImage;   //字典图标绝对路径
	private String dictImageUrl;//字典图标相对路径
	private String dictTypeValue;

	public String getDictUrl() {
		return dictUrl;
	}

	public void setDictUrl(String dictUrl) {
		this.dictUrl = dictUrl;
	}

	public void setDictId(int dictId) {
		this.dictId = dictId;
	}

	public long getDictId() {
		return dictId;
	}

	public void setDictId(long dictId) {
		this.dictId = dictId;
	}

	public long getDictTypeId() {
		return dictTypeId;
	}

	public void setDictTypeId(long dictTypeId) {
		this.dictTypeId = dictTypeId;
	}

	public void setDictTypeId(int dictTypeId) {
		this.dictTypeId = dictTypeId;
	}

	public String getDictName() {
		return dictName;
	}

	public void setDictName(String dictName) {
		this.dictName = dictName;
	}

	public String getDictValue() {
		if (StringUtils.isNotBlank(dictValue)) {
			dictValue = dictValue.trim();
		}
		return dictValue;
	}

	public void setDictValue(String dictValue) {
		this.dictValue = dictValue;
	}

	public String getDictImage() {
		if(StringUtils.isBlank(dictImage) && StringUtils.isNotBlank(dictImageUrl) && StringUtils.isNotBlank(Global.URL_DOMAIN)) {
			if(dictImageUrl.indexOf("http") < 0) {
				dictImage = Global.URL_DOMAIN + dictImageUrl ;
			}else{
				dictImage=dictImageUrl;
			}
		}
		return dictImage;
	}

	public void setDictImage(String dictImage) {
		this.dictImage = dictImage;
	}

	public String getDictImageUrl() {
		if(StringUtils.isBlank(dictImageUrl) && StringUtils.isNotBlank(dictImage) && StringUtils.isNotBlank(Global.URL_DOMAIN)) {
			if(dictImage.indexOf(Global.URL_DOMAIN) == 0) {
				dictImageUrl = dictImage.substring(Global.URL_DOMAIN.length()) ;
			}else{
				dictImageUrl=dictImage;
			}
		}
		return dictImageUrl;
	}

	public void setDictImageUrl(String dictImageUrl) {
		this.dictImageUrl = dictImageUrl;
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
}
