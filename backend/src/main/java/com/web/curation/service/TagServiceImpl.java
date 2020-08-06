package com.web.curation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.curation.dao.TagDAO;
import com.web.curation.model.Tag;

@Service
public class TagServiceImpl implements TagService {
	@Autowired
	TagDAO tagdao;

	@Override
	public int createTag(Tag tag) {
		return tagdao.createTag(tag);
	}

	@Override
	public Tag selectTag(int tagid) {
		return tagdao.selectTag(tagid);
	}

	@Override
	public int deleteTag(int tagid) {
		return tagdao.deleteTag(tagid);
	}

}
