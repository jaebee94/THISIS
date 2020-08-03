package com.web.curation.controller.account;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.web.curation.dao.user.UserDao;
import com.web.curation.model.BasicResponse;
import com.web.curation.model.user.SignupRequest;
import com.web.curation.model.user.User;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.beans.factory.annotation.Autowired;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized", response = BasicResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = BasicResponse.class),
        @ApiResponse(code = 404, message = "Not Found", response = BasicResponse.class),
        @ApiResponse(code = 500, message = "Failure", response = BasicResponse.class) })

@CrossOrigin(origins = { "http://localhost:3000" })
@RestController
public class AccountController {

    @Autowired
    UserDao userDao;
    
    /* TEST (WORKS FINE) */
    @GetMapping("/account")
    @ApiOperation(value="회원전체정보")
    public Object getUsers() {
    	List<User> users = userDao.findAll();
    	ResponseEntity response = null;
		if(users.isEmpty()) {
			response = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		} else {
			response = new ResponseEntity<>(users, HttpStatus.OK);
		}
		System.out.println(users.toString());
		return response;
    }

    @GetMapping("/account/login")
    @ApiOperation(value = "로그인")
    public Object login(@RequestParam(required = true) final String email,
            @RequestParam(required = true) final String password) {
    	
    	Optional<User> userOpt2 = userDao.findUserByEmail(email);
        ResponseEntity response = null;

        if (userOpt2.isPresent()) {	//이메일 존재
        	 Optional<User> userOpt = userDao.findUserByEmailAndPassword(email, password);
        	
        	 if(userOpt.isPresent()) {	//비밀번호까지 다 맞음
        		 final BasicResponse result = new BasicResponse();
                 result.status = true;
                 result.data = "success";
                 response = new ResponseEntity<>(result, HttpStatus.OK);
        	 }else {	//비밀번호가 맞지 않음
        		 final BasicResponse result = new BasicResponse();
                 result.status = false;
                 result.data = "wrong password";
                 response = new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        	 }   
        } else {	//이메일 존재 x
        	 final BasicResponse result = new BasicResponse();
             result.status = false;
             result.data = "wrong email";
            response = new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }

        return response;

    }

    /*
    	@RequestParam("email") String email,
    	@RequestParam("password") String password,
    	@RequestParam("nickName") String nickName
    */
    
    @PostMapping("/account/signup")
    @ApiOperation(value = "가입하기")
    public Object signup(@Valid @RequestBody SignupRequest request) {
        // 이메일, 닉네임 중복처리 필수
        // 회원가입단을 생성해 보세요.
    	String email = request.getEmail();
    	String nickName = request.getNickname();
    	String password = request.getPassword();
    	/* 값 테스트 */
    	System.out.println(email + nickName + password);
    	
    	
    	/* ============이메일 중복 처리 시작============= */
    	User userTest = null;
    	userTest = userDao.getUserByEmail(email);
    	final BasicResponse result = new BasicResponse();
    	if(userTest != null) {
    		result.status = false;
    		result.data = "emailFail";
    		return new ResponseEntity<>(result, HttpStatus.CONFLICT);
    	}
    	
    	userTest = null;
    	userTest = userDao.getUserByUid(nickName);
    	if(userTest != null) {
    		result.status = false;
    		result.data = "nickFail";
    		return new ResponseEntity<>(result, HttpStatus.CONFLICT);
    	}
    	/* ============이메일 중복 처리 끝============ */
    	
    	
    	User user = new User();
    	user.setUid(nickName);
    	user.setEmail(email);
    	user.setPassword(password);
    	
    	userDao.saveAndFlush(user);
    	
        result.status = true;
        result.data = "success";
    	return new ResponseEntity<>(result, HttpStatus.OK);
        
    }
    
    @GetMapping("/account/change")
    @ApiOperation(value = "비밀번호 변경하기")
    public Object chanegePassword(@RequestParam(required = true) final String email,
          @RequestParam(required = true) final String password,
          @RequestParam(required = true) final String newPassword) {

        ResponseEntity response = null;
        final BasicResponse result = new BasicResponse();
        
        Optional<User> userOpt = userDao.findUserByEmail(email);
        if(userOpt.isPresent()){
           //동일 이메일이 존재하면 바꿀수 잇음
           User user = userOpt.get();
           if(user.getPassword().equals(password)) {
              //비밀번호 일치
              user.setPassword(newPassword);
              userDao.save(user);
                result.status = true;
                result.data = "success";
                response = new ResponseEntity<>(result, HttpStatus.OK);
           }
           else {
              //불일치
              result.status = false;
              result.data = "wrong password";
              response = new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
           }
        }
        else {   //이메일 존재 x
          result.status = false;
          result.data = "wrong email";
           response = new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
        
        return response;
    }

}

