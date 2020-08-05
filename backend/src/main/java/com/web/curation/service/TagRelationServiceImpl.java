package com.web.curation.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.curation.dao.TagRelationDAO;
import com.web.curation.model.Tag_relation;

@Service
public class TagRelationServiceImpl implements TagRelationService {

	@Autowired
	TagRelationDAO tagrelationdao;

	@Override
	public int createTagRelation(Tag_relation tagrelation) {
		return tagrelationdao.createTagRelation(tagrelation);
	}

	@Override
	public int deleteTagRelation(int tagid, int posts_id) {
		return tagrelationdao.deleteTagRelation(tagid, posts_id);
	}

	@Override
	public List<Tag_relation> selectTagRelationpost(int posts_id) {
		return tagrelationdao.selectTagRelationpost(posts_id);
	}

	@Override
	public List<Tag_relation> selectTagRelationtag(int tagid) {
		return tagrelationdao.selectTagRelationtag(tagid);
	}

}
