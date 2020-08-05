package com.web.curation.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.web.curation.model.Disease;

@Mapper
public interface DiseaseDAO {
	public List<Disease> selectDisease();
	public Disease selectDiseaseByDiseasecode(String diseasecode);
	public int createDisease(Disease disease);
	public int deleteDisease(String diseasecode);
	public List<Disease> selectDiseaseByUserid(int user_id);
}
