package com.web.curation.config;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.web.curation.model.Auth;
import com.web.curation.model.TokenSet;
import com.web.curation.service.AuthService;
import com.web.curation.service.JwtService;
import com.web.curation.service.UserInfoService;

//인증을 위한 인터셉터
@Component
public class AuthInterceptor extends HandlerInterceptorAdapter{
	
	@Resource
	private UserInfoService userInfoService;
	
	@Resource
	private JwtService jwtService;
	
	
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// 컨트롤러가 실행되기 전
		System.out.println("==========FILTER BEGIN=============");
		logger.debug("==========FILTER BEGIN=============");
		
		//if(handler instanceof HandlerMethod) {
			//HandlerMethod hm = (HandlerMethod) handler;

			//헤더로부터 토큰을 읽음
			String accessToken = request.getHeader("accessToken");
			System.out.println("accessToken : " + accessToken);
			final String refreshToken = request.getHeader("refreshToken");
			System.out.println("refreshToken : " + refreshToken);
			
			if(refreshToken != null) {	//ACCESS_TOKEN이 없어서 REFRESH TOKEN을 보낸것임
				TokenSet tokenSet = jwtService.refreshAccessToken(refreshToken);
				accessToken = tokenSet.getAccessToken();
				response.addHeader("accessToken", accessToken);
				//response.addHeader("refreshToken", tokenSet.getRefreshToken());
			}

			/*if(jwtService.isValidToken(accessToken, JwtService.AT_SECRET_KEY)){
				System.out.println("확인");
			}*/
			//if (hm.hasMethodAnnotation(LoginRequired.class) && (accessToken == null || !jwtService.isValidToken(accessToken, JwtService.AT_SECRET_KEY)))
			if (accessToken == null /*|| !jwtService.isValidToken(accessToken, JwtService.AT_SECRET_KEY)*/) {
				//throw new AuthenticationException("로그인되어있지 않습니다.");
				//System.out.println("액세스 토큰 x");
				accessToken =null;
			}
			/*else if(!jwtService.isValidToken(accessToken, JwtService.AT_SECRET_KEY)){
				Auth auth = authService.findAuthByAccessToken(accessToken);
				System.out.println("만료");
				response.addHeader("refreshToken", auth.getRefresh_token());
				//throw new AuthenticationException("액세스 토큰이 유효하지 않습니다");
				//accessToken = null;
			}*/
			else {
				//accessToken이 확인됨
				//토큰으로 유저정보 가져오기
//				System.out.println("access_token22 : " + accessToken);
//				Auth auth = authService.findAuthByAccessToken(accessToken);
//				UserInfo userinfo = userInfoService.selectUserInfoByUserid(auth.getUser_id());
//				if(auth != null) {
//					//있다면 user정보를 request attribute로 넘겨줌
//					request.setAttribute("userinfo", userinfo);
//				}
				
			}
			request.setAttribute("accessToken", accessToken);
			//if (hm.hasMethodAnnotation(AdminOnly.class) && !jwtService.getUser(accessToken, JwtService.AT_SECRET_KEY).getAuthority().contains(ADMIN))
			//관리자 권한만 있을때 필요할 시  위 쿼리를 쓸수도 있고 아니면 프론트에서 버튼 노출을 다르게 할 수 있음
		//}	
		return super.preHandle(request, response, handler);
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// 컨트롤러 실행 후
		logger.debug("==========FILTER END=============");
		super.postHandle(request, response, handler, modelAndView);
	}
	
	
	
}
