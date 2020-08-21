package com.web.curation.model;

public class PoliceJoin {
	private int posts_id;
	private int user_id;
	private String reason;

	public int getPosts_id() {
		return posts_id;
	}

	public void setPosts_id(int posts_id) {
		this.posts_id = posts_id;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public PoliceJoin() {

	}

	public PoliceJoin(int posts_id, int user_id, String reason) {
		this.posts_id = posts_id;
		this.user_id = user_id;
		this.reason = reason;
	}

}
