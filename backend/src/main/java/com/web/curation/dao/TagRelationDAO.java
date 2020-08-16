package com.web.curation.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.web.curation.model.TagRelation;
@Mapper
public interface TagRelationDAO {
	public int createTagRelation(TagRelation tagrelation);
	public List<TagRelation> selectTagRelationpost(int posts_id);
	public List<TagRelation> selectTagRelationtag(int tag_id);
	public int deleteTagRelation(int tag_id, int posts_id);
}
