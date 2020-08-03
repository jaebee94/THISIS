package com.web.curation.service;

import java.util.List;

import com.web.curation.model.Health;

public interface HealthService {
	public int selectHealth(int posts_id);
	public int selectUserHealth(int user_id);
	public int deleteHealth(int posts_id, int user_id, String nickname);
	public int possibleHealth(int posts_id, int user_id, String nickname);
	public int createHealth(int posts_id, int user_id, String nickname);
	public List<Health> selectHealthList(int posts_id);
	public List<Health> selectHealthUser(int user_id);
}
