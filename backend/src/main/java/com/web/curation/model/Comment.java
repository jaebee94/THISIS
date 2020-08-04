package com.web.curation.model;

public class Comment {
	private int user_id;
	private int posts_id;
	private String comment_main;
	private int hide;
	private int comment_id;
	private String comment_date;

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public int getPosts_id() {
		return posts_id;
	}

	public void setPosts_id(int posts_id) {
		this.posts_id = posts_id;
	}

	public String getComment_main() {
		return comment_main;
	}

	public void setComment_main(String comment_main) {
		this.comment_main = comment_main;
	}

	public int getHide() {
		return hide;
	}

	public void setHide(int hide) {
		this.hide = hide;
	}

	public int getComment_id() {
		return comment_id;
	}

	public void setComment_id(int comment_id) {
		this.comment_id = comment_id;
	}

	public String getComment_date() {
		return comment_date;
	}

	public void setComment_date(String comment_date) {
		this.comment_date = comment_date;
	}

	public Comment(int user_id, int posts_id, String comment_main, int hide, int comment_id,
			String comment_date) {
		this.user_id = user_id;
		this.posts_id = posts_id;
		this.comment_main = comment_main;
		this.hide = hide;
		this.comment_id = comment_id;
		this.comment_date = comment_date;
	}

	public Comment() {
	}

}
