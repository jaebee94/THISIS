package com.web.curation.controller;

import java.util.ArrayList;
import java.util.List;

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
import org.springframework.web.bind.annotation.RestController;

import com.web.curation.model.Notification;
import com.web.curation.model.NotificationResponse;
import com.web.curation.service.NotificationService;
import com.web.curation.service.UserInfoService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = { "*" })
@RestController
@RequestMapping("/notification")
//http://localhost:9999/THISIS/swagger-ui.html
public class NotificationController {
	
	private static final Logger logger = LoggerFactory.getLogger(UserInfoController.class); 
	private static final String SUCCESS = "success"; 
	private static final String FAIL = "fail"; 
	
	@Autowired
	private NotificationService notificationService;
	@Autowired
	private UserInfoService userInfoService;
	
	@ApiOperation(value = "모든 알람/요청을 반환한다.", response = List.class)
	@GetMapping
		public ResponseEntity<List<Notification>> selectNotification() throws Exception {
			return new ResponseEntity<List<Notification>>(notificationService.selectNotification(), HttpStatus.OK);
	}
	
	@ApiOperation(value = "팔로우를 요청했을 때 레코드를 저장한다.", response = String.class)
	@PostMapping()
		public ResponseEntity<String> insertNotification(@RequestBody Notification noti) throws Exception {
//			noti.setFollowee_id(followee_id);
//			noti.setFollower_id(follower_id);
//			noti.setApproval(0);//0이면 아직 안함, 1이면 승낙, 2는 거절
			if(notificationService.insertNotification(noti) == 1) {
				return new ResponseEntity<String>("success",HttpStatus.OK);
			}
			return new ResponseEntity<String>("fail",HttpStatus.NO_CONTENT);
	}
	
	@ApiOperation(value = "팔로우를 요청 취소했을 때 레코드를 삭제한다.", response = String.class)
	@DeleteMapping()
		public ResponseEntity<String> deleteNotification(@RequestBody Notification noti) throws Exception {
			System.out.println("11"); 
			System.out.println(noti.toString());
		
			if(notificationService.deleteNotification(String.valueOf(noti.getFollower_id()), String.valueOf(noti.getFollowee_id())) == 1) {
				return new ResponseEntity<String>("success",HttpStatus.OK);
			}
			return new ResponseEntity<String>("fail",HttpStatus.NO_CONTENT);
	}
	
	@ApiOperation(value = "해당 id에 해당하는 알람/요청 정보를 수정한다.", response = String.class)
	@PutMapping()
	public ResponseEntity<String> updateUserInfo(@RequestBody Notification notification) { 
			System.out.println(notification.getApproval()+" "+ notification.getFollower_id()+" "+ notification.getFollowee_id());
			if(notificationService.updateNotification(notification) == 1) {
				return new ResponseEntity<String>("success",HttpStatus.OK);
			}
			return new ResponseEntity<String>("fail",HttpStatus.NO_CONTENT);
	}
	
	@ApiOperation(value = "해당 user_id가 follower인 레코드 목록 조회.", response = List.class)
	@GetMapping("follower/{user_id}")
		public ResponseEntity<List<NotificationResponse>> GetListByFollower(@PathVariable int user_id) { 
			List<Notification> list = notificationService.GetListByFollower(user_id);
			List<NotificationResponse> notificationResponses = new ArrayList<NotificationResponse>();
			for(Notification noti : list) {
				NotificationResponse notificationResponse = new NotificationResponse();
				notificationResponse.setNotification(noti);
				notificationResponse.setUserInfo(userInfoService.selectUserInfoByUserid(noti.getFollowee_id()));
				notificationResponses.add(notificationResponse);
			}
			
			return new ResponseEntity<List<NotificationResponse>>(notificationResponses, HttpStatus.OK);
			
	}
	
	@ApiOperation(value = "해당 user_id가 followee인 레코드 목록 조회.", response = List.class)
	@GetMapping("/followee/{user_id}")
		public ResponseEntity<List<NotificationResponse>> GetListByFollowee(@PathVariable int user_id) { 
		List<Notification> list = notificationService.GetListByFollowee(user_id);
		List<NotificationResponse> notificationResponses = new ArrayList<NotificationResponse>();
		for(Notification noti : list) {
			NotificationResponse notificationResponse = new NotificationResponse();
			notificationResponse.setNotification(noti);
			notificationResponse.setUserInfo(userInfoService.selectUserInfoByUserid(noti.getFollower_id()));
			notificationResponses.add(notificationResponse);
		}
		return new ResponseEntity<List<NotificationResponse>>(notificationResponses, HttpStatus.OK);
	}
	

	
	

}
