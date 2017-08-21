package com.bscc.db.tool;

import com.bscc.db.entity.ColumnEntity;
import com.bscc.db.entity.DBEntity;
import com.bscc.db.entity.TableEntity;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>Title: DBHelper</p>
 * <p>Description: 数据库连接 </p>
 *
 * @author LiuZhen
 * @Company 博视创诚
 * @data 2016-08-31 09:57
 */
public class DBHelper {
    public static final String URL = "jdbc:mysql://127.0.0.1:3306/cy_v1";
    public static final String DRIVER_NAME = "com.mysql.jdbc.Driver";
    public static final String USERNAME = "root";
    public static final String PASSWORD = "123456";

    public Connection conn = null;
    public PreparedStatement pst = null;

    /**
     * 数据库连接
     * @param sql
     */
    public DBHelper(String sql) {
        try {
            Class.forName(DRIVER_NAME);//指定连接类型
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);//获取连接
            pst = conn.prepareStatement(sql);//准备执行语句
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 数据库连接
     * @param url
     * @param username
     * @param password
     * @param sql
     */
    public DBHelper(String url, String username ,String password ,String sql) {
        try {
            Class.forName(DRIVER_NAME);//指定连接类型
            conn = DriverManager.getConnection(url, username, password);//获取连接
            pst = conn.prepareStatement(sql);//准备执行语句
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 数据库连接
     * @param sql
     */
    public DBHelper(DBEntity db, String sql) {
        try {
            Class.forName(db.getDriveName());//指定连接类型
            conn = DriverManager.getConnection(db.getUrl()+db.getDbName(), db.getUsername(), db.getPassword());//获取连接
            pst = conn.prepareStatement(sql);//准备执行语句
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取当前数据库表信息
     * @return
     */
    public static List<TableEntity> findDBTableList(DBEntity db) {
        List<TableEntity> list = new ArrayList<TableEntity>() ;
        String sql = "SELECT TABLE_NAME,TABLE_COMMENT FROM information_schema.tables WHERE table_schema = '"+db.getDbName()+"' AND table_type='base table'" ;
//        System.out.println("---> sql: " + sql );
        DBHelper dbHelper = null;
        ResultSet ret = null;

        dbHelper = new DBHelper(db ,sql);    //创建DBHelper对象

        try {
            ret = dbHelper.pst.executeQuery();//执行语句，得到源表结果集
            while (ret.next()) {
                TableEntity table = new TableEntity() ;
                table.setTableName(ret.getString(1));
                table.setTableCommen(ret.getString(2));
                if(table != null && table.getTableName() != null && !"".equals(table.getTableName().trim())) {
                    list.add(table);
                }
            }//显示数据
            ret.close();
            dbHelper.close();//关闭连接

        } catch (SQLException e) {
            e.printStackTrace();
        }
//        System.out.println("---> list :" + list.size());
        return list ;
    }

    /**
     * 获取当前数据库表信息
     * @return
     */
    public static List<ColumnEntity> findDBColumnList(DBEntity db ,String tableName) {
        List<ColumnEntity> list = new ArrayList<ColumnEntity>() ;
        String sql = "SELECT\n" +
                "\tcolumn_name,\n" +
                "\tcolumn_type,\n" +
                "\tis_nullable,\n" +
                "\tcolumn_key,\n" +
                "\tcolumn_default,\n" +
                "\textra,\n" +
                "\tcolumn_comment\n" +
                "FROM\n" +
                "\tinformation_schema. COLUMNS\n" +
                "WHERE\n" +
                "\ttable_schema = '"+db.getDbName()+"'\n" +
                "AND table_name = '"+tableName+"'" ;
//        System.out.println("---> sql: " + sql );
        DBHelper dbHelper = null;
        ResultSet ret = null;

        dbHelper = new DBHelper(db ,sql);    //创建DBHelper对象

        try {
            ret = dbHelper.pst.executeQuery();//执行语句，得到源表结果集
            while (ret.next()) {
                ColumnEntity column = new ColumnEntity() ;
                column.setName(ret.getString(1));
                column.setType(ret.getString(2));
                column.setIsNull(ret.getString(3));
                column.setKey(ret.getString(4));
                column.setDefaultValue(ret.getString(5));
                column.setExtra(ret.getString(6));
                column.setComment(ret.getString(7));
                if(column != null && column.getName() != null && !"".equals(column.getName().trim())) {
                    list.add(column);
                }
            }//显示数据
            ret.close();
            dbHelper.close();//关闭连接

        } catch (SQLException e) {
            e.printStackTrace();
        }
//        System.out.println("---> list :" + list.size());
        return list ;
    }

    /**
     * 获取生成表的sql 语句
     * @return
     */
    public static String getCreateTableSql(DBEntity db ,String tableName) {
        String createSql = null ;
        String sql = "SHOW create table " + tableName ;
//        System.out.println("---> sql: " + sql );
        DBHelper dbHelper = null;
        ResultSet ret = null;

        dbHelper = new DBHelper(db ,sql);    //创建DBHelper对象

        try {
            ret = dbHelper.pst.executeQuery();//执行语句，得到源表结果集
            while (ret.next()) {
                createSql = ret.getString(2) ;
                break;
            }//显示数据
            ret.close();
            dbHelper.close();//关闭连接

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return createSql ;
    }

    public void close() {
        try {
            this.conn.close();
            this.pst.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}