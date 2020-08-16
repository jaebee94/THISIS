package com.web.curation.model;

public class TagRelation {
	int tag_id;
	int posts_id;

	public int getTag_id() {
		return tag_id;
	}

	public void setTag_id(int tag_id) {
		this.tag_id = tag_id;
	}

	public int getPost_id() {
		return posts_id;
	}

	public void setPost_id(int posts_id) {
		this.posts_id = posts_id;
	}

	public TagRelation() {
	}

	public TagRelation(int tag_id, int posts_id) {
		this.tag_id = tag_id;
		this.posts_id = posts_id;
	}

	@Override
	public String toString() {
		return "TagRelation [tag_id=" + tag_id + ", posts_id=" + posts_id + "]";
	}
	
	

}
