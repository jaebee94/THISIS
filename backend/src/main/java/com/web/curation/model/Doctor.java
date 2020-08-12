package com.web.curation.model;

public class Doctor {
	private int user_id;
	private String doctorimg;
	private String doctorauth;

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public String getDoctorimg() {
		return doctorimg;
	}

	public void setDoctorimg(String doctorimg) {
		this.doctorimg = doctorimg;
	}

	public String getDoctorauth() {
		return doctorauth;
	}

	public void setDoctorauth(String doctorauth) {
		this.doctorauth = doctorauth;
	}

	public Doctor() {

	}

	public Doctor(int user_id, String doctorimg, String doctorauth) {
		this.user_id = user_id;
		this.doctorimg = doctorimg;
		this.doctorauth = doctorauth;
	}

}
