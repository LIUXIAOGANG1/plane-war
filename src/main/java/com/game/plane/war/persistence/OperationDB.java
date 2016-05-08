package com.game.plane.war.persistence;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.game.plane.war.beans.User;
import com.game.plane.war.utils.DataBaseTool;

public class OperationDB {
	public static boolean isExist(String name) {

		try {
			Connection con = DataBaseTool.getConnection();
			Statement sta = con.createStatement();
			ResultSet rs = sta.executeQuery("SELECT * FROM user WHERE name='"
					+ name + "' ");
			while (rs.next()) {
				return true;
			}
		} catch (Exception e) {
		}
		return false;
	}
	
	public static boolean login(User user) {
		try {
			Connection con = DataBaseTool.getConnection();
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(
					"SELECT * FROM user WHERE name='" + user.getName() + "'AND passwd='" + user.getPasswd() + "'");
			while (rs.next()) {
				return true;
			}
		} catch (SQLException e) {
		}
		return false;
	}

	public static boolean register(User user) {
		try {
			Connection con = DataBaseTool.getConnection();
			Statement st = con.createStatement();
			int i = st.executeUpdate("INSERT INTO user SET name='" + user.getName() + "',passwd='" + user.getPasswd()+"'");
			if (i > 0) {
				return true;
			}
		} catch (SQLException e) {
		}
		return false;
	}
	
	public static User findByName(String name){
		User user = new User();
		try {
			Connection con = DataBaseTool.getConnection();
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(
					"SELECT * FROM user WHERE name='" + name + "'");
			
			while (rs.next()) {
				user.setName(rs.getString("name"));
				user.setPasswd(rs.getString("passwd"));
				user.setScore(rs.getInt("score"));
				user.setLevel(rs.getInt("level"));
			}
		} catch (SQLException e) {
			return null;
		}
		return user;
	}
	
	public static void updateMatch(User user){
		try {
			User old = OperationDB.findByName(user.getName());
			Connection con = DataBaseTool.getConnection();
			Statement st = con.createStatement();
			
			if(old.getLevel() <= user.getLevel()){
				st.executeUpdate("UPDATE user set score=" + user.getScore() + ", level=" + user.getLevel() + " WHERE name='" + user.getName() + "'");
			}
			
		} catch (SQLException e) {
		}
	}
	
	public static List<User> findTop3(){
		List<User> list = new ArrayList<User>();
		
		try {
			Connection con = DataBaseTool.getConnection();
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(
					"SELECT * FROM user ORDER BY level DESC LIMIT 0, 3");
			
			while (rs.next()) {
				User user = new User();
				user.setName(rs.getString("name"));
				user.setPasswd(rs.getString("passwd"));
				user.setScore(rs.getInt("score"));
				user.setLevel(rs.getInt("level"));
				
				list.add(user);
			}
		} catch (SQLException e) {
			return null;
		}
		return list;
	}
}
