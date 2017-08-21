package com.cy.util.db;

import org.apache.log4j.Logger;

import com.cy.system.Global;

import java.sql.Connection;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;

public class DBConnectionManager {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(DBConnectionManager.class);

	private static DBConnectionManager instance;// 唯一数据库连接池管理实例类
	private Vector<DBConfigBean> drivers = new Vector<DBConfigBean>();// 驱动信息
	private Hashtable<String, DBConnectionPool> pools = new Hashtable<String, DBConnectionPool>();// 连接池

	/**
	 * 实例化管理类
	 */
	private DBConnectionManager() {
		this.init();
	}

	/**
	 * 得到唯一实例管理类
	 * 
	 * @return
	 */
	public static synchronized DBConnectionManager getInstance() {
		if (instance == null) {
			instance = new DBConnectionManager();
		}
		return instance;

	}

	/**
	 * 释放连接
	 * 
	 * @param name
	 * @param con
	 */
	public void freeConnection(String name, Connection con) {
		DBConnectionPool pool = pools.get(name);// 根据关键名字得到连接池
		if (pool != null) {
			pool.freeConnection(con);// 释放连接
		} else {
			logger.error("释放连接:找不到与" + name + "匹配的连接池");
		}
	}

	/**
	 * 得到一个连接根据连接池的名字name
	 * 
	 * @param name
	 * @return
	 */
	public Connection getConnection(String name) {
		DBConnectionPool pool = null;
		Connection con = null;
		pool = (DBConnectionPool) pools.get(name);// 从名字中获取连接池
		if (pool != null) {
			con = pool.getConnection();// 从选定的连接池中获得连接
		} else {
			logger.error("获取连接失败，找不到与" + name + "匹配的连接池");
		}

		return con;
	}

	/**
	 * 释放所有连接
	 */
	public synchronized void release() {
		Enumeration<DBConnectionPool> allpools = pools.elements();
		while (allpools.hasMoreElements()) {
			DBConnectionPool pool = allpools.nextElement();
			if (pool != null) {
				pool.release();
			}
		}
		pools.clear();
	}

	/**
	 * 创建连接池
	 * 
	 * @param db
	 */
	private void createPools(DBConfigBean db) {
		DBConnectionPool dbpool = new DBConnectionPool();
		dbpool.setName(db.getName());
		dbpool.setDriver(db.getDriver());
		dbpool.setUrl(db.getUrl());
		dbpool.setUser(db.getUsername());
		dbpool.setPassword(db.getPassword());
		dbpool.setMaxConn(db.getMaxconn());
		pools.put(db.getName(), dbpool);
	}

	/**
	 * 初始化连接池的参数
	 */
	private void init() {
		// 加载驱动程序
//		this.loadDrivers();
		// 创建连接池
		logger.info("开始创建连接池....");
		Iterator<DBConfigBean> alldriver = drivers.iterator();
		while (alldriver.hasNext()) {
			this.createPools((DBConfigBean) alldriver.next());
		}
		logger.info("连接池创建完毕....");
	}

//	/**
//	 * 加载驱动程序
//	 *
//	 * @param props
//	 */
//	private void loadDrivers() {
//		logger.info("开始初始化驱动....");
//		DBConfigBean configBean = new DBConfigBean();
//		configBean.setDriver("com.mysql.jdbc.Driver");
//		configBean.setMaxconn(Global.tigase_db_conn_size);
//		configBean.setName(Global.tigase_db_name);
//		configBean.setPassword(Global.tigase_db_password);
//		configBean.setUrl("jdbc:mysql://" + Global.tigase_db_ip + ":" + Global.tigase_db_port + "/" + Global.tigase_db_name
//				+ "?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull&noAccessToProcedureBodies=true&autoReconnect=true");
//		configBean.setUsername(Global.tigase_db_user);
//		drivers.add(configBean);
//		logger.info("驱动初始化完成....");
//	}
}
