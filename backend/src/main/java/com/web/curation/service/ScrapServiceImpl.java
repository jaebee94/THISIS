package com.web.curation.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.curation.dao.ScrapDAO;
import com.web.curation.model.Scrap;
@Service
public class ScrapServiceImpl implements ScrapService{
	
	@Autowired
	private ScrapDAO scrapdao;
	@Override
	public List<Scrap> selectScrap(int user_id) {
		return scrapdao.selectScrap(user_id);
	}

	@Override
	public int createScrap(Scrap scrap) {
		return scrapdao.createScrap(scrap);
	}

	@Override
	public int deleteScrap(int posts_id, int user_id) {
		return scrapdao.deleteScrap(posts_id, user_id);
	}

	@Override
	public int isExistedScrap(int posts_id, int user_id) {
		return scrapdao.isExistedScrap(posts_id, user_id);
	}

}
