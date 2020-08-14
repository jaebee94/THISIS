package com.web.curation.model;

import java.util.List;

public class PostResponse {
	public int posts_id;
	public String diseasename;
	public Post post;
	public UserInfo userinfo;
	public List<Comment> comments;
	public List<Health> healths;
	public List<Tag> tags;
}
