package com.web.curation.service;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.web.curation.exception.AuthenticationException;
import com.web.curation.exception.JWTException;
import com.web.curation.model.Auth;
import com.web.curation.model.TokenSet;
import com.web.curation.model.UserInfo;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public interface JwtService {
	
	//access token secret key
	public static final String AT_SECRET_KEY = "CREATEDBYTID_AT";
		//refresh token secret key
	public static final String RT_SECRET_KEY = "CREATEDBYTID_RT";
	public static final Logger LOGGER = LoggerFactory.getLogger(JwtService.class);
	public static final String DATA_KEY = "user";
		
	public TokenSet createTokenSet(UserInfo userinfo);
	public byte[] generateKey(String secretKey);
	public TokenSet refreshAccessToken(String refreshToken);
	public TokenSet refreshTokenSet(String refreshToken);
	public UserInfo getUserInfo(String jwt, String secretKey);
	public boolean isValidToken(String jwt, String secretKey);
	
}
