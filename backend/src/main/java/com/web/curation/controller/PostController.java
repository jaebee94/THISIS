package com.web.curation.controller;

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

import com.web.curation.model.Auth;
import com.web.curation.model.Post;
import com.web.curation.model.PostRequest;
import com.web.curation.model.PostResponse;
import com.web.curation.model.Tag;
import com.web.curation.model.UserInfo;
import com.web.curation.service.AuthService;
import com.web.curation.service.CommentService;
import com.web.curation.service.HealthService;
import com.web.curation.service.PostService;
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
	
	@ApiOperation(value = "모든 게시판을 반환한다.", response = List.class)
	@GetMapping
	public ResponseEntity<List<PostResponse>> selectAllPost(@RequestParam int num) throws Exception {
		List<Post> Allpage = postservice.selectAllPost();
		List<Post> page = null;
		List<PostResponse> response = new ArrayList<>();
		if (Allpage.size() / 10 > num && num * 10 + 10 <= Allpage.size()) {
			page = Allpage.subList(num * 10, num * 10 + 10);
			
			//페이지 돌면서 response에 다시 저장
			for(int i=0; i<page.size(); i++) {
				PostResponse temp = new PostResponse();
				temp.posts_id = page.get(i).getPosts_id();
				temp.post = page.get(i);
				int user_id = temp.post.getUser_id();
				temp.userinfo = userinfoService.selectUserInfoByUserid(user_id);
				temp.userinfo.setPassword(null);
				temp.comments = commentsService.selectComment(temp.posts_id);
				temp.healths = healthService.selectHealthList(temp.posts_id);
				response.add(temp);
			}
			return new ResponseEntity<List<PostResponse>>(response, HttpStatus.OK);
		} else if (Allpage.size() / 10 < num) {
			return new ResponseEntity<List<PostResponse>>(response, HttpStatus.NO_CONTENT);
		}
		else {
			page = Allpage.subList(num*10, Allpage.size());
			for(int i=0; i<page.size(); i++) {
				PostResponse temp = new PostResponse();
				temp.posts_id = page.get(i).getPosts_id();
				temp.post = page.get(i);
				int user_id = temp.post.getUser_id();
				temp.userinfo = userinfoService.selectUserInfoByUserid(user_id);
				temp.userinfo.setPassword(null);
				temp.comments = commentsService.selectComment(temp.posts_id);
				temp.healths = healthService.selectHealthList(temp.posts_id);
				response.add(temp);
			}
			return new ResponseEntity<List<PostResponse>>(response, HttpStatus.OK);
		}

	}

	@ApiOperation(value = "유저에 해당하는 모든 게시판를 반환한다.", response = List.class)
	@GetMapping("{user_id}")
	public ResponseEntity<List<Post>> selectPost(@PathVariable int user_id) throws Exception {
		return new ResponseEntity<List<Post>>(postservice.selectPostInfo(user_id), HttpStatus.OK);
	}

	@ApiOperation(value = "게시글 생성", response = String.class)
	@PostMapping
	public ResponseEntity<String> CreatePost(@RequestBody Post post, HttpServletRequest request) {

		// post 자신 아이디로 생성하게 수정
		String accessToken = (String) request.getAttribute("accessToken");
		//System.out.println(name);
		int user_id = 1;
		if (accessToken != null) {
			Auth auth = authService.findAuthByAccessToken(accessToken);
			user_id = auth.getUser_id();
		}

		post.setUser_id(user_id);
		UserInfo userInfo = userinfoService.selectUserInfoByUserid(user_id);
		//post.setNickname(userInfo.getNickname());

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
		//System.out.println(name);
		int user_id = 1;
		if (accessToken != null) {
			Auth auth = authService.findAuthByAccessToken(accessToken);
			user_id = auth.getUser_id();
		}

		postRequest.post.setUser_id(user_id);
		
		if (postservice.createPost(postRequest.post) == 1) {
			//태그 저장
			List<String> tags = postRequest.tags;
			
			for(int i=0; i<tags.size(); i++) {
				//태그 저장
				System.out.println(tags.get(i));
				
			}
			return new ResponseEntity<String>("success", HttpStatus.OK);
		}
		return new ResponseEntity<String>("fail", HttpStatus.NO_CONTENT);
	}
	
	@ApiOperation(value = "게시글 수정", response = String.class)
	@PutMapping("{posts_id}")
	public ResponseEntity<String> updatePost(@RequestBody Post post) {
		if (postservice.modifyPost(post) == 1) {
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
	
	 @ApiOperation(value = "파일을 입력한다. 그리고 DB입력 성공여부에 따라 파일 경로 또는 'fail' 문자열을 반환한다.")
	  @PostMapping("upload")
		public String doFileUpload(@RequestParam("upload_file") MultipartFile uploadfile, HttpServletRequest request) {	
			try {
				//업로드되는 파일 이름 중간에 날짜줄거임
				SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
				Date nowdate = new Date();
				String dateString = formatter.format(nowdate);
				
		  		//웹서비스 경로 지정
				//String root_path = request.getSession().getServletContext().getRealPath("\\");
				String root_path = "C:\\Users\\multicampus\\git\\s03p13a301\\backend\\src\\main\\resources\\upload\\";
				//String attach_path = "resources\\upload\\";
				String filename = dateString+"_"+uploadfile.getOriginalFilename();
				
				System.out.println(root_path+filename);
				
	            FileOutputStream fos = new FileOutputStream(root_path+filename);
	            // 파일 저장할 경로 + 파일명을 파라미터로 넣고 fileOutputStream 객체 생성하고
	            InputStream is = uploadfile.getInputStream();
	            // file로 부터 inputStream을 가져온다.
		  		
		  		int readCount = 0;
		        byte[] buffer = new byte[2048];
		        // 파일을 읽을 크기 만큼의 buffer를 생성하고
		        // ( 보통 1024, 2048, 4096, 8192 와 같이 배수 형식으로 버퍼의 크기를 잡는 것이 일반적이다.)
		            
		        while ((readCount = is.read(buffer)) != -1) {
		          //  파일에서 가져온 fileInputStream을 설정한 크기 (1024byte) 만큼 읽고    
		          fos.write(buffer, 0, readCount);
		          // 위에서 생성한 fileOutputStream 객체에 출력하기를 반복한다
		        }

				return root_path+filename;
				
		    }catch (Exception ex) {
		         throw new RuntimeException("file Save Error");
		    }
		}

	
}
