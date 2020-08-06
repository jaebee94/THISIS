package com.web.curation.service;


import java.util.List;

import com.web.curation.model.Tag_relation;

public interface TagRelationService {
	public int createTagRelation(Tag_relation tagrelation);
	public List<Tag_relation> selectTagRelationpost(int posts_id);
	public List<Tag_relation> selectTagRelationtag(int tagid);
	public int deleteTagRelation(int tagid, int posts_id);
}
