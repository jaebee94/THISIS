package com.web.curation.service;

import java.util.List;

import com.web.curation.model.Follow;

public interface FollowService {
	public List<Follow> searchFollower(int user_id);
	public List<Follow> searchFollowee(int user_id);
	public int sumOfFollower(int user_id);
	public int sumOfFollowee(int user_id);
	public int createFollow(Follow follow);
	public int deleteFollow(Follow follow);
}
