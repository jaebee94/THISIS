package com.web.curation.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.web.curation.model.TagRelation;
@Mapper
public interface TagRelationDAO {
	public int createTagRelation(TagRelation tagrelation);
	public List<TagRelation> selectTagRelationpost(int posts_id);
	public List<TagRelation> selectTagRelationtag(int tagid);
	public int deleteTagRelation(int tagid, int posts_id);
}
