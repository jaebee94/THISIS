package com.web.curation.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.curation.dao.DiseaseDAO;
import com.web.curation.model.Disease;

@Service
public class DiseaseServiceImpl implements DiseaseService{

	@Autowired
	DiseaseDAO diseaseDao;
	
	@Override
	public List<Disease> selectDisease() {
		return diseaseDao.selectDisease();
	}

	@Override
	public Disease selectDiseaseByDiseasecode(String diseasecode) {
		return diseaseDao.selectDiseaseByDiseasecode(diseasecode);
	}

	@Override
	public int createDisease(Disease disease) {
		return diseaseDao.createDisease(disease);
	}

	@Override
	public int deleteDisease(String diseasecode) {
		return diseaseDao.deleteDisease(diseasecode);
	}

	@Override
	public List<Disease> selectDiseaseByUserid(int user_id) {
		return diseaseDao.selectDiseaseByUserid(user_id);
	}

}
