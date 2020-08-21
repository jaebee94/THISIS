package com.web.curation.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.web.curation.model.Subscribe;


@Mapper
public interface SubscribeDAO {
	public List<Subscribe> selectSubscribe();
	public List<Subscribe> selectSubscribeByUserid(String user_id);
	public int createSubscribe(Subscribe subscribe);
	public int deleteSubscribe(Subscribe subscribe);
}
