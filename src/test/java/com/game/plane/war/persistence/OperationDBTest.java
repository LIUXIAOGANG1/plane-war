package com.game.plane.war.persistence;

import java.util.List;

import com.game.plane.war.beans.User;

public class OperationDBTest {
	public static void main(String[] args) {
		List<User> list = OperationDB.findTop3();
		
		for(User user : list){
			System.out.println(user.toString());
		}
	}
}
