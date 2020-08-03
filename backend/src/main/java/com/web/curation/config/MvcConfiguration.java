package com.web.curation.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.MappedInterceptor;

import com.web.curation.config.AuthInterceptor;

@Configuration
public class MvcConfiguration extends WebMvcConfigurerAdapter{

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(authInterceptor())
		.addPathPatterns("/**")
		.excludePathPatterns("/account/login","/account/signup",
				"/swagger-ui.html", "/v2/api-docs",  "/swagger-resources/**",  "/webjars/**");
		//위에 제외할 로그인 api 경로 넣으삼
	}
	
	public AuthInterceptor authInterceptor() {
	        return new AuthInterceptor();
	}
}	