package com.cy.system;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import com.cy.util.WebUtil;
import org.apache.log4j.Logger;

import com.cy.util.db.DBConnectionManager;

public class TigaseUtils {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TigaseUtils.class);

	private DBConnectionManager dbConnectionManager;

	private static TigaseUtils instance;

	public static String TIGASE_DB_NAME ;	//
	public static String TIGASE_SERVER_DOMAIN ;	//
	public static String TIGASE_ADMIN_ACCOUNT ;	//
	public static String TIGASE_ADMIN_PASSWD ;	//
	public static String TIGASE_SERVER_IP ;	//

	public static synchronized TigaseUtils getInstance() {
		if (instance == null) {
			instance = new TigaseUtils();
		}
		return instance;

	}

	private TigaseUtils() {
		dbConnectionManager = DBConnectionManager.getInstance();
	}

	/*
	 * 在聊天服务器上创建账号
	 */
	public boolean createAccount(String account, String passwd) throws Exception {
		CallableStatement stmt = null;
		Connection conn = null;
		try {
			if (WebUtil.isEmpty(account) || WebUtil.isEmpty(passwd)) {
				return false;
			}
			conn = dbConnectionManager.getConnection(TigaseUtils.TIGASE_DB_NAME);
			StringBuffer buf = new StringBuffer();
			if (!account.endsWith(TigaseUtils.TIGASE_SERVER_DOMAIN)) {
				buf.append(account).append("@").append(TigaseUtils.TIGASE_SERVER_DOMAIN);
			} else {
				buf.append(account);
			}
			// 该存储过程执行会返回结果集，返回为true,没返回为false
			stmt = (CallableStatement) conn.prepareCall("{call TigAddUserPlainPw(?,?)}");
			stmt.setString(1, buf.toString());
			stmt.setString(2, passwd);
			return stmt.execute();
		} finally {
			if (stmt != null) {
				stmt.close();
			}
			if (conn != null) {
				dbConnectionManager.freeConnection(TigaseUtils.TIGASE_DB_NAME, conn);
			}
		}
	}

	/*
	 * 在聊天服务器上删除账号
	 */
	public boolean deleteAccount(String account) {
		if (account == null || account.equals("")) {
			return false;
		}

		Connection conn = null;
		CallableStatement stmt = null;
		try {
			conn = dbConnectionManager.getConnection(TigaseUtils.TIGASE_DB_NAME);
			StringBuffer buf = new StringBuffer();
			if (!account.endsWith(TigaseUtils.TIGASE_SERVER_DOMAIN)) {
				buf.append(account).append("@").append(TigaseUtils.TIGASE_SERVER_DOMAIN);
			} else {
				buf.append(account);
			}
			stmt = (CallableStatement) conn.prepareCall("{call TigRemoveUser(?)}");
			stmt.setString(1, buf.toString());
			return stmt.execute();
		} catch (SQLException e) {
			logger.error(e, e);
			return false;
		} catch (Exception e) {
			logger.error(e, e);
			return false;
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					logger.error(e, e);
				}
			}
			if (conn != null) {
				dbConnectionManager.freeConnection(TigaseUtils.TIGASE_DB_NAME, conn);
			}
		}
	}

	/*
	 * 在聊天服务器上修改密码
	 */
	public boolean changePassword(String account, String newPasswd) throws SQLException {
		if (account == null || account.equals("") || newPasswd == null || newPasswd.equals("")) {
			return false;
		}
		Connection conn = null;
		CallableStatement stmt = null;
		try {
			conn = dbConnectionManager.getConnection(TigaseUtils.TIGASE_DB_NAME);
			StringBuffer buf = new StringBuffer();
			if (!account.endsWith(TigaseUtils.TIGASE_SERVER_DOMAIN)) {
				buf.append(account).append("@").append(TigaseUtils.TIGASE_SERVER_DOMAIN);
			} else {
				buf.append(account);
			}
			stmt = (CallableStatement) conn.prepareCall("{call TigUpdatePasswordPlainPw(?,?)}");
			stmt.setString(1, buf.toString());
			stmt.setString(2, newPasswd);
			return stmt.execute();
		} finally {
			if (stmt != null) {
				stmt.close();
			}
			if (conn != null) {
				dbConnectionManager.freeConnection(TigaseUtils.TIGASE_DB_NAME, conn);
			}
		}
	}

	public int callShell(String shellString) {
		try {
			Process process = Runtime.getRuntime().exec(shellString);
			logger.info(shellString);
			int exitValue = process.waitFor();
			if (0 != exitValue) {
				logger.error("call shell failed. error code is :" + exitValue);
			}
			return exitValue;
		} catch (Throwable e) {
			logger.error("call shell failed. " + e);
			return -1;
		}
	}

	public int addUser(String jid2, String pwd2, String email2) {
		if (email2 == null || email2.equals("")) {
			email2 = new String("x");
		}
		StringBuffer add = new StringBuffer();
		add.append("tclmt -u ").append(TigaseUtils.TIGASE_ADMIN_ACCOUNT).append("@").append(TigaseUtils.TIGASE_SERVER_DOMAIN).append(" -p ")
				.append(TigaseUtils.TIGASE_ADMIN_PASSWD).append(" -s ").append(TigaseUtils.TIGASE_SERVER_IP).append(" add-user ").append(jid2).append("@")
				.append(TigaseUtils.TIGASE_SERVER_DOMAIN).append(" ").append(pwd2).append(" ").append(email2);
		return callShell(add.toString());
	}

	public int delUser(String jid2) {
		StringBuffer del = new StringBuffer();
		del.append("tclmt -u ").append(TigaseUtils.TIGASE_ADMIN_ACCOUNT).append("@").append(TigaseUtils.TIGASE_SERVER_DOMAIN).append(" -p ")
				.append(TigaseUtils.TIGASE_ADMIN_PASSWD).append(" -s ").append(TigaseUtils.TIGASE_SERVER_IP).append(" delete-user ").append(jid2).append("@")
				.append(TigaseUtils.TIGASE_SERVER_DOMAIN);
		return callShell(del.toString());
	}

	/** --tigase上创建群组,节点-- **/
	public String createGroupNod(String node_name, String accountNum) throws Exception {
		Connection conn = null;
		CallableStatement stmt = null;
		try {
			conn = dbConnectionManager.getConnection(TigaseUtils.TIGASE_DB_NAME);
			StringBuffer buf = new StringBuffer();
			if (!accountNum.endsWith(TigaseUtils.TIGASE_SERVER_DOMAIN)) {
				buf.append(accountNum).append("@").append(TigaseUtils.TIGASE_SERVER_DOMAIN);
			} else {
				buf.append(accountNum);
			}
			String pubsub = "pubsub." + TigaseUtils.TIGASE_SERVER_DOMAIN;
			
			
			// 查询节点是否存在TigPubSubGetNodeId(,)
			CallableStatement stmtQuery = (CallableStatement) conn.prepareCall("{call TigPubSubGetNodeId (?,?)}");
			stmtQuery.setString(1, pubsub);
			stmtQuery.setString(2, node_name);
			ResultSet result = stmtQuery.executeQuery();
			List list = new ArrayList();
			while (result.next()) {
				list.add(result.getString("node_id"));
			}
			stmtQuery.close();
			if(WebUtil.isEmpty(list)){
				String steSQL = "{CALL TigPubSubCreateNode(?, ?, 1, ?, (SELECT pval FROM tig_pairs WHERE pkey = 'cy-group-configuration'), NULL)}";
				stmt = conn.prepareCall(steSQL);
				stmt.setString(1, pubsub);
				stmt.setString(2, node_name);
				stmt.setString(3, buf.toString());
				stmt.execute();
			}
		} finally {
			if (stmt != null) {
				stmt.close();
			}
			if (conn != null) {
				dbConnectionManager.freeConnection(TigaseUtils.TIGASE_DB_NAME, conn);
			}
		}
		return "success";
	}

	/** --tigase上删除群组node_name为群id- **/
	public String deleteGroupNod(String node_name, String accountNum) throws Exception {
		Connection conn = dbConnectionManager.getConnection(TigaseUtils.TIGASE_DB_NAME);
		StringBuffer buf = new StringBuffer();
		if (!accountNum.endsWith(TigaseUtils.TIGASE_SERVER_DOMAIN)) {
			buf.append(accountNum).append("@").append(TigaseUtils.TIGASE_SERVER_DOMAIN);
		} else {
			buf.append(accountNum);
		}

		String pubsub = "pubsub." + TigaseUtils.TIGASE_SERVER_DOMAIN;
		// 查询节点是否存在TigPubSubGetNodeId(,)
		CallableStatement stmtQuery = (CallableStatement) conn.prepareCall("{call TigPubSubGetNodeId (?,?)}");
		stmtQuery.setString(1, pubsub);
		stmtQuery.setString(2, node_name);
		ResultSet result = stmtQuery.executeQuery();
		List list = new ArrayList();
		while (result.next()) {
			list.add(result.getString("node_id"));
		}
		stmtQuery.close();
		// 删除节点
		if (list != null && list.size() == 1) {
			String node_id = (String) list.get(0);
			CallableStatement stmt = (CallableStatement) conn.prepareCall("{call TigPubSubRemoveNode (?)}");
			stmt.setString(1, node_id);
			stmt.execute();
			stmt.close();
		}
		dbConnectionManager.freeConnection(TigaseUtils.TIGASE_DB_NAME, conn);
		return "success";
	}

}