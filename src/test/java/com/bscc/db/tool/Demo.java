package com.bscc.db.tool;

import com.bscc.db.entity.DBEntity;
import com.bscc.db.entity.TableEntity;
import com.bscc.db.util.PairUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * <p>Title: Demo</p>
 * <p>Description: </p>
 *
 * @author LiuZhen
 * @Company 博视创诚
 * @data 2016-08-31 09:58
 */
public class Demo {

    static String sql = null;
    static DBHelper db1 = null;
    static ResultSet ret = null;

    public static void main(String[] args) {
//        String showTableSql = "show tables";        //SQL语句 (查询数据库表)
//        db1 = new DBHelper(showTableSql);    //创建DBHelper对象
//
//        try {
//            ret = db1.pst.executeQuery();//执行语句，得到结果集
//            while (ret.next()) {
//                String tableName = ret.getString(1);
////                String ufname = ret.getString(2);
////                String ulname = ret.getString(3);
////                String udate = ret.getString(4);
////                System.out.println(tableName + "\t" + ufname + "\t" + ulname + "\t" + udate );
//                System.out.println("tableName : " + tableName);
//            }//显示数据
//            ret.close();
//            db1.close();//关闭连接
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
        createCompareDBSQLFile() ;
//        compareDBSQL() ;
//        compareDB();
//        compareTable() ;

//        String sql = "SELECT\n" +
//                "\tcolumn_name,\n" +
//                "\tcolumn_type,\n" +
//                "\tis_nullable,\n" +
//                "\tcolumn_key,\n" +
//                "\tcolumn_default,\n" +
//                "\textra,\n" +
//                "\tcolumn_comment\n" +
//                "FROM\n" +
//                "\tinformation_schema. COLUMNS\n" +
//                "WHERE\n" +
//                "\ttable_schema = '"+"cy_v1"+"'\n" +
//                "AND table_name = '"+"cy_user"+"'" ;
//        System.out.println(sql);
    }

    public static void createCompareDBSQLFile() {

        DBEntity sourceDB = new DBEntity() ;
        DBEntity targetDB = new DBEntity() ;

        sourceDB.setUrl("jdbc:mysql://219.234.7.4:3351/");
        sourceDB.setDbName("cy_gkm");
        sourceDB.setDriveName("com.mysql.jdbc.Driver");
        sourceDB.setUsername("bsccadmin");
        sourceDB.setPassword("bscc2016admin");
        targetDB.setUrl("jdbc:mysql://114.215.194.18:3351/");
        targetDB.setDbName("cy_v2");
        targetDB.setDriveName("com.mysql.jdbc.Driver");
        targetDB.setUsername("root");
        targetDB.setPassword("cy199cn1816");

        DBCompareUtils.createCompareDBSQLFile(sourceDB,targetDB,"/Users/liuzhen/Documents/99 其他文件/01 数据库表结构变化脚本/"+new SimpleDateFormat("yyyyMMdd").format(new Date())+"/"
                ,"原始库数据库表结构变化脚本_"+new SimpleDateFormat("HHmmss").format(new Date())+".sql") ;
    }

    public static void compareDBSQL() {

        DBEntity sourceDB = new DBEntity() ;
        DBEntity targetDB = new DBEntity() ;
//        sourceDB.setUrl("jdbc:mysql://127.0.0.1:3306/");
//        sourceDB.setDbName("cy_v1_wkd_old");
//        sourceDB.setDriveName("com.mysql.jdbc.Driver");
//        sourceDB.setUsername("root");
//        sourceDB.setPassword("123456");
//        targetDB.setUrl("jdbc:mysql://127.0.0.1:3306/");
//        targetDB.setDbName("cy_v1");
//        targetDB.setDriveName("com.mysql.jdbc.Driver");
//        targetDB.setUsername("root");
//        targetDB.setPassword("123456");

        targetDB.setUrl("jdbc:mysql://219.234.7.4:3351/");
        targetDB.setDbName("cy_origin");
        targetDB.setDriveName("com.mysql.jdbc.Driver");
        targetDB.setUsername("bscc");
        targetDB.setPassword("bscc2016");
        sourceDB.setUrl("jdbc:mysql://219.234.7.4:3351/");
        sourceDB.setDbName("cy_114");
        sourceDB.setDriveName("com.mysql.jdbc.Driver");
        sourceDB.setUsername("bscc");
        sourceDB.setPassword("bscc2016");
        String result = DBCompareUtils.compareDBSQL(sourceDB,targetDB) ;
        System.out.println(result);
    }

    public static void compareDB() {

        DBEntity sourceDB = new DBEntity() ;
        DBEntity targetDB = new DBEntity() ;
        sourceDB.setUrl("jdbc:mysql://219.234.7.4:3351/");
        sourceDB.setDbName("cy_origin");
        sourceDB.setDriveName("com.mysql.jdbc.Driver");
        sourceDB.setUsername("bscc");
        sourceDB.setPassword("bscc2016");
        targetDB.setUrl("jdbc:mysql://219.234.7.4:3351/");
        targetDB.setDbName("cy_114");
        targetDB.setDriveName("com.mysql.jdbc.Driver");
        targetDB.setUsername("bscc");
        targetDB.setPassword("bscc2016");
        PairUtil<List<TableEntity>, PairUtil<List<TableEntity>, List<TableEntity>>> result = DBCompareUtils.compareDatabase(sourceDB,targetDB) ;
        if (result != null) {
            System.out.println("***********************源与目标数据库相同的表*******************************");
            List<TableEntity> sameList = result.getOne() ;
            System.out.println(sameList);

            PairUtil<List<TableEntity>, List<TableEntity>> pair = result.getTwo() ;
            System.out.println("\n***********************源比目标数据库多出的表*******************************");
            List<TableEntity> outputList = pair.getOne() ;
            System.out.println(outputList);

            System.out.println("\n***********************源相对目标数据库缺少的表*******************************");
            List<TableEntity> lactList = pair.getTwo() ;
            System.out.println(lactList);
        }
    }

    public static void compareTable() {

        DBEntity sourceDB = new DBEntity() ;
        sourceDB.setUrl("jdbc:mysql://127.0.0.1:3306/");
        sourceDB.setDbName("cy_v1_test");
        sourceDB.setDriveName("com.mysql.jdbc.Driver");
        sourceDB.setUsername("root");
        sourceDB.setPassword("123456");
        DBEntity targetDB = new DBEntity() ;
        targetDB.setUrl("jdbc:mysql://127.0.0.1:3306/");
        targetDB.setDbName("cy_v1.5_wkd_new");
        targetDB.setDriveName("com.mysql.jdbc.Driver");
        targetDB.setUsername("root");
        targetDB.setPassword("123456");
        String changeSql = DBCompareUtils.compareTable(sourceDB,targetDB,"cy_news","cy_news") ;
        System.out.println("***********************表cy_alumni 变化SQL 语句:*******************************\n"+changeSql);
    }

}