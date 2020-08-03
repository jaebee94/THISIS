package com.web.curation.service;

import java.util.List;

import com.web.curation.model.Scrap;

public interface ScrapService {
	public List<Scrap> selectScrap(int user_id);
	public int createScrap(Scrap scrap);
	public int deleteScrap(int posts_id, int user_id);
	public int isExistedScrap(int posts_id, int user_id);
}
