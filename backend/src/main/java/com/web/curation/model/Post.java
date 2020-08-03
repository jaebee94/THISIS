package com.web.curation.model;

import java.util.Date;

public class Post {
	private int posts_id;
	private int user_id;
	private String posts_title;
	private String posts_main;
	private int health_count;
	private int notification_id;
	private String post_date;
	private String nickname;

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

	public String getPosts_title() {
		return posts_title;
	}

	public void setPosts_title(String posts_title) {
		this.posts_title = posts_title;
	}

	public String getPosts_main() {
		return posts_main;
	}

	public void setPosts_main(String posts_main) {
		this.posts_main = posts_main;
	}

	public int getHealth_count() {
		return health_count;
	}

	public void setHealth_count(int health_count) {
		this.health_count = health_count;
	}

	public int getNotification_id() {
		return notification_id;
	}

	public void setNotification_id(int notification_id) {
		this.notification_id = notification_id;
	}

	public Post() {

	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getPost_date() {
		return post_date;
	}

	public void setPost_date(String post_date) {
		this.post_date = post_date;
	}

	public Post(int posts_id, int user_id, String posts_title, String posts_main, int health_count, int notification_id,
			String post_date, String nickname) {
		super();
		this.posts_id = posts_id;
		this.user_id = user_id;
		this.posts_title = posts_title;
		this.posts_main = posts_main;
		this.health_count = health_count;
		this.notification_id = notification_id;
		this.post_date = post_date;
		this.nickname = nickname;
	}


}
