package com.web.curation.model;

public class Scrap {
	private int posts_id;
	private int user_id;

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

	public Scrap(int posts_id, int user_id) {
		this.posts_id = posts_id;
		this.user_id = user_id;
	}

	public Scrap() {
	}

	@Override
	public String toString() {
		return "Scrap [posts_id=" + posts_id + ", user_id=" + user_id + "]";
	}
	
}
