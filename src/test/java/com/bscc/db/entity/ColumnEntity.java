package com.bscc.db.entity;

import java.io.Serializable;

/**
 * <p>Title: Columns</p>
 * <p>Description: 字段信息</p>
 *
 * @author LiuZhen
 * @Company 博视创诚
 * @data 2016-08-31 10:57
 */


public class ColumnEntity implements Serializable {
    public static final String ADD_COLUMN = "ADD COLUMN `${COLUMN_NAME}` ${COLUMN_TYPE} " ;
    public static final String DROP_COLUMN = "DROP COLUMN `${COLUMN_NAME}` ${COLUMN_TYPE} " ;
    public static final String CHANGE_COLUMN = "CHANGE COLUMN `${COLUMN_NEW_NAME}` `${COLUMN_OLD_NAME}` ${COLUMN_TYPE} " ;

    private String name ;           // 字段名称
    private String type ;           // 字段类型
    private String isNull ;         // 是否为空
    private String key ;            // 是否为主键
    private String defaultValue ;   // 默认值
    private String extra ;          // 额外
    private String comment ;        // 字段描述

    private String changeSql ;      // 字段变化语句

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIsNull() {
        return isNull;
    }

    public void setIsNull(String isNull) {
        this.isNull = isNull;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getChangeSql() {
        return changeSql;
    }

    public void setChangeSql(String changeSql) {
        this.changeSql = changeSql;
    }
}
