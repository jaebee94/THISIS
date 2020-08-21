package com.web.curation.service;

import java.util.List;

import com.web.curation.model.Police;
import com.web.curation.model.PoliceJoin;

public interface PoliceService {
	public int selectPolice(int posts_id);
	public int selectUserPolice(int user_id);
	public int createPolice(Police police);
	public List<Police> selectpostPolice(int posts_id);
	public List<PoliceJoin> selectMyPolice(int user_id);
}
