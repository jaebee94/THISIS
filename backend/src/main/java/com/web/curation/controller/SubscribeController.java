package com.web.curation.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.web.curation.model.Auth;
import com.web.curation.model.Disease;
import com.web.curation.model.Subscribe;
import com.web.curation.service.AuthService;
import com.web.curation.service.DiseaseService;
import com.web.curation.service.SubscribeService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = { "*" })
@RestController
@RequestMapping("/subscribe")
public class SubscribeController {

	private static final Logger logger = LoggerFactory.getLogger(UserInfoController.class); 
	private static final String SUCCESS = "success"; 
	private static final String FAIL = "fail"; 
	
	@Autowired
	private SubscribeService subscribeService;
	
	@Autowired
	private AuthService authService;
	
	@Autowired
	private DiseaseService diseaseService;
	
	@ApiOperation(value = "모든 구독 정보를 반환한다.", response = List.class)
	@GetMapping
		public ResponseEntity<List<Subscribe>> selectSubscribe() throws Exception {
			return new ResponseEntity<List<Subscribe>>(subscribeService.selectSubscribe(), HttpStatus.OK);
	}
	
	@ApiOperation(value = "유저 아이디에 해당하는 구독 정보를 반환한다.", response = Disease.class)     
 	@GetMapping("user") 
		public ResponseEntity<List<Subscribe>> selectSubscribeByUserid(@RequestParam String user_id) { 
			return new ResponseEntity<List<Subscribe>>(subscribeService.selectSubscribeByUserid(user_id), HttpStatus.OK); 
	}
	
	@ApiOperation(value = "유저의 질병 구독 여부를 저장한다", response = String.class)     
 	@PostMapping
		public ResponseEntity<String> createDisease(@RequestParam String diseasecode, @RequestParam String diseasename, HttpServletRequest request){ 
		
		// subscribe 자신 아이디로 생성하게 수정
		String accessToken = (String) request.getAttribute("accessToken");
		//System.out.println(name);
		int user_id = 1;
		if (accessToken != null) {
			Auth auth = authService.findAuthByAccessToken(accessToken);
			user_id = auth.getUser_id();
		}
		
		//질병 태그가 DB에 존재하는지 확인 후 없으면 디비에 넣기!!

		if(diseaseService.selectDiseaseByDiseasecode(diseasecode) == null) {
			Disease disease = new Disease(diseasecode, diseasename);
			diseaseService.createDisease(disease);
		}

		
		Subscribe sub = new Subscribe();
		sub.setDiseasecode(diseasecode);
		sub.setUser_id(user_id);
		
		if(subscribeService.createSubscribe(sub) == 1) {
			return new ResponseEntity<String>("success",HttpStatus.OK);
		}
		return new ResponseEntity<String>("fail",HttpStatus.NO_CONTENT);
	}
	
	@ApiOperation(value = "유저가 질병 구독을 취소한다", response = String.class)     
 	@DeleteMapping
		public ResponseEntity<String> deleteDisease(@RequestParam String diseasecode, HttpServletRequest request) { 
		
		// subscribe 자신 아이디로 생성하게 수정
		String accessToken = (String) request.getAttribute("accessToken");
		//System.out.println(name);
		int user_id = 1;
		if (accessToken != null) {
			Auth auth = authService.findAuthByAccessToken(accessToken);
			user_id = auth.getUser_id();
		}
		
		Subscribe sub = new Subscribe();
		sub.setDiseasecode(diseasecode);
		sub.setUser_id(user_id);
		
		
		if(subscribeService.deleteSubscribe(sub) == 1) {
			return new ResponseEntity<String>("success",HttpStatus.OK);
		}
		return new ResponseEntity<String>("fail",HttpStatus.NO_CONTENT);
	}
	
}
