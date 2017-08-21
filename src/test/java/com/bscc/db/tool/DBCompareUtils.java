package com.bscc.db.tool;

import com.bscc.db.entity.ColumnEntity;
import com.bscc.db.entity.DBEntity;
import com.bscc.db.entity.TableEntity;
import com.bscc.db.util.PairUtil;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>Title: CompareUtils</p>
 * <p>Description: 数据库比较工具类</p>
 *
 * @author LiuZhen
 * @Company 博视创诚
 * @data 2016-08-31 10:14
 */
public class DBCompareUtils {
    private DBEntity sourceDB ;     // 源数据库
    private DBEntity targetDB ;     // 目标数据库

    public DBCompareUtils(){

    }

    public DBCompareUtils(DBEntity sourceDB ,DBEntity targetDB) {
        this.sourceDB = sourceDB ;
        this.targetDB = targetDB ;
    }

    /**
     * 比较数据库不同
     * @return
     */
    public static PairUtil<List<TableEntity>, PairUtil<List<TableEntity>, List<TableEntity>>> compareDatabase(DBEntity sourceDB ,DBEntity targetDB) {
        PairUtil<List<TableEntity>, List<TableEntity>> pair = new PairUtil<List<TableEntity>, List<TableEntity>>() ;
        List<TableEntity> sourceList = new ArrayList<TableEntity>() ;         // 源数据库表
        List<TableEntity> targetList = new ArrayList<TableEntity>() ;         // 目标数据库表
        List<TableEntity> outputList = new ArrayList<TableEntity>() ;         // 多出的数据库表
        List<TableEntity> lackList = new ArrayList<TableEntity>() ;           // 缺少的数据库表
        List<TableEntity> sameList = new ArrayList<TableEntity>() ;           // 相同的数据库表

        sourceList = DBHelper.findDBTableList(sourceDB) ;
        targetList = DBHelper.findDBTableList(targetDB) ;

        if(sourceList == null || sourceList.isEmpty()) {
            lackList = targetList ;
        } else if(targetList == null || targetList.isEmpty()) {
            outputList = sourceList ;
        } else {
            int sourceIndex = sourceList.size() ;
            System.out.println("---------> 开始比较源数据库:(共有表"+sourceIndex+")");
            // 获得多出和相同的数据库表
            for (TableEntity source : sourceList) {
                System.out.println("开始比较第"+(sourceIndex--)+"张表:("+source.getTableName()+")");
                int i ;
                for (i = 0 ; i < targetList.size() ; i++) {
                    TableEntity target = targetList.get(i) ;
                    if(source.getTableName().equalsIgnoreCase(target.getTableName())) {
                        break ;
                    }
                }
                if(i >= targetList.size()) {
                    source.setChangeSql(DBHelper.getCreateTableSql(sourceDB,source.getTableName()));
                    outputList.add(source);
                }
            }
            int targetIndex = targetList.size() ;
            System.out.println("---------> 开始比较目标数据库:(共有表"+targetIndex+")");
            // 获取缺少的数据库表
            for (TableEntity target : targetList) {
                System.out.println("开始比较第"+(targetIndex--)+"张表:("+target.getTableName()+")");
                int i ;
                for (i = 0 ; i < sourceList.size() ; i++) {
                    TableEntity source = sourceList.get(i) ;
                    if(source.getTableName().equalsIgnoreCase(target.getTableName())) {
                        target.setChangeSql(compareTable(sourceDB,targetDB,source.getTableName() ,target.getTableName()));
                        break ;
                    }
                }
                if(i >= sourceList.size()) {
                    target.setChangeSql(DBHelper.getCreateTableSql(targetDB,target.getTableName()));
                    lackList.add(target);
                } else {
                    sameList.add(target);
                }
            }

        }

        pair.setOne(outputList);
        pair.setTwo(lackList);

        PairUtil<List<TableEntity>, PairUtil<List<TableEntity>, List<TableEntity>>> resultPair = new PairUtil<List<TableEntity>, PairUtil<List<TableEntity>, List<TableEntity>>>() ;
        resultPair.setOne(sameList);
        resultPair.setTwo(pair) ;
        System.out.println("---------> 数据库比较完成");
        return resultPair ;
    }

    /**
     * 比较数据库表字段的不同
     * @param sourceDB
     * @param targetDB
     * @param sourceTable
     * @param targetTable
     */
    public static String compareTable(DBEntity sourceDB ,DBEntity targetDB ,String sourceTable ,String targetTable) {
        String changeSql = "" ;
        List<ColumnEntity> sourceList = DBHelper.findDBColumnList(sourceDB,sourceTable) ;
        List<ColumnEntity> targetList = DBHelper.findDBColumnList(targetDB,targetTable) ;

        if(sourceList != null && targetList != null) {
            String key = "" ;
            boolean isKeyChange = false ;
            for (ColumnEntity target : targetList ) {
                String sql = null ;
                int i = 0 ;
                for (i = 0 ; i < sourceList.size() ; i++) {
                    ColumnEntity source = sourceList.get(i) ;
                    if(target.getName().equalsIgnoreCase(source.getName())) {
                        if (!target.getType().equals(source.getType())
                                || (target.getDefaultValue() != null && !target.getDefaultValue().equals(source.getDefaultValue()))
                                || (target.getExtra() != null && !target.getExtra().equals(source.getExtra()))
                                || (target.getIsNull() != null && !target.getIsNull().equals(source.getIsNull()))
                                || (target.getComment() != null && !target.getComment().equals(source.getComment()))
                                || (target.getKey() != null && !target.getKey().equals(source.getKey()))) {
                            sql = ColumnEntity.CHANGE_COLUMN.replace("${COLUMN_NEW_NAME}",target.getName())
                                    .replace("${COLUMN_OLD_NAME}",source.getName())
                                    .replace("${COLUMN_TYPE}",target.getType());
                        }

                        if("PRI".equals(target.getKey())) {
                            if(key != null && !"".equals(key.trim())) {
                                key += ", " ;
                            }
                            key += "`"+target.getName()+"`" ;
                        }
                        if ((target.getKey() != null && !target.getKey().equals(source.getKey()))
                                || (source.getKey() != null && !source.getKey().equals(target.getKey()))) {
                            isKeyChange = true ;
                        }

                        break ;
                    }
                }
                if(i >= sourceList.size()) {
                    sql = ColumnEntity.ADD_COLUMN.replace("${COLUMN_NAME}",target.getName()).replace("${COLUMN_TYPE}",target.getType()) ;
                }

                if (sql != null) {
                    if(target.getDefaultValue() != null && !"".equals(target.getDefaultValue())) {
                        sql += " DEFAULT " + target.getDefaultValue() + "" ;
                    }
                    if("NO".equalsIgnoreCase(target.getIsNull())) {
                        sql += " NOT NULL" ;
                    }
                    if(target.getComment() != null && !"".equals(target.getComment())) {
                        sql += " COMMENT '"+target.getComment()+"'" ;
                    }
                    if(isKeyChange) {
                        sql += " DROP PRIMARY KEY, ADD PRIMARY KEY ("+key+")" ;
                    }
                    if(target.getExtra() != null && !"".equals(target.getExtra().trim())) {
                        sql += " " + target.getExtra() ;
                    }
                    if (!"".equals(changeSql)) {
                        changeSql += " \n\t,";
                    }
                    changeSql += sql;
                }
            }

            if(changeSql != null && !changeSql.trim().equals("")) {
                changeSql = "ALTER TABLE `"+targetTable+"` \n\t" + changeSql ;
            }
        }

        return changeSql ;
    }

    /**
     * 从源数据库变成目标数据库表结构改变SQL 脚本
     * @return
     */
    public static String compareDBSQL(DBEntity sourceDB ,DBEntity targetDB) {
        System.out.println("\n\n---------------------------开始生成SQL语句-----------------------------\n");
        StringBuffer resultSQL = new StringBuffer() ;
        PairUtil<List<TableEntity>, PairUtil<List<TableEntity>, List<TableEntity>>> result = DBCompareUtils.compareDatabase(sourceDB,targetDB) ;
        int i = 0 ;
        if (result != null) {
            List<TableEntity> sameList = result.getOne() ;
//            System.out.println(sameList);
            int sameIndex = sameList.size() ;
            System.out.println("\n\n---> 1.数据库表改变:(共有表"+sameIndex+"张)");
            resultSQL.append("#***********************1.数据库表改变*******************************#\n\n") ;
            if(sameList != null) {
                for (TableEntity table : sameList) {
                    System.out.println("生成第"+(sameIndex--)+"张:("+table.getTableName()+")");
                    if(table.getChangeSql() != null && !"".equals(table.getChangeSql())) {
                        resultSQL.append(TableEntity.TABLE_COMMENT
                                .replace("${NUMBER}",String.valueOf(++i))
                                .replace("${TABLE_NAME}",table.getTableName())
                                .replace("${TABLE_COMMENT}",table.getTableCommen()) + "\n");
                        resultSQL.append(table.getChangeSql() + ";\n") ;
                    }
                }
            }

            PairUtil<List<TableEntity>, List<TableEntity>> pair = result.getTwo() ;
            List<TableEntity> outputList = pair.getOne() ;
//            System.out.println(outputList);

            List<TableEntity> lactList = pair.getTwo() ;
//            System.out.println(lactList);
            resultSQL.append("\n#***********************2.数据库表添加*******************************#\n\n") ;
            int lactIndex = lactList.size() ;
            System.out.println("\n\n---> 2.数据库表添加:(共有表"+lactIndex+"张)");
            if(lactList != null) {
                for (TableEntity table : lactList) {
                    System.out.println("生成第"+(lactIndex--)+"张:("+table.getTableName()+")");
                    if(table.getChangeSql() != null && !"".equals(table.getChangeSql())) {
                        resultSQL.append(TableEntity.TABLE_COMMENT
                                .replace("${NUMBER}",String.valueOf(++i))
                                .replace("${TABLE_NAME}",table.getTableName())
                                .replace("${TABLE_COMMENT}",table.getTableCommen()) + "\n");
                        resultSQL.append(table.getChangeSql() + ";\n\n") ;
                    }
                }
            }
        }
        return resultSQL.toString() ;
    }

    /**
     * 生成从源数据库变成目标数据库表结构改变SQL 脚本文件
     * @param sourceDB  源数据库
     * @param targetDB  目的数据库
     * @param dirPath   脚本文件保存目录
     * @param fileName  脚本文件文件名
     */
    public static void createCompareDBSQLFile(DBEntity sourceDB ,DBEntity targetDB ,String dirPath ,String fileName) {
        System.out.println("------------准备生成脚本文件------------");
        File dir = new File(dirPath) ;
        if(!dir.exists() || !dir.isDirectory()) {
            dir.mkdirs() ;
        }
        File file = new File(dirPath + File.separator + fileName) ;
        String resultSql = DBCompareUtils.compareDBSQL(sourceDB,targetDB) ;
        if(resultSql != null && !"".equals(resultSql)) {
            try {
                FileUtils.writeStringToFile(file, resultSql, "UTF-8");
                System.out.println("------------脚本文件生成成功------------");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
