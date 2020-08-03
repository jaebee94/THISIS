package com.web.curation.controller;

import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.web.curation.model.UserInfo;
import com.web.curation.service.UserInfoService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = { "*" })
@RestController
@RequestMapping("/email")
public class EmailController {

	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private UserInfoService userInfoService;
	
	@ApiOperation(value = "비밀번호 이메일로 찾기", response = String.class)
	@GetMapping
	public ResponseEntity<String> sendEmailAction (@RequestParam String email) throws Exception {
        UserInfo userinfo = userInfoService.findUserInfoByEmail(email);
        String USERNAME = userinfo.getUsername();
        String EMAIL = email;
        String PASSWORD = userinfo.getPassword();
             
        try {
            MimeMessage msg = mailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(msg, true, "UTF-8");
             
            messageHelper.setSubject(USERNAME+"님 비밀번호 찾기 메일입니다.");
            messageHelper.setText("비밀번호는 "+PASSWORD+" 입니다.");
            messageHelper.setFrom("ru940203@naver.com");
            messageHelper.setTo(EMAIL);
            msg.setRecipients(MimeMessage.RecipientType.TO , InternetAddress.parse(EMAIL));
            mailSender.send(msg);
            
        }catch(MessagingException e) {
            System.out.println("MessagingException");
            e.printStackTrace();
            return new ResponseEntity<String>("fail", HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<String>("success", HttpStatus.OK); 
    }

}
