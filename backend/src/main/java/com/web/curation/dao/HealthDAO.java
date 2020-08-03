package com.web.curation.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.web.curation.model.Health;
import com.web.curation.model.Post;

@Mapper
public interface HealthDAO {
	public int selectHealth(int posts_id);
	public int selectUserHealth(int user_id);
	public int createHealth(int posts_id, int user_id, String nickname);
	public int deleteHealth(int posts_id, int user_id, String nickname);
	public int possibleHealth(int posts_id, int user_id, String nickname);
	public List<Health> selectHealthList(int posts_id);
	public List<Health> selectHealthUser(int user_id);
}
