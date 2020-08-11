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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.web.curation.model.Auth;
import com.web.curation.model.Police;
import com.web.curation.model.PoliceJoin;
import com.web.curation.model.Post;
import com.web.curation.service.AuthService;
import com.web.curation.service.PoliceService;
import com.web.curation.service.UserInfoService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = { "*" })
@RestController
@RequestMapping("/police")
public class PoliceController {

	@Autowired
	PoliceService policeservice;

	@Autowired
	AuthService authService;

	@Autowired
	UserInfoService userinfoService;

	@ApiOperation(value = "게시글에 해당하는 신고한다 수를 반환한다.", response = Integer.class)
	@GetMapping("post/{posts_id}")
	public ResponseEntity<Integer> selectpostpolice(@PathVariable int posts_id) {
		return new ResponseEntity<Integer>(policeservice.selectPolice(posts_id), HttpStatus.OK);
	}
	
	@ApiOperation(value = "유저에 해당하는 신고한다 수를 반환한다.", response = Integer.class)
	@GetMapping("user/{user_id}")
	public ResponseEntity<Integer> selectuserpolice(@PathVariable int user_id) throws Exception {
		return new ResponseEntity<Integer>(policeservice.selectUserPolice(user_id), HttpStatus.OK);
	}
	
	@ApiOperation(value = "게시글에 해당하는 모든 신고한다를 반환한다.", response = List.class)
	@GetMapping("{posts_id}")
	public ResponseEntity<List<Police>> selectPolice(@PathVariable int posts_id) throws Exception {
		return new ResponseEntity<List<Police>>(policeservice.selectpostPolice(posts_id), HttpStatus.OK);
	}
	
	@ApiOperation(value = "신고한다 생성", response = String.class)
	@PostMapping
	public ResponseEntity<String> CreatePolice(@RequestBody Police police, HttpServletRequest request) {
		// post 자신 아이디로 생성하게 수정
		String accessToken = (String) request.getAttribute("accessToken");
		// System.out.println(name);
		int user_id = 1;
		if (accessToken != null) {
			Auth auth = authService.findAuthByAccessToken(accessToken);
			user_id = auth.getUser_id();
		}
		police.setUser_id(user_id);
		if (policeservice.createPolice(police) == 1) {
			return new ResponseEntity<String>("success", HttpStatus.OK);
		}
		return new ResponseEntity<String>("fail", HttpStatus.NO_CONTENT);
	}
	
	@ApiOperation(value = "유저에 해당하는 모든 신고 자료를 반환한다.", response = List.class)
	@GetMapping("/my/{user_id}")
	public ResponseEntity<List<PoliceJoin>> selectuserPolice(@PathVariable int user_id) throws Exception {
		return new ResponseEntity<List<PoliceJoin>>(policeservice.selectMyPolice(user_id), HttpStatus.OK);
	}
	
}
