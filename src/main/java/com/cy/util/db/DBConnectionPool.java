package com.cy.util.db;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Vector;

/**
 * 数据库连接池
 * 
 * @author joe
 *
 */
public class DBConnectionPool
{
	/**
	* Logger for this class
	*/
	private static final Logger logger = Logger.getLogger(DBConnectionPool.class);

	private int inUsed = 0; // 使用的连接数
	private Vector<Connection> freeConnections = new Vector<Connection>();// 容器，空闲连接
	private int maxConn; // 最大连接
	private String user; // 用户名
	private String name; // 连接池名字
	private String password; // 密码
	private String url; // 数据库连接地ַ
	private String driver; // 驱动

	public DBConnectionPool()
	{
	}

	/**
	 * 创建连接池
	 * @param driver
	 * @param name
	 * @param URL
	 * @param user
	 * @param password
	 * @param maxConn
	 */
	public DBConnectionPool(String name, String driver, String URL, String user, String password, int maxConn)
	{
		this.name = name;
		this.driver = driver;
		this.url = URL;
		this.user = user;
		this.password = password;
		this.maxConn = maxConn;
	}

	/**
	 * 
	 * 从连接池里得到连接
	 * @return
	 */
	public synchronized Connection getConnection()
	{
		Connection con = null;
		if (this.freeConnections.size() > 0)
		{
			con = (Connection) this.freeConnections.get(0);
			if (con == null)
			{
				try
				{
					Thread.sleep(200);
				} catch (InterruptedException e)
				{
					logger.error(e, e);
				}
				con = getConnection();// 继续获得连接
			} else
			{
				this.freeConnections.remove(0);// 如果连接分配出去了，就从空闲连接里删除
			}
		} else
		{
			// 检查是否超过最大连接
			if (this.maxConn == 0 || this.maxConn <= this.inUsed)
			{
				logger.error("没有可用连接");
				con = null;
			} else
			{
				con = newConnection(); // 新建连接
			}
		}

		if (con != null)
		{
			this.inUsed++;
			logger.info("得到" + this.name + "的连接，现有" + inUsed + "个连接在使用!");
		}
		return con;
	}

	/**
	 * 用完，释放连接
	 * @param con
	 */
	public synchronized void freeConnection(Connection con)
	{
		this.freeConnections.add(con);// 添加到空闲连接的末尾
		this.inUsed--;
	}

	/**
	 * 释放全部连接
	 */
	public synchronized void release()
	{
		Iterator<Connection> allConns = this.freeConnections.iterator();
		while (allConns.hasNext())
		{
			Connection con = (Connection) allConns.next();
			try
			{
				con.close();
			} catch (SQLException e)
			{
				logger.error(e, e);
			}

		}
		this.freeConnections.clear();

	}

	/**
	 * 创建新连接
	 * @return
	 */
	private Connection newConnection()
	{
		Connection con = null;
		try
		{
			Class.forName(driver);
			con = DriverManager.getConnection(url, user, password);
		} catch (ClassNotFoundException e)
		{
			logger.error("sorry can't find db driver!");
		} catch (SQLException e1)
		{
			logger.error("sorry can't create Connection!");
		}
		return con;
	}

	public int getInUsed()
	{
		return inUsed;
	}

	public void setInUsed(int inUsed)
	{
		this.inUsed = inUsed;
	}

	public Vector<Connection> getFreeConnections()
	{
		return freeConnections;
	}

	public void setFreeConnections(Vector<Connection> freeConnections)
	{
		this.freeConnections = freeConnections;
	}

	public int getMaxConn()
	{
		return maxConn;
	}

	public void setMaxConn(int maxConn)
	{
		this.maxConn = maxConn;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public String getUrl()
	{
		return url;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}

	public String getDriver()
	{
		return driver;
	}

	public void setDriver(String driver)
	{
		this.driver = driver;
	}

	public String getUser()
	{
		return user;
	}

	public void setUser(String user)
	{
		this.user = user;
	}

}
