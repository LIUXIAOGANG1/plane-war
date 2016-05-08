package com.game.plane.war.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSourceFactory;

/**
 * 
 * <使用连接池连接数据库，每个连接作为一个线程来处理>
 * 
 * @author wlx
 * @version [V1.00, 2015年9月24日]
 * @see [相关类/方法]
 * @since V1.00
 */
public class DataBaseTool {
	private static DataSource dataSource;

	private static ThreadLocal<Connection> threadLocal = new ThreadLocal<Connection>();

	static {
		try {
			Properties source = new Properties();

			source.load(DataBaseTool.class.getClassLoader().getResourceAsStream("properties/config.properties"));
			dataSource = BasicDataSourceFactory.createDataSource(source);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * < 一个业务一个事务 一个事务一个连接 一个连接一个线程 > <功能详细描述>
	 * 
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public static Connection getConnection() {
		Connection con = null;

		try {
			con = threadLocal.get();
			if (con == null) {
				con = dataSource.getConnection();
				threadLocal.set(con);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return con;
	}

	public static void closeAll(ResultSet rs, PreparedStatement pstmt, Connection conn) throws SQLException {
		if (rs != null) {
			rs.close();
		}
		if (pstmt != null) {
			pstmt.close();
		}
		if (conn != null) {
			conn.close();
		}
	}

	public static void closeConnection() throws SQLException {
		Connection conn = threadLocal.get();
		if (conn != null) {
			conn.close();
			threadLocal.set(null);
		}
	}
}