package com.web.curation.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.curation.dao.SubscribeDAO;
import com.web.curation.model.Subscribe;

@Service
public class SubscribeServiceImpl implements SubscribeService{

	@Autowired
	SubscribeDAO subscribeDao;
	
	@Override
	public List<Subscribe> selectSubscribe() {
		return subscribeDao.selectSubscribe();
	}

	@Override
	public List<Subscribe> selectSubscribeByUserid(String user_id) {
		return subscribeDao.selectSubscribeByUserid(user_id);
	}

	@Override
	public int createSubscribe(Subscribe subscribe) {
		return subscribeDao.createSubscribe(subscribe);
	}

	@Override
	public int deleteSubscribe(Subscribe subscribe) {
		return subscribeDao.deleteSubscribe(subscribe);
	}

}
