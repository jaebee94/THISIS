package com.web.curation.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.web.curation.model.Doctor;

@Mapper
public interface DoctorDAO {
	public int insertDoctor(Doctor doctor);

	public int deleteDoctor(int user_id);

	public int updateDoctor(int user_id);

	public List<Doctor> selectDoctor();

	public List<Doctor> selectCheckDoctor();

	public Doctor selectDoctorByUserid(int user_id);

	public int updateDoctorImg(String img);

	public int updateDoctorAuth(int user_id);
	
	public int updateDoctorAuthFail(int user_id);
}
