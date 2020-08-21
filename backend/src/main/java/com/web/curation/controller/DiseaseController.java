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
import com.web.curation.service.AuthService;
import com.web.curation.service.DiseaseService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = { "*" })
@RestController
@RequestMapping("/disease")
public class DiseaseController {
	
	private static final Logger logger = LoggerFactory.getLogger(UserInfoController.class); 
	private static final String SUCCESS = "success"; 
	private static final String FAIL = "fail"; 
	
	@Autowired
	private DiseaseService diseaseService;
	
	@Autowired
	private AuthService authService;
	
	@ApiOperation(value = "모든 질병 정보를 반환한다.", response = List.class)
	@GetMapping
		public ResponseEntity<List<Disease>> selectDisease() throws Exception {
			return new ResponseEntity<List<Disease>>(diseaseService.selectDisease(), HttpStatus.OK);
	}
	
	@ApiOperation(value = "질병코드에 해당하는 질병 정보를 반환한다.", response = Disease.class)     
 	@GetMapping("diseasecode") 
		public ResponseEntity<Disease> selectDiseaseByDiseasecode(@RequestParam String diseasecode) { 
			return new ResponseEntity<Disease>(diseaseService.selectDiseaseByDiseasecode(diseasecode), HttpStatus.OK); 
	}
	
	@ApiOperation(value = "질병 정보를 등록한다.", response = String.class)     
 	@PostMapping
		public ResponseEntity<String> createDisease(@RequestBody Disease disease) { 
		if(diseaseService.createDisease(disease) == 1) {
			return new ResponseEntity<String>("success",HttpStatus.OK);
		}
		return new ResponseEntity<String>("fail",HttpStatus.NO_CONTENT);
	}
	
	@ApiOperation(value = "질병 정보를 삭제한다.", response = String.class)     
 	@DeleteMapping
		public ResponseEntity<String> deleteDisease(@RequestParam String diseasecode) { 
			if(diseaseService.deleteDisease(diseasecode) == 1) {
				return new ResponseEntity<String>("success",HttpStatus.OK);
			}
			return new ResponseEntity<String>("fail",HttpStatus.NO_CONTENT);
	}
	
	
	@ApiOperation(value = "유저가 구독하는 질병 정보를 반환한다.", response = Disease.class)     
 	@GetMapping("user") 
		public ResponseEntity<List<Disease>> selectDiseaseByUserid(HttpServletRequest request) { 
			// subscribe 자신 아이디로 생성하게 수정
			String accessToken = (String) request.getAttribute("accessToken");
			//System.out.println(name);
			int user_id = 1;
			if (accessToken != null) {
				Auth auth = authService.findAuthByAccessToken(accessToken);
				user_id = auth.getUser_id();
			}
			System.out.println("user_id : " +user_id);
			return new ResponseEntity<List<Disease>>(diseaseService.selectDiseaseByUserid(user_id), HttpStatus.OK); 
	}

	
}
