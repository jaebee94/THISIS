package com.web.curation.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.curation.dao.UserInfoDAO;
import com.web.curation.model.UserInfo;
import com.web.curation.model.UserPolice;

@Service
public class UserInfoServiceImpl implements UserInfoService {

	@Autowired
	private UserInfoDAO userInfoDao;

	@Override
	public List<UserInfo> selectUserInfo() {
		return userInfoDao.selectUserInfo();
	}

	@Override
	public UserInfo selectUserInfoByUserid(int user_id) {
		return userInfoDao.selectUserInfoByUserid(user_id);
	}

	@Override
	public int insertUserInfo(UserInfo userinfo) {
		return userInfoDao.insertUserInfo(userinfo);
	}

	@Override
	public int updateUserInfo(UserInfo userinfo) {
		return userInfoDao.updateUserInfo(userinfo);
	}

	@Override
	public int deleteUserInfo(int user_id) {
		return userInfoDao.deleteUserInfo(user_id);
	}

	@Override
	public UserInfo findUserInfoByEmail(String email) {
		return userInfoDao.findUserInfoByEmail(email);
	}

	@Override
	public UserInfo findUserByEmailAndPassword(String email, String password) {
		return userInfoDao.findUserByEmailAndPassword(email, password);
	}

	@Override
	public UserInfo findUserInfoByNickname(String nickname) {
		return userInfoDao.findUserInfoByNickname(nickname);
	}

	@Override
	public List<UserInfo> selectUserInfoSearch(String username) {
		return userInfoDao.selectUserInfoSearch(username);
	}

	@Override
	public int getUserId(String email) {
		return userInfoDao.getUserId(email);
	}

	@Override
	public String findNicknameById(int user_id) {
		return userInfoDao.findNicknameById(user_id);
	}

	@Override
	public int updateImage(UserInfo userinfo) {
		return userInfoDao.updateImage(userinfo);
	}

	@Override
	public int updatePassword(UserInfo userinfo) {
		return userInfoDao.updatePassword(userinfo);
	}

	@Override
	public int updateDisable(int user_id) {
		return userInfoDao.updateDisable(user_id);
	}

	@Override
	public int updateRole(int user_id) {
		return userInfoDao.updateRole(user_id);
	}

	@Override
	public String selectSaltByUserId(int user_id) {
		return userInfoDao.selectSaltByUserId(user_id);
	}
	
	public List<UserPolice> selectUserInfoPolice() {
		return userInfoDao.selectUserInfoPolice();
	}

	@Override
	public int updateable(int user_id) {
		return userInfoDao.updateable(user_id);
	}

	@Override
	public int deleteRole(int user_id) {
		return userInfoDao.deleteRole(user_id);
		
	}

}
