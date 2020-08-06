package com.web.curation.service;

import com.web.curation.model.Tag;

public interface TagService {
	public int createTag(String tagname);
	public Tag selectTag(int tagid);
	public int deleteTag(int tagid);
	public Tag selectTagByTagname(String tagname);
	public int selectCountByTagname(String tagname);
}
