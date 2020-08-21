package com.web.curation.model;

public class DesidePost {
	int posts_id;
	String check;

	public int getPosts_id() {
		return posts_id;
	}

	public void setPosts_id(int posts_id) {
		this.posts_id = posts_id;
	}

	public String getCheck() {
		return check;
	}

	public void setCheck(String check) {
		this.check = check;
	}

	public DesidePost(int posts_id, String check) {
		this.posts_id = posts_id;
		this.check = check;
	}

	public DesidePost() {
	}

}
