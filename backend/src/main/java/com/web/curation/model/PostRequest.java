package com.web.curation.model;

import java.util.List;

public class PostRequest {
	public Post post;
	public String diseasename;
	public List<String> tags;
	@Override
	public String toString() {
		return "PostRequest [post=" + post + ", diseasename=" + diseasename + ", tags=" + tags + "]";
	}
	
	
}
