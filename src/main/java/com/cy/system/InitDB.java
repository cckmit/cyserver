package com.cy.system;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.apache.log4j.Logger;

import com.mysql.jdbc.Connection;

public class InitDB {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(InitDB.class);

	public static void initDB(String jdbc_url, String jdbc_username, String jdbc_password) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = (Connection) DriverManager.getConnection(jdbc_url, jdbc_username, jdbc_password);
			DatabaseMetaData dbmd = conn.getMetaData();
			ResultSet rs = dbmd.getTables(null, "cy_v1", "cy_resource", new String[] { "TABLE" });
			String tableName = "";
			while (rs.next()) {
				tableName = rs.getString("TABLE_NAME");
			}
			if (tableName.length() == 0) {
				logger.info("*********************************数据库正在初始化****************************");
//				ScriptRunner runner = new ScriptRunner(conn);
//				runner.setErrorLogWriter(null);
//				runner.setLogWriter(null);
//				runner.runScript(new InputStreamReader(new FileInputStream(Resources.getResourceURL("cy_v1.sql").getPath()),"UTF-8"));
//				runner.closeConnection();
				logger.info("*********************************数据库初始化完成****************************");
			}
			if (rs != null) {
				rs.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (ClassNotFoundException e) {
			logger.error(e, e);
		} catch (SQLException e) {
			logger.error(e, e);
//		} catch (IOException e) {
//			logger.error(e, e);
		}
	}
}
