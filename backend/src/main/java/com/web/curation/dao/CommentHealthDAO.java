package com.web.curation.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.web.curation.model.Comment_health;

@Mapper
public interface CommentHealthDAO {
	public int selectCommentHealth(int comment_id);
	public int selectUserHealth(int user_id);
	public int createCommentHealth(int comment_id, int user_id);
	public int deleteCommentHealth(int comment_id, int user_id);
	public int possibleCommentHealth(int comment_id, int user_id);
	public List<Comment_health> selectCommentHealthList(int comment_id);
	public List<Comment_health> selectCommentHealthUser(int user_id);
}
