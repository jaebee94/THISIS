package com.web.curation.model;

public class UserInfo {
	private int user_id;
	private String username;
	private String nickname;
	private String email;
	private String password;
	private String introduction;
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getIntroduction() {
		return introduction;
	}
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}
	public UserInfo(int user_id, String username, String nickname, String email, String password, String introduction) {
		super();
		this.user_id = user_id;
		this.username = username;
		this.nickname = nickname;
		this.email = email;
		this.password = password;
		this.introduction = introduction;
	}
	public UserInfo() {
		// TODO Auto-generated constructor stub
	}
	
	
}
