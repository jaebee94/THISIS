package com.web.curation.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.curation.dao.TagRelationDAO;
import com.web.curation.model.TagRelation;

@Service
public class TagRelationServiceImpl implements TagRelationService {

	@Autowired
	TagRelationDAO tagrelationdao;

	@Override
	public int createTagRelation(TagRelation tagrelation) {
		return tagrelationdao.createTagRelation(tagrelation);
	}

	@Override
	public int deleteTagRelation(int tag_id, int post_id) {
		return tagrelationdao.deleteTagRelation(tag_id, post_id);
	}

	@Override
	public List<TagRelation> selectTagRelationpost(int post_id) {
		return tagrelationdao.selectTagRelationpost(post_id);
	}

	@Override
	public List<TagRelation> selectTagRelationtag(int tag_id) {
		return tagrelationdao.selectTagRelationtag(tag_id);
	}

}
