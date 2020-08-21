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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.web.curation.model.Auth;
import com.web.curation.model.Comment;
import com.web.curation.model.CommentResponse;
import com.web.curation.service.AuthService;
import com.web.curation.service.CommentService;
import com.web.curation.service.UserInfoService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = { "*" })
@RestController
@RequestMapping("/comment")
public class CommentController {

	@Autowired
	CommentService commentservice;
	
	@Autowired
	AuthService authService;
	
	@Autowired
	UserInfoService userinfoService;
	
	@ApiOperation(value = "게시판에 해당하는 댓글들를 반환한다.", response = List.class)
	@GetMapping("{posts_id}")
	public ResponseEntity<List<CommentResponse>> selectComment(@PathVariable int posts_id) throws Exception {
		List<Comment> comment = commentservice.selectComment(posts_id);
		List<CommentResponse> response = new ArrayList<>();
		for (int i = 0; i < comment.size(); i++) {
			CommentResponse temp = new CommentResponse();
			temp.comment=comment.get(i);
			int user_id = temp.comment.getUser_id();
			temp.userinfo = userinfoService.selectUserInfoByUserid(user_id);
			temp.userinfo.setPassword(null);
			response.add(temp);
		}
		return new ResponseEntity<List<CommentResponse>>(response, HttpStatus.OK);
	}

	@ApiOperation(value = "댓글 생성", response = String.class)
	@PostMapping
	public ResponseEntity<String> Createcomment(@RequestBody Comment comment, HttpServletRequest request) {
		String accessToken = (String) request.getAttribute("accessToken");
		int user_id = 1;
		if (accessToken != null) {
			Auth auth = authService.findAuthByAccessToken(accessToken);
			user_id = auth.getUser_id();
		}
		comment.setUser_id(user_id);
		if (commentservice.createComment(comment) == 1) {
			return new ResponseEntity<String>("success", HttpStatus.OK);
		}
		return new ResponseEntity<String>("fail", HttpStatus.NO_CONTENT);
	}

	@ApiOperation(value = "댓글 수정", response = String.class)
	@PutMapping("{comment_id}")
	public ResponseEntity<String> updatecomment(@RequestBody Comment comment) {
		if (commentservice.modifyComment(comment) == 1) {
			return new ResponseEntity<String>("success", HttpStatus.OK);
		}
		return new ResponseEntity<String>("fail", HttpStatus.NO_CONTENT);
	}

	@ApiOperation(value = "댓글 삭제", response = String.class)
	@DeleteMapping("{comment_id}")
	public ResponseEntity<String> deleteUserInfo(@PathVariable int comment_id) {
		if (commentservice.deleteComment(comment_id) == 1) {
			return new ResponseEntity<String>("success", HttpStatus.OK);
		}
		return new ResponseEntity<String>("fail", HttpStatus.NO_CONTENT);
	}

	@ApiOperation(value = "숨김 설정", response = String.class)
	@PutMapping("hidden/{comment_id}")
	public ResponseEntity<String> updatehidecomment(@RequestBody Comment comment) {
		if (commentservice.modifyhideComment(comment) == 1) {
			return new ResponseEntity<String>("success", HttpStatus.OK);
		}
		return new ResponseEntity<String>("fail", HttpStatus.NO_CONTENT);
	}
	
	@ApiOperation(value = "게시판에 내 댓글이 있는지 유무 파악.", response = Integer.class)
	@GetMapping("/check/{posts_id}")
		public ResponseEntity<Integer> Checkcomment(@PathVariable int posts_id, HttpServletRequest request) {
		String accessToken = (String) request.getAttribute("accessToken");
		int user_id = 1;
		if (accessToken != null) {
			Auth auth = authService.findAuthByAccessToken(accessToken);
			user_id = auth.getUser_id();
		}
		return new ResponseEntity<Integer>(commentservice.checkComment(user_id, posts_id), HttpStatus.OK);
	}
}
