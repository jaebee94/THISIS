package com.web.curation.config;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.web.curation.model.Auth;
import com.web.curation.model.TokenSet;
import com.web.curation.model.UserInfo;
import com.web.curation.service.AuthService;
import com.web.curation.service.JwtService;
import com.web.curation.service.UserInfoService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtTokenProvider {
	
	@Autowired
	private AuthService authService;
	
	@Autowired
	private UserInfoService userinfoService;
	
	//access token secret key
	public static final String AT_SECRET_KEY = "CREATEDBYTID_AT";
	//refresh token secret key
	private static final String RT_SECRET_KEY = "CREATEDBYTID_RT";
	private static final Logger LOGGER = LoggerFactory.getLogger(JwtService.class);
	private static final String DATA_KEY = "user";
	
	public TokenSet createTokenSet(int user_id) {
		long curTime = System.currentTimeMillis();
		
		UserInfo userinfo = userinfoService.selectUserInfoByUserid(user_id);
		
		TokenSet tokenSet = TokenSet.create().refreshToken(Jwts.builder()
								 .setHeaderParam("typ", "JWT")
								 .setExpiration(new Date(curTime + (1000*60*60*24*14)))
								 .setIssuedAt(new Date(curTime))
								 .claim(DATA_KEY, userinfo)
								 .signWith(SignatureAlgorithm.HS256, this.generateKey(RT_SECRET_KEY))
								 .compact());
		
		tokenSet = tokenSet.accessToken(Jwts.builder()
							 .setHeaderParam("typ", "JWT")
							 .setExpiration(new Date(curTime + (1000*60*30)))
							 .setIssuedAt(new Date(curTime))
							 .claim(DATA_KEY, userinfo)
							 .signWith(SignatureAlgorithm.HS256, this.generateKey(AT_SECRET_KEY))
							 .compact());
		//mongoOperations.insert(tokenSet, "refreshToken");
		if(authService.selectAuthByUserid(user_id) != null) {
			authService.updateAuth(new Auth(user_id, tokenSet.getRefreshToken(),tokenSet.getAccessToken()));
		}else
			authService.insertAuth(new Auth(user_id, tokenSet.getRefreshToken(),tokenSet.getAccessToken()));
		
		return tokenSet;
	}
	
	private byte[] generateKey(String secretKey) {
		byte[] key = null;
		try {
			key = secretKey.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			LOGGER.error("Making secret Key Error :: ", e);
		}

		return key;
	}

}
