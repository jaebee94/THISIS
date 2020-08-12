package com.web.curation.service;

import java.util.List;

import com.web.curation.model.Doctor;

public interface DoctorService {
	public int insertDoctor(Doctor doctor);
	public int deleteDoctor(int user_id);
	public int updateDoctor(int user_id);
	public List<Doctor> selectDoctor();
	public List<Doctor> selectCheckDoctor();
	public Doctor selectDoctorByUserid(int user_id);
	public int updateDoctorImg(String img);
	public int updateDoctorAuth(int user_id);
}
