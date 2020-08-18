package com.web.curation.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.web.curation.model.Auth;
import com.web.curation.model.Comment_health;
import com.web.curation.model.UserInfo;
import com.web.curation.service.AuthService;
import com.web.curation.service.CommentHealthService;
import com.web.curation.service.UserInfoService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = { "*" })
@RestController
@RequestMapping("/comment-health")
public class CommentHealthController {

	@Autowired
	CommentHealthService commenthealthservice;
	
	@Autowired
	AuthService authService;
	
	@Autowired
	UserInfoService userinfoService;

	@ApiOperation(value = "댓글에 해당하는 좋아한다 수를 반환한다.", response = Integer.class)
	@GetMapping("comment/{comment_id}")
	public ResponseEntity<Integer> selectcommenthealth(@PathVariable int comment_id) {
		return new ResponseEntity<Integer>(commenthealthservice.selectCommentHealth(comment_id), HttpStatus.OK);
	}
	
	@ApiOperation(value = "사람에 해당하는 모든 좋아한다를 반환한다.", response = List.class)
	@GetMapping("{user_id}")
	public ResponseEntity<List<Comment_health>> selectcommenthealthUser(@PathVariable int user_id) {
		return new ResponseEntity<List<Comment_health>>(commenthealthservice.selectCommentHealthUser(user_id), HttpStatus.OK);
	}
	
	@ApiOperation(value = "좋아해요 추가", response = String.class)
	@PostMapping("/{comment_id}")
	public ResponseEntity<String> createcommentHealth(@PathVariable int comment_id, HttpServletRequest request) {
		
		// post 자신 아이디로 생성하게 수정
		String accessToken = (String) request.getAttribute("accessToken");

		int user_id = 1;
		if (accessToken != null) {
			Auth auth = authService.findAuthByAccessToken(accessToken);
			user_id = auth.getUser_id();
		}

		UserInfo userInfo = userinfoService.selectUserInfoByUserid(user_id);
		//String nickname = userInfo.getNickname();
				
		if (commenthealthservice.possibleCommentHealth(comment_id, user_id) == 1) {
			commenthealthservice.deleteCommentHealth(comment_id, user_id);
			return new ResponseEntity<String>("success", HttpStatus.OK);
		} else if (commenthealthservice.possibleCommentHealth(comment_id, user_id) == 0) {
			commenthealthservice.createCommentHealth(comment_id, user_id);
			return new ResponseEntity<String>("success", HttpStatus.OK);
		}
		return new ResponseEntity<String>("fail", HttpStatus.NO_CONTENT);
	}

	@ApiOperation(value = "유저에 해당하는 댓글 좋아한다 수를 반환한다.", response = Integer.class)
	@GetMapping("user/{user_id}")
	public ResponseEntity<Integer> selectusercommenthealth(@PathVariable int user_id) throws Exception {
		return new ResponseEntity<Integer>(commenthealthservice.selectUserHealth(user_id), HttpStatus.OK);
	}

}
