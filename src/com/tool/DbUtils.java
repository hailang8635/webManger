package com.tool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.action.article.ArticleAction;

/*
 *此类主要完成对数据库的操作。
 *  包括：数据库的连接，数据库的增、删、改、查以及释放数据库的连接
 */
public class DbUtils {
	private static Logger log = Logger.getLogger(ArticleAction.class);
	private String mysqlDriver = Config.getValue("mysqlDriver");
	private String mysqlURL = Config.getValue("mysqlURL");
	private String dbName = Config.getValue("dbName");
	private String user = Config.getValue("user");
	private String password = Config.getValue("password");

	// 获取数据库的连接
	private Connection getConnection() {
		Connection conn = null;
		try {
			Class.forName(mysqlDriver);
			conn = DriverManager.getConnection(mysqlURL + dbName, user, password);
			/*
			 * System.out.println("========="); Context ct = new
			 * javax.naming.InitialContext(); DataSource ds =
			 * (DataSource)ct.lookup("jdbc/shop"); conn = ds.getConnection();
			 */

		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}

	// 查询表中所有用户的信息，并将结果放入List里面
	public List<Object> query(String sql, Object[] paramsValues) {
		List<Object> list = new ArrayList<Object>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = this.getConnection();
			ps = conn.prepareStatement(sql);
			if (paramsValues != null) {
				for (int i = 0; i < paramsValues.length; i++) {
					ps.setObject(i + 1, paramsValues[i]);
				}
			}
			rs = ps.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			int count = rsmd.getColumnCount();
			while (rs.next()) {
				String[] str = new String[count];
				for (int i = 0; i < count; i++) {
					str[i] = rs.getString(i + 1);
				}
				list.add(str);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			free(conn, ps, rs);
		}
		return list;
	}
	
	// 将修改过的数据保存或者添加进数据库
	public int save(String sql, Object[] paramsValues) {
		int count=0;
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = this.getConnection();
			ps = conn.prepareStatement(sql);
			if (paramsValues != null) {
				for (int i = 0; i < paramsValues.length; i++) {
					ps.setObject(i + 1, paramsValues[i]);
				}
			}
			log.info(sql);
			count = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			free(conn, ps, null);
		}
		return count;
	}

	// 将修改过的数据保存或者添加进数据库
	public int update(String sql, Object[] paramsValues) {
		Connection conn = null;
		PreparedStatement ps = null;
		int count=0;
		try {
			conn = this.getConnection();
			ps = conn.prepareStatement(sql);
			if (paramsValues != null) {
				for (int i = 0; i < paramsValues.length; i++) {
					ps.setObject(i + 1, paramsValues[i]);
				}
			}
			log.info(sql);
			count = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			free(conn, ps, null);
		}
		return count;
	}

	// 查询表中所有用户的信息，并将结果放入List里面
	public List<Map<String,Object>> query1(String sql, Object[] paramsValues) {
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = this.getConnection();
			ps = conn.prepareStatement(sql);
			if (paramsValues != null) {
				for (int i = 0; i < paramsValues.length; i++) {
					ps.setObject(i + 1, paramsValues[i]);
				}
			}
			log.info(sql);
			rs = ps.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			int count = rsmd.getColumnCount();
			while (rs.next()) {
				Map<String,Object> map = new HashMap<String,Object>();
				for (int i = 0; i < count; i++) {
					String colName = rsmd.getColumnName(i + 1);
					Object colValue = rs.getObject(colName);
					map.put(colName.toLowerCase(), colValue);
				}
				list.add(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			free(conn, ps, rs);
		}
		return list;
	}

	// 释放数据库的连接
	private void free(Connection conn, Statement sta, ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
				rs = null;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (sta != null) {
			try {
				sta.close();
				sta = null;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (conn != null) {
			try {
				conn.close();
				conn = null;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
