package com.web.curation.model;

public class TagRelation {
	int tag_id;
	int post_id;

	public int getTag_id() {
		return tag_id;
	}

	public void setTag_id(int tagid) {
		this.tag_id = tagid;
	}

	public int getPost_id() {
		return post_id;
	}

	public void setPost_id(int posts_id) {
		this.post_id = posts_id;
	}

	public TagRelation() {
	}

	public TagRelation(int tag_id, int post_id) {
		this.tag_id = tag_id;
		this.post_id = post_id;
	}

}
