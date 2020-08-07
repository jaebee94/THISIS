package com.web.curation.service;

import java.util.List;

import com.web.curation.model.Comment;

public interface CommentService {
	public List<Comment> selectComment(int posts_id);
	public int createComment(Comment comment);
	public int modifyComment(Comment comment);
	public int deleteComment(int comment_id);
	public int modifyhideComment(Comment comment);
	public int checkComment(int user_id, int posts_id);
}
