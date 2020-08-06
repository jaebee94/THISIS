package com.web.curation.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

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

import com.web.curation.model.Auth;
import com.web.curation.model.Health;
import com.web.curation.model.Post;
import com.web.curation.model.PostResponse;
import com.web.curation.model.Scrap;
import com.web.curation.model.UserInfo;
import com.web.curation.service.AuthService;
import com.web.curation.service.CommentService;
import com.web.curation.service.DiseaseService;
import com.web.curation.service.HealthService;
import com.web.curation.service.PostService;
import com.web.curation.service.ScrapService;
import com.web.curation.service.UserInfoService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = { "*" })
@RestController
@RequestMapping("/scrap")
public class ScrapController {
	
	@Autowired
	ScrapService scrapService;
	
	@Autowired
	PostService postService;
	
	@Autowired
	UserInfoService userinfoService;
	
	@Autowired
	CommentService commentsService;
	
	@Autowired
	HealthService healthService;
	
	@Autowired
	AuthService authService;
	
	@Autowired
	DiseaseService diseaseService;
	
	@ApiOperation(value = "유저에 해당하는 스크랩 불러오기.", response = List.class)
	@GetMapping("{user_id}")
	public ResponseEntity<List<PostResponse>> selectuserScrap(@PathVariable int user_id) throws Exception {
		List<Scrap> scrapList = scrapService.selectScrap(user_id);
		//System.out.println(scrapList.toString());
		List<Post> postList = postService.selectScrapInfo(scrapList);
		List<PostResponse> response = new ArrayList<>();

		for (int i = 0; i < postList.size(); i++) {
			PostResponse temp = new PostResponse();
			temp.posts_id=postList.get(i).getPosts_id();
			temp.post=postList.get(i);
			try {
				temp.diseasename = diseaseService.selectDiseaseByDiseasecode(temp.post.getDiseasecode()).getDiseasename();
			}catch(NullPointerException e) {
				temp.diseasename = "";
			}
			int post_user_id = temp.post.getUser_id();
			temp.userinfo = userinfoService.selectUserInfoByUserid(post_user_id);
			temp.userinfo.setPassword(null);
			temp.comments=commentsService.selectComment(temp.posts_id);
			temp.healths = healthService.selectHealthList(temp.posts_id);
			response.add(temp);
		}
		return new ResponseEntity<List<PostResponse>>(response, HttpStatus.OK);
	}
	
	@ApiOperation(value = "해당하는 스크랩 존재유무", response = List.class)
	@GetMapping
	public ResponseEntity<Integer> isExistedScrap(@RequestParam int posts_id,@RequestParam int user_id) throws Exception {
		int result = scrapService.isExistedScrap(posts_id,user_id);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@ApiOperation(value = "스크랩 추가", response = String.class)
	@PostMapping
	public ResponseEntity<String> Createhealth(@RequestBody Scrap scrap, HttpServletRequest request) {
		//System.out.println(scrap.toString());
		
		String accessToken = (String) request.getAttribute("accessToken");
		int user_id = 1;
		if (accessToken != null) {
			Auth auth = authService.findAuthByAccessToken(accessToken);
			user_id = auth.getUser_id();
		}
		
		scrap.setuser_id(user_id);
		UserInfo userInfo = userinfoService.selectUserInfoByUserid(user_id);
		
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
