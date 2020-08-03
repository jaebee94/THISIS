package com.web.curation.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.curation.dao.FollowDAO;
import com.web.curation.model.Follow;
@Service
public class FollowServiceImpl implements FollowService{

	@Autowired
	private FollowDAO followDao;
	
	@Override
	public List<Follow> searchFollower(int user_id) {
		return followDao.searchFollower(user_id);
	}

	@Override
	public List<Follow> searchFollowee(int user_id) {
		return followDao.searchFollowee(user_id);
	}

	@Override
	public int sumOfFollower(int user_id) {
		return followDao.sumOfFollower(user_id);
	}

	@Override
	public int sumOfFollowee(int user_id) {
		return followDao.sumOfFollowee(user_id);
	}

	@Override
	public int createFollow(Follow follow) {
		return followDao.createFollow(follow);
	}

	@Override
	public int deleteFollow(Follow follow) {
		return followDao.deleteFollow(follow);
	}

}
