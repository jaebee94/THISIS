package com.web.curation.model;

public class disease {
	private String diseasecode;
	private String diseasename;

	public String getDiseasecode() {
		return diseasecode;
	}

	public void setDiseasecode(String diseasecode) {
		this.diseasecode = diseasecode;
	}

	public String getDiseasename() {
		return diseasename;
	}

	public void setDiseasename(String diseasename) {
		this.diseasename = diseasename;
	}

	public disease() {
	}

	public disease(String diseasecode, String diseasename) {
		this.diseasecode = diseasecode;
		this.diseasename = diseasename;
	}

}
