package com.web.curation.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.web.curation.model.UserInfo;
import com.web.curation.model.UserPolice;

@Mapper
public interface UserInfoDAO {
	public List<UserInfo> selectUserInfo();
	public List<UserPolice> selectUserInfoPolice();
	public UserInfo selectUserInfoByUserid(int user_id);
	public int insertUserInfo(UserInfo userinfo);
	public int updateUserInfo(UserInfo userinfo);
	public int deleteUserInfo(int user_id);
	public UserInfo findUserInfoByEmail(String email);
	public UserInfo findUserByEmailAndPassword(String email, String password);
	public UserInfo findUserInfoByNickname(String nickname);
	public List<UserInfo> selectUserInfoSearch(String keyword);
	public String findNicknameById(int user_id);
	public int getUserId(String email);
	public int updateImage(UserInfo userinfo);
	public int updatePassword(UserInfo userinfo);
	public int updateDisable(int user_id);
	public int updateRole(int user_id);
	public String selectSaltByUserId(int user_id);
	public int updateable(int user_id);
	public int deleteRole(int user_id);
}
