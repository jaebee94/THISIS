package com.web.curation.model;

public class UserInfo {
	private int user_id;
	private String username;
	private String nickname;
	private String email;
	private String password;
	private String introduction;
	private String userimage;
	private String doctorauth;

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

	public String getDoctorauth() {
		return doctorauth;
	}

	public void setDoctorauth(String doctorauth) {
		this.doctorauth = doctorauth;
	}

	public UserInfo() {

	}

	public UserInfo(int user_id, String username, String nickname, String email, String password, String introduction,
			String userimage, String doctorauth) {
		this.user_id = user_id;
		this.username = username;
		this.nickname = nickname;
		this.email = email;
		this.password = password;
		this.introduction = introduction;
		this.userimage = userimage;
		this.doctorauth = doctorauth;
	}

}
