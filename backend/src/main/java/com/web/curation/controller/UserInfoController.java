package com.web.curation.controller;

import java.io.File;
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

import com.google.gson.JsonObject;
import com.web.curation.model.Auth;
import com.web.curation.model.BasicResponse;
import com.web.curation.model.Post;
import com.web.curation.model.TokenSet;
import com.web.curation.model.UserInfo;
import com.web.curation.model.UserResponse;
import com.web.curation.service.AuthService;
import com.web.curation.service.FollowService;
import com.web.curation.service.HealthService;
import com.web.curation.service.JwtService;
import com.web.curation.service.PostService;
import com.web.curation.service.SubscribeService;
import com.web.curation.service.UserInfoService;
import com.web.curation.utils.SHA256Util;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = { "*" })
@RestController
@RequestMapping("/account")
//http://localhost:9999/THISIS/swagger-ui.html
public class UserInfoController {

	private static final Logger logger = LoggerFactory.getLogger(UserInfoController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";

	@Autowired
	private JwtService jwtService;

	@Autowired
	private UserInfoService userInfoService;

	@Autowired
	private HealthService healthservice;

	@Autowired
	private FollowService followService;

	@Autowired
	private PostService postservice;

	@Autowired
	private AuthService authService;

	@Autowired
	private SubscribeService subscribeService;
	
	@ApiOperation(value = "모든 회원 정보를 반환한다.", response = List.class)
	@GetMapping
	public ResponseEntity<List<UserInfo>> selectUser() throws Exception {
		return new ResponseEntity<List<UserInfo>>(userInfoService.selectUserInfo(), HttpStatus.OK);
	}

	@ApiOperation(value = "유저 ID에 해당하는 회원 정보를 반환한다.", response = UserInfo.class)
	@GetMapping("{user_id}")
	public ResponseEntity<UserInfo> selectUserInfoByUserid(@PathVariable int user_id) {
		return new ResponseEntity<UserInfo>(userInfoService.selectUserInfoByUserid(user_id), HttpStatus.OK);
	}

	@ApiOperation(value = "유저 회원가입 시 회원 정보를 등록한다.", response = String.class)
	@PostMapping("signup")
	public ResponseEntity<BasicResponse> insertUserInfo(@RequestBody UserInfo userinfo) {
		userinfo.setIntroduction("한줄 소개를 작성해 주세요");
		userinfo.setUserimage("http://i3a301.p.ssafy.io/images/profile/default.jpg");
		String salt = SHA256Util.generateSalt();
		userinfo.setSalt(salt);
		String password = userinfo.getPassword();
		password = SHA256Util.getEncrypt(password, salt);
		userinfo.setPassword(password);
		
		if (userInfoService.insertUserInfo(userinfo) == 1) {
			BasicResponse result = new BasicResponse();
			result.status = true;
			result.data = "success";
			result.object = String.valueOf(userInfoService.getUserId(userinfo.getEmail()));
			return new ResponseEntity<>(result, HttpStatus.OK);
		}
		BasicResponse result = new BasicResponse();
		result.status = false;
		result.data = "fail";
		result.object = null;
		return new ResponseEntity<>(result, HttpStatus.NO_CONTENT);
	}

	@ApiOperation(value = "유저 id에 해당하는 회원 정보를 수정한다.", response = String.class)
	@PutMapping
	public ResponseEntity<String> updateUserInfo(@RequestBody UserInfo userinfo, HttpServletRequest request) {

		String accessToken = (String) request.getAttribute("accessToken");
		UserInfo userinfo2;
		if (accessToken != null) {
			Auth auth = authService.findAuthByAccessToken(accessToken);
			userinfo2 = userInfoService.selectUserInfoByUserid(auth.getUser_id());
		} else { // accessToken없을때는 user_id 1인 유저로 들어감
			userinfo2 = userInfoService.selectUserInfoByUserid(1);
		}

		if (userinfo.getIntroduction() != null) {
			userinfo2.setIntroduction(userinfo.getIntroduction());
		}
		if (userinfo.getNickname() != null) {
			userinfo2.setNickname(userinfo.getNickname());
		}
		if (userinfo.getPassword() != null) {
			String password = userinfo.getPassword();	//입력받은 비밀번호
			String salt = userInfoService.selectSaltByUserId(userinfo2.getUser_id());	//내 user_id
			userinfo2.setPassword(SHA256Util.getEncrypt(password, salt));
		}

		if (userInfoService.updateUserInfo(userinfo2) == 1) {
			return new ResponseEntity<String>("success", HttpStatus.OK);
		}
		return new ResponseEntity<String>("fail", HttpStatus.NO_CONTENT);
	}

	@ApiOperation(value = "회원 탈퇴시 id에 해당하는 회원 정보를 삭제한다.", response = String.class)
	@DeleteMapping
	public ResponseEntity<String> deleteUserInfo(HttpServletRequest request) {
		String accessToken = (String) request.getAttribute("accessToken");

		UserInfo userinfo;
		if (accessToken != null) {
			Auth auth = authService.findAuthByAccessToken(accessToken);
			userinfo = userInfoService.selectUserInfoByUserid(auth.getUser_id());
		} else { // accessToken없을때는 user_id 1인 유저로 들어감
			userinfo = userInfoService.selectUserInfoByUserid(1);
		}
		
		if (userInfoService.deleteUserInfo(userinfo.getUser_id()) == 1) {
			return new ResponseEntity<String>("success", HttpStatus.OK);
		}
		return new ResponseEntity<String>("fail", HttpStatus.NO_CONTENT);
	}

	@ApiOperation(value = "회원 email과 password로 로그인 가능 여부 응답", response = String.class)
	@GetMapping("login")
	public ResponseEntity<String> login(@RequestParam String email, @RequestParam String password) {
		Optional<UserInfo> userOpt2 = Optional.ofNullable(userInfoService.findUserInfoByEmail(email));
        ResponseEntity response = null;

        if (userOpt2.isPresent()) {	//이메일 존재
        	 Optional<UserInfo> userOpt = Optional.ofNullable(userInfoService.findUserByEmailAndPassword(email, password));
        	
        	 if(userOpt.isPresent()) {	//비밀번호까지 다 맞음
        		 final UserResponse result = new UserResponse();
                 result.status = true;
                 result.data = "success";
                 
                 UserInfo userinfo = userOpt.get();
                 //로그인이 완료됬으니 토큰 만들기
                 TokenSet tokenSet = jwtService.createTokenSet(userinfo);
                
                 if(authService.selectAuthByUserid(userinfo.getUser_id()) != null) {
                	 authService.updateAuth(new Auth(userinfo.getUser_id(), tokenSet.getRefreshToken(), tokenSet.getAccessToken()));      	 
                 }
                 else
                	 authService.insertAuth(new Auth(userinfo.getUser_id(), tokenSet.getRefreshToken(), tokenSet.getAccessToken())); 
                 
                 if(tokenSet != null) {	//토큰까지 만듬
                	result.accessToken = tokenSet.getAccessToken();
                	result.user_id = userinfo.getUser_id();
                	result.username = userinfo.getUsername();
                	result.introduction = userinfo.getIntroduction();
                	result.email = userinfo.getEmail();
                	result.nickname = userinfo.getNickname();
                	//obj.addProperty("userinfo", obj.toString());
                	 //result.object = obj.toString();
                	 response = new ResponseEntity<>(result, HttpStatus.OK);
                 }else {
                	 response = new ResponseEntity<>(result, HttpStatus.UNAUTHORIZED);
                 }
        	 }else {	//비밀번호가 맞지 않음
        		 final UserResponse result = new UserResponse();
                 result.status = false;
                 result.data = "wrong password";
                 response = new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        	 }   
        } else {	//이메일 존재 x
        	 final UserResponse result = new UserResponse();
             result.status = false;
             result.data = "wrong email";
             response = new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
        return response;
	}
	
	@ApiOperation(value = "AccessToken 재생성 테스트", response = String.class)     
	@GetMapping("refreshAccessToken")
	public ResponseEntity<String> refreshAccessToken(HttpServletRequest request) {
		String accessToken = request.getHeader("accessToken");
		System.out.println("accessToken : " + accessToken);
		UserInfo userinfo;
		if(accessToken != null) {
		Auth auth = authService.findAuthByAccessToken(accessToken);
		userinfo = userInfoService.selectUserInfoByUserid(auth.getUser_id());
		}
		else {	//accessToken없을때는 user_id 1인 유저로 들어감
			userinfo = userInfoService.selectUserInfoByUserid(1);
		}
		
		System.out.println("user_id :" + userinfo.getUser_id());
		Auth auth = authService.selectAuthByUserid(userinfo.getUser_id());
		String refreshToken = auth.getRefresh_token();
		
		TokenSet tokenSet = jwtService.refreshAccessToken(refreshToken);
		//auth table update
		authService.updateAuth(new Auth(userinfo.getUser_id(), auth.getRefresh_token(), tokenSet.getAccessToken()));
		
		ResponseEntity response = null;
		final UserResponse result = new UserResponse();
		System.out.println("뉴 토큰 : " + tokenSet.toString());
		if(tokenSet != null) {
			 result.accessToken = tokenSet.getAccessToken();
        	 response = new ResponseEntity<>(result, HttpStatus.OK);
		}else {
			 response = new ResponseEntity<>(result, HttpStatus.UNAUTHORIZED);
		}

		return response;
	}

	@ApiOperation(value = "이메일 중복 테스트", response = String.class)
	@GetMapping("email")
	public ResponseEntity<UserInfo> checkEmail(@RequestParam String email) {
		ResponseEntity response = null;
		Optional<UserInfo> userOpt = Optional.ofNullable(userInfoService.findUserInfoByEmail(email));
		final BasicResponse result = new BasicResponse();
		if (userOpt.isPresent()) { // 이메일 존재
			result.status = false;
			result.data = "wrong email";
			response = new ResponseEntity<>(result, HttpStatus.FORBIDDEN);
		} else {
			result.status = true;
			result.data = "success";
			response = new ResponseEntity<>(result, HttpStatus.OK);
		}
		return response;
	}

	@ApiOperation(value = "닉네임 중복 테스트", response = String.class)
	@GetMapping("nickname")
	public ResponseEntity<UserInfo> checkNickname(@RequestParam String nickname) {
		ResponseEntity response = null;
		Optional<UserInfo> userOpt = Optional.ofNullable(userInfoService.findUserInfoByNickname(nickname));
		final BasicResponse result = new BasicResponse();
		if (userOpt.isPresent()) { // 닉네임 존재
			result.status = false;
			result.data = "wrong nickname";
			response = new ResponseEntity<>(result, HttpStatus.FORBIDDEN);
		} else {
			result.status = true;
			result.data = "success";
			response = new ResponseEntity<>(result, HttpStatus.OK);
		}
		return response;
	}

	@ApiOperation(value = "검색어에 해당하는 회원 정보를 반환한다.", response = List.class)
	@GetMapping("search")
	public ResponseEntity<List<UserInfo>> selectUserInfoBySearch(@RequestParam String keyword) {
		return new ResponseEntity<List<UserInfo>>(userInfoService.selectUserInfoSearch(keyword), HttpStatus.OK);
	}

	@ApiOperation(value = "유저 ID에 해당하는 프로필 정보를 반환한다(게시물,좋아요,팔로우,팔로워).", response = String.class)
	@GetMapping("profile/{user_id}")
	public ResponseEntity<String> selectProfileByUserid(@PathVariable int user_id, HttpServletRequest request) {
		JsonObject obj = new JsonObject();
		String accessToken = (String) request.getAttribute("accessToken");

		UserInfo userinfo;
		if (accessToken != null) {
			Auth auth = authService.findAuthByAccessToken(accessToken);
			userinfo = userInfoService.selectUserInfoByUserid(auth.getUser_id());
		} else { // accessToken없을때는 user_id 1인 유저로 들어감
			userinfo = userInfoService.selectUserInfoByUserid(1);
		}

		if (user_id == 0) { // 내 프로필
			logger.debug(userinfo.toString());
			user_id = userinfo.getUser_id();
			obj.addProperty("myinfo", true);
		} else { // 0이 아닌 내 프로필 요청 시
			if (userinfo.getUser_id() == user_id) {
				obj.addProperty("myinfo", true);
			} else {
				obj.addProperty("myinfo", false);
			}
		}

		int follower = followService.sumOfFollower(user_id);
		int followee = followService.sumOfFollowee(user_id);
		int postnum = postservice.sumOfPost(user_id);

		System.out.println("follower : " + follower);

		List<Post> userpost = postservice.selectPost(user_id);
		int healthnum = 0;
		for (int i = 0; i < userpost.size(); i++) {
			healthnum += healthservice.selectHealth(userpost.get(i).getPosts_id());
		}

		obj.addProperty("followernum", follower);
		obj.addProperty("followeenum", followee);
		obj.addProperty("postnum", userpost.size());
		obj.addProperty("healthnum", healthnum);

		return new ResponseEntity<String>(obj.toString(), HttpStatus.OK);
	}

	@ApiOperation(value = "파일을 입력한다. 그리고 DB입력 성공여부에 따라 파일 경로 또는 'fail' 문자열을 반환한다.")
	@PostMapping("upload")
	public String doFileUpload(@RequestParam("upload_file") MultipartFile uploadfile, HttpServletRequest request) {
		String accessToken = (String) request.getAttribute("accessToken");
		// System.out.println(name);
		int user_id = 1;
		if (accessToken != null) {
			Auth auth = authService.findAuthByAccessToken(accessToken);
			user_id = auth.getUser_id();
		}
		System.out.println(user_id);
		UserInfo userinfo = userInfoService.selectUserInfoByUserid(user_id);
		try {

			// 업로드되는 파일 이름 중간에 날짜줄거임
			SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
			Date nowdate = new Date();
			String dateString = formatter.format(nowdate);
			String access_path = "http://i3a301.p.ssafy.io/images/profile/";
			// 웹서비스 경로 지정
			// String root_path =
			// request.getSession().getServletContext().getRealPath("\\");

			String root_path = "C:\\Users\\multicampus\\Desktop\\s03p13a301\\backend\\src\\main\\resources\\profile\\";
			String real_path = "/home/ubuntu/s03p13a301/backend/src/main/resources/profile/";
			// String attach_path = "resources\\upload\\";

			String filename = user_id + ".jpg";
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
			
			if(userinfo.getUserimage()!=path) {
				userinfo.setUserimage(path);
				userInfoService.updateImage(userinfo);
			}
			
			return path;

		} catch (Exception ex) {
			throw new RuntimeException("file Save Error");
		}
	}

}
