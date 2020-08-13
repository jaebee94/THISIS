package com.web.curation.model;

public class RequestDoctor {
	private String check;
	private int user_id;
	public String getCheck() {
		return check;
	}
	public void setCheck(String check) {
		this.check = check;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public RequestDoctor(String check, int user_id) {
		this.check = check;
		this.user_id = user_id;
	}
	
	
}
