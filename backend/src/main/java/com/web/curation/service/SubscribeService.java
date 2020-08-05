package com.web.curation.service;

import java.util.List;

import com.web.curation.model.Subscribe;

public interface SubscribeService {
	public List<Subscribe> selectSubscribe();
	public List<Subscribe> selectSubscribeByUserid(String user_id);
	public int createSubscribe(Subscribe subscribe);
	public int deleteSubscribe(Subscribe subscribe);
}
