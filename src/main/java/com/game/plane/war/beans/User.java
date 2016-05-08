package com.game.plane.war.beans;

public class User {
	private String name;
	private String passwd;
	private int score;
	private int level;
	
	public User(){};
	
	public User(String name, String passwd){
		this.name = name;
		this.passwd = passwd;
	}
	
	public User(String name, int score, int level){
		this.name = name;
		this.score = score;
		this.level = level;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	@Override
	public String toString() {
		return "User [name=" + name + ", passwd=" + passwd + ", score=" + score + ", level=" + level + "]";
	}
}
