package com.web.curation.model;

public class DoctorResponse {
	public Doctor doctor;
	public UserInfo userinfo;

	public DoctorResponse() {
	}

	@Override
	public String toString() {
		return "DoctorResponse [doctor=" + doctor + ", userinfo=" + userinfo + "]";
	}

}
