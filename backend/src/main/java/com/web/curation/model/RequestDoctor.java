package com.web.curation.model;

public class RequestDoctor {
	private String check;
	private int doctor_id;
	public String getCheck() {
		return check;
	}
	public void setCheck(String check) {
		this.check = check;
	}
	public int getDoctor_id() {
		return doctor_id;
	}
	public void setDoctor_id(int doctor_id) {
		this.doctor_id = doctor_id;
	}
	public RequestDoctor() {
	}
	public RequestDoctor(String check, int doctor_id) {
		this.check = check;
		this.doctor_id = doctor_id;
	}
	
	
	
	
}
