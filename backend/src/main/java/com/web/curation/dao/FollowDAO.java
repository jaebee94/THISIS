package com.web.curation.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.web.curation.model.Follow;

@Mapper
public interface FollowDAO {
	public List<Follow> searchFollower(int user_id);
	public List<Follow> searchFollowee(int user_id);
	public int sumOfFollower(int user_id);
	public int sumOfFollowee(int user_id);
	public int createFollow(Follow follow);
	public int deleteFollow(Follow follow);
}
