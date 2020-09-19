package com.web.curation.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
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

import com.web.curation.dao.TagDAO;
import com.web.curation.model.Auth;
import com.web.curation.model.Disease;
import com.web.curation.model.Post;
import com.web.curation.model.PostRequest;
import com.web.curation.model.PostResponse;
import com.web.curation.model.Tag;
import com.web.curation.model.TagRelation;
import com.web.curation.model.UserInfo;
import com.web.curation.service.AuthService;
import com.web.curation.service.CommentService;
import com.web.curation.service.DiseaseService;
import com.web.curation.service.HealthService;
import com.web.curation.service.PostService;
import com.web.curation.service.TagRelationService;
import com.web.curation.service.TagService;
import com.web.curation.service.UserInfoService;
import com.web.curation.service.UserInfoServiceImpl;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = { "*" })
@RestController
@RequestMapping("/articles")
public class PostController {

	@Autowired
	PostService postservice;

	@Autowired
	AuthService authService;

	@Autowired
	UserInfoService userinfoService;

	@Autowired
	CommentService commentsService;

	@Autowired
	HealthService healthService;

	@Autowired
	DiseaseService diseaseService;

	@Autowired
	TagService tagService;

	@Autowired
	TagRelationService tagRelationService;
	
	public void createDisease(PostRequest postRequest) {
		if (postRequest.post.getDiseasecode() != null && postRequest.diseasename != null) {
			if (diseaseService.selectDiseaseByDiseasecode(postRequest.post.getDiseasecode()) == null) {
				Disease disease = new Disease(postRequest.post.getDiseasecode(), postRequest.diseasename);
				diseaseService.createDisease(disease);
			}
		}
	}
	public void saveTags(List<String> tags,int posts_id) {
	
		System.out.println("posts_id :" + posts_id);
		for (int i = 0; i < tags.size(); i++) {
			// 태그 저장
			System.out.println(tags.get(i));
			if (tagService.selectCountByTagname(tags.get(i)) == 0) { // 없음
				tagService.createTag(tags.get(i));
			}
			Tag now = tagService.selectTagByTagname(tags.get(i));
			tagRelationService.createTagRelation(new TagRelation(now.getTagid(), posts_id));
		}
	}
	public List<PostResponse> reSaveResponse(List<Post> page) {
		List<PostResponse> response = new ArrayList<>();
		// 페이지 돌면서 response에 다시 저장
		for (int i = 0; i < page.size(); i++) {
			PostResponse temp = new PostResponse();
			temp.posts_id = page.get(i).getPosts_id();
			temp.post = page.get(i);
			try {
				temp.diseasename = diseaseService.selectDiseaseByDiseasecode(temp.post.getDiseasecode()).getDiseasename();
			}catch(NullPointerException e) {
				temp.diseasename = "";
			}

			int user_id = temp.post.getUser_id();
			temp.userinfo = userinfoService.selectUserInfoByUserid(user_id);
			temp.userinfo.setPassword(null);
			temp.comments = commentsService.selectComment(temp.posts_id);
			temp.healths = healthService.selectHealthList(temp.posts_id);
			temp.tags = tagService.selectTagByPostsId(temp.posts_id);
			response.add(temp);
		}
		return response;
	}

	// 반환 보내는 부분 함수로 통일
	public ResponseEntity<List<PostResponse>> sendResponse(List<Post> Allpage, int num) {

		List<PostResponse> response = new ArrayList<>();
		if (Allpage.size() / 10 > num && num * 10 + 10 <= Allpage.size()) {
			response = reSaveResponse(Allpage.subList(num * 10, num * 10 + 10));
			return new ResponseEntity<List<PostResponse>>(response, HttpStatus.OK);
		} else if (Allpage.size() / 10 < num) {
			return new ResponseEntity<List<PostResponse>>(response, HttpStatus.NO_CONTENT);
		} else {
			response = reSaveResponse(Allpage.subList(num * 10, Allpage.size()));
			return new ResponseEntity<List<PostResponse>>(response, HttpStatus.OK);
		}
	}

	@ApiOperation(value = "모든 게시판을 반환한다.", response = List.class)
	@GetMapping
	public ResponseEntity<List<PostResponse>> selectAllPost(@RequestParam int num) throws Exception {
		return sendResponse(postservice.selectAllPost(), num);
	}

	@ApiOperation(value = "유저에 해당하는 모든 게시판를 반환한다.", response = List.class)
	@GetMapping("{user_id}")
	public ResponseEntity<List<Post>> selectPost(@PathVariable int user_id) throws Exception {
		System.out.println("user_id :" + user_id);
		return new ResponseEntity<List<Post>>(postservice.selectPost(user_id), HttpStatus.OK);
	}

	@ApiOperation(value = "모든 qna 게시판을 반환한다.", response = List.class)
	@GetMapping("qna")
	public ResponseEntity<List<PostResponse>> selectQnA(@RequestParam int num) throws Exception {
		return sendResponse(postservice.selectQnA(), num);

	}

	@ApiOperation(value = "글 내용에 해당하는 qna 게시판을 반환한다.", response = List.class)
	@GetMapping("qna/main")
	public ResponseEntity<List<PostResponse>> selectQnAByMain(@RequestParam int num, @RequestParam String keyword)
			throws Exception {
		return sendResponse(postservice.selectQnAByMain(keyword), num);

	}

	@ApiOperation(value = "user_id 인자에 해당하는 게시판 목록를 반환한다.", response = List.class)
	@GetMapping("new")
	public ResponseEntity<List<PostResponse>> selectPostList(@RequestParam int num, @RequestParam int user_id,
			HttpServletRequest request) throws Exception {
		List<Post> Allpage = null;
		if (user_id >= 1) { // 다른 사람이 작성한 피드
			System.out.println("user_id :" + user_id);
			Allpage = postservice.selectPost(user_id);
		} else if (user_id == 0) { // 내가 작성한 피드
			// 자기 아이디 가져옴
			String accessToken = (String) request.getAttribute("accessToken");
			// System.out.println(name);
			if (accessToken != null) {
				Auth auth = authService.findAuthByAccessToken(accessToken);
				user_id = auth.getUser_id();
			} else {
				user_id = 1;
			}
			System.out.println("user_id :" + user_id);
			Allpage = postservice.selectPost(user_id);
		} else { // 나의 메인 피드
			// 자기 아이디 가져옴
			String accessToken = (String) request.getAttribute("accessToken");
			// System.out.println(name);
			if (accessToken != null) {
				Auth auth = authService.findAuthByAccessToken(accessToken);
				user_id = auth.getUser_id();
			} else {
				user_id = 1;
			}
			System.out.println("user_id :" + user_id);
			Allpage = postservice.selectMainFeed(user_id);
		}

		return sendResponse(Allpage, num);
	}

	@ApiOperation(value = "게시글 생성", response = String.class)
	@PostMapping
	public ResponseEntity<String> CreatePost(@RequestBody Post post, HttpServletRequest request) {

		// post 자신 아이디로 생성하게 수정
		String accessToken = (String) request.getAttribute("accessToken");
		// System.out.println(name);
		int user_id = 1;
		if (accessToken != null) {
			Auth auth = authService.findAuthByAccessToken(accessToken);
			user_id = auth.getUser_id();
		}
		post.setUser_id(user_id);
		System.out.println("user_id :" + user_id);
		if (postservice.createPost(post) == 1) {
			return new ResponseEntity<String>("success", HttpStatus.OK);
		}
		return new ResponseEntity<String>("fail", HttpStatus.NO_CONTENT);
	}

	@ApiOperation(value = "게시글 생성 : 태그포함", response = String.class)
	@PostMapping("new")
	public ResponseEntity<String> CreatePost(@RequestBody PostRequest postRequest, HttpServletRequest request) {

		// post 자신 아이디로 생성하게 수정
		String accessToken = (String) request.getAttribute("accessToken");
		// System.out.println(name);
		int user_id = 1;
		if (accessToken != null) {
			Auth auth = authService.findAuthByAccessToken(accessToken);
			user_id = auth.getUser_id();
		}
		postRequest.post.setUser_id(user_id);
		System.out.println("user_id :" + user_id);
		// 질병 태그가 DB에 존재하는지 확인 후 없으면 디비에 넣기!!
		createDisease(postRequest);

		if (postservice.createPost(postRequest.post) == 1) {
			// 태그 저장
			saveTags(postRequest.tags, postservice.selectAutoIncrement());
			return new ResponseEntity<String>("success", HttpStatus.OK);
		}
		return new ResponseEntity<String>("fail", HttpStatus.NO_CONTENT);
	}

	@ApiOperation(value = "게시글 수정", response = String.class)
	@PutMapping
	public ResponseEntity<String> updatePost(@RequestBody PostRequest postRequest) {
		System.out.println("게시글 수정 : " + postRequest.toString());
		createDisease(postRequest);
		if (postservice.modifyPost(postRequest.post) == 1) {
			System.out.println("게시글 수정 완료");
			saveTags(postRequest.tags,postRequest.post.getPosts_id());
			System.out.println("태그 저장 완료");
			return new ResponseEntity<String>("success", HttpStatus.OK);
		}
		return new ResponseEntity<String>("fail", HttpStatus.NO_CONTENT);
	}

	@ApiOperation(value = "게시글 삭제", response = String.class)
	@DeleteMapping("{posts_id}")
	public ResponseEntity<String> deleteUserInfo(@PathVariable int posts_id) {

		if (postservice.deletePost(posts_id) == 1) {
			return new ResponseEntity<String>("success", HttpStatus.OK);
		}
		return new ResponseEntity<String>("fail", HttpStatus.NO_CONTENT);
	}

	@ApiOperation(value = "포스트의 지난 파일 삭제 및 갱신")
	@DeleteMapping("upload")
	public ResponseEntity<String> deleteFile(@RequestParam("delete_file") String deletefile, HttpServletRequest request) {
		try {
			String root_path = "C:\\Users\\multicampus\\git\\s03p13a301\\backend\\src\\main\\resources\\upload\\";
			String real_path = "/home/ubuntu/s03p13a301/backend/src/main/resources/upload/";
			
			String deletefileName = deletefile.substring(40); //앞에 경로 부분 자르고 파일명만 access_path 바꿀시 다시 재정립 필요
			File checkFile = new File(real_path + deletefileName);
			if (checkFile.exists()) {
				System.out.println("deletefile : "+ real_path + deletefile);
				checkFile.delete();
				return new ResponseEntity<String>("success", HttpStatus.OK);
			}

		} catch (Exception ex) {
			throw new RuntimeException("file delete Error");
		}
		return new ResponseEntity<String>("fail", HttpStatus.NO_CONTENT);
	}
	@ApiOperation(value = "파일을 입력한다. 그리고 DB입력 성공여부에 따라 파일 경로 또는 'fail' 문자열을 반환한다.")
	@PostMapping("upload")
	public String doFileUpload(@RequestParam("upload_file") MultipartFile uploadfile, HttpServletRequest request) {
		try {
			// 업로드되는 파일 이름 중간에 날짜줄거임
			SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
			Date nowdate = new Date();
			String dateString = formatter.format(nowdate);

			// 웹서비스 경로 지정
			// String root_path =
			// request.getSession().getServletContext().getRealPath("\\");

			String root_path = "C:\\Users\\multicampus\\git\\s03p13a301\\backend\\src\\main\\resources\\upload\\";
			String real_path = "/home/ubuntu/s03p13a301/backend/src/main/resources/upload/";
			// String attach_path = "resources\\upload\\";
			String filename = dateString + "_" + uploadfile.getOriginalFilename();

			System.out.println(real_path + filename);

			FileOutputStream fos = new FileOutputStream(real_path + filename);
			// 파일 저장할 경로 + 파일명을 파라미터로 넣고 fileOutputStream 객체 생성하고
			InputStream is = uploadfile.getInputStream();
			// file로 부터 inputStream을 가져온다.

			int readCount = 0;
			byte[] buffer = new byte[2048];
			// 파일을 읽을 크기 만큼의 buffer를 생성하고
			// ( 보통 1024, 2048, 4096, 8192 와 같이 배수 형식으로 버퍼의 크기를 잡는 것이 일반적이다.)

			while ((readCount = is.read(buffer)) != -1) {
				// 파일에서 가져온 fileInputStream을 설정한 크기 (1024byte) 만큼 읽고
				fos.write(buffer, 0, readCount);
				// 위에서 생성한 fileOutputStream 객체에 출력하기를 반복한다
			}

			String access_path = "http://i3a301.p.ssafy.io/images/upload/";
			return access_path + filename;

		} catch (Exception ex) {
			throw new RuntimeException("file Save Error");
		}
	}
	
	@ApiOperation(value = "글제목에 해당하는 qna 게시판을 반환한다.", response = List.class)
	@GetMapping("qna/title")
	public ResponseEntity<List<PostResponse>> selectQnAByTitle(@RequestParam int num, @RequestParam String keyword)
			throws Exception {
		return sendResponse(postservice.selectQnAByTitle(keyword), num);
	}
	
	@ApiOperation(value = "질병태그에 해당하는 qna 게시판을 반환한다.", response = List.class)
	@GetMapping("qna/disease")
	public ResponseEntity<List<PostResponse>> selectQnAByDisease(@RequestParam int num, @RequestParam String keyword)
			throws Exception {
		return sendResponse(postservice.selectQnAByDisease(keyword), num);
	}
	
}
