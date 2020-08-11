package com.web.curation.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.web.curation.model.Police;
import com.web.curation.model.PoliceJoin;

@Mapper
public interface PoliceDAO {
	public int selectPolice(int posts_id);
	public int selectUserPolice(int user_id);
	public int createPolice(Police police);
	public List<Police> selectpostPolice(int posts_id);
	public List<PoliceJoin> selectMyPolice(int user_id);
}
