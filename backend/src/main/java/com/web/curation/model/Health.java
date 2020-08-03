package com.web.curation.model;

public class Health {
	private int posts_id;
	private int user_id;
	private String nickname;
	
	public int getPosts_id() {
		return posts_id;
	}

	public void setPosts_id(int posts_id) {
		this.posts_id = posts_id;
	}

	public int getuser_id() {
		return user_id;
	}

	public void setuser_id(int user_id) {
		this.user_id = user_id;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	public Health(int posts_id, int user_id, String nickname) {
		this.posts_id = posts_id;
		this.user_id = user_id;
		this.nickname = nickname;
	}

	public Health() {
	}

}
