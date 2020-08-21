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
import com.web.curation.model.Health;
import com.web.curation.model.UserInfo;
import com.web.curation.service.AuthService;
import com.web.curation.service.HealthService;
import com.web.curation.service.UserInfoService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = { "*" })
@RestController
@RequestMapping("/health")
public class HealthController {

	@Autowired
	HealthService healthservice;
	
	@Autowired
	AuthService authService;
	
	@Autowired
	UserInfoService userinfoService;

	@ApiOperation(value = "게시글에 해당하는 좋아한다 수를 반환한다.", response = Integer.class)
	@GetMapping("post/{posts_id}")
	public ResponseEntity<Integer> selectposthealth(@PathVariable int posts_id) {
		return new ResponseEntity<Integer>(healthservice.selectHealth(posts_id), HttpStatus.OK);
	}
	
	@ApiOperation(value = "사람에 해당하는 모든 좋아한다를 반환한다.", response = List.class)
	@GetMapping("{user_id}")
	public ResponseEntity<List<Health>> selectposthealthUser(@PathVariable int user_id) {
		return new ResponseEntity<List<Health>>(healthservice.selectHealthUser(user_id), HttpStatus.OK);
	}
	
	@ApiOperation(value = "좋아해요 추가", response = String.class)
	@PostMapping("/{posts_id}")
	public ResponseEntity<String> createHealth(@PathVariable int posts_id, HttpServletRequest request) {
		
		// post 자신 아이디로 생성하게 수정
		String accessToken = (String) request.getAttribute("accessToken");

		int user_id = 1;
		if (accessToken != null) {
			Auth auth = authService.findAuthByAccessToken(accessToken);
			user_id = auth.getUser_id();
		}

		UserInfo userInfo = userinfoService.selectUserInfoByUserid(user_id);
		String nickname = userInfo.getNickname();
				
		if (healthservice.possibleHealth(posts_id, user_id, nickname) == 1) {
			healthservice.deleteHealth(posts_id, user_id, nickname);
			return new ResponseEntity<String>("success", HttpStatus.OK);
		} else if (healthservice.possibleHealth(posts_id, user_id, nickname) == 0) {
			healthservice.createHealth(posts_id, user_id, nickname);
			return new ResponseEntity<String>("success", HttpStatus.OK);
		}
		return new ResponseEntity<String>("fail", HttpStatus.NO_CONTENT);
	}

	@ApiOperation(value = "유저에 해당하는 좋아한다 수를 반환한다.", response = Integer.class)
	@GetMapping("user/{user_id}")
	public ResponseEntity<Integer> selectuserhealth(@PathVariable int user_id) throws Exception {
		return new ResponseEntity<Integer>(healthservice.selectUserHealth(user_id), HttpStatus.OK);
	}
	
}
