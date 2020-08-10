package com.web.curation.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.curation.dao.TagDAO;
import com.web.curation.model.Tag;

@Service
public class TagServiceImpl implements TagService {
	@Autowired
	TagDAO tagdao;

	@Override
	public int createTag(String tagname) {
		return tagdao.createTag(tagname);
	}

	@Override
	public Tag selectTag(int tagid) {
		return tagdao.selectTag(tagid);
	}

	@Override
	public int deleteTag(int tagid) {
		return tagdao.deleteTag(tagid);
	}

	@Override
	public Tag selectTagByTagname(String tagname) {
		return tagdao.selectTagByTagname(tagname);
	}

	@Override
	public int selectCountByTagname(String tagname) {
		return tagdao.selectCountByTagname(tagname);
	}

	@Override
	public List<Tag> selectTagByPostsId(int posts_id) {
		return tagdao.selectTagByPostsId(posts_id);
	}

}
