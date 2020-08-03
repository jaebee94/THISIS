package com.web.curation.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.web.curation.model.Health;
import com.web.curation.model.Post;
import com.web.curation.model.Scrap;

@Mapper
public interface ScrapDAO {
	public List<Scrap> selectScrap(int user_id);
	public int createScrap(Scrap scrap);
	public int deleteScrap(int posts_id, int user_id);
	public int isExistedScrap(int posts_id, int user_id);
	
}
