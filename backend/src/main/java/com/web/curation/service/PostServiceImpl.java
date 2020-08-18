package com.web.curation.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.curation.dao.PostDAO;
import com.web.curation.model.Post;
import com.web.curation.model.Postpolice;
import com.web.curation.model.Scrap;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private PostDAO PostDao;
	
	@Override
	public int createPost(Post post) {
		return PostDao.createPost(post);
	}

	@Override
	public int modifyPost(Post post) {
		return PostDao.modifyPost(post);

	}

	@Override
	public int deletePost(int id) {
		return PostDao.deletePost(id);
		
	}

	@Override
	public List<Post> selectAllPost() {
		return PostDao.selectAllPost();
	}
	@Override
	public int sumOfPost(int user_id) {
		return PostDao.sumOfPost(user_id);
	}

	@Override
	public List<Post> selectScrapInfo(List<Scrap> scrapList){;
		return PostDao.selectScrapInfo(scrapList);
	}

	@Override
	public List<Post> selectMainFeed(int user_id) {
		return PostDao.selectMainFeed(user_id);
	}

	@Override
	public int selectAutoIncrement() {
		return PostDao.selectAutoIncrement();
	}

	@Override
	public List<Post> selectQnA() {
		return PostDao.selectQnA();
	}

	@Override
	public List<Post> selectPost(int user_id) {
		return PostDao.selectPost(user_id);
	}

	@Override
	public int hiddenupdate(int posts_id) {
		return PostDao.hiddenupdate(posts_id);
	}

	@Override
	public List<Postpolice> selectAllPostpolice() {
		return PostDao.selectAllPostpolice();
	}

	@Override
	public List<Post> selectQnAByMain(String keyword) {
		return PostDao.selectQnAByMain(keyword);
	}

	@Override
	public List<Post> selectQnAByTitle(String keyword) {
		return PostDao.selectQnAByTitle(keyword);
	}

	@Override
	public List<Post> selectQnAByDisease(String keyword) {
		return PostDao.selectQnAByDisease(keyword);
	}

	@Override
	public int showupdate(int posts_id) {
		return PostDao.showupdate(posts_id);
	}

	@Override
	public int updatehiddenuser(int user_id) {
		return PostDao.updatehiddenuser(user_id);
		
	}

	@Override
	public int updateshownuser(int user_id) {
		return PostDao.updateshownuser(user_id);
		
	}
}
