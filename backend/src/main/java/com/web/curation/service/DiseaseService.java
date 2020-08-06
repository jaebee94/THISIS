package com.web.curation.service;

import java.util.List;

import com.web.curation.model.Disease;

public interface DiseaseService {
	public List<Disease> selectDisease();
	public Disease selectDiseaseByDiseasecode(String diseasecode);
	public int createDisease(Disease disease);
	public int deleteDisease(String diseasecode);
	public List<Disease> selectDiseaseByUserid(int user_id);
}
