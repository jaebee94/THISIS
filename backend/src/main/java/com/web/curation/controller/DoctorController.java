package com.web.curation.controller;


import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;


import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.web.curation.model.Auth;
import com.web.curation.model.Doctor;
import com.web.curation.service.AuthService;
import com.web.curation.service.DoctorService;
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
		Integer doctor_id = doctorService.selectdoctorid();
		if(doctor_id==null) {
			doctor_id=1;
		}
		else
			doctor_id+=1;
		doctor.setDoctorauth("unread");
		try {
			String access_path = "http://i3a301.p.ssafy.io/images/doctor/";

			String root_path = "C:\\Users\\multicampus\\Desktop\\s03p13a301\\backend\\src\\main\\resources\\doctor\\";
			String real_path = "/home/ubuntu/s03p13a301/backend/src/main/resources/doctor/";
			// String attach_path = "resources\\upload\\";

			String filename = doctor_id + ".jpg";
			File checkFile = new File(real_path + filename);
			if (checkFile.exists()) {
				System.out.println("Delete");
				checkFile.delete();
			}
			System.out.println(real_path + filename);

			FileOutputStream fos = new FileOutputStream(real_path + filename);
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
	
	@ApiOperation(value = "내 신청 결과를 반환한다.", response = String.class)
	@GetMapping("result")
	public ResponseEntity<String> resultDoctor(HttpServletRequest request) throws Exception {
		String accessToken = (String) request.getAttribute("accessToken");
		Doctor doctor = new Doctor();
		int user_id = 1;
		if (accessToken != null) {
			Auth auth = authService.findAuthByAccessToken(accessToken);
			user_id = auth.getUser_id();
		}
		doctor = doctorService.selectMyDoctor(user_id);
		System.out.println(doctor.getUser_id());
		return new ResponseEntity<String>(doctor.getDoctorauth(), HttpStatus.OK);
	}
}
