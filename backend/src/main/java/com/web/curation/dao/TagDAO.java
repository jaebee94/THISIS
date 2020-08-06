package com.web.curation.dao;

import org.apache.ibatis.annotations.Mapper;

import com.web.curation.model.Tag;
@Mapper
public interface TagDAO {
	public int createTag(String tagname);
	public Tag selectTag(int tagid);
	public int deleteTag(int tagid);
	public Tag selectTagByTagname(String tagname);
	public int selectCountByTagname(String tagname);
}
