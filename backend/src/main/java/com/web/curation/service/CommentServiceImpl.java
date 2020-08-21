package com.web.curation.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.curation.dao.CommentDAO;
import com.web.curation.model.Comment;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentDAO CommentDao;

	@Override
	public List<Comment> selectComment(int posts_id) {
		return CommentDao.selectComment(posts_id);
	}

	@Override
	public int createComment(Comment comment) {
		return CommentDao.createComment(comment);
	}

	@Override
	public int modifyComment(Comment comment) {
		return CommentDao.modifyComment(comment);
	}

	@Override
	public int deleteComment(int id) {
		return CommentDao.deleteComment(id);
	}

	@Override
	public int modifyhideComment(Comment comment) {
		return CommentDao.modifyhideComment(comment);
	}

	@Override
	public int checkComment(int user_id, int posts_id) {
		return CommentDao.checkComment(user_id, posts_id);
	}
}
