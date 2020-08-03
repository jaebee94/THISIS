package com.web.curation.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.curation.dao.HealthDAO;
import com.web.curation.dao.PostDAO;
import com.web.curation.model.Health;

@Service
public class HealthServiceImpl implements HealthService{
	
	@Autowired
	private HealthDAO healthdao;
	
	@Override
	public int selectHealth(int posts_id) {
		return healthdao.selectHealth(posts_id);
	}

	@Override
	public int selectUserHealth(int user_id) {
		return healthdao.selectUserHealth(user_id);
	}

	@Override
	public int deleteHealth(int posts_id, int user_id, String nickname) {
		return healthdao.deleteHealth(posts_id, user_id, nickname);
	}

	@Override
	public int possibleHealth(int posts_id, int user_id, String nickname) {
		return healthdao.possibleHealth(posts_id, user_id, nickname);
	}

	@Override
	public int createHealth(int posts_id, int user_id, String nickname) {
		return healthdao.createHealth(posts_id, user_id, nickname);
	}

	@Override
	public List<Health> selectHealthList(int posts_id) {
		return healthdao.selectHealthList(posts_id);
	}

	@Override
	public List<Health> selectHealthUser(int user_id) {
		return healthdao.selectHealthUser(user_id);
	}


}
