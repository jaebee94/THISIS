package com.web.curation.model;

public class Tag_relation {
	int tagid;
	int posts_id;

	public int getTagid() {
		return tagid;
	}

	public void setTagid(int tagid) {
		this.tagid = tagid;
	}

	public int getPosts_id() {
		return posts_id;
	}

	public void setPosts_id(int posts_id) {
		this.posts_id = posts_id;
	}

	public Tag_relation() {
	}

	public Tag_relation(int tagid, int posts_id) {
		this.tagid = tagid;
		this.posts_id = posts_id;
	}

}
