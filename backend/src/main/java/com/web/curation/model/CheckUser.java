package com.web.curation.model;

public class CheckUser {
	private int user_id;
	private String check;

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public String getCheck() {
		return check;
	}

	public void setCheck(String check) {
		this.check = check;
	}

	public CheckUser(int user_id, String check) {
		this.user_id = user_id;
		this.check = check;
	}

	public CheckUser() {
	}

}
