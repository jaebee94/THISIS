package com.web.curation.service;


import java.util.List;

import com.web.curation.model.TagRelation;

public interface TagRelationService {
	public int createTagRelation(TagRelation tagrelation);
	public List<TagRelation> selectTagRelationpost(int posts_id);
	public List<TagRelation> selectTagRelationtag(int tagid);
	public int deleteTagRelation(int tagid, int posts_id);
}
