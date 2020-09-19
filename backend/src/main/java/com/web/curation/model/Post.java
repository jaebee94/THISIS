package com.web.curation.model;

public class Post {
	private int posts_id;
	private int user_id;
	private String posts_title;
	private String posts_main;
	private String post_date;
	private int category;
	private String diseasecode;
	private String imgsrc;
	private int hidden;

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

	public String getPost_date() {
		return post_date;
	}

	public void setPost_date(String post_date) {
		this.post_date = post_date;
	}

	public int getCategory() {
		return category;
	}

	public void setCategory(int category) {
		this.category = category;
	}

	public String getDiseasecode() {
		return diseasecode;
	}

	public void setDiseasecode(String diseasecode) {
		this.diseasecode = diseasecode;
	}

	public String getImgsrc() {
		return imgsrc;
	}

	public void setImgsrc(String imgsrc) {
		this.imgsrc = imgsrc;
	}
	
	public int getHidden() {
		return hidden;
	}

	public void setHidden(int hidden) {
		this.hidden = hidden;
	}

	public int getHidden() {
		return hidden;
	}

	public void setHidden(int hidden) {
		this.hidden = hidden;
	}

	public Post() {
	}

	public Post(int posts_id, int user_id, String posts_title, String posts_main, String post_date, int category,
			String diseasecode, String imgsrc, int hidden) {
		this.posts_id = posts_id;
		this.user_id = user_id;
		this.posts_title = posts_title;
		this.posts_main = posts_main;
		this.post_date = post_date;
		this.category = category;
		this.diseasecode = diseasecode;
		this.imgsrc = imgsrc;
		this.hidden = hidden;
	}

	@Override
	public String toString() {
		return "Post [posts_id=" + posts_id + ", user_id=" + user_id + ", posts_title=" + posts_title + ", posts_main="
				+ posts_main + ", post_date=" + post_date + ", category=" + category + ", diseasecode=" + diseasecode
				+ ", imgsrc=" + imgsrc + ", hidden=" + hidden + "]";
	}
	

}
