package com.web.curation.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.web.curation.model.Tag_relation;
@Mapper
public interface TagRelationDAO {
	public int createTagRelation(Tag_relation tagrelation);
	public List<Tag_relation> selectTagRelationpost(int posts_id);
	public List<Tag_relation> selectTagRelationtag(int tagid);
	public int deleteTagRelation(int tagid, int posts_id);
}
