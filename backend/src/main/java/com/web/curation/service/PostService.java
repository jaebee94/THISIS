package com.web.curation.service;

import java.util.List;

import com.web.curation.model.Post;
import com.web.curation.model.Scrap;

public interface PostService {
	public List<Post> selectPostInfo(int user_id);
	public List<Post> selectAllPost();
	public int createPost(Post post);
	public int modifyPost(Post post);
	public int deletePost(int id);
	public int sumOfPost(int user_id);
	public List<Post> selectScrapInfo(List<Scrap> scrapList);
	public List<Post> selectMainFeed(int user_id);
}
