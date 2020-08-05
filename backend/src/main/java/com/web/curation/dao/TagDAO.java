package com.web.curation.dao;

import org.apache.ibatis.annotations.Mapper;

import com.web.curation.model.Tag;
@Mapper
public interface TagDAO {
	public int createTag(Tag tag);
	public Tag selectTag(int tagid);
	public int deleteTag(int tagid);
}
