package com.web.curation.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.web.curation.model.Post;
import com.web.curation.model.Postpolice;
import com.web.curation.model.Scrap;

@Mapper
public interface PostDAO {
	public List<Post> selectPost(int user_id);
	public List<Post> selectAllPost();
	public List<Postpolice> selectAllPostpolice();
	public int createPost(Post post);
	public int modifyPost(Post post);
	public int deletePost(int id);
	public int sumOfPost(int user_id);
	public List<Post> selectScrapInfo(List<Scrap> scrapList);
	public List<Post> selectMainFeed(int user_id);
	public int selectAutoIncrement();
	public List<Post> selectQnA();
	public int hiddenupdate(int posts_id);
	public List<Post> selectQnAByMain(String keyword);
	public List<Post> selectQnAByTitle(String keyword);
	public List<Post> selectQnAByDisease(String keyword);
	public int showupdate(int posts_id);
	public int updatehiddenuser(int user_id);
	public int updateshownuser(int user_id);
}
