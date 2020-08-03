package com.web.curation.controller;

import java.util.List;

import javax.validation.Valid;

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

import com.web.curation.model.BasicResponse;
import com.web.curation.model.Follow;
import com.web.curation.service.FollowService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiResponse;

@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized", response = BasicResponse.class),
		@ApiResponse(code = 403, message = "Forbidden", response = BasicResponse.class),
		@ApiResponse(code = 404, message = "Not Found", response = BasicResponse.class),
		@ApiResponse(code = 500, message = "Failure", response = BasicResponse.class) })

@CrossOrigin(origins = { "*" })
@RestController
@RequestMapping("/follow")
//http://localhost:9999/THISIS/swagger-ui.html
public class FollowController {

	@Autowired
	private FollowService followService;

	// ===================== 팔로우 리스트 반환 ================================

	@ApiOperation(value = "해당 아이디의 팔로잉 정보 반환.", response = BasicResponse.class)
	@GetMapping("/followee/{user_id}")
	public ResponseEntity<BasicResponse> searchFollowee(@PathVariable("user_id") int user_id) throws Exception {

		ResponseEntity response = null;
		System.out.println(user_id);
		List<Follow> followList = followService.searchFollowee(user_id);
//		for(Follow f : followList) {
//			System.out.println(f.toString());
//		}
		if (followList.isEmpty()) {
			response = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		} else {
			final BasicResponse result = new BasicResponse();
			result.status = true;
			result.data = "success";
			result.object = followList;
			response = new ResponseEntity<>(result, HttpStatus.OK);
		}
		return response;
	}

	@ApiOperation(value = "해당 아이디의 팔로워 정보 반환.", response = BasicResponse.class)
	@GetMapping("/follower/{user_id}")
	public ResponseEntity<BasicResponse> searchFollower(@PathVariable("user_id") int user_id) throws Exception {

		ResponseEntity response = null;

		List<Follow> followList = followService.searchFollower(user_id);
		if (followList.isEmpty()) {
			response = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		} else {
			final BasicResponse result = new BasicResponse();
			result.status = true;
			result.data = "success";
			result.object = followList;
			response = new ResponseEntity<>(result, HttpStatus.OK);
		}
		return response;
	}

	// ===================== 팔로우 집계 반환 ================================
	@ApiOperation(value = "해당 아이디의 팔로잉 집계 정보 반환.", response = BasicResponse.class)
	@GetMapping("/followee/sum/{user_id}")
	public ResponseEntity<BasicResponse> sumOfFollowee(@PathVariable("user_id") int user_id) throws Exception {

		ResponseEntity response = null;

		int sum = followService.sumOfFollowee(user_id);
		if (sum == 0) {
			response = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		} else {
			final BasicResponse result = new BasicResponse();
			result.status = true;
			result.data = "success";
			result.object = sum;
			response = new ResponseEntity<>(result, HttpStatus.OK);
		}
		return response;
	}

	@ApiOperation(value = "해당 아이디의 팔로워 집계 정보 반환.", response = BasicResponse.class)
	@GetMapping("/follower/sum/{user_id}")
	public ResponseEntity<BasicResponse> sumOfFollower(@PathVariable("user_id") int user_id) throws Exception {

		ResponseEntity response = null;

		int sum = followService.sumOfFollower(user_id);
		if (sum == 0) {
			response = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		} else {
			final BasicResponse result = new BasicResponse();
			result.status = true;
			result.data = "success";
			result.object = sum;
			response = new ResponseEntity<>(result, HttpStatus.OK);
		}
		return response;
	}

	// ===================== 팔로우 생성 ================================
	@ApiOperation(value = "팔로우 생성", response = BasicResponse.class)
	@PostMapping
	public ResponseEntity<BasicResponse> createFollow(@RequestBody Follow follow) throws Exception {

		try {
			int queryResult = followService.createFollow(follow);
			System.out.println(queryResult);
			final BasicResponse result = new BasicResponse();
			result.status = true;
			result.data = "success";
			return new ResponseEntity<>(result, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	// ===================== 팔로우 끊기 ================================
	@ApiOperation(value = "팔로우 취소", response = BasicResponse.class)
	@DeleteMapping
	public ResponseEntity<BasicResponse> deleteFollow(@Valid @RequestBody Follow follow) throws Exception {

		ResponseEntity response = null;
		int successRow = followService.deleteFollow(follow);
		final BasicResponse result = new BasicResponse();
		
		if(successRow > 0) {
			result.status = true;
			result.data = "success";
			return new ResponseEntity<>(result, HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	

	}
}
