package com.web.curation.controller;

import java.io.Console;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JacksonInject.Value;
import com.google.gson.JsonObject;
import com.web.curation.model.Auth;
import com.web.curation.model.BasicResponse;
import com.web.curation.model.Comment;
import com.web.curation.model.Doctor;
import com.web.curation.model.Post;
import com.web.curation.model.TokenSet;
import com.web.curation.model.UserInfo;
import com.web.curation.model.UserResponse;
import com.web.curation.service.AuthService;
import com.web.curation.service.DoctorService;
import com.web.curation.service.FollowService;
import com.web.curation.service.HealthService;
import com.web.curation.service.JwtService;
import com.web.curation.service.PostService;
import com.web.curation.service.UserInfoService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = { "*" })
@RestController
@RequestMapping("/doctor")

public class DoctorController {

	private static final Logger logger = LoggerFactory.getLogger(DoctorController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";

	@Autowired
	private UserInfoService userInfoService;

	@Autowired
	private AuthService authService;

	@Autowired
	private DoctorService doctorService;

	@ApiOperation(value = "모든 의사를 반환한다.", response = List.class)
	@GetMapping
	public ResponseEntity<List<Doctor>> selectDoctor() throws Exception {
		return new ResponseEntity<List<Doctor>>(doctorService.selectDoctor(), HttpStatus.OK);
	}

	@ApiOperation(value = "체크하지 않은 사람들을 반환한다.", response = List.class)
	@GetMapping("check")
	public ResponseEntity<List<Doctor>> selectCheckDoctor() throws Exception {
		return new ResponseEntity<List<Doctor>>(doctorService.selectCheckDoctor(), HttpStatus.OK);
	}

	@ApiOperation(value = "의사 정보를 신청한다.", response = String.class)
	@PostMapping("register")
	public ResponseEntity<String> insertDoctor(@RequestParam("upload_file") MultipartFile uploadfile,
			HttpServletRequest request) {
		String accessToken = (String) request.getAttribute("accessToken");
		Doctor doctor = new Doctor();
		// System.out.println(name);
		int user_id = 1;
		if (accessToken != null) {
			Auth auth = authService.findAuthByAccessToken(accessToken);
			user_id = auth.getUser_id();
		}
		doctor.setUser_id(user_id);
		System.out.println(doctor.getUser_id());
		doctor.setDoctorauth("unread");
		try {
			String access_path = "http://i3a301.p.ssafy.io/images/doctor/";

			String root_path = "C:\\Users\\multicampus\\Desktop\\s03p13a301\\backend\\src\\main\\resources\\doctor\\";
			String real_path = "/home/ubuntu/s03p13a301/backend/src/main/resources/doctor/";
			// String attach_path = "resources\\upload\\";

			String filename = user_id + ".jpg";
			File checkFile = new File(root_path + filename);
			if (checkFile.exists()) {
				System.out.println("Delete");
				checkFile.delete();
			}
			System.out.println(real_path + filename);

			FileOutputStream fos = new FileOutputStream(root_path + filename);
			// 파일 저장할 경로 + 파일명을 파라미터로 넣고 fileOutputStream 객체 생성하고
			InputStream is = uploadfile.getInputStream();
			// file로 부터 inputStream을 가져온다.

			int readCount = 0;
			byte[] buffer = new byte[2048];

			while ((readCount = is.read(buffer)) != -1) {
				fos.write(buffer, 0, readCount);
			}
			String path = access_path + filename;
			doctor.setDoctorimg(path);
			doctorService.insertDoctor(doctor);
			return new ResponseEntity<String>("success", HttpStatus.OK);

		} catch (Exception ex) {
			throw new RuntimeException("file Save Error");

		}
	}
	
	@ApiOperation(value = "의사 결정", response = String.class)
	@PutMapping("{user_id}")
	public ResponseEntity<String> updatedoctor(@PathVariable int user_id) {
		if (doctorService.updateDoctorAuth(user_id) == 1) {
			return new ResponseEntity<String>("success", HttpStatus.OK);
		}
		return new ResponseEntity<String>("fail", HttpStatus.NO_CONTENT);
	}
	
	
	
	
	
	
	
	
	
	
	
}