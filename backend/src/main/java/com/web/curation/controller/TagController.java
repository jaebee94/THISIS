package com.web.curation.controller;

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

import com.web.curation.model.Tag;
import com.web.curation.service.TagService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = { "*" })
@RestController
@RequestMapping("/tag")
public class TagController {
	
	@Autowired
	TagService tagservice;
	
	@ApiOperation(value = "태그 추가", response = String.class)
	@PostMapping
	public ResponseEntity<String> CreateTag(@RequestBody Tag tag) {
		if (tagservice.createTag(tag.getTagname()) == 1) {
			return new ResponseEntity<String>("success", HttpStatus.OK);
		}
		return new ResponseEntity<String>("fail", HttpStatus.NO_CONTENT);
	}
	
	@ApiOperation(value = "태그 불러오기", response = Tag.class)
	@GetMapping("{tagid}")
	public ResponseEntity<Tag> selectTag(@PathVariable int tagid) throws Exception {
		Tag tag = tagservice.selectTag(tagid);
		return new ResponseEntity<Tag>(tag, HttpStatus.OK);
	}
	
	@ApiOperation(value = "태그 삭제", response = String.class)
	@DeleteMapping("{tagid}")
	public ResponseEntity<String> deleteTag(@PathVariable int tagid) {
		if (tagservice.deleteTag(tagid) == 1) {
			return new ResponseEntity<String>("success", HttpStatus.OK);
		}
		return new ResponseEntity<String>("fail", HttpStatus.NO_CONTENT);
	}
	
}
