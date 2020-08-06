package com.web.curation.service;

import com.web.curation.model.Tag;

public interface TagService {
	public int createTag(Tag tag);
	public Tag selectTag(int tagid);
	public int deleteTag(int tagid);
}
