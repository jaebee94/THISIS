package com.web.curation.model;

public class UserPolice {
	private int user_id;
	private String username;
	private String nickname;
	private String email;
	private String password;
	private String introduction;
	private String userimage;
	private String role;
	private int disabled;
	private int count;

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

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

	public String getUserimage() {
		return userimage;
	}

	public void setUserimage(String userimage) {
		this.userimage = userimage;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public int getDisabled() {
		return disabled;
	}

	public void setDisabled(int disabled) {
		this.disabled = disabled;
	}

	public UserPolice() {

	}

	public UserPolice(int user_id, String username, String nickname, String email, String password, String introduction,
			String userimage, String role, int disabled, int count) {
		this.user_id = user_id;
		this.username = username;
		this.nickname = nickname;
		this.email = email;
		this.password = password;
		this.introduction = introduction;
		this.userimage = userimage;
		this.role = role;
		this.disabled = disabled;
		this.count = count;
	}

}
