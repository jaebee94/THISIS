package com.web.curation.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.web.curation.model.Auth;
import com.web.curation.model.Post;
import com.web.curation.model.PostResponse;
import com.web.curation.model.UserInfo;
import com.web.curation.service.AuthService;
import com.web.curation.service.CommentService;
import com.web.curation.service.HealthService;
import com.web.curation.service.PostService;
import com.web.curation.service.UserInfoService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = { "*" })
@RestController
@RequestMapping("/articles")
public class PostController {

	@Autowired
	PostService postservice;

	@Autowired
	AuthService authService;
	
	@Autowired
	UserInfoService userinfoService;

	@Autowired
	CommentService commentsService;
	
	@Autowired
	HealthService healthService;
	
	@ApiOperation(value = "모든 게시판을 반환한다.", response = List.class)
	@GetMapping
	public ResponseEntity<List<PostResponse>> selectAllPost(@RequestParam int num) throws Exception {
		List<Post> Allpage = postservice.selectAllPost();
		List<Post> page = null;
		List<PostResponse> response = new ArrayList<>();
		if (Allpage.size() / 10 > num && num * 10 + 10 <= Allpage.size()) {
			page = Allpage.subList(num * 10, num * 10 + 10);
			
			//페이지 돌면서 response에 다시 저장
			for(int i=0; i<page.size(); i++) {
				PostResponse temp = new PostResponse();
				temp.post_id = page.get(i).getPosts_id();
				temp.post = page.get(i);
				temp.comments = commentsService.selectComment(temp.post_id);
				temp.healths = healthService.selectHealthList(temp.post_id);
				response.add(temp);
			}
			return new ResponseEntity<List<PostResponse>>(response, HttpStatus.OK);
		} else if (Allpage.size() / 10 < num) {
			return new ResponseEntity<List<PostResponse>>(response, HttpStatus.NO_CONTENT);
		}
		else {
			page = Allpage.subList(num*10, Allpage.size());
			for(int i=0; i<page.size(); i++) {
				PostResponse temp = new PostResponse();
				temp.post_id = page.get(i).getPosts_id();
				temp.post = page.get(i);
				temp.comments = commentsService.selectComment(temp.post_id);
				temp.healths = healthService.selectHealthList(temp.post_id);
				response.add(temp);
			}
			return new ResponseEntity<List<PostResponse>>(response, HttpStatus.OK);
		}

	}

	@ApiOperation(value = "유저에 해당하는 모든 게시판를 반환한다.", response = List.class)
	@GetMapping("{user_id}")
	public ResponseEntity<List<Post>> selectPost(@PathVariable int user_id) throws Exception {
		return new ResponseEntity<List<Post>>(postservice.selectPostInfo(user_id), HttpStatus.OK);
	}

	@ApiOperation(value = "게시글 생성", response = String.class)
	@PostMapping
	public ResponseEntity<String> CreatePost(@RequestBody Post post, HttpServletRequest request) {

		// post 자신 아이디로 생성하게 수정
		String accessToken = (String) request.getAttribute("accessToken");

		int user_id = 1;
		if (accessToken != null) {
			Auth auth = authService.findAuthByAccessToken(accessToken);
			user_id = auth.getUser_id();
		}

		post.setUser_id(user_id);
		UserInfo userInfo = userinfoService.selectUserInfoByUserid(user_id);
		post.setNickname(userInfo.getNickname());

		if (postservice.createPost(post) == 1) {
			return new ResponseEntity<String>("success", HttpStatus.OK);
		}
		return new ResponseEntity<String>("fail", HttpStatus.NO_CONTENT);
	}

	@ApiOperation(value = "게시글 수정", response = String.class)
	@PutMapping("{posts_id}")
	public ResponseEntity<String> updatePost(@RequestBody Post post) {
		if (postservice.modifyPost(post) == 1) {
			return new ResponseEntity<String>("success", HttpStatus.OK);
		}
		return new ResponseEntity<String>("fail", HttpStatus.NO_CONTENT);
	}

	@ApiOperation(value = "게시글 삭제", response = String.class)
	@DeleteMapping("{posts_id}")
	public ResponseEntity<String> deleteUserInfo(@PathVariable int posts_id) {
		if (postservice.deletePost(posts_id) == 1) {
			return new ResponseEntity<String>("success", HttpStatus.OK);
		}
		return new ResponseEntity<String>("fail", HttpStatus.NO_CONTENT);
	}
}
