package com.web.curation.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.web.curation.model.Auth;

@Mapper
public interface AuthDAO {
	public Auth selectAuthByUserid(int user_id);
	public int insertAuth(Auth auth);
	public int updateAuth(Auth auth);
	public int deleteAuth(String user_id);
	public Auth findAuthByRefreshToken(String refresh_token);
	public Auth findAuthByAccessToken(String access_token);
}
