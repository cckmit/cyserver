package com.bscc.db.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>Title: TableEntity</p>
 * <p>Description: 数据库表信息</p>
 *
 * @author LiuZhen
 * @Company 博视创诚
 * @data 2016-08-31 10:54
 */
public class TableEntity implements Serializable {
    public static final String TABLE_COMMENT =
            "/*==============================================================*/\n" +
            "/* ${NUMBER}.Table: ${TABLE_NAME}   ${TABLE_COMMENT}                             */\n" +
            "/*==============================================================*/" ;

    private String tableName ;      // 表名称
    private String tableCommen ;    // 表描述
    private String changeSql ;      // 改变表SQL 语句

    private List<ColumnEntity> columnList = new ArrayList<ColumnEntity>() ;         // 表中字段列表
    private List<ColumnEntity> changeColumnList = new ArrayList<ColumnEntity>() ;   // 表中变化字段列表

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableCommen() {
        return tableCommen;
    }

    public void setTableCommen(String tableCommen) {
        this.tableCommen = tableCommen;
    }

    public String getChangeSql() {
        return changeSql;
    }

    public void setChangeSql(String changeSql) {
        this.changeSql = changeSql;
    }

    @Override
    public String toString() {
        return "\nTableEntity{" +
                "表名='" + tableName + '\'' +
                ", 表描述='" + tableCommen + '\'' +
                ", \n表变化SQL 语句='" + changeSql + '\'' +
                "} \n";
    }
}
