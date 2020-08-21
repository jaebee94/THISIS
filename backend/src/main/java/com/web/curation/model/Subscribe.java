package com.web.curation.model;

public class Subscribe {
	private int user_id;
	private String diseasecode;

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public String getDiseasecode() {
		return diseasecode;
	}

	public void setDiseasecode(String diseasecode) {
		this.diseasecode = diseasecode;
	}

	public Subscribe() {
	}

	public Subscribe(int user_id, String diseasecode) {
		this.user_id = user_id;
		this.diseasecode = diseasecode;
	}

	@Override
	public String toString() {
		return "Subscribe [user_id=" + user_id + ", diseasecode=" + diseasecode + "]";
	}
	
	
}
