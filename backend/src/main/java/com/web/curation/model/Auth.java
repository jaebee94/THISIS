package com.web.curation.model;

public class Auth {
	private int user_id;
	private String refresh_token;
	private String access_token;
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public String getRefresh_token() {
		return refresh_token;
	}
	public void setRefresh_token(String refresh_token) {
		this.refresh_token = refresh_token;
	}
	public String getAccess_token() {
		return access_token;
	}
	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}
	public Auth(int user_id, String refresh_token, String access_token) {
		super();
		this.user_id = user_id;
		this.refresh_token = refresh_token;
		this.access_token = access_token;
	}
	public Auth() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "Auth [user_id=" + user_id + ", refresh_token=" + refresh_token + ", access_token=" + access_token + "]";
	}
	
	
		
}
