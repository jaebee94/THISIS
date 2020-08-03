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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.web.curation.model.Health;
import com.web.curation.model.Post;
import com.web.curation.model.Scrap;
import com.web.curation.service.HealthService;
import com.web.curation.service.PostService;
import com.web.curation.service.ScrapService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = { "*" })
@RestController
@RequestMapping("/scrap")
public class ScrapController {
	
	@Autowired
	ScrapService scrapService;
	@Autowired
	PostService postService;
	@ApiOperation(value = "유저에 해당하는 스크랩 불러오기.", response = List.class)
	@GetMapping("{user_id}")
	public ResponseEntity<List<Post>> selectuserScrap(@PathVariable int user_id) throws Exception {
		List<Scrap> scrapList = scrapService.selectScrap(user_id);
		System.out.println(scrapList.toString());
		List<Post> postList = postService.selectScrapInfo(scrapList);
		return new ResponseEntity<List<Post>>(postList, HttpStatus.OK);
	}
	
	@ApiOperation(value = "해당하는 스크랩 존재유무", response = List.class)
	@GetMapping
	public ResponseEntity<Integer> isExistedScrap(@RequestParam int posts_id,@RequestParam int user_id) throws Exception {
		int result = scrapService.isExistedScrap(posts_id,user_id);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@ApiOperation(value = "스크랩 추가", response = String.class)
	@PostMapping
	public ResponseEntity<String> Createhealth(@RequestBody Scrap scrap) {
		System.out.println(scrap.toString());
		if (scrapService.createScrap(scrap) == 1) {
			return new ResponseEntity<String>("success", HttpStatus.OK);
		}
		return new ResponseEntity<String>("fail", HttpStatus.NO_CONTENT);
	}

	
	@ApiOperation(value = "스크랩 삭제", response = String.class)
	@DeleteMapping("{posts_id}/{user_id}")
	public ResponseEntity<String> deleteUserInfo(@PathVariable int posts_id,@PathVariable int user_id) {
		if (scrapService.deleteScrap(posts_id, user_id) == 1) {
			return new ResponseEntity<String>("success", HttpStatus.OK);
		}
		return new ResponseEntity<String>("fail", HttpStatus.NO_CONTENT);
	}
}
