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

import com.web.curation.model.TagRelation;
import com.web.curation.service.TagRelationService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = { "*" })
@RestController
@RequestMapping("/tagrelation")
public class TagRelationController {
	
	@Autowired
	TagRelationService tagrelationservice;
	
	@ApiOperation(value = "태그 릴레이션 추가", response = String.class)
	@PostMapping
	public ResponseEntity<String> CreateTagRelation(@RequestBody TagRelation tagrelation) {
		if (tagrelationservice.createTagRelation(tagrelation)==1) {
			return new ResponseEntity<String>("success", HttpStatus.OK);
		}
		return new ResponseEntity<String>("fail", HttpStatus.NO_CONTENT);
	}
	
	@ApiOperation(value = "태그 릴레이션 불러오기", response = TagRelation.class)
	@GetMapping("/post/{posts_id}")
	public ResponseEntity<List<TagRelation>> selectTagRelationpost(@PathVariable int posts_id) throws Exception {
		List<TagRelation> tagrelation=tagrelationservice.selectTagRelationpost(posts_id);
		return new ResponseEntity<List<TagRelation>>(tagrelation, HttpStatus.OK);
	}
	
	@ApiOperation(value = "태그 릴레이션 불러오기", response = TagRelation.class)
	@GetMapping("/tag/{tagid}")
	public ResponseEntity<List<TagRelation>> selectTagRelationtag(@PathVariable int tagid) throws Exception {
		List<TagRelation> tagrelation=tagrelationservice.selectTagRelationtag(tagid);
		return new ResponseEntity<List<TagRelation>>(tagrelation, HttpStatus.OK);
	}
	
	@ApiOperation(value = "태그 릴레이션 삭제", response = String.class)
	@DeleteMapping
	public ResponseEntity<String> deleteTagRelation(@RequestBody TagRelation tagrelation) {
		System.out.println("delete tagRelation : " + tagrelation.toString());
		if (tagrelationservice.deleteTagRelation(tagrelation.getTag_id(), tagrelation.getPost_id())== 1) {
			return new ResponseEntity<String>("success", HttpStatus.OK);
		}
		return new ResponseEntity<String>("fail", HttpStatus.NO_CONTENT);
	}
}
