package com.web.curation.service;

import java.util.List;

import com.web.curation.model.UserInfo;
import com.web.curation.model.UserPolice;

public interface UserInfoService {
	public List<UserInfo> selectUserInfo();
	public List<UserPolice> selectUserInfoPolice();
	public UserInfo selectUserInfoByUserid(int user_id);
	public int insertUserInfo(UserInfo userinfo);
	public int updateUserInfo(UserInfo userinfo);
	public int deleteUserInfo(int user_id);
	public UserInfo findUserInfoByEmail(String email);
	public UserInfo findUserByEmailAndPassword(String email, String password);
	public UserInfo findUserInfoByNickname(String nickname);
	public List<UserInfo> selectUserInfoSearch(String username);
	public String findNicknameById(int user_id);
	public int getUserId(String email);
	public int updateImage(UserInfo userinfo);
	public int updatePassword(UserInfo userinfo);
	public int updateDisable(int user_id);
	public int updateRole(int user_id);
}
