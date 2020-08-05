package com.web.curation.model;

public class Tag {
	private int tagid;
	private String tagname;

	public int getTagid() {
		return tagid;
	}

	public void setTagid(int tagid) {
		this.tagid = tagid;
	}

	public String getTagname() {
		return tagname;
	}

	public void setTagname(String tagname) {
		this.tagname = tagname;
	}

	public Tag() {

	}

	public Tag(int tagid, String tagname) {
		this.tagid = tagid;
		this.tagname = tagname;
	}

}
