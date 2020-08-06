package com.web.curation.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.web.curation.model.Post;
import com.web.curation.model.Scrap;
import com.web.curation.model.Tag;
import com.web.curation.model.Tag_relation;
import com.web.curation.service.TagRelationService;
import com.web.curation.service.TagService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = { "*" })
@RestController
@RequestMapping("/tag-relation")
public class TagRelationController {
	
	@Autowired
	TagRelationService tagrelationservice;
	
	@ApiOperation(value = "태그 릴레이션 추가", response = String.class)
	@PostMapping
	public ResponseEntity<String> CreateTagRelation(@RequestBody Tag_relation tagrelation) {
		if (tagrelationservice.createTagRelation(tagrelation)==1) {
			return new ResponseEntity<String>("success", HttpStatus.OK);
		}
		return new ResponseEntity<String>("fail", HttpStatus.NO_CONTENT);
	}
	
	@ApiOperation(value = "태그 릴레이션 불러오기", response = Tag_relation.class)
	@GetMapping("/post/{posts_id}")
	public ResponseEntity<List<Tag_relation>> selectTagRelationpost(@PathVariable int posts_id) throws Exception {
		List<Tag_relation> tagrelation=tagrelationservice.selectTagRelationpost(posts_id);
		return new ResponseEntity<List<Tag_relation>>(tagrelation, HttpStatus.OK);
	}
	
	@ApiOperation(value = "태그 릴레이션 불러오기", response = Tag_relation.class)
	@GetMapping("/tag/{tagid}")
	public ResponseEntity<List<Tag_relation>> selectTagRelationtag(@PathVariable int tagid) throws Exception {
		List<Tag_relation> tagrelation=tagrelationservice.selectTagRelationtag(tagid);
		return new ResponseEntity<List<Tag_relation>>(tagrelation, HttpStatus.OK);
	}
	
	@ApiOperation(value = "태그 릴레이션 삭제", response = String.class)
	@DeleteMapping("{tagid}/{posts_id}")
	public ResponseEntity<String> deleteTagRelation(@PathVariable int tagid,@PathVariable int posts_id) {
		if (tagrelationservice.deleteTagRelation(tagid, posts_id)== 1) {
			return new ResponseEntity<String>("success", HttpStatus.OK);
		}
		return new ResponseEntity<String>("fail", HttpStatus.NO_CONTENT);
	}
}
