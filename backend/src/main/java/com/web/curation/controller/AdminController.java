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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.web.curation.model.Auth;
import com.web.curation.model.BasicResponse;
import com.web.curation.model.CheckUser;
import com.web.curation.model.Doctor;
import com.web.curation.model.DoctorResponse;
import com.web.curation.model.Police;
import com.web.curation.model.PoliceJoin;
import com.web.curation.model.Postpolice;
import com.web.curation.model.PostpoliceResponse;
import com.web.curation.model.RequestDoctor;
import com.web.curation.model.UserInfo;
import com.web.curation.model.UserPolice;
import com.web.curation.service.AuthService;
import com.web.curation.service.CommentService;
import com.web.curation.service.DiseaseService;
import com.web.curation.service.DoctorService;
import com.web.curation.service.PoliceService;
import com.web.curation.service.PostService;
import com.web.curation.service.UserInfoService;
import com.web.curation.model.DesidePost;
import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = { "*" })
@RestController
@RequestMapping("/admin")
public class AdminController {
	@Autowired
	private UserInfoService userInfoService;

	@Autowired
	private DiseaseService diseaseService;

	@Autowired
	private AuthService authService;

	@Autowired
	private DoctorService doctorService;

	@Autowired
	private PoliceService policeservice;

	@Autowired
	private PostService postservice;

	@Autowired
	private CommentService commentsService;

	// post
	@ApiOperation(value = "신고 많이 받은 순으로 게시글 반환", response = List.class)
	@GetMapping("post")
	public ResponseEntity<List<PostpoliceResponse>> selectAllPostpolice(@RequestParam int num,
			HttpServletRequest request) throws Exception {
		if (check(request) == 1) {
			List<Postpolice> Allpage = postservice.selectAllPostpolice();
			List<Postpolice> page = null;
			List<PostpoliceResponse> response = new ArrayList<>();
			if (Allpage.size() / 10 > num && num * 10 + 10 <= Allpage.size()) {
				page = Allpage.subList(num * 10, num * 10 + 10);
				// 페이지 돌면서 response에 다시 저장
				for (int i = 0; i < page.size(); i++) {
					PostpoliceResponse temp = new PostpoliceResponse();
					temp.posts_id = page.get(i).getPosts_id();
					temp.postpolice = page.get(i);
					try {
						temp.diseasename = diseaseService.selectDiseaseByDiseasecode(temp.postpolice.getDiseasecode())
								.getDiseasename();
					} catch (NullPointerException e) {
						temp.diseasename = "";
					}
					int user_id = temp.postpolice.getUser_id();
					temp.userinfo = userInfoService.selectUserInfoByUserid(user_id);
					temp.userinfo.setPassword(null);
					temp.comments = commentsService.selectComment(temp.posts_id);
					response.add(temp);
				}
				return new ResponseEntity<List<PostpoliceResponse>>(response, HttpStatus.OK);
			} else if (Allpage.size() / 10 < num) {
				return new ResponseEntity<List<PostpoliceResponse>>(response, HttpStatus.NO_CONTENT);
			} else {
				page = Allpage.subList(num * 10, Allpage.size());
				for (int i = 0; i < page.size(); i++) {
					PostpoliceResponse temp = new PostpoliceResponse();
					temp.posts_id = page.get(i).getPosts_id();
					temp.postpolice = page.get(i);
					try {
						temp.diseasename = diseaseService.selectDiseaseByDiseasecode(temp.postpolice.getDiseasecode())
								.getDiseasename();
					} catch (NullPointerException e) {
						temp.diseasename = "";
					}
					int user_id = temp.postpolice.getUser_id();
					temp.userinfo = userInfoService.selectUserInfoByUserid(user_id);
					temp.userinfo.setPassword(null);
					temp.comments = commentsService.selectComment(temp.posts_id);
					response.add(temp);
				}
				return new ResponseEntity<List<PostpoliceResponse>>(response, HttpStatus.OK);
			}
		} else {
			return new ResponseEntity<List<PostpoliceResponse>>(HttpStatus.UNAUTHORIZED);
		}
	}

	@ApiOperation(value = "게시글 설정", response = String.class)
	@PostMapping("post")
	public ResponseEntity<String> DesidePost(@RequestBody DesidePost desidepost, HttpServletRequest request) {
		if (check(request) == 1) {
			int posts_id = desidepost.getPosts_id();
			String check = desidepost.getCheck();
			if (check.equals("hide")) {
				if (postservice.hiddenupdate(posts_id) == 1) {
					return new ResponseEntity<String>("success", HttpStatus.OK);
				}
				return new ResponseEntity<String>("fail", HttpStatus.NO_CONTENT);
			} else if (check.equals("delete")) {
				if (postservice.deletePost(posts_id) == 1) {
					return new ResponseEntity<String>("success", HttpStatus.OK);
				}
				return new ResponseEntity<String>("fail", HttpStatus.NO_CONTENT);
			} else if (check.equals("show")) {
				if (postservice.showupdate(posts_id) == 1) {
					return new ResponseEntity<String>("success", HttpStatus.OK);
				}
				return new ResponseEntity<String>("fail", HttpStatus.NO_CONTENT);
			} else {
				return new ResponseEntity<String>("Wrong Check", HttpStatus.NO_CONTENT);
			}
		}
		return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
	}

	// user
	@ApiOperation(value = "신고 많이 받은 순으로 유저 반환  ", response = List.class)
	@GetMapping("user")
	public ResponseEntity<List<UserPolice>> selectAllUserpolice(@RequestParam int num, HttpServletRequest request)
			throws Exception {
		if (check(request) == 1) {
			List<UserPolice> Allpage = userInfoService.selectUserInfoPolice();
			List<UserPolice> page = null;
			if (Allpage.size() / 10 > num && num * 10 + 10 <= Allpage.size()) {
				page = Allpage.subList(num * 10, num * 10 + 10);
				return new ResponseEntity<List<UserPolice>>(page, HttpStatus.OK);
			} else if (Allpage.size() / 10 < num) {
				return new ResponseEntity<List<UserPolice>>(page, HttpStatus.NO_CONTENT);
			} else {
				page = Allpage.subList(num * 10, Allpage.size());
				return new ResponseEntity<List<UserPolice>>(page, HttpStatus.OK);
			}
		} else {
			return new ResponseEntity<List<UserPolice>>(HttpStatus.UNAUTHORIZED);
		}
	}

	@ApiOperation(value = "계정 사용판단", response = BasicResponse.class)
	@PostMapping("user")
	public ResponseEntity<String> DesiceAccount(@RequestBody CheckUser checkuser, HttpServletRequest request)
			throws Exception {
		if (check(request) == 1) {
			int user_id = checkuser.getUser_id();
			String check = checkuser.getCheck();
			if (check.equals("disable")) {
				if (userInfoService.updateDisable(user_id) == 1) {
					postservice.updatehiddenuser(user_id);
					return new ResponseEntity<String>("success", HttpStatus.OK);
				} else {
					return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
				}
			} else if (check.equals("delete")) {
				if (userInfoService.deleteUserInfo(user_id) == 1) {
					return new ResponseEntity<String>("success", HttpStatus.OK);
				} else {
					return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
				}
			} else if (check.equals("able")) {
				if (userInfoService.updateable(user_id) == 1) {
					postservice.updateshownuser(user_id);
					return new ResponseEntity<String>("success", HttpStatus.OK);
				} else {
					return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
				}
			} else {
				return new ResponseEntity<String>("Wrong Check", HttpStatus.NO_CONTENT);
			}
		} else {
			return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
		}
	}

	// police
	@ApiOperation(value = "게시글에 해당하는 신고한다 수를 반환한다.", response = Integer.class)
	@GetMapping("police/post/{posts_id}")
	public ResponseEntity<Integer> selectpostpolice(@PathVariable int posts_id, HttpServletRequest request) {
		if (check(request) == 1) {
			return new ResponseEntity<Integer>(policeservice.selectPolice(posts_id), HttpStatus.OK);
		} else {
			return new ResponseEntity<Integer>(HttpStatus.UNAUTHORIZED);
		}
	}

	@ApiOperation(value = "유저가 신고받은 수를 반환한다.", response = Integer.class)
	@GetMapping("police/user/{user_id}")
	public ResponseEntity<Integer> selectuserpolice(@PathVariable int user_id, HttpServletRequest request)
			throws Exception {
		if (check(request) == 1) {
			return new ResponseEntity<Integer>(policeservice.selectUserPolice(user_id), HttpStatus.OK);
		}
		return new ResponseEntity<Integer>(HttpStatus.UNAUTHORIZED);
	}

	@ApiOperation(value = "게시글에 해당하는 모든 신고한다를 반환한다.", response = List.class)
	@GetMapping("police/{posts_id}")
	public ResponseEntity<List<Police>> selectPolice(@PathVariable int posts_id, HttpServletRequest request)
			throws Exception {
		if (check(request) == 1) {
			return new ResponseEntity<List<Police>>(policeservice.selectpostPolice(posts_id), HttpStatus.OK);
		}
		return new ResponseEntity<List<Police>>(HttpStatus.UNAUTHORIZED);
	}

	@ApiOperation(value = "유저에 해당하는 모든 신고 자료를 반환한다.", response = List.class)
	@GetMapping("police/users/{user_id}")
	public ResponseEntity<List<PoliceJoin>> selectuserPolice(@PathVariable int user_id, HttpServletRequest request)
			throws Exception {
		if (check(request) == 1) {
			return new ResponseEntity<List<PoliceJoin>>(policeservice.selectMyPolice(user_id), HttpStatus.OK);
		}
		return new ResponseEntity<List<PoliceJoin>>(HttpStatus.UNAUTHORIZED);
	}

	// doctor-auth
	@ApiOperation(value = "모든 의사를 반환한다.", response = List.class)
	@GetMapping("doctor-auth")
	public ResponseEntity<List<DoctorResponse>> selectDoctor(HttpServletRequest request) throws Exception {
		if (check(request) == 1) {
			List<DoctorResponse> doctorResponse = new ArrayList<>();
			List<Doctor> doctor = doctorService.selectDoctor();
			for (int i = 0; i < doctor.size(); i++) {
				DoctorResponse response = new DoctorResponse();
				response.doctor = doctor.get(i);
				response.userinfo = userInfoService.selectUserInfoByUserid(doctor.get(i).getUser_id());
				doctorResponse.add(response);
				System.out.println(response.toString());
			}
			return new ResponseEntity<List<DoctorResponse>>(doctorResponse, HttpStatus.OK);
		} else {
			return new ResponseEntity<List<DoctorResponse>>(HttpStatus.UNAUTHORIZED);
		}
	}

	@ApiOperation(value = "체크하지 않은 사람들을 반환한다.", response = List.class)
	@GetMapping("doctor-auth/check")
	public ResponseEntity<List<DoctorResponse>> selectCheckDoctor(HttpServletRequest request) throws Exception {
		if (check(request) == 1) {
			List<DoctorResponse> doctorResponse = new ArrayList<>();
			List<Doctor> doctor = doctorService.selectCheckDoctor();
			for (int i = 0; i < doctor.size(); i++) {
				DoctorResponse response = new DoctorResponse();
				response.doctor = doctor.get(i);
				response.userinfo = userInfoService.selectUserInfoByUserid(doctor.get(i).getUser_id());
				doctorResponse.add(response);
				System.out.println(response.toString());
			}
			return new ResponseEntity<List<DoctorResponse>>(doctorResponse, HttpStatus.OK);
		} else {
			return new ResponseEntity<List<DoctorResponse>>(HttpStatus.UNAUTHORIZED);
		}
	}

	@ApiOperation(value = "의사 결정", response = String.class)
	@PutMapping("doctor-auth")
	public ResponseEntity<String> updatedoctor(@RequestBody RequestDoctor requestdoctor, HttpServletRequest request) {
		if (check(request) == 1) {
			String check = requestdoctor.getCheck();
			int doctor_id = requestdoctor.getDoctor_id();
			System.out.println(doctor_id);
			Doctor doctor = doctorService.selectMyDoctorID(doctor_id);
			int user_id = doctor.getUser_id();
			if (check.equals("accept")) {
				if (doctorService.updateDoctorAuth(doctor_id) == 1) {
					userInfoService.updateRole(user_id);
					return new ResponseEntity<String>("success", HttpStatus.OK);
				} else {
					return new ResponseEntity<String>("fail", HttpStatus.NO_CONTENT);
				}
			} else if (check.equals("reject")) {
				if (doctorService.updateDoctorAuthFail(doctor_id) == 1) {
					userInfoService.deleteRole(user_id);
					return new ResponseEntity<String>("success", HttpStatus.OK);
				}
				return new ResponseEntity<String>("fail", HttpStatus.NO_CONTENT);
			} else
				return new ResponseEntity<String>("fail", HttpStatus.NO_CONTENT);
		} else
			return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
	}

	private int check(HttpServletRequest request) {
		String accessToken = (String) request.getAttribute("accessToken");
		int user_id = 1;
		if (accessToken != null) {
			Auth auth = authService.findAuthByAccessToken(accessToken);
			user_id = auth.getUser_id();
		}
		UserInfo userinfo = userInfoService.selectUserInfoByUserid(user_id);
		System.out.println(userinfo.getRole());
		if (userinfo.getRole().equals("admin"))
			return 1;
		else
			return 0;
	}
}
