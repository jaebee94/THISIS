package com.web.curation.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.curation.dao.CommentHealthDAO;
import com.web.curation.model.Comment_health;

@Service
public class CommentHealthServiceImpl implements CommentHealthService{
	@Autowired
	CommentHealthDAO commenthealthdao;

	@Override
	public int selectCommentHealth(int comment_id) {
		return commenthealthdao.selectCommentHealth(comment_id);
	}

	@Override
	public int selectUserHealth(int user_id) {
		return commenthealthdao.selectUserHealth(user_id);
	}

	@Override
	public int createCommentHealth(int comment_id, int user_id) {
		return commenthealthdao.createCommentHealth(comment_id, user_id);
	}

	@Override
	public int deleteCommentHealth(int comment_id, int user_id) {
		return commenthealthdao.deleteCommentHealth(comment_id, user_id);
	}

	@Override
	public int possibleCommentHealth(int comment_id, int user_id) {
		return commenthealthdao.possibleCommentHealth(comment_id, user_id);
	}

	@Override
	public List<Comment_health> selectCommentHealthList(int comment_id) {
		return commenthealthdao.selectCommentHealthList(comment_id);
	}

	@Override
	public List<Comment_health> selectCommentHealthUser(int user_id) {
		return commenthealthdao.selectCommentHealthUser(user_id);
	}


}
