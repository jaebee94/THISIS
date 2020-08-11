package com.web.curation.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.curation.dao.PoliceDAO;
import com.web.curation.model.Police;
import com.web.curation.model.PoliceJoin;

@Service
public class PoliceServiceImpl implements PoliceService {
	@Autowired
	PoliceDAO policedao;

	@Override
	public int selectPolice(int posts_id) {
		return policedao.selectPolice(posts_id);
	}

	@Override
	public int selectUserPolice(int user_id) {
		return policedao.selectUserPolice(user_id);
	}

	@Override
	public int createPolice(Police police) {
		return policedao.createPolice(police);
	}

	@Override
	public List<Police> selectpostPolice(int posts_id) {
		return policedao.selectpostPolice(posts_id);
	}

	@Override
	public List<PoliceJoin> selectMyPolice(int user_id) {
		return policedao.selectMyPolice(user_id);
	}

}
