package com.web.curation.service;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

@Service
public class JwtServiceImpl implements JwtService{
	
	//access token secret key
	public static final String AT_SECRET_KEY = "CREATEDBYTID_AT";
	//refresh token secret key
	private static final String RT_SECRET_KEY = "CREATEDBYTID_RT";
	private static final Logger LOGGER = LoggerFactory.getLogger(JwtServiceImpl.class);
	private static final String DATA_KEY = "user";
	
	public TokenSet createTokenSet(UserInfo userinfo) {
		long curTime = System.currentTimeMillis();
		
		TokenSet tokenSet = TokenSet.create().refreshToken(Jwts.builder()
								 .setHeaderParam("typ", "JWT")
								 .setExpiration(new Date(curTime + (1000*60*60*24*14)))
								 .setIssuedAt(new Date(curTime))
								 .claim(DATA_KEY, userinfo)
								 .signWith(SignatureAlgorithm.HS256, this.generateKey(RT_SECRET_KEY))
								 .compact());
		
		tokenSet = tokenSet.accessToken(Jwts.builder()
							 .setHeaderParam("typ", "JWT")
							 .setExpiration(new Date(curTime + (1000*60*1)))	//1분
							 .setIssuedAt(new Date(curTime))
							 .claim(DATA_KEY, userinfo)
							 .signWith(SignatureAlgorithm.HS256, this.generateKey(AT_SECRET_KEY))
							 .compact());
		//mongoOperations.insert(tokenSet, "refreshToken");

		return tokenSet;
	}
	
	public byte[] generateKey(String secretKey) {
		byte[] key = null;
		try {
			key = secretKey.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			LOGGER.error("Making secret Key Error :: ", e);
		}

		return key;
	}
	
	public TokenSet refreshAccessToken(String refreshToken) {
		long curTime = System.currentTimeMillis();
		//refreshToken의 만료기간이 남았는지 확인하고, 
		
		System.out.println("Refresh_token : " + refreshToken);
		if(!isValidToken(refreshToken, RT_SECRET_KEY)) {
			throw new AuthenticationException("로그인되어있지 않습니다.");
		}
		//DB로부터 refreshToken 유효한지 조회
		//Auth validToken = authService.findAuthByRefreshToken(refreshToken);
//		Query query = new Query();
//		query.addCriteria(Criteria.where("refreshToken").is(refreshToken));
//		List<TokenSet> validToken = mongoOperations.find(query, TokenSet.class, "refreshToken");
		/*if(validToken == null) {
			throw new AuthenticationException("로그인되어있지 않습니다.");
		}*/
		
		Jws<Claims> claims = null;
		try {
			claims = Jwts.parser().setSigningKey(this.generateKey(RT_SECRET_KEY)).parseClaimsJws(refreshToken);
		} catch (Exception e) {
			throw new JWTException("decodeing failed");
		}
		
		//curTime이 refreshToken의 만료일 7일 이내면, refreshTokenSet 진행. 
		if(Long.parseLong(String.valueOf(claims.getBody().get("exp"))) * 1000 - curTime <= (1000*60*60*24*7)) {
			return refreshTokenSet(refreshToken);
		}
		return TokenSet.create()
				  .accessToken(Jwts.builder()
									 .setHeaderParam("typ", "JWT")
									 .setExpiration(new Date(curTime + (1000*60*1)))
									 .setIssuedAt(new Date(curTime))
									 .claim(DATA_KEY, getUserInfo(refreshToken, RT_SECRET_KEY))
									 .signWith(SignatureAlgorithm.HS256, this.generateKey(AT_SECRET_KEY))
									 .compact());
	}
	
	public TokenSet refreshTokenSet(String refreshToken) {
		return createTokenSet(getUserInfo(refreshToken, RT_SECRET_KEY));
	}
	

	public UserInfo getUserInfo(String jwt, String secretKey) {
		Jws<Claims> claims = null;
		try {
			claims = Jwts.parser().setSigningKey(this.generateKey(secretKey)).parseClaimsJws(jwt);
		} catch (Exception e) {
			LOGGER.debug(e.getMessage(), e);
			throw new JWTException("decodeing failed");
		}
		return new ObjectMapper().convertValue(claims.getBody().get(DATA_KEY), UserInfo.class);
	}

	public boolean isValidToken(String jwt, String secretKey) {
		try {
			Jwts.parser().setSigningKey(this.generateKey(secretKey)).parseClaimsJws(jwt);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
		
}
